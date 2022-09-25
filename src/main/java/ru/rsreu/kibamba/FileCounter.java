package ru.rsreu.kibamba;

import java.io.*;

public class FileCounter {
    public static void countBreakLine() throws FileNotFoundException{
        if(!FileWorker.filesPathList.isEmpty()){
            int breakLineCount = 0;
            for(int i = 0; i<FileWorker.filesPathList.size();i++){
                File file = new File(FileWorker.filesPathList.get(i));
                try(FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);){
                    String line = null;
                    while ((line=br.readLine())!=null){
                        breakLineCount++;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(breakLineCount>=10){
                System.out.println("Найдено суммарно как минимум 10 символов переноса строка в указанных файлах");
            }else{
                System.out.println("Нет 10 символов переноса строка в указанных файлах");
            }
        }
    }
}
