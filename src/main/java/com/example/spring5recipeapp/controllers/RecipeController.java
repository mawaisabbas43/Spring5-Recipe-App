package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.commands.RecipeCommand;
import com.example.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    //RequestMapping is older why GetMapping is alternate and preferred why for get request
    @RequestMapping("/recipe/{id}/show")
    public String getRecipeDetail(@PathVariable String id, Model model) {
        log.debug("Getting recipe detail.");

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        log.debug("Getting recipe form for new Recipe.");

        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String addOrUpdate(@ModelAttribute RecipeCommand recipe) {
        log.debug("Saving new recipe.");

        RecipeCommand recipeSaved = recipeService.saveRecipeCommand(recipe);

        String view="/recipe/"+recipeSaved.getId()+"/show";
        return "redirect:"+view;
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        log.debug("Updating recipe.");

        model.addAttribute("recipe", recipeService.getCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id, Model model) {
        log.debug("Deleting recipe for Id: "+id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
