import entities.*;
import exceptions.IngredientNotFoundexception;
import exceptions.InsufficientIngredientException;
import exceptions.InsufficientMoney;
import exceptions.ReceipeNotFoundException;
import readingFiles.ReadAccountFile;
import readingFiles.ReadIngeridentFile;
import readingFiles.ReadReceipeFile;
import service.AccountHandler;
import service.IngredientHandler;
import service.ReceipeHandler;
import sun.plugin.javascript.navig.Link;

import javax.management.remote.rmi._RMIConnection_Stub;
import java.io.IOException;
import java.util.*;

public class Main {
    private static List<Sales> salesList;
    private static List<Expense> expenseList;
    private static double availableMoney;
    private static List<Ingredient> ingredientList;
    private static List<Receipe> receipeList;
    private static AccountHandler accountHandler;
    public static void main(String [] args) throws IOException {
        CommandType currentCommand = CommandType.No_Command;
        Ingredient selectIngredient=null;
        double qnt=0;
        Receipe selectReceipe=null;
        Map<Ingredient,Double> insufficientIngredients=null;
        ingredientList= ReadIngeridentFile.readIngridentlist("ResourceFiles/Inventory.txt");
        receipeList= ReadReceipeFile.readReceipeFile("ResourceFiles/Receipe.txt",ingredientList);
        availableMoney= ReadAccountFile.readAccountFile("ResourceFiles/Accounts.txt");
        salesList=new ArrayList<>();
        expenseList=new ArrayList<>();
        try {
            while (true) {
                if (currentCommand == CommandType.No_Command) {
                    int selectedNumber = displaypromt();
                    currentCommand = CommandType.values()[selectedNumber];
                } else if (currentCommand == CommandType.View_Available_Ingredients) {

                    IngredientHandler.printIngredients(ingredientList);
                    currentCommand = CommandType.No_Command;
                } else if (currentCommand==CommandType.Order_Specific_Ingredients) {
                        selectIngredient=selectIngredient();
                        currentCommand=CommandType.Input_Ingredient_Qnt;

                } else if (currentCommand==CommandType.Input_Ingredient_Qnt) {
                    qnt=inputIngredientQnt();
                    if(IngredientHandler.isPossibleToOrderIngredient(selectIngredient,qnt,availableMoney))
                    {
                        System.out.println("Ordered Successfully");
                        purchaseIngredientQty(selectIngredient,qnt);
                        currentCommand=CommandType.No_Command;
                    }
                    else {
                        throw new InsufficientMoney("Insufficient money to order");
                    }

                } else if (currentCommand == CommandType.View_Total_Sales) {
                    AccountHandler.printSales(salesList);
                    currentCommand = CommandType.No_Command;
                } else if (currentCommand == CommandType.View_Total_Expenses) {
                    AccountHandler.printExpense(expenseList);
                    currentCommand = CommandType.No_Command;
                } else if (currentCommand == CommandType.View_net_Profit) {
                    AccountHandler.printNetProfit(salesList, expenseList);
                    currentCommand = CommandType.No_Command;
                } else if (currentCommand==CommandType.Place_Order) {
                         System.out.println("Enter Receipe Name");
                         selectReceipe=selectReceipe();
                    ReceipeHandler.checkIfPossibleToPrepareReceipe(selectReceipe,ingredientList);
                    currentCommand=CommandType.Finilize_The_Order;
                } else if (currentCommand==CommandType.Order_Multiple_Ingredients) {

                       IngredientHandler.isPossibleToOrderIngredients(insufficientIngredients,availableMoney);
                       purchaseIngredients(insufficientIngredients);
                       currentCommand=CommandType.Finilize_The_Order;
                } else if (currentCommand==CommandType.Finilize_The_Order) {

                             finalizeOrder(selectReceipe);
                             System.out.println("Order finilized successfully");
                             currentCommand=CommandType.No_Command;
                } else if (currentCommand == CommandType.Exit) {
                    System.exit(0);
                }
            }
        }
        catch (InsufficientIngredientException ex)
        {
            insufficientIngredients=ex.getInsufficientIngredient();
            currentCommand=CommandType.Order_Multiple_Ingredients;
        }

        catch (Exception re)
        {
            System.out.println(re.getMessage());
            currentCommand=CommandType.No_Command;
        }
    }

