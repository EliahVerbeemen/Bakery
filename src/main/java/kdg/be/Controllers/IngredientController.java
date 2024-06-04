package kdg.be.Controllers;


import kdg.be.Services.IngredientService;
import kdg.be.Services.Interfaces.IIngredientService;
import kdg.be.Models.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class IngredientController {
    Logger logger = LoggerFactory.getLogger(IngredientController.class);
    private final IIngredientService ingredientService;

    public IngredientController(IngredientService ingredientManager) {
        ingredientService = ingredientManager;
    }


    @GetMapping({"/ingredienten", "/ingredienten/{ingredientId}"})
    public String AllIngredients(Model model, @PathVariable(required = false) Integer ingredientId) {
        List<Ingredient> ing = ingredientService.getAllIngredients();
        model.addAttribute("Ingredienten", ing);
        Ingredient nieuwIngredient = new Ingredient();
        model.addAttribute("nieuwIngredient", nieuwIngredient);
        model.addAttribute("updateMe", ingredientId);
        return "Ingredienten/IngredientOverview";
    }

    @PostMapping(value = {"/ingredienten/ingredient/create"})
    public ModelAndView NewIngredient(Ingredient ingredient, @PathVariable(required = false) Long ingredientId, Model model) {
        ingredientService.saveIngredient(ingredient);
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        model.addAttribute("Ingredienten", ingredients);
        model.addAttribute("nieuwIngredient", new Ingredient());

        return new ModelAndView("redirect:/ingredienten", (Map<String, ?>) model) /*modelAndView*/;
    }


    @PostMapping("/ingredienten/update/{ingredientId}")
    public ModelAndView SaveIngredient(Ingredient ingredient, @PathVariable(required = false) Long ingredientId, Model model) {
        ingredientService.updateIngredient(ingredient.getIngredientId(), ingredient);
        List<Ingredient> ing = ingredientService.getAllIngredients();
        model.addAttribute("Ingredienten", ing);
        Ingredient nieuwIngredient = new Ingredient();
        model.addAttribute("nieuwIngredient", nieuwIngredient);
        return new ModelAndView("redirect:/ingredienten", (Map<String, ?>) model);
    }

    @GetMapping(value = {"/ingredienten/verwijderen/{ingredientId}"})
    public ModelAndView DeleteIngredient(@PathVariable(required = true) Long ingredientId, Model model) {
        ingredientService.deleteIngredient(ingredientId);
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        model.addAttribute("Ingredienten", ingredients);
        model.addAttribute("nieuwIngredient", new Ingredient());
        return new ModelAndView("redirect:/ingredienten", (Map<String, ?>) model);
    }
}
