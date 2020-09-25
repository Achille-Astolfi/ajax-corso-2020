package com.example.corso.rest.serviceimpl;

import java.util.concurrent.ThreadLocalRandom;

class ServiceUtils {

	static String randomKey() {
		char[] chars = { randomChar(), randomChar(), };
		return new String(chars);
	}

	static char randomChar() {
		return (char) ('A' + ThreadLocalRandom.current().nextInt(26));
	}

}
