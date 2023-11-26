package io;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static jdk.internal.jimage.decompressor.CompressIndexes.*;

public final class FunctionsIO {
    private FunctionsIO() {
        throw new UnsupportedOperationException();
    }

    public static void writeTabulatedFunction(BufferedWriter outputStream, TabulatedFunction function){
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(function.getCount());
        for (Point point : function) {
            printWriter.printf("%f %f\n", point.x, point.y);
        }
        printWriter.flush();
    }
    public static void writeTabulatedFunction(BufferedOutputStream outputStream,TabulatedFunction function) throws IOException {
        DataOutputStream dataOutput = new DataOutputStream(outputStream);
        dataOutput.writeInt(function.getCount());
        for(Point point: function){
            dataOutput.writeDouble(point.x);
            dataOutput.writeDouble(point.y);
        }
        dataOutput.flush();
    }


    static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        int length = Integer.parseInt(reader.readLine());
        double[] xValue = new double[length];
        double[] yValue = new double[length];
        NumberFormat numberFormatter;
        numberFormatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));

        for (int i = 0; i < length; i++) {
            String[] pointsHalf = reader.readLine().split(" ");

            try {
                xValue[i] = numberFormatter.parse(pointsHalf[0]).doubleValue();
                yValue[i] = numberFormatter.parse(pointsHalf[1]).doubleValue();
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }
        return factory.create(xValue, yValue);
    }

    public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeObject(function);
        objectOutputStream.flush();
    }
    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int size = dataInputStream.readInt();
        double[] xValues = new double[size];
        double[] yValues = new double[size];
        for(int i = 0; i<size; ++i){
            xValues[i] = dataInputStream.readDouble();
            yValues[i] = dataInputStream.readDouble();
        }
        return factory.create(xValues, yValues);
    }
    public static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(stream);
        return (TabulatedFunction) objectInputStream.readObject();
    }

}
