package kdg.be.Controllers;


import kdg.be.Models.Ingredient;
import kdg.be.Repositories.IIngredientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class IngredientController {

 private IIngredientRepository IIngredientRepository;

    public IngredientController(IIngredientRepository IIngredientRepository){
    this.IIngredientRepository = IIngredientRepository;

}

@GetMapping("/ingredienten")
public String AlleIngredienten(Model model){

     List<Ingredient> ing= IIngredientRepository.findAll();
model.addAttribute("Ingredienten",ing);
Ingredient nieuwIngredient= new Ingredient();
    model.addAttribute("nieuwIngredient",nieuwIngredient);

    return "Ingredienten/IngredientenOverzicht";
}

    @GetMapping(value={"/ingredienten/ingredient/{ingredientId}"})
    public String IngredientDetailpagina( @PathVariable Long ingredientId, Model model){
    Optional<Ingredient> ingredient=  IIngredientRepository.findById(ingredientId);
    if(ingredient.isPresent()){
        model.addAttribute("Ingredient",ingredient.get());
        return "Ingredienten/IngredientDetails";
    }
    else{

    return "errorPagina";
    }
    }


    @PostMapping(value={"/ingredienten/ingredient","/Ingredienten/ingredient/{ingredientId}"})
    public ModelAndView BewaarIngredient(Ingredient ingredient, @PathVariable(required = false) Long ingredientId){
   System.out.println("save");
    IIngredientRepository.save(ingredient);
        ModelAndView modelAndView=new ModelAndView("Ingredienten/IngredientenOverzicht");
        Ingredient nieuwIngredient=new Ingredient("testje","testje2");
        modelAndView.addObject("nieuwIngredient",new Ingredient());
        List<Ingredient> ing= IIngredientRepository.findAll();
        modelAndView.addObject("Ingredienten",ing);


        return modelAndView;
    }
    @GetMapping(value={"/ingredienten/ingredient/verwijderen/{productId}"})
    public ModelAndView VerwijderIngredient(Ingredient ingredient, @PathVariable(required = true) Long productId ){
       IIngredientRepository.deleteById(productId);
List<Ingredient> ingredienten= IIngredientRepository.findAll();
ModelAndView modelAndView=new ModelAndView("Ingredienten/IngredientenOverzicht");
modelAndView.addObject("Ingredienten",ingredienten);
        modelAndView.addObject("nieuwIngredient",new Ingredient());

        return modelAndView;
    }
}
