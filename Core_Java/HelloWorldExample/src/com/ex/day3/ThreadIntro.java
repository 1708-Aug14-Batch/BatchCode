package com.ex.day3;

public class ThreadIntro {

	public static void main(String[] args) {

		ExtendsThread et = new ExtendsThread();
		ImplementRunnable ir = new ImplementRunnable();

		// This will run the two threads sequentially
		// It is a trivial way to use threads
		et.run();
		ir.run();

		// This is the better way to use threads
		System.out.println("Second try\n");

		et.start();
		Thread something = new Thread(ir);
		something.start();


		Runnable anonRun = new Runnable() {

			@Override
			public void run() {
				System.out.println("In anonymous class");
				for(int i = 0; i < 10; i++)
					System.out.println(i + ": in anonymous class");
			}

		};
		
		Thread anonThread = new Thread(anonRun);
		anonThread.start();
		
		
		// Lambda
		
		Runnable lambda = () -> {
			System.out.println("In lambda");
			for(int i = 0; i < 10; i++)
				System.out.println(i + ": in lambda");
		};
		
		Thread l = new Thread(lambda);
		l.start();
	}

}

// extends Thread
class ExtendsThread extends Thread {

	public void run() {

		System.out.println("In ExtendsThread");

		for(int i = 0; i < 10; i++)
			System.out.println(i + ": in ExtendsThread");
	}

}

// implements runnable
class ImplementRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("In ImplementRunnable");

		for(int i = 0; i < 10; i++)
			System.out.println(i + ": in ImplementRunnable");
	}
}