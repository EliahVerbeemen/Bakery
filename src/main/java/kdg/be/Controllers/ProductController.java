package kdg.be.Controllers;

import kdg.be.Managers.IIngredientManager;
import kdg.be.Managers.IProductManager;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import kdg.be.Models.ProductState;
import kdg.be.RabbitMQ.RabbitSender;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


@Controller
public class ProductController {
    private IIngredientManager ingredientManager;
    private IProductManager productManager;

    private  RabbitSender rabbitSender;
//Modelbinding vb
    //Controller advice vb
    private final Logger logger= LoggerFactory.getLogger(ProductController.class);
    public ProductController(IIngredientManager ingredientManager, IProductManager productManager, RabbitSender rabbitSender) {

        this.ingredientManager = ingredientManager;
        this.productManager = productManager;
        this.rabbitSender=rabbitSender;
    }

    @GetMapping("/producten")
    public String AlleProducten(Model model) {

logger.info("test");
        List<Product> alleProducten = productManager.getAllProducts();
        System.out.println(alleProducten.size());
        model.addAttribute("Producten", alleProducten);


        return "Producten/ProductenOverzicht";
    }


    @GetMapping("/producten/product/detail/{productId}")
    public String ProductDetailPagina(Model model, @PathVariable Long productId) {

        Optional<Product> mogelijkProduct = productManager.getProductById(productId);
        if (mogelijkProduct.isPresent()) {
            Product product = mogelijkProduct.get();
            model.addAttribute("Product", product);


            return "Producten/ProductDetailPagina";
        } else {
            //voorlopig
            return "errorPagina";
        }
    }



    @GetMapping(value = {"/producten/product/bewerk", "/producten/product/bewerk/{productId}"})
    public String ProductBewerkPagina(Model model, @PathVariable(required = false) Long productId) {
System.out.println("gesaved");

        if (productId != null) {
            Optional<Product> mogelijkProduct = productManager.getProductById(productId);
            if (mogelijkProduct.isPresent()) {
                Product product = mogelijkProduct.get();
                List<Ingredient> ingredienten = ingredientManager.getAllIngredients();
                model.addAttribute("Product", product);
                model.addAttribute("ingredienten", ingredienten);


                return "Producten/ProductBewerken";
            } else {
                System.out.println(productId);
                //voorlopig
                return "errorPagina";
            }

        } else {
            Product product = new Product();
            model.addAttribute("Product", product);
            return "Producten/NieuwProduct";

        }

    }
    @PostMapping(value = {"producten/product/bewerk", "/producten/product/bewerk/{productId}"},params = "finaliseer")
    public ModelAndView FinaliseerProduct(Product binnekomendProduct) {
        System.out.println("Finaliseer");
        Optional<Product> optioneelProduct = productManager.getProductById(binnekomendProduct.getProductId());
        if (optioneelProduct.isPresent()) {
            Product product = optioneelProduct.get();
            product.setProductStatus(ProductState.Finaal);
            productManager.saveProduct(product);

            ModelAndView modelAndView = new ModelAndView("/Producten/ProductenOverzicht");
            modelAndView.addObject("Producten", productManager.getAllProducts());
            modelAndView.addObject("Product", new Product());
            return modelAndView;

        } else {
            return new ModelAndView("errorPagina");

        }
    }


    @GetMapping(value = {"producten/product/bewerk", "/producten/product/bewerk/{productId}"}, params = "deactiveer")
    public ModelAndView DeactiveerProduct(@PathVariable Long productId) {
        System.out.println("deactiveer");
        Optional<Product> optioneelProduct = productManager.getProductById(productId);
        if (optioneelProduct.isPresent()) {
            Product product = optioneelProduct.get();

            product.setProductStatus(ProductState.Gedeactiveerd);
            productManager.saveProduct(product);
            ModelAndView modelAndView = new ModelAndView("/Producten/ProductenOverzicht");
            modelAndView.addObject("Producten", productManager.getAllProducts());
            modelAndView.addObject("Product", new Product());
            return modelAndView;

        } else {
            return new ModelAndView("errorPagina");
        }

    }

    @PostMapping(value = {"producten/product/bewerk", "/producten/product/bewerk/{productId}"})
    public ModelAndView ProductOpslaan(Product product) {

        System.out.println(product.getName());
        productManager.saveProduct(product);
        List<Product> producten = productManager.getAllProducts();

        ModelAndView modelAndView = new ModelAndView("Producten/ProductenOverzicht");
        modelAndView.addObject("Producten", producten);
        modelAndView.addObject("Product", product);
        return modelAndView;
    }


    @PostMapping(value = {"producten/product/bewerk", "/producten/product/bewerk/{productId}"}, params = "stap=toevoegen")
    public ModelAndView VoegStapToe(Product product) {

        product.getStappenplan().add("");
        ModelAndView modelAndView = new ModelAndView("Producten/ProductBewerken");
        modelAndView.addObject("Product", product);
        return modelAndView;
    }


    @GetMapping("/test")
    public String test(){
        HashMap<Ingredient,Double> composition=new HashMap<>();
      Ingredient ingredientOne=  this.ingredientManager.saveIngredient(new Ingredient("testIgredient","testBeschrijving"));
      Ingredient ingredientTwo= this.ingredientManager.saveIngredient(new Ingredient("testIgredient2","testBeschrijving"));
        composition.put(ingredientOne,50d);
        composition.put(ingredientTwo,50d);
        Product product=this.productManager.saveProduct(new Product("testRecept", List.of("testBeschrijving"), composition)
        );

      this.rabbitSender.sendNewRecepy(product);
return "errorPagina";
    }

}

