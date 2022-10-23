package ru.rsreu.kibamba.labswork06;

import java.io.FileReader;

public class FileCharacterSearcher {
    private int count;
    private int numberCharacterSearched;


    public FileCharacterSearcher(int numberCharacterSearched){
        this.numberCharacterSearched = numberCharacterSearched;
        count = 0;
    }
    public synchronized String search(String fileName, char ch){
        try(FileReader fileReader = new FileReader(FilePathCreator.createFilePath(fileName))){
            int charRead = fileReader.read();
            while (charRead != -1){
                if(Thread.currentThread().isInterrupted()){
                    fileReader.close();
                    return Thread.currentThread().getName()+" is interrupted ";
                }
                if((char) charRead==ch){
                    count++;
                }
                if(count== numberCharacterSearched){
                    return "Find";
                }
                charRead = fileReader.read();
            }
        }catch (Exception e){
            System.out.println("File "+fileName+" not found");
        }
        return "There are not at least 10 " + "\'"+ch+"\' characters in these files";
    }

}
