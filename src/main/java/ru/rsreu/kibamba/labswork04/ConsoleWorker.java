package ru.rsreu.kibamba.labswork04;

import ru.rsreu.kibamba.labswork03.RectangleMethodIntegralCalculator;

import java.util.Scanner;

public class ConsoleWorker {
    private final TaskWorker taskWorker = new TaskWorker();

    private int getTaskCode(String text) {
        switch (text.toLowerCase()) {
            case "start":
                return 1;
            case "stop":
                return 2;
            case "await":
                return 3;
            case "exit":
                return 4;
            default:
                return 5;
        }
    }

    public void runTasks(){
        while (true) {
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            String inputCommand = scanner.nextLine();
            String[] inputCommandArray = inputCommand.split(" ");
            if (inputCommandArray.length < 1 || inputCommandArray.length > 2) {
                System.out.println("Incorrect input data format");
                continue;
            }
            if (inputCommandArray.length == 1) {
                int commandCode = getTaskCode(inputCommandArray[0]);
                if (commandCode==4) {
                    taskWorker.exit();
                    break;
                } else {
                    if (commandCode==5) {
                        System.out.println("Unknown command or incorrect input data format");
                    }
                    continue;
                }
            }
            int taskLocalID;
            try {
                taskLocalID = Integer.parseInt(inputCommandArray[1]);
            } catch (NumberFormatException e) {
                System.out.println("Parameter n should be an integer");
                continue;
            }
            int commandCode = getTaskCode(inputCommandArray[0]);
            TaskCommandEnum taskCommandEnum = TaskCommandEnum.getTaskCommandEnum(commandCode);
            taskCommandEnum.doTask(taskWorker, taskLocalID);
            System.out.println();
        }
    }

}
