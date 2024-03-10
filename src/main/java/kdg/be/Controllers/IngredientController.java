package kdg.be.Controllers;


import kdg.be.Managers.IIngredientManager;
import kdg.be.Managers.IngredientManager;
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

    private IIngredientManager ingredientMgr;

    public IngredientController(IngredientManager ingredientManager) {
        ingredientMgr = ingredientManager;

    }

    @GetMapping("/ingredienten")
    public String AllIngredients(Model model) {

        List<Ingredient> ing = ingredientMgr.getAllIngredients();
        model.addAttribute("Ingredienten", ing);
        Ingredient nieuwIngredient = new Ingredient();
        model.addAttribute("nieuwIngredient", nieuwIngredient);

        return "Ingredienten/IngredientenOverview";
    }

    @GetMapping(value = {"/ingredienten/ingredient/{ingredientId}"})
    public String IngredientDetails(@PathVariable Long ingredientId, Model model) {
        Optional<Ingredient> ingredient = ingredientMgr.getIngredientById(ingredientId);
        if (ingredient.isPresent()) {
            model.addAttribute("Ingredient", ingredient.get());
            model.addAttribute("IngredientId", ingredientId);
            return "Ingredienten/IngredientDetails";
        } else {
            return "errorPagina";
        }
    }

    @PostMapping(value = {"/ingredienten/ingredient/create"})
    public ModelAndView NewIngredient(Ingredient ingredient, @PathVariable(required = false) Long ingredientId) {
        System.out.println(ingredient.getBeschrijving());
        System.out.println(ingredient.getName());

        ingredientMgr.saveIngredient(ingredient);
        ModelAndView modelAndView = new ModelAndView("Ingredienten/IngredientenOverview");
        List<Ingredient> ingredienten = ingredientMgr.getAllIngredients();
        modelAndView.addObject("Ingredienten", ingredienten);

        return modelAndView;
    }


    @PostMapping(value = {"/ingredienten/ingredient/update/{ingredientId}"})
    public ModelAndView SaveIngredient(Ingredient ingredient, @PathVariable(required = false) Long ingredientId) {
        System.out.println(ingredientId);
        ModelAndView modelAndView = new ModelAndView();
        Optional<Ingredient> ingredientOptional = ingredientMgr.getIngredientById(ingredientId);
        if (ingredientOptional.isPresent()) {
            modelAndView.setViewName("IngredientenOverwiew");
            Ingredient oudIngredient = ingredientOptional.get();
            oudIngredient.setName(ingredient.getName());
            oudIngredient.setBeschrijving(ingredient.getBeschrijving());
            ingredientMgr.saveIngredient(oudIngredient);
            //  Ingredient nieuwIngredient = new Ingredient("testje", "testje2");
            modelAndView.addObject("nieuwIngredient", new Ingredient());
            List<Ingredient> ing = ingredientMgr.getAllIngredients();
            modelAndView.addObject("Ingredienten", ing);
        } else {
            modelAndView.setViewName("errorPagina");
        }

        return modelAndView;

    }

    @GetMapping(value = {"/ingredienten/ingredient/verwijderen/{ingredientId}"})
    public ModelAndView DeleteIngredient(@PathVariable(required = true) Long ingredientId) {
        ingredientMgr.deleteIngredient(ingredientId);
        List<Ingredient> ingredienten = ingredientMgr.getAllIngredients();
        ModelAndView modelAndView = new ModelAndView("IngredientenOverwiew");
        modelAndView.addObject("Ingredienten", ingredienten);
        modelAndView.addObject("nieuwIngredient", new Ingredient());

        return modelAndView;
    }
}
