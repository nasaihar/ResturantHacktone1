package exceptions;

import entities.Ingredient;

import java.util.Map;

public class InsufficientIngredientException extends RuntimeException{

    private  Map<Ingredient,Double> insufficientIngredient;

    public Map<Ingredient, Double> getInsufficientIngredient() {
        return insufficientIngredient;
    }

    public InsufficientIngredientException(Map<Ingredient,Double>insufficientIngredient)
    {
        this.insufficientIngredient=insufficientIngredient;
    }

}
