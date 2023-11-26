package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;


import java.io.*;

public class TabulatedFunctionFileOutputStream {
         public static void main(String[] args) {
         try{
             try (BufferedOutputStream outputArray = new BufferedOutputStream(new FileOutputStream("output/array_function.bin"));
                  BufferedOutputStream outputLinked = new BufferedOutputStream(new FileOutputStream("output/linked_list_function.bin"))) {


                 double[] xValue = {1, 2, 3, 4, 5};
                 double[] yValue = {5, 10, 15, 20, 25};
                 TabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xValue, yValue);
                 TabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(xValue,yValue);

                 FunctionsIO.writeTabulatedFunction(outputArray,arrayTabulatedFunction);
                 FunctionsIO.writeTabulatedFunction(outputLinked, linkedListTabulatedFunction);

             }
         }
         catch(IOException e) {
             e.printStackTrace();
         }

     }
}
