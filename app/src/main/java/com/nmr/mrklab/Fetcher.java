package com.nmr.mrklab;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Fetcher {
    String  ip;

    public void setIp(String ip) {
        this.ip = ip;
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();

    Callable<String> task = () -> {
        StringBuilder sb = new StringBuilder();
        // Perform background work
        try (Socket nmrSocket = new Socket( ip, 80)) {
            if (nmrSocket.isConnected()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(nmrSocket.getInputStream()));
                OutputStream writer = nmrSocket.getOutputStream();
                writer.write("GET /MRK.txt\r\n\r\n".getBytes());
                @SuppressWarnings("ReassignedVariable") String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    if (line.isEmpty())
                        sb.append(System.lineSeparator());
                }
            }
        } catch(IOException e){
            System.err.println("Uncaught Exception: " + e.getMessage());
        }
        return sb.toString();
    };

    Future<String> future = executor.submit(task);

    public String fetch() {
        try {
            String fetched;
            fetched = future.get();
            closeConnection();
            return fetched;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        // graceful shutdown
        executor.shutdown();
    }
}