package com.example.bakkery.Controllers;

import com.example.bakkery.Modellen.Ingredient;
import com.example.bakkery.Modellen.Product;
import com.example.bakkery.Repositories.IngredientRepository;
import com.example.bakkery.Repositories.ProductenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class BakkerijController {
    //er gaat gevraagd worden om met @ModelAttribute te werken
//Methodes annoteren met @ModelAttribute
    private Product tebewerkenproduct;

    @RequestMapping("/test")
    private String ShowTest(){



        return "testje";
    }

    ProductenRepository productenRepository;
    IngredientRepository ingredientRepository;
    public BakkerijController(ProductenRepository productenRepository, IngredientRepository ingredientRepository){
        this.productenRepository=productenRepository;
        this.ingredientRepository=ingredientRepository;
    }

    @GetMapping("/nieuw")
    private String NieuwProduct(Model model){
Product product=new Product();
tebewerkenproduct=product;
HashMap<Ingredient,Double>re=new HashMap<Ingredient, Double>();
re.put(new Ingredient("test"),5.0);
product.setRecept(re);
product.getStappenplan().add("Voeg vervolgens de bloem toe");
model.addAttribute("product", product);

        return "testje";
    }
    @PostMapping("/nieuw")
    private String CreateProduct(Product product){



        return "redirect:testje";
    }



    @GetMapping("/product/{productid}")
    public String ToonProduct(Model model, @PathVariable(value = "productid",required = false) Long productid){

        if(productid!=0) {

            Product product = new Product("eersteProduct");
            tebewerkenproduct = product;
            HashMap<Ingredient, Double> re = new HashMap<Ingredient, Double>();
            re.put(new Ingredient("test"), 5.0);
            product.setRecept(re);
            product.getStappenplan().add("Voeg vervolgens de bloem toe");
            model.addAttribute("product", product);

            return "ToonProduct";
        }
        else return "ToonProduct";

    }
    @GetMapping("/product/bewerken/{productid}")
    public String StartMetBewerken(Model model, @PathVariable(value = "productid",required = true) Long productid){


            Product product = new Product("eersteProduct");
            tebewerkenproduct = product;
            HashMap<Ingredient, Double> re = new HashMap<Ingredient, Double>();
            //direct testen met database, ingredienten moeten matchen, override equals en hashcode
        //voo zekerheid
            re.put(new Ingredient("test"), 5.0);
        re.put(new Ingredient("test2"), 5.0);

        product.setRecept(re);
            product.getStappenplan().add("Voeg vervolgens de bloem toe");

            List<Ingredient>ingredientList=new ArrayList<>();
            ingredientList.add(new Ingredient("mijn Ingredient"));
        ingredientList.add(new Ingredient("mijn 2de Ingredient"));


        model.addAttribute("product", product);
        model.addAttribute("ingredienten", ingredientList);

            return "BewerkProduct";


    }



    @PostMapping("/product/bewerken/{productid}")
    public String BewaarProduct(@ModelAttribute Product product,BindingResult bindingResult, @PathVariable Long productid, @RequestParam(value = "add",required = false) String actie ){

System.out.println(product.getStappenplan().get(0));
if(actie!=null&&actie.equals("Voeg stap toe")){

    product.getStappenplan().add("");
  this.tebewerkenproduct=product;

    return "redirect:/product/p/"+productid;
}
        if(actie!=null&&actie.equals("Voeg ingrediënt toe")){

            product.getRecept().put(new Ingredient(""),0d);
            this.tebewerkenproduct=product;

            return "redirect:/product/p/"+productid;
        }

       else {

         productenRepository.save(product);
            return "redirect:/product/"+productid;
        }


    }
@GetMapping("/product/p/{productid}")
public String ToonModel(Model model, @PathVariable Long productid){

model.addAttribute("product",tebewerkenproduct);


        return "BewerkProduct";
}


}
