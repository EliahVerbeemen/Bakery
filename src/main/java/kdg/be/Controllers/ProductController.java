package kdg.be.Controllers;

import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import kdg.be.Models.ProductStatus;
import kdg.be.Repositories.IngredientRepository;
import kdg.be.Repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {


  private  IngredientRepository ingredientRepository;

  private ProductRepository productRepository;


    public ProductController(IngredientRepository ingredientRepository,ProductRepository productRepository){

        this.productRepository=productRepository;
        this.ingredientRepository=ingredientRepository;


    }


    @GetMapping("/producten")
    public String AlleProducten(Model model){
        Product nieuwProduct=new Product();
        nieuwProduct.setNaam("testNaam");
        ArrayList<String> stappenplan=new ArrayList<>();
        stappenplan.add("Voeg dan nu de bloem toe");
        nieuwProduct.setStappenplan(stappenplan);
        Ingredient nieuwIngredient=new Ingredient();
        nieuwIngredient.setNaam("testIngredient");
        nieuwIngredient.setBeschrijving("Ik ben en testIngredient");
        ingredientRepository.save(nieuwIngredient);
        Ingredient ing=ingredientRepository.findAll().get(0);
//nieuwProduct.getSamenstelling().add(new Product_Ingredient(nieuwProduct,ing,50));



        productRepository.save(nieuwProduct);





        List<Product> alleProducten =    productRepository.findAll();
System.out.println(alleProducten.size());
        model.addAttribute("Producten",alleProducten);


return  "Producten/ProductenOverzicht";
    }


    @GetMapping("/producten/product/detail/{productId}")
    public String ProductDetailPagina(Model model, @PathVariable Long productId){

        Optional<Product> mogelijkProduct =    productRepository.findById(productId);
   if(mogelijkProduct.isPresent()) {
       Product product=mogelijkProduct.get();
       model.addAttribute("Product", product);


       return "Producten/ProductDetailPagina";
   }
   else{
       //voorlopig
       return "errorPagina";
   }
    }


    @GetMapping(value={"/producten/product/bewerk","/producten/product/bewerk/{productId}"})
    public String ProductBewerkPagina(Model model, @PathVariable(required = false) Long productId){


        if(productId!=null) {
            Optional<Product> mogelijkProduct = productRepository.findById(productId);
            if (mogelijkProduct.isPresent()) {
                Product product = mogelijkProduct.get();
                List<Ingredient> ingredienten=ingredientRepository.findAll();
                model.addAttribute("Product", product);
                model.addAttribute("ingredienten", ingredienten);


                return "Producten/ProductBewerken";
            }
            else{
                System.out.println(productId);
                //voorlopig
                return "errorPagina";
            }

        }
        else{
            Product product=new Product();
            model.addAttribute("Product", product);
            return "Producten/NieuwProduct";

        }

    }

    @PostMapping(value={"producten/product/bewerk","/producten/product/bewerk/{productId}"} )
    public ModelAndView ProductOpslaan(Product product){
this.productRepository.save(product);
List<Product>producten=productRepository.findAll();

        ModelAndView modelAndView=new ModelAndView("Producten/ProductenOverzicht");
        modelAndView.addObject("Producten",producten);
        modelAndView.addObject("Product",product);
        return modelAndView;
    }

    @PostMapping(value={"producten/product/bewerk","/producten/product/bewerk/{productId}"},params ="stap=toevoegen" )
    public ModelAndView VoegStapToe(Product product){

        product.getStappenplan().add("");
        ModelAndView modelAndView=new ModelAndView("Producten/ProductBewerken");
        modelAndView.addObject("Product",product);
        return modelAndView;
    }

    @GetMapping(value={"/producten/product/deactiveren/{productId}"} )
public ModelAndView DeactiveerProduct(@PathVariable Long productId){
System.out.println("deactiveer");
        Optional<Product> optioneelProduct = productRepository.findById(productId);
    if(optioneelProduct.isPresent()){
        Product product=optioneelProduct.get();

        product.setProductStatus(ProductStatus.Gedeactiveerd);
        productRepository.save(product);
        ModelAndView modelAndView=new ModelAndView("/Producten/productenOverzicht");
        modelAndView.addObject("Producten",productRepository.findAll());
        modelAndView.addObject("Product", new Product());
        return modelAndView;

    }
    else{
        return new ModelAndView("errorPagina");
    }

    }
    @GetMapping("/producten/product/finaliseer/{productId}")
    public ModelAndView FinaliseerProduct(@PathVariable Long productId){
System.out.println("Finaliseer");
        Optional<Product> optioneelProduct = productRepository.findById(productId);
        if(optioneelProduct.isPresent()){
Product product=optioneelProduct.get();
            product.setProductStatus(ProductStatus.Finaal);
            productRepository.save(product);

        ModelAndView modelAndView=new ModelAndView("/Producten/ProductenOverzicht");
modelAndView.addObject("Producten",productRepository.findAll());
        modelAndView.addObject("Product", new Product());
        return modelAndView;

        }   else{
            return new ModelAndView("errorPagina");

        }
    }
}
