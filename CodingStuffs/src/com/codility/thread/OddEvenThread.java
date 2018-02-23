package com.codility.thread;

public class OddEvenThread {

	public static void main(String... args) {
		Printer print = new Printer();
		Thread t1 = new Thread(new TaskEvenOdd(print, 10, false));
		Thread t2 = new Thread(new TaskEvenOdd(print, 10, true));
		t1.start();
		t2.start();
	}

}

class TaskEvenOdd implements Runnable {
	private int max;
	private Printer print;
	private boolean isEvenNumber;

	TaskEvenOdd(Printer print, int max, boolean isEvenNumber) {
		this.print = print;
		this.max = max;
		this.isEvenNumber = isEvenNumber;
	}

	public void run() {

		// System.out.println("Run method");
		int number = isEvenNumber == true ? 2 : 1;
		while (number <= max) {
			if (isEvenNumber) {
				print.printEven(number);
			} else {
				print.printOdd(number);
			}
			number += 2;
		}

	}

}

class Printer {
	boolean isOdd = false;

	synchronized void printEven(int num) {
		while (isOdd == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Even: " + num);
		isOdd = false;
		notifyAll();
	}

	synchronized void printOdd(int num) {
		while (isOdd == true) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Odd: " + num);
		isOdd = true;
		notifyAll();
	}
}