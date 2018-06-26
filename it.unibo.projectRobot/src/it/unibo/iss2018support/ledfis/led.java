package it.unibo.iss2018support.ledfis;

import java.io.File;
import java.io.IOException;

import it.unibo.qactors.akka.QActor;

public class led {
	private static int status; // 0/off, 1/blink

	public static int getStatus() {
		return status;
	}

	public static void setStatus(int status) {
		led.status = status;
	}

	public static void blinkLed(QActor actor) {
		Runtime rut = Runtime.getRuntime();
		try {
			led.setStatus(1);
			rut.exec(new String[] { "Blink.sh", "blink" }, null, new File("/home/pi"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void stopLed(QActor actor) {
		Runtime rut = Runtime.getRuntime();
		try {
			led.setStatus(0);
			rut.exec(new String[] { "BlinkStop.sh", "" }, null, new File("/home/pi"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
