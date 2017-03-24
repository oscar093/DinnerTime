package demo;

import java.util.ArrayList;

public class DemoDatabase {

	private ArrayList<Recipe> recipeDB = new ArrayList<Recipe>();
	
	public DemoDatabase(){
		this.makeDemoRecipes();
	}
	
	public void addRecipe(Recipe recipe){
		recipeDB.add(recipe);
	}
	
	public Recipe getRecipe(int index){
		return recipeDB.get(index);
	}
	
	public Recipe searchRecipeByName(String name){
		for(Recipe r : recipeDB){
			if(r.getName().contentEquals(name)){
				return r;
			}
		}
		return null;
	}
	
	public Recipe[] searchRecipeByCountry(String country){
		ArrayList<Recipe> rList = new ArrayList<Recipe>();
		for(Recipe r : recipeDB){
			if(r.getCountry().contentEquals(country)){
				rList.add(r);
			}
		}
		Recipe[] rArray = rList.toArray(new Recipe[rList.size()]);
		
		return rArray;
	}
	
	public void makeDemoRecipes(){
		Recipe r = new Recipe();
		r.setAuthor("Oscar");
		r.setName("Pizza");
		r.setTillag(15);
		r.addIngredient("Ost");
		r.addIngredient("tomatSås");
		r.addIngredient("champinjoner");
		r.addIngredient("Oregano");
		r.setCountry("Italien");
		
		
		Recipe r2 = new Recipe();
		r2.setAuthor("Simon");
		r2.setName("Lasagne");
		r2.setTillag(45);
		r2.addIngredient("Ost");
		r2.addIngredient("tomatSås");
		r2.addIngredient("champinjoner");
		r2.addIngredient("Oregano");
		r2.addIngredient("Zucchini");
		r2.addIngredient("Spenat");
		r2.setCountry("Skåneland");
		
		Recipe r3 = new Recipe();
		r3.setAuthor("David");
		r3.setName("Dall");
		r3.setTillag(30);
		r3.addIngredient("Lincer");
		r3.addIngredient("kummin");
		r3.addIngredient("curry");
		r3.addIngredient("Oregano");
		r3.addIngredient("Zucchini");
		r3.addIngredient("Spenat");
		r3.addIngredient("lök");
		r3.setCountry("Danmark");
		
		Recipe r4 = new Recipe();
		r4.setAuthor("Olof");
		r4.setName("köttfärssås");
		r4.setTillag(30);
		r4.addIngredient("färs");
		r4.addIngredient("muskot");
		r4.addIngredient("peppar");
		r4.addIngredient("Oregano");
		r4.addIngredient("Zucchini");
		r4.addIngredient("Spenat");
		r4.addIngredient("lök");
		r4.setCountry("Tyskland");
		
		Recipe r5 = new Recipe();
		r5.setAuthor("Jonathan");
		r5.setName("stråganoff");
		r5.setTillag(30);
		r5.addIngredient("korv");
		r5.addIngredient("muskot");
		r5.addIngredient("peppar");
		r5.addIngredient("Oregano");
		r5.addIngredient("morötter");
		r5.addIngredient("Spenat");
		r5.addIngredient("lök");
		r5.setCountry("Tyskland");
		
		this.addRecipe(r);
		this.addRecipe(r2);
		this.addRecipe(r3);
		this.addRecipe(r4);
		this.addRecipe(r5);
	}
	
}
