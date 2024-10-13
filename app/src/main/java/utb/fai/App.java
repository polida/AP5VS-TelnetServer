package utb.fai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class App {

    public static void main(String[] args) {

        int port = 23;
        int max_threads = 2;

        if (args.length > 1) {
            port = Integer.parseInt(args[0]);
            max_threads = Integer.parseInt(args[1]);
        } else {
            System.out.println("Usage: java -jar app.jar <port> <max_threads>, using default values");
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(max_threads);
            executor.prestartAllCoreThreads();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(new ClientThread(clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}