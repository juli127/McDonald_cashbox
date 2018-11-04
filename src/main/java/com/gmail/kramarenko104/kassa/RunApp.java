package com.gmail.kramarenko104.kassa;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

class RunApp {

	static final int CUSOMERS_COUNT = 5;
	LinkedList<Thread> customers;

	public RunApp() {

		customers = new LinkedList<>();
		Kassa kassa = new Kassa(false);
		for (int i = 0; i < CUSOMERS_COUNT; i++) {
			Thread t = new Thread(new Customer(Integer.toString(i), kassa));
			t.start();
			System.out.println("Customer " + i + " is shopping...");
			customers.add(t);
		}

		// open kassa in some random time
		int timeToOpen = (int) (Math.random() * 4000);
		System.out.println("KASSA WILL BE OPENED in " + timeToOpen + " milliseconds");
		try {
			TimeUnit.MILLISECONDS.sleep(timeToOpen);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// kassa is set to 'open'
		System.out.println("KASSA IS OPEN ----------------------------------------");
		kassa.setWorking(true);

		// but it can be closed with 50% possibility at the nearest time
		while (thereIsCustomer() && !kassa.isStop()) {
			boolean possibilityToClose = ((int) ((Math.random() * 1000)) % 2 == 0 ? true : false);
			int timeToClose = (possibilityToClose ? (int) (Math.random() * 1000) : 0);
			if (timeToClose > 0) {
				synchronized (kassa) {
					try {
						System.out.println("by the way, KASSA WILL BE CLOSED in " + timeToClose + " milliseconds !!!! HURRY UP !!!");
						TimeUnit.MILLISECONDS.sleep(timeToClose);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println("KASSA IS CLOSED. LIVE THE STORE!!!!");
					kassa.setStop(true);
				}
			}
		}

		// be sure that all customers are processed
		for (Thread c : customers) {
			if (c.isAlive()) {
				try {
					c.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("ALL customers were processed!");
		System.out.println("================================================");
	}

	//////////////////////////////////////////////////////////////
	private boolean thereIsCustomer() {
		for (Thread c : customers) {
			if (c.isAlive()) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		new RunApp();
	}
}