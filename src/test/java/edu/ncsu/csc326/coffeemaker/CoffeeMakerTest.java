/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 *
 * Permission has been explicitly granted to the University of Minnesota
 * Software Engineering Center to use and distribute this source for
 * educational purposes, including delivering online education through
 * Coursera or other entities.
 *
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including
 * fitness for purpose.
 *
 *
 * Modifications
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for CoffeeMaker class.
 *
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {

    /**
     * The object under test.
     */
    private CoffeeMaker coffeeMaker;

    // Sample recipes to use in testing.
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Recipe recipe4;
    private Recipe recipe5;

    /**
     * Initializes some recipes to test with and the {@link CoffeeMaker}
     * object we wish to test.
     *
     * @throws RecipeException if there was an error parsing the ingredient
     *                         amount when setting up the recipe.
     */
    @Before
    public void setUp() throws RecipeException {
        coffeeMaker = new CoffeeMaker();

        //Set up for r1
        recipe1 = createRecipe("Coffee", "0","3","1","1","50");
        //Set up for r2
        recipe2 = createRecipe("Mocha", "20","3","1","1","75");
        //Set up for r3
        recipe3 = createRecipe("Latte", "0","3","3","1","100");
        //Set up for r4
        recipe4 = createRecipe("Hot Chocolate", "4","0","1","1","65");
        //Set up for r5
        recipe5 = createRecipe("Ultimate Coffee", "100","100","100","100","50");

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

    /**
     * Given a coffee maker with one valid recipe
     * <p>
     * Coffee Maker will not throw exception if recipe is properly added
     */
    @Test
    public void testAddRecipe() {
        assertTrue(coffeeMaker.addRecipe(recipe1));
    }

    /**
     * Given a coffee maker with two recipe with same name
     * Coffee maker should not have two recipe with same name both name must be unique
     */
    @Test
    public void testAddRecipeSameName() {
        assertTrue(coffeeMaker.addRecipe(recipe1));
        assertFalse(coffeeMaker.addRecipe(recipe1));
    }

    /**
     * Given a coffee maker with two recipe with unique name
     * Coffee maker should accept both recipe.
     */
    @Test
    public void testAddRecipeUniqueName() {
        assertTrue(coffeeMaker.addRecipe(recipe1));
        assertTrue(coffeeMaker.addRecipe(recipe2));
    }

    /**
     * Coffee maker can only save up to 3 recipe
     */
    @Test
    public void testAddRecipeMax() {
        assertTrue(coffeeMaker.addRecipe(recipe1));
        assertTrue(coffeeMaker.addRecipe(recipe2));
        assertTrue(coffeeMaker.addRecipe(recipe3));
        assertFalse(coffeeMaker.addRecipe(recipe4));
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe and paying more than
     * the coffee costs
     * Then we check if recipe is properly
     */
    @Test
    public void testDeleteRecipe() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.deleteRecipe(0);
        Recipe empty = new Recipe();
        assertEquals(empty, coffeeMaker.getRecipes()[0]);
    }

    /**
     * Delete first index of recipe when recipe is empty
     */
    @Test
    public void testDeleteRecipeEmpty() {
        coffeeMaker.deleteRecipe(0);
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe and paying more than
     * the coffee costs
     * Then we check if recipe is properly
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testDeleteRecipeOutOfBound() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.deleteRecipe(5);
    }

    /**
     * Given a coffee maker with one valid recipe
     * then edit the recipe and check if recipe is edited or not
     */
    @Test
    public void testEditRecipe() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.editRecipe(0, recipe2);
        assertEquals(coffeeMaker.getRecipes()[0], recipe2);
    }

    /**
     * Given a coffee maker with one valid recipe
     * then try to edit recipe at index 5
     *
     * @throws ArrayIndexOutOfBoundsException
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testEditRecipeOutOfBound() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.editRecipe(5, recipe2);
    }

    /**
     * Given a coffee maker with one valid recipe
     * then edit the recipe and check if the name is the same as new one
     */
    @Test
    public void testEditRecipeName() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.editRecipe(0, recipe2);
        assertEquals("Mocha", coffeeMaker.getRecipes()[0].getName());
    }

    /**
     * Check if inventory is work as intended.
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            *   to a positive integer.
     */
    @Test
    public void testAddInventory() throws InventoryException {
        coffeeMaker.addInventory("1","1","1","1");
        assertEquals("Coffee: 16\nMilk: 16\nSugar: 16\nChocolate: 16\n", coffeeMaker.checkInventory());
    }

    /**
     * Given the positive amount of chocolate and added to chocolate's supply in the inventory
     * <p>
     * Then the amount of chocolate is correctly added.
     */
    @Test
    public void testAddInventoryCoffeePositive() throws InventoryException {
        coffeeMaker.addInventory("5", "0", "0", "0");
    }

    /**
     * Given the negative amount of chocolate and added to chocolate's supply in the inventory
     * the coffee maker should not accept the negative amount and throw InventoryException
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddInventoryCoffeeNegative() throws InventoryException {
        coffeeMaker.addInventory("-5", "0", "0", "0");
    }

    /**
     * Given the positive amount of milk and added to milk's supply in the inventory
     * <p>
     * Then the amount of milk is correctly added.
     */
    @Test
    public void testAddInventoryMilkPositive() throws InventoryException {
        coffeeMaker.addInventory("0", "5", "0", "0");
    }

    /**
     * Given the negative amount of milk and added to milk's supply in the inventory
     * the coffee maker should not accept the negative amount and throw InventoryException
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddInventoryMilkNegative() throws InventoryException {
        coffeeMaker.addInventory("0", "-5", "0", "0");
    }

    /**
     * Given the positive amount of sugar and added to sugar's supply in the inventory
     * <p>
     * Then the amount of sugar is correctly added.
     */
    @Test
    public void testAddInventorySugarPositive() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "5", "0");
    }

    /**
     * Given the negative amount of sugar and added to sugar's supply in the inventory
     * the coffee maker should not accept the negative amount and throw InventoryException
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddInventorySugarNegative() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "-5", "0");
    }

    /**
     * Given the positive amount of chocolate and added to chocolate's supply in the inventory
     * <p>
     * Then the amount of chocolate is correctly added.
     */
    @Test
    public void testAddInventoryChocolatePositive() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "0", "5");
    }

    /**
     * Given the negative amount of chocolate and added to chocolate's supply in the inventory
     * the coffee maker should not accept the negative amount and throw InventoryException
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddInventoryChocolateNegative() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "0", "-5");
    }

    /**
     * Coffee Maker should be able to add inventory even if all amount is 0
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test
    public void testAddInventoryZero() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "0", "0");
    }

    /**
     * Check if inventory is work as intended.
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            *   to a positive integer.
     */
    @Test
    public void testCheckInventory() throws InventoryException {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with one valid recipe
     * and make a coffee and check if Inventory change according to the
     * ingredient use in that recipe
     */
    @Test
    public void testUseIngredient() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.makeCoffee(0, 75);
        assertEquals("Coffee: 12\nMilk: 14\nSugar: 14\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe and paying more than
     * the coffee costs
     * Then we get the correct change back.
     */
    @Test
    public void testMakeCoffee() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(25, coffeeMaker.makeCoffee(0, 75));
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe and paying less than
     * the coffee costs
     * Then you got the same amount of money back
     */
    @Test
    public void testMakeCoffeeNotEnoughMoney() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(25, coffeeMaker.makeCoffee(0, 25));
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe but Negative amount of money
     * <p>
     * Then throws Exception money can't be negative
     */
    @Test(expected = RecipeException.class)
    public void testMakeCoffeeNegativeMoney() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.makeCoffee(0, -10);
    }

    /**
     * Given a coffee maker only one valid recipe
     * then try to make coffee by recipe that is over limit of the coffee maker
     *
     * @throws ArrayIndexOutOfBoundsException
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testMakeCoffeeOutOfBound() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.makeCoffee(100, 25);
    }

    /** Add recipe5 which used large amount of ingredient
     * and makeCoffee()
     * the change is the same as paid amount
     */
    @Test
    public void testMakeCoffeeNoIngredient() {
        coffeeMaker.addRecipe(recipe5);
        assertEquals(50 ,coffeeMaker.makeCoffee(0, 50));
    }

    /** makeCoffee() with null recipe
     * the change is the same as paid amount
     */
    @Test
    public void testMakeCoffeeNullRecipe() {
        assertEquals(50 ,coffeeMaker.makeCoffee(0, 50));
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with string
     * Then we get an inventory exception
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddCoffeeString() throws InventoryException {
        coffeeMaker.addInventory("Coffee", "0", "0", "3");
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with string
     * Then we get an inventory exception
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddMilkString() throws InventoryException {
        coffeeMaker.addInventory("4", "Milk", "0", "3");
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with string
     * Then we get an inventory exception
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddSugarString() throws InventoryException {
        coffeeMaker.addInventory("4", "0", "Sugar", "3");
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with string
     * Then we get an inventory exception
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddChocolateString() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "0", "Chocolate");
    }

    /**
     * edit the recipe when you didn't add any.
     */
    @Test
    public void testEditNullRecipe() {
        coffeeMaker.editRecipe(0, recipe2);
        assertNull(coffeeMaker.getRecipes()[0]);
    }
}
