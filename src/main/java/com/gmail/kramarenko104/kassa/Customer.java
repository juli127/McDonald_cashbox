package com.gmail.kramarenko104.kassa;

import java.util.concurrent.TimeUnit;

public class Customer implements Runnable {

	private String name;
	private Kassa kassa;

	public Customer(String name, Kassa kassa) {
		this.name = name;
		this.kassa = kassa;
	}

	@Override
	public void run() {
		while (true) {
			while (kassa.isWorking() && !kassa.isOccupied()) {
				synchronized (kassa) {
					while (kassa.isStop()) {
						System.out.println(">>>> The store closed, Customer " + name + " just exit the store now...");
						return;
					}
					System.out.println("Customer " + name + " occupies the  kassa on 3 sec..");
					kassa.setOccupied(true);
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Customer " + name + " frees the kassa...");
					kassa.setOccupied(false);
					System.out.println("Customer " + name + " exit the  store...");
					System.out.println("............................................");
					return;
				}

			}
		}
	}
}
