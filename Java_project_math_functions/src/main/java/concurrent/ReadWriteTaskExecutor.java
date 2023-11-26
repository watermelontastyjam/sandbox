package concurrent;

import functions.*;

public class ReadWriteTaskExecutor {
    public static void main(String[] args) {
        TabulatedFunction func = new LinkedListTabulatedFunction(new ConstantFunction(-1),1,1000,100);
        ReadTask read = new ReadTask(func);
        WriteTask write = new WriteTask(func, 0.5);
        Thread thread1 = new Thread(write, "WriteTask");
        Thread thread2 = new Thread(read,"ReadTask");
        thread1.start();
        thread2.start();

    }
}
