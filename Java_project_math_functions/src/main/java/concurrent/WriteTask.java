package concurrent;

import functions.TabulatedFunction;

public class WriteTask implements Runnable{
    private final TabulatedFunction func;
    private final double value;
    public WriteTask(TabulatedFunction f, double v){
        this.func = f;
        this.value = v;
    }
    @Override
    public void run(){
        for( int i = 0; i < func.getCount(); ++i){
            synchronized (func) {
                func.setY(i, value);
                System.out.println("Writing for index " + i + " complete");
            }
        }
    }
}
