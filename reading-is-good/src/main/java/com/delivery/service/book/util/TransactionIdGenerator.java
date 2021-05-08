package com.delivery.service.book.util;

import java.util.UUID;

public class TransactionIdGenerator {

	private static final int DEFAULT_LENGTH = 18;

	private TransactionIdGenerator() {
	}

	/**
	 * 
	 * @return return random number with length of 18
	 */
	public static String getTransactionId() {
		return getTransactionIdWithLength(DEFAULT_LENGTH);
	}

	public static String getTransactionIdWithLength(int length) {
		return getTransactionIdWithLength(length, '0');
	}

	/**
	 * return random number with given length
	 * @param length
	 * @param paddingChar
	 * @return
	 */
	public static String getTransactionIdWithLength(int length, char paddingChar) {
		String randomUid = Long.toString(getLongUUID());
		if (randomUid.length() < length) {
			for (int j = randomUid.length(); j < length; j++) {
				randomUid = randomUid + paddingChar;
			}
		} else if (randomUid.length() > length) {
			randomUid = randomUid.substring(randomUid.length() - length, randomUid.length());
		}

		return randomUid;
	}

	public static long getLongUUID() {
		UUID uuid = UUID.randomUUID();
		return Math.abs(uuid.getMostSignificantBits());
	}

}
