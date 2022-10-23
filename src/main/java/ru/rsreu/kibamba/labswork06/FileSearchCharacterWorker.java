package ru.rsreu.kibamba.labswork06;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class FileSearchCharacterWorker {
    private FileCharacterSearcher fileCharacterSearcher;
    private ExecutorService executorService;

    private List<String> fileNameList;
    private static final int TASK_WAITING_TIME = 5;

    public FileSearchCharacterWorker(int numberCharacterSearched, String... fileNames) {
        fileNameList = new ArrayList<>();
        setFileNameList(fileNames);
        fileCharacterSearcher = new FileCharacterSearcher(numberCharacterSearched);
    }

    private void setFileNameList(String... fileNames) {
        for (int i = 0; i < fileNames.length; i++) {
            fileNameList.add(fileNames[i]);
        }
    }

    public String search(char ch) {
        executorService = Executors.newFixedThreadPool(fileNameList.size());
        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 0; i < fileNameList.size(); i++) {
            String fileName = fileNameList.get(i);
            Future<String> future = executorService.submit(() -> searchCharacterInFileText(fileName, ch));
            futureList.add(future);
        }
        AtomicReference<String> result = new AtomicReference<>("");
        futureList.forEach(future -> {
            try {
                String value = future.get(TASK_WAITING_TIME, TimeUnit.MINUTES);
                result.set(value);
            } catch (InterruptedException interuruptedException) {
                interuruptedException.printStackTrace();
            } catch (ExecutionException ex) {
                throw new RuntimeException();
            } catch (TimeoutException timeOutException) {
                timeOutException.printStackTrace();
            } catch (CancellationException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        return result.get();
    }

    private String searchCharacterInFileText(String fileName, char ch) {
        return fileCharacterSearcher.search(fileName, ch);
    }
}
