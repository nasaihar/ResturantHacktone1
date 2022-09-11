package service;

import entities.Ingredient;
import exceptions.InsufficientMoney;

import java.util.List;
import java.util.Map;

public class IngredientHandler {

    public static void printIngredients(List<Ingredient> ingredientList)
    {
        for (Ingredient ig:ingredientList ) {
           System.out.println(ig.toString());

}

}
    public static boolean isPossibleToOrderIngredient(Ingredient ing,double qnt,double availabeMoney)
    {
        if(availabeMoney>=ing.getRate()*qnt){
            return true;
        }
      else{
return false;}

}
  public static void isPossibleToOrderIngredients(Map<Ingredient,Double> ingredientsToOrder,double availableMoney)
  {
      double moneyrequired=0.0;
      for(Ingredient ig:ingredientsToOrder.keySet())
      {
          double   qtyOrder=ingredientsToOrder.get(ig);
          double price=ig.getRate();
          moneyrequired=qtyOrder*price;
      }
      if(availableMoney<moneyrequired)
      {
          throw new InsufficientMoney("Insufficent money to order ingredients");
      }
  }











}