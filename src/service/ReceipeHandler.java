package service;

import entities.Ingredient;
import entities.Receipe;
import exceptions.InsufficientIngredientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceipeHandler {

    public static void checkIfPossibleToPrepareReceipe(Receipe receipe, List<Ingredient> ingredientList){
        Map<Ingredient,Double> composition=receipe.getComposition();
        Map<Ingredient,Double> insufficientIngredient=new HashMap<>();
        for(Ingredient ig:ingredientList)
        {
            if(composition.containsKey(ig))
            {
                double qntUsed=composition.get(ig);
                if(qntUsed>ig.getQnt())
                {
                    insufficientIngredient.put(ig,qntUsed-ig.getQnt());
                }
            }

        }
        if(insufficientIngredient.size()>0)
        {
            throw new InsufficientIngredientException(insufficientIngredient);
        }
    }


}
