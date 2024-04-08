import java.io.*;

public class AvMinMax {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); //поток для чтения с консоли
        int count =0;                                                           //число для посдсчета вхождений символа в файл
        String name = in.readLine();                                            //считываем имя файла
        String chars = in.readLine();                                           //считываем символ
        try{FileReader file = new FileReader(name);                             //открываем файл для чтения
            while (file.ready()){
                if (file.read()==chars.charAt(0))count++;                           //идем по файлу если считанный симвло равен искомому увеличиваем переменную счетчик
            }
            System.out.println(count);                                              // выводим количество вхождений
            file.close();                                                           // закрываем файл
        }catch (FileNotFoundException ex){
            System.out.println("Что-то пошло не так " + ex.getMessage());
        }
    }
}
