package io;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;
import org.junit.platform.engine.support.hierarchical.ThrowableCollector;

import java.io.*;

public class TabulatedFunctionFileInputStream {
    public static void main(String[] args) {
        try{
            try(BufferedInputStream input =new BufferedInputStream( new FileInputStream("input/binary_function.bin"))){
                TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
                TabulatedFunction function = FunctionsIO.readTabulatedFunction(input, factory);
                System.out.println(function);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        try{
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите размер и значения функции: ");
            TabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
            TabulatedDifferentialOperator oper = new TabulatedDifferentialOperator(factory);
            TabulatedFunction function = FunctionsIO.readTabulatedFunction(inputReader, factory);
            System.out.println(oper.derive(function));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
