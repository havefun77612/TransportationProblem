/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leastcostmethod;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class TransportationProblem {

    double[] required;
    double[] stock;
    double[][] cost;
      
    double [] value =new double[10];

  public static  LinkedList<transportationproblem.Variable> feasible = new LinkedList<transportationproblem.Variable>();

   static int stockSize;
   static int requiredSize;
    static TransportationProblem test;

    public void checkers(int stockSize, int requiredSize) {

        stock = new double[stockSize];
        required = new double[requiredSize];
        cost = new double[stockSize][requiredSize];

        for (int i = 0; i < (requiredSize + stockSize - 1); i++) {
            feasible.add(new transportationproblem.Variable());
        }

    }

    public void setStock(double value, int index) {
        stock[index] = value;
    }

    public void setRequired(double value, int index) {
        required[index] = value;
    }

    public void setCost(double value, int stock, int required) {
        cost[stock][required] = value;
    }

    /**
     * initializes the feasible solution list using the Least Cost Rule
     *
     * it differs from the North-West Corner rule by the order of candidate
     * cells which is determined by the corresponding cost
     *
     * @return double: time elapsed
     */
    public double leastCostRule() {
        long start = System.nanoTime();

        double min;
        int k = 0; //feasible solutions counter

        //isSet is responsible for annotating cells that have been allocated
        boolean[][] isSet = new boolean[stockSize][requiredSize];
        for (int j = 0; j < requiredSize; j++) {
            for (int i = 0; i < stockSize; i++) {
                isSet[i][j] = false;
                
            }
        }

        int i = 0, j = 0;
        transportationproblem.Variable minCost = new transportationproblem.Variable();
       int count2=0;
        //this will loop is responsible for candidating cells by their least cost
        while (k < (stockSize + requiredSize - 1)) {

            minCost.setValue(Double.MAX_VALUE);
            //picking up the least cost cell          
            for (int m = 0; m < stockSize; m++) {
                for (int n = 0; n < requiredSize; n++) {
                    if (!isSet[m][n]) {
                        if (cost[m][n] < minCost.getValue()) {
                            minCost.setStock(m);
                            minCost.setRequired(n);
                            minCost.setValue(cost[m][n]);
                           
                        }
                    }
                }
            }

            i = minCost.getStock();
            j = minCost.getRequired();
             value[count2]=required[i];
            System.out.println(" ?? "+i);
            count2++;
            
            //allocating stock in the proper manner
            min = Math.min(required[j], stock[i]);

            feasible.get(k).setRequired(j);
            feasible.get(k).setStock(i);
            feasible.get(k).setValue(min);
            k++;
           
            

            required[j] -= min;
            stock[i] -= min;

            //allocating null values in the removed row/column
            if (stock[i] == 0) {
                for (int l = 0; l < requiredSize; l++) {
                    isSet[i][l] = true;
                }
            } else {
                for (int l = 0; l < stockSize; l++) {
                    isSet[l][j] = true;
                }
            }
                
        }
        count2=0;

        return (System.nanoTime() - start) * 1.0e-9;

    }

    public double getSolution() {
        double result = 0;
        int count=0;
        for (transportationproblem.Variable x : feasible) {
            result += x.getValue() * cost[x.getStock()][x.getRequired()];
            System.out.println(value[count]+"  "+ cost[x.getStock()][x.getRequired()]+" " + value[9]);
            
            count++;
        }
        count=0;
        return result;

    }

    
    static int s;
    static int r;

    public static void main(String[] args) throws IOException {
        test=new TransportationProblem();

        new MainFrame(test).setVisible(true);


    }

    public void last() {
        
        test.leastCostRule();

        for (transportationproblem.Variable t : test.feasible) {
            System.out.println(t);
        }

        System.out.println("Target function: " + test.getSolution());
    }

}
