package kdg.be.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import kdg.be.Managers.IIngredientManager;
import kdg.be.Managers.IProductManager;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import kdg.be.Models.ProductState;
import kdg.be.RabbitMQ.RabbitSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
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
    //Modelbinding vb
    //Controller advice vb
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final IIngredientManager ingredientManager;
    private final IProductManager productManager;
    private final RabbitSender rabbitSender;

    ;
    public ProductController(IIngredientManager ingredientManager, IProductManager productManager,  RabbitSender rabbitSender) {

        this.ingredientManager = ingredientManager;
        this.productManager = productManager;
        this.rabbitSender = rabbitSender;

    }


    @Autowired
    @Qualifier("ProductValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }



    @GetMapping("/")
    public String ToonHomePage(){



        return "redirect:/products";
    }


    @GetMapping(value={"/products"})
    public ModelAndView AllProducts(Model model,  @ModelAttribute("error") String flashAttribute) {

        List<Product> alleProducten = productManager.getAllProducts();
        model.addAttribute("Producten", alleProducten);
model.addAttribute("error",flashAttribute);
     return new ModelAndView("Products/ProductenOverzicht");
    }

    @GetMapping(value={"/products/create","/products/create/{productId}"})
    public String Newproduct(Model model, @PathVariable(required = false) Integer productId) {
        Product product=null;

        List<Ingredient> alleIngredienten = ingredientManager.getAllIngredients();

        if(productId==null) {
            product = new Product();
            product.getSteps().add("");

        }
        else{

            Optional<Product> optionalProduct=  productManager.getProductById(productId);
            if(optionalProduct.isPresent()){

                product=optionalProduct.get();
            product.setProductId(Long.valueOf(productId));

            }
            else{
                product=new Product("",new ArrayList<String>(),new ArrayList<>(),new ArrayList<>());
            }

        }

        model.addAttribute("product", product);


        model.addAttribute("alleIngredienten", alleIngredienten);

        return "Products/newProduct";
    }



    @PostMapping(value="/products/create")
    public ModelAndView saveProduct(Model model, @Valid Product product, BindingResult bindingResult) {

        List<Ingredient>alleIngredienten=  ingredientManager.getAllIngredients();
        model.addAttribute("alleIngredienten",alleIngredienten);


        if(bindingResult.hasErrors()){

            List<String> error=new ArrayList<>();
       bindingResult.getAllErrors().forEach(err->error.add(err.getDefaultMessage()));


            model.addAttribute("validationError", error);

            return new ModelAndView("Products/newProduct", (Map<String, ?>) model);

        }
if(product.getComposition().size()!=product.getComposition().stream().distinct().toList().size()){
    product.getComposition().remove(product.getComposition().size()-1);
    product.getAmounts().remove(product.getAmounts().size()-1);
}

        productManager.saveOrUpdate(product);

        return new ModelAndView("redirect:/products", (Map<String, ?>) model);
    }
    @PostMapping(value="/products/create", params="stap")
    public ModelAndView AddStep(Model model, Product product) {
        product.getSteps().add("");
        model.addAttribute("product", product);
        List<Ingredient>alleIngredienten=  ingredientManager.getAllIngredients();
        model.addAttribute("alleIngredienten",alleIngredienten);


        return new ModelAndView("Products/newProduct", (Map<String, ?>) model);
    }
    @PostMapping(value="/products/create", params="addIng")
    public ModelAndView AddIng(Model model, Product product) {
        List<Ingredient> alleIngredienten = ingredientManager.getAllIngredients();
        if(product.getComposition().stream().distinct().toList().size()!=product.getComposition().size()){
         model.addAttribute("error","this ingredient is already part of the recepy");
        } else if
        (alleIngredienten.size()>0){
            product.getComposition().add(alleIngredienten.get(0));
            product.getAmounts().add(0.0);
        }




        model.addAttribute("product", product);
        model.addAttribute("alleIngredienten", alleIngredienten);

        return new ModelAndView("Products/newProduct", (Map<String, ?>) model);


    }
    @PostMapping(value="/products/create", params ="removeIng" )
    public ModelAndView RemoveIng(Model model, Product product, @RequestParam(name = "removeIng") int removeIng) {
        product.getAmounts().remove(removeIng);
        product.getComposition().remove(removeIng);
        model.addAttribute("product", product);
        List<Ingredient> alleIngredienten=ingredientManager.getAllIngredients();
        model.addAttribute("alleIngredienten",alleIngredienten);
        return new ModelAndView("Products/newProduct", (Map<String, ?>) model);
    }

    @PostMapping(value="/products/create", params ="removeStep" )
    public ModelAndView RemoveStep(Model model, Product product, @RequestParam(name = "removeStep") int removeStep) {
        product.getSteps().remove(removeStep);
        model.addAttribute("product", product);
        List<Ingredient> alleIngredienten=ingredientManager.getAllIngredients();
        model.addAttribute("alleIngredienten",alleIngredienten);
        return new ModelAndView("Products/newProduct", (Map<String, ?>) model);
    }



@GetMapping("/products/final/{productId}")
public RedirectView MakeFinal(@PathVariable int productId, RedirectAttributes redirectAttributes) throws JsonProcessingException {

Optional<Product> optioneelProduct=productManager.getProductById(productId);

if(optioneelProduct.isPresent()){
    Product product =optioneelProduct.get();
    product.set_ProductStatus(ProductState.Finaal);
    rabbitSender.sendNewRecipe(product);
    productManager.saveOrUpdate(product);

}
else{

    redirectAttributes.addFlashAttribute("error","The requested product could not been found");

}

    return new RedirectView("/products");

}

    @GetMapping("/products/deactivate/{productId}")
    public String Deactivate(@PathVariable int productId) throws JsonProcessingException {

        Optional<Product> optioneelProduct=productManager.getProductById(productId);

        if(optioneelProduct.isPresent()){
            Product product =optioneelProduct.get();
            product.set_ProductStatus(ProductState.Gedeactiveerd);
            rabbitSender.sendNewRecipe(product);
            productManager.saveOrUpdate(product);

        }

        return "redirect:/products";




    }


@GetMapping("/products/detail/{productId}")
    public ModelAndView DetailPage(@PathVariable Long productId, ModelMap model,RedirectAttributes redirectAttributes){


     Optional<Product>optionalProduct=   productManager.getProductById(productId);
        if(optionalProduct.isPresent()){
            model.addAttribute("product",optionalProduct.get());

            return new ModelAndView("Products/productDetail", (Map<String, ?>) model);
        }

        else {
        redirectAttributes.addFlashAttribute("error","the requested product could not been found");

        return new ModelAndView("redirect:/products", (Map<String, ?>) model);}





        }

    @GetMapping(value="**")
    public String NotFoundPage(){
        return "NotFound";
    }
}



