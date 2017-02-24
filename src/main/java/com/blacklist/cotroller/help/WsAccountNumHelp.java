package com.blacklist.cotroller.help;

import java.util.concurrent.atomic.AtomicInteger;

public class WsAccountNumHelp {
	private static AtomicInteger count = new AtomicInteger(0);

	public static void add() {
		count.incrementAndGet();
	}

	public static void subtract() {
		count.decrementAndGet();
	}

	public static int get() {
		return count.get();
	}
}
