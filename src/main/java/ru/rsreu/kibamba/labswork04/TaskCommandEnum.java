package ru.rsreu.kibamba.labswork04;

import ru.rsreu.kibamba.labswork03.RectangleMethodIntegralCalculator;

public enum TaskCommandEnum {
    START(1){
       public void doTask(TaskWorker taskWorker,int taskLocalID){
           Runnable task = () -> {
               try {
                   long startTime = System.currentTimeMillis();
                   RectangleMethodIntegralCalculator rectangleMethodIntegralCalculator = new RectangleMethodIntegralCalculator(getErrorRate(taskLocalID));
                   double result = rectangleMethodIntegralCalculator.calculateIntegral(A, B, x -> {
                       return Math.sin(x) * x;
                   });
                   System.out.println();
                   System.out.println("\033[0;1m Task completed");
                   System.out.println("\033[0;1m Result :" + result);
                   System.out.println("\033[0;1m Time : " + (System.currentTimeMillis() - startTime) + " ms");
                   System.out.println();
                   System.out.print("> ");

               } catch (InterruptedException interruptedException) {

               }
           };
           taskWorker.start(task);

        }
    },
    STOP(2) {
        public void doTask(TaskWorker taskWorker,int taskLocalID) {
            taskWorker.stop(taskLocalID);
        }
    },
    AWAIT(3){
        public void doTask(TaskWorker taskWorker,int taskLocalID){
            try {
                taskWorker.await(taskLocalID);
                ConsoleWorker.clearConsole();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    },
    EXIT(4){
        public void doTask(TaskWorker taskWorker,int taskLocalID){
            taskWorker.exit();
        }
    },
    UNKNOWN(5){
        public void doTask(TaskWorker taskWorker,int taskLocalID){
            System.out.println("Unknown command");
        }
    };
    TaskCommandEnum(int code){
        this.code = code;
    }
    private int code;
    private static final double A = 0.0;
    private static final double B = 1.0;
    public abstract void doTask(TaskWorker taskWorker,int taskLocalID);
    private static double getErrorRate(int n) {
        return Math.pow(10, -n);
    }
    public static TaskCommandEnum getTaskCommandEnum(int code){
        for(TaskCommandEnum taskCommandEnum:values()){
            if(code ==taskCommandEnum.code){
                return taskCommandEnum;
            }
        }
        return UNKNOWN;
    }

}
