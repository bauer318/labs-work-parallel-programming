package ru.rsreu.kibamba;

import ru.rsreu.kibamba.labswork05.FutureWorker;
import ru.rsreu.kibamba.labswork08.CustomSemaphoreRealizer;

public class Runner {

	public static void main(String[] args) {
		/*FutureWorker futureWorker = new FutureWorker();
		long startTime = System.currentTimeMillis();
		double result = futureWorker.doTask(4,(currentTask,totalTask)-> System.out.println("Task done "+currentTask+"/" +totalTask));
		System.out.println("Result :" + result);
		System.out.println("Time : " + (System.currentTimeMillis() - startTime) + " ms");*/
		CustomSemaphoreRealizer customSemaphoreRealizer = new CustomSemaphoreRealizer(2);
	}
}
