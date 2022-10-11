package ru.rsreu.kibamba;

import ru.rsreu.kibamba.labswork05.FutureWorker;

public class Runner {

	public static void main(String[] args) {
		FutureWorker futureWorker = new FutureWorker();
		long startTime = System.currentTimeMillis();
		double result = futureWorker.doTask(4);
		System.out.println("Result :" + result);
		System.out.println("Time : " + (System.currentTimeMillis() - startTime) + " ms");
	}
}