    public static int displaypromt()
    {
        System.out.println("Please select one of the following command");
        System.out.println("1.View Available Ingredients");
        System.out.println("2. Order specific Ingredient");
        System.out.println("3. View Total Sales");
        System.out.println("4. View Total Expenses");
        System.out.println("5. View Net Profit");
        System.out.println("6. Place Order");
        System.out.println("7. Exit from the program");
        Scanner s=new Scanner(System.in);
        return s.nextInt();
    }
    public static Ingredient selectIngredient()
    {
        Scanner s=new Scanner(System.in);
        String name= s.nextLine();
        for (int i=0;i<ingredientList.size();i++)
        {
            if(name.equals(ingredientList.get(i).getName()))
            {
                return ingredientList.get(i);
            }
        }
        throw new IngredientNotFoundexception("Ingredient"+name+"is not found");
    }
public static double inputIngredientQnt()
{
    Scanner s=new Scanner(System.in);
    return s.nextDouble();
}

public static Receipe selectReceipe()
{
    Scanner s=new Scanner(System.in);
    String receipeName=s.nextLine();
    for(int i=0;i<receipeList.size();i++)
    {
        if(receipeList.get(i).getName().equals(receipeName))
        {
            return receipeList.get(i);
        }

    }
    throw new ReceipeNotFoundException("Recipe"+receipeName+"Not found");
}

public static void purchaseIngredientQty(Ingredient ingredientOrdered,double qtyOrdered) {
    for (int i = 0; i < ingredientList.size(); i++) {
        if (ingredientList.get(i).getName().equals(ingredientOrdered.getName())) {
            double oldQnt = ingredientList.get(i).getQnt();
            ingredientList.get(i).setQnt(oldQnt + qtyOrdered);
        }
    }
   double totalAmount=ingredientOrdered.getRate()*qtyOrdered;
    Map<Ingredient,Double> composition=new HashMap<>();
    composition.put(ingredientOrdered,qtyOrdered);
    PurchaseOrder p=new PurchaseOrder(totalAmount,composition);
    expenseList.add(new Expense(totalAmount,p,ExpenseType.Purchase));
    availableMoney-=totalAmount;
}
 public static void purchaseIngredients(Map<Ingredient,Double> ingredientsToOrder)
 {
     double moneyspent=0.0;
     for(int i=0;i<ingredientList.size();i++)
     {
         if(ingredientsToOrder.containsKey(ingredientList.get(i)))
         {
             double oldQnt=ingredientList.get(i).getQnt();
             double qtyToOrder=ingredientsToOrder.get(ingredientList.get(i));
             moneyspent+=qtyToOrder*ingredientList.get(i).getRate();
             ingredientList.get(i).setQnt(oldQnt+qtyToOrder);
         }
     }
     PurchaseOrder p=new PurchaseOrder(moneyspent,ingredientsToOrder);
     expenseList.add(new Expense(moneyspent,p,ExpenseType.Purchase));
     availableMoney-=moneyspent;
 }

   public static void finalizeOrder(Receipe receipe)
   {
       Map<Ingredient,Double> com=receipe.getComposition();
       for(int i=0;i<ingredientList.size();i++)
       {
           if(com.containsKey(ingredientList.get(i)))
           {
               double oldQty=ingredientList.get(i).getQnt();
               double newOty=com.get(ingredientList.get(i));
               ingredientList.get(i).setQnt(oldQty-newOty);
           }
       }

       Order or=new Order(receipe,receipe.getAmount());
       Sales sales=new Sales(or,receipe.getAmount());
       salesList.add(sales);
       availableMoney+=receipe.getAmount();


   }

}


