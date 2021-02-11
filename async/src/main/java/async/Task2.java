package async;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Task2 {
    private static List<Integer> readFile(String filename) {
        System.out.println("solver thread: " + Thread.currentThread().getId());
        List<Integer> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                result.add(Integer.parseInt(line));
                sleep(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static List<Integer> writeFile(String filename, List<Integer> result) {
        System.out.println("solver thread: " + Thread.currentThread().getId());
        try(FileWriter writer = new FileWriter(filename, false)) {
            for (int c : result) {
                System.out.println(c);
                writer.write(Integer.toString(c) + '\n');
                sleep(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<List<Integer>> listFromFile1 = CompletableFuture
                .supplyAsync(() -> readFile("file1.txt"))
                .handle((r, e) -> {
                    System.out.println("result " + r + "\nexception " + e);
                    return r;
                });
        CompletableFuture<List<Integer>> listFromFile2 = CompletableFuture
                .supplyAsync(() -> readFile("file2.txt"))
                .handle((r, e) -> {
                    System.out.println("result " + r + "\nexception " + e);
                    return r;
                });

        CompletableFuture<Void> writeSumList = listFromFile1.thenCombine(listFromFile2, (a, b) -> {
            List<Integer> sumList = new ArrayList<>();
            for (int i = 0; i < a.size(); i++) {
                sumList.add(a.get(i) + b.get(i));
            }
            return sumList;
        }).thenAccept((sumList) -> writeFile("file3.txt", sumList))
                .handle((r, e) -> {
                    System.out.println("result " + r + "\nexception " + e);
                    return r;
                });

        CompletableFuture<Void> writeProdList = listFromFile1.thenCombine(listFromFile2, (a, b) -> {
            List<Integer> prodList = new ArrayList<>();
            for (int i = 0; i < a.size(); i++) {
                prodList.add(a.get(i) * b.get(i));
            }
            return prodList;
        }).thenAccept((prodList) -> writeFile("file4.txt", prodList))
                .handle((r, e) -> {
                    System.out.println("result " + r + "\nexception " + e);
                    return r;
                });

        writeProdList.get();
        writeSumList.get();
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
