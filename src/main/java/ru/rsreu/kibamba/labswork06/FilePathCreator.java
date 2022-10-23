package ru.rsreu.kibamba.labswork06;


import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilePathCreator {
    private static String[] filesNameArray;
    private static Scanner in = new Scanner(System.in);
    public static List<String> filesPathList = new ArrayList<>();
    public static void inputFilesName(){
        System.out.print("Количество файлах: ");
        int fileCount = Integer.parseInt(in.next());
        in.nextLine();
        filesNameArray = new String[fileCount];
        String fileName = "";
        for(int i=0; i<filesNameArray.length;i++){
            int temp = i+1;
            System.out.print("название файла номер "+temp+" : ");
            fileName = in.nextLine();
            if(isExistFile(fileName)){
                filesPathList.add(createFilePath(fileName));
            }else{
                System.out.println("Входной файл не существует");
                break;
            }
        }
    }
    public static String createFilePath(String fileName){
        String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        String filePath = userDirectory +"\\"+ fileName + ".txt";
        return filePath;
    }
    public static boolean isExistFile(String fileName){
        return new File(createFilePath(fileName)).isFile();
    }
}
