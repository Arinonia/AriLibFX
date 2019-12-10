package fr.arinonia.utils;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import fr.arinonia.AriLibFX;


public class Utils {

	public static void log(String msg, boolean error) {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String str = dateTime.format(timeFormatter);
		if (error) {
			System.err.println("[" + str + "]" + "[" + AriLibFX.getProgramName() + " @Arinonia] " + msg);
		} else {
			System.out.println("[" + str + "]" + "[" + AriLibFX.getProgramName() + " @Arinonia] " + msg);
		}
	}

	/**
	 * 
	 * @return memory disponible
	 */
	public static final long getPhysicalMemoryBytes() {
		return ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean())
				.getTotalPhysicalMemorySize() / (1024 * 1024 * 1024);
	}

	/**
	 * 
	 * @return true if java 64 bits false if java 32 bits
	 */
	public static boolean hasJava64Bits() {
		if (System.getProperty("sun.arch.data.model").contains("64")) {
			return true;
		} else {
			return false;
		}
	}

	
}
