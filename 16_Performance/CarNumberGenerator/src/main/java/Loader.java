import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loader {

    private final static int THREADS = 4;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        ExecutorService service = Executors.newFixedThreadPool(THREADS);

        List<Callable<String>> listOfThreads = new ArrayList<>();

        for (int i = 0; i < THREADS; i++) {

            MyThread myThread = new MyThread();
            //myThread.call();
            listOfThreads.add(myThread);
        }

        try {
            service.invokeAll(listOfThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();

        System.out.println((System.currentTimeMillis() - start) / 1000+ " s");
    }
}