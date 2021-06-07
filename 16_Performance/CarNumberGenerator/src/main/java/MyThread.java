import java.io.FileNotFoundException;
import java.util.concurrent.Callable;

public class MyThread implements Callable<String> {

    @Override
    public String call() {


        System.out.println("start thread " + Thread.currentThread().getId());
        try {
            NumberGenerator.generateNumbers();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("finish thread " + Thread.currentThread().getId());
        return "Thread_OK";
    }
}
