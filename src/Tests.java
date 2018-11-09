import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Tests {

    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        test();
    }

    public static void test () {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            for (int i = 0; i < 1000; i++) {
                String threadName = Thread.currentThread().getName();
                System.out.println("Hello " + threadName + "  " + i);
            }
        });
        executor.submit(() -> {
            for (int i = 0; i < 1000; i++) {
                String threadName = Thread.currentThread().getName();
                System.out.println("Hello " + threadName + "  " + i);
            }
        });

        executor.shutdown();
    }
}
