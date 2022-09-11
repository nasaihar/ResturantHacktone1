package service;

import entities.Expense;
import entities.Sales;

import java.util.List;

public class AccountHandler {

    public static void printSales(List<Sales> saleList)
    {
        for (Sales sale:saleList) {
            System.out.println(sale.toString());
        }
    }
    public static void printExpense(List<Expense> expenseList)
    {
        for (Expense exp:expenseList)
        {
            System.out.println(exp.toString());
        }

         }

     public static void printNetProfit(List<Sales> salesList,List<Expense> expenseList)
     {
         double netProfit=0;
         for (Sales sale:salesList) {

             netProfit+=sale.getAmount();
         }
         for (Expense exp:expenseList) {

             netProfit-=exp.getAmount();
         }
         System.out.println("Net profit is"+netProfit);
     }
}
