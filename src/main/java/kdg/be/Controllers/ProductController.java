package kdg.be.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import kdg.be.Models.ProductState;
import kdg.be.RabbitMQ.RabbitSender;
import kdg.be.Services.Interfaces.IIngredientService;
import kdg.be.Services.Interfaces.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final IIngredientService ingredientService;
    private final IProductService productService;
    private final RabbitSender rabbitSender;

    @Autowired
    @Qualifier("ProductValidator")
    private Validator validator;
    private String returnUrl;

    public ProductController(IIngredientService ingredientService, IProductService productService, RabbitSender rabbitSender) {
        this.ingredientService = ingredientService;
        this.productService = productService;
        this.rabbitSender = rabbitSender;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/products";
    }


    @GetMapping(value = {"/products"})
    public ModelAndView allProducts(Model model, @ModelAttribute("error") String flashAttribute) {
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("Producten", allProducts);
        model.addAttribute("error", flashAttribute);
        return new ModelAndView("Products/ProductenOverzicht");
    }

    @GetMapping(value = {"/products/create", "/products/create/{productId}"})
    public String newProduct(Model model, @PathVariable(required = false) Integer productId, HttpServletRequest request) {
        String ref = request.getHeader("referer");
        System.out.println(ref);
        if (ref != null && ref.contains("batch") || ref != null && ref.contains("product")) {
            this.returnUrl = ref;
        }
        Product product = null;
        List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        if (productId == null) {
            product = new Product();
            product.getSteps().add("");
        } else {
            Optional<Product> optionalProduct = productService.getProductById(productId);
            if (optionalProduct.isPresent()) {
                product = optionalProduct.get();
                product.setProductId(Long.valueOf(productId));
            } else {
                product = new Product("", new ArrayList<String>(), new ArrayList<>(), new ArrayList<>());
            }
        }
        model.addAttribute("product", product);
        model.addAttribute("alleIngredienten", allIngredients);
        return "Products/newProduct";
    }


    @PostMapping(value = "/products/create")
    public ModelAndView saveProduct(Model model, @Valid Product product, BindingResult bindingResult) {

        List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        model.addAttribute("alleIngredienten", allIngredients);
        if (bindingResult.hasErrors()) {
            List<String> error = new ArrayList<>();
            bindingResult.getAllErrors().forEach(err -> error.add(err.getDefaultMessage()));
            model.addAttribute("validationError", error);
            return new ModelAndView("Products/newProduct", (Map<String, ?>) model);
        }
        if (product.getComposition().size() != product.getComposition().stream().distinct().toList().size()) {
            product.getComposition().remove(product.getComposition().size() - 1);
            product.getAmounts().remove(product.getAmounts().size() - 1);
        }
        productService.saveOrUpdate(product);
        if (this.returnUrl.contains("batch")) {
            return new ModelAndView("redirect:/batch");
        }
        return new ModelAndView("redirect:/products", (Map<String, ?>) model);
    }

    @PostMapping(value = "/products/create", params = "stap")
    public ModelAndView addStep(Model model, Product product) {
        product.getSteps().add("");
        model.addAttribute("product", product);
        List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        model.addAttribute("alleIngredienten", allIngredients);

        return new ModelAndView("Products/newProduct", (Map<String, ?>) model);
    }

    @PostMapping(value = "/products/create", params = "addIng")
    public ModelAndView addIng(Model model, Product product) {
        List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        if (product.getComposition().stream().distinct().toList().size() != product.getComposition().size()) {
            model.addAttribute("error", "this ingredient is already part of the recepy");
        } else if
        (!allIngredients.isEmpty()) {
            product.getComposition().add(allIngredients.get(0));
            product.getAmounts().add(0.0);
        }
        model.addAttribute("product", product);
        model.addAttribute("alleIngredienten", allIngredients);
        return new ModelAndView("Products/newProduct", (Map<String, ?>) model);
    }

    @PostMapping(value = "/products/create", params = "removeIng")
    public ModelAndView removeIng(Model model, Product product, @RequestParam(name = "removeIng") int removeIng) {
        product.getAmounts().remove(removeIng);
        product.getComposition().remove(removeIng);
        model.addAttribute("product", product);
        List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        model.addAttribute("alleIngredienten", allIngredients);
        return new ModelAndView("Products/newProduct", (Map<String, ?>) model);
    }

    @PostMapping(value = "/products/create", params = "removeStep")
    public ModelAndView removeStep(Model model, Product product, @RequestParam(name = "removeStep") int removeStep) {
        product.getSteps().remove(removeStep);
        model.addAttribute("product", product);
        List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        model.addAttribute("alleIngredienten", allIngredients);
        return new ModelAndView("Products/newProduct", (Map<String, ?>) model);
    }


    @GetMapping("/products/final/{productId}")
    public RedirectView makeFinal(@PathVariable int productId, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        Optional<Product> optionalProduct = productService.getProductById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.set_ProductStatus(ProductState.FINAL);
            rabbitSender.sendNewRecipe(product);
            productService.saveOrUpdate(product);
        } else {
            redirectAttributes.addFlashAttribute("error", "The requested product could not been found");
        }
        return new RedirectView("/products");
    }

    @GetMapping("/products/deactivate/{productId}")
    public String deactivate(@PathVariable int productId) throws JsonProcessingException {
        Optional<Product> optionalProduct = productService.getProductById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.set_ProductStatus(ProductState.DEACTIVATED);
            rabbitSender.sendNewRecipe(product);
            productService.saveOrUpdate(product);
        }
        return "redirect:/products";
    }


    @GetMapping("/products/detail/{productId}")
    public ModelAndView detailPage(@PathVariable Long productId, ModelMap model, RedirectAttributes redirectAttributes) {
        Optional<Product> optionalProduct = productService.getProductById(productId);
        if (optionalProduct.isPresent()) {
            model.addAttribute("product", optionalProduct.get());
            return new ModelAndView("Products/productDetail", model);
        } else {
            redirectAttributes.addFlashAttribute("error", "the requested product could not been found");
            return new ModelAndView("redirect:/products", model);
        }
    }

    @GetMapping(value = "**")
    public String notFoundPage() {
        return "NotFound";
    }
}



