package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.CoffeeMaker;
import edu.ncsu.csc326.coffeemaker.Recipe;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class CoffeeCucumberTest {

    public CoffeeCucumberTest() throws RecipeException {
    }

    private static Recipe createRecipe(String name, String amtChocolate, String amtCoffee, String amtMilk, String amtSugar, String price) throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setAmtChocolate(amtChocolate);
        recipe.setAmtCoffee(amtCoffee);
        recipe.setAmtMilk(amtMilk);
        recipe.setAmtSugar(amtSugar);
        recipe.setPrice(price);

        return recipe;
    }

    private CoffeeMaker coffeeMaker;
    private int money;
    private int amount;

    //Set up for r1
    private Recipe recipe1 = createRecipe("Coffee", "0","3","1","1","50");
    //Set up for r2
    private Recipe recipe2 = createRecipe("Mocha", "20","3","1","1","75");
    //Set up for r3
    private Recipe recipe3 = createRecipe("Latte", "0","3","3","1","100");

    @Given("I buy new coffee maker")
    public void iBuyNewCoffeeMaker() {
        coffeeMaker = new CoffeeMaker();
    }

    @Then("the ingredient in inventory is all {int}")
    public void theIngredientInInventoryIsAll(int arg0) {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @When("I add new recipe")
    public void iAddNewRecipe() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.addRecipe(recipe2);
        coffeeMaker.addRecipe(recipe3);
    }

    @Then("there is recipes present in coffee maker")
    public void thereIsRecipesPresentInTheCoffeeMaker() {
        assertEquals("Coffee", coffeeMaker.getRecipes()[0].getName());
        assertEquals("Mocha", coffeeMaker.getRecipes()[1].getName());
        assertEquals("Latte", coffeeMaker.getRecipes()[2].getName());
    }

    @And("I pay {int}")
    public void iPay(int arg0) {
        money = arg0;
    }

    @And("make a cup of coffee")
    public void makeACupOfCoffee() {
        amount = coffeeMaker.makeCoffee(0, money);
    }

    @Then("the change is {int}")
    public void theChangeIs(int arg0) {
        assertEquals(arg0, amount);
    }
}
