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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

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

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
	}


	/**
	 * Given a coffee maker with one valid recipe
	 *
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
	 *
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
	@Test (expected = ArrayIndexOutOfBoundsException.class)
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
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than
	 * 		the coffee costs
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
	 * Given a coffee maker with the default inventory
	 * When we add inventory with string
	 * Then we get an inventory exception
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryStringException() throws InventoryException {
		coffeeMaker.addInventory("4", "0", "asdf", "3");
	}

	/**
	 * Given the positive amount of chocolate and added to chocolate's supply in the inventory
	 *
	 * Then the amount of chocolate is correctly added.
	 */
	@Test
	public void testAddInventoryCoffeePositive() throws InventoryException {
		coffeeMaker.addInventory("5", "0", "0", "0");
		assertEquals("Coffee: 20\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
	}

	/**
	 * Given the negative amount of chocolate and added to chocolate's supply in the inventory
	 * the coffee maker should not accept the negative amount and throw InventoryException
	 *
	 * @throws InventoryException if there was an error parsing the quantity
	 *   to a positive integer.
	 */
	@Test (expected = InventoryException.class)
	public void testAddInventoryCoffeeNegative() throws InventoryException {
		coffeeMaker.addInventory("-5", "0", "0", "0");
	}

	/**
	 * Given the positive amount of milk and added to milk's supply in the inventory
	 *
	 * Then the amount of milk is correctly added.
	 */
	@Test
	public void testAddInventoryMilkPositive() throws InventoryException {
		coffeeMaker.addInventory("0", "5", "0", "0");
		assertEquals("Coffee: 15\nMilk: 20\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
	}

	/**
	 * Given the negative amount of milk and added to milk's supply in the inventory
	 * the coffee maker should not accept the negative amount and throw InventoryException
	 *
	 * @throws InventoryException if there was an error parsing the quantity
	 *   to a positive integer.
	 */
	@Test (expected = InventoryException.class)
	public void testAddInventoryMilkNegative() throws InventoryException {
		coffeeMaker.addInventory("0", "-5", "0", "0");
	}

	/**
	 * Given the positive amount of sugar and added to sugar's supply in the inventory
	 *
	 * Then the amount of sugar is correctly added.
	 */
	@Test
	public void testAddInventorySugarPositive() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "5", "0");
		assertEquals("Coffee: 15\nMilk: 15\nSugar: 20\nChocolate: 15\n", coffeeMaker.checkInventory());
	}

	/**
	 * Given the negative amount of sugar and added to sugar's supply in the inventory
	 * the coffee maker should not accept the negative amount and throw InventoryException
	 *
	 * @throws InventoryException if there was an error parsing the quantity
	 *   to a positive integer.
	 */
	@Test (expected = InventoryException.class)
	public void testAddInventorySugarNegative() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "-5", "0");
	}

	/**
	 * Given the positive amount of chocolate and added to chocolate's supply in the inventory
	 *
	 * Then the amount of chocolate is correctly added.
	 */
	@Test
	public void testAddInventoryChocolatePositive() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "0", "5");
		assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 20\n", coffeeMaker.checkInventory());
	}

	/**
	 * Given the negative amount of chocolate and added to chocolate's supply in the inventory
	 * the coffee maker should not accept the negative amount and throw InventoryException
	 *
	 * @throws InventoryException if there was an error parsing the quantity
	 *   to a positive integer.
	 */
	@Test (expected = InventoryException.class)
	public void testAddInventoryChocolateNegative() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "0", "-5");
	}

	/**
	 * Coffee Maker should be able to add inventory even if all amount is 0
	 *
	 * @throws InventoryException if there was an error parsing the quantity
	 *   to a positive integer.
	 */
	@Test
	public void testAddInventoryZero() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "0", "0");
	}

	/**
	 * Check if inventory is work as intended.
	 *
	 * @throws InventoryException if there was an error parsing the quantity
	 * 	 *   to a positive integer.
	 */
	@Test
	public void testCheckInventory() throws InventoryException {
		coffeeMaker.addInventory("1", "1", "1", "1");
		assertEquals("Coffee: 16\nMilk: 16\nSugar: 16\nChocolate: 16\n", coffeeMaker.checkInventory());
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
	 * 		the coffee costs
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
	 * 		the coffee costs
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
	 *
	 * Then throws Exception money can't be negative
	 */
	@Test (expected = Exception.class)
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
	@Test (expected = ArrayIndexOutOfBoundsException.class)
	public void testMakeCoffeeOutOfBound() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.makeCoffee(100, 25);
	}
}
