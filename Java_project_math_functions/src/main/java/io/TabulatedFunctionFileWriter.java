package io;


import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

    public class TabulatedFunctionFileWriter {
        public static void main(String[] args) {
            try {
                try (BufferedWriter writerArray = new BufferedWriter(new FileWriter("output/array function.txt"));
                     BufferedWriter writerLinked = new BufferedWriter(new FileWriter("output/linked list function.txt"))) {

                    double[] xValue = {1, 2, 3, 4, 5};
                    double[] yValue = {1, 4, 8, 16, 32};
                    TabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xValue, yValue);
                    TabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(xValue,yValue);

                    io.FunctionsIO.writeTabulatedFunction(writerArray,arrayTabulatedFunction);
                    io.FunctionsIO.writeTabulatedFunction(writerLinked,linkedListTabulatedFunction);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
