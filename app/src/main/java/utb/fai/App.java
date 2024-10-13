package utb.fai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.SimpleTimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class App {

    public static void main(String[] args) {

        int port = 23;
        int max_threads = 5;

        if (args.length > 1) {
            port = Integer.parseInt(args[0]);
            max_threads = Integer.parseInt(args[1]);
        }
        else{
            System.out.println("Usage: java -jar app.jar <port> <max_threads>, using default values");
        }

        // Implement input parameter processing
        
        // Implementation of the main server loop

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(max_threads);
            while (true) {
                try {
                    System.out.println(executor.getActiveCount());
                    if (executor.getActiveCount() == max_threads) {
                        continue;
                    }
                    executor.execute(new ClientThread(serverSocket.accept()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}