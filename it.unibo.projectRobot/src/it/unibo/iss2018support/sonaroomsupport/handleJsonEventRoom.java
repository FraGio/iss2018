package it.unibo.iss2018support.sonaroomsupport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.JSONObject;

import it.unibo.iss2018support.mqttUtils.mqttTools;
import it.unibo.qactors.akka.QActor;

/**
 * questa classe permette di acquisire valori dai sonar virtuali e pubblicarli
 * su mqtt broker, in modo che la mind possa riceverli
 */
public class handleJsonEventRoom {
	private static String hostName = "localhost";
	private static int port = 8999;
	private static String sep = ";";
	private static int lastvaluesonar1;
	private static int lastvaluesonar2;
	protected static Socket clientSocket;
	protected static PrintWriter outToServer;
	protected static BufferedReader inFromServer;

	public static void initClientConn(QActor actor) throws Exception {
		clientSocket = new Socket(hostName, port);
		outToServer = new PrintWriter(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public static void sendCmd(String msg) throws Exception {
		if (outToServer == null)
			return;
		String jsonString = "{ 'type': '" + msg + "', 'arg': 800 }";
		JSONObject jsonObject = new JSONObject(jsonString);
		msg = sep + jsonObject.toString() + sep;
		System.out.println("sending msg=" + msg);
		outToServer.println(msg);
		outToServer.flush();
	}

	protected void println(String msg) {
		System.out.println(msg);
	}

	public static void mbotForward(QActor actor) {
		try {
			sendCmd("moveForward");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mbotBackward(QActor actor) {
		try {
			sendCmd("moveBackward");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mbotLeft(QActor actor) {
		try {
			sendCmd("turnLeft");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mbotRight(QActor actor) {
		try {
			sendCmd("turnRight");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mbotStop(QActor actor) {
		try {
			sendCmd("alarm");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void retriveEventFromSonar1(QActor actor) {
		String line = null;
		String name = null;
		String type = null;
		int value;
		lastvaluesonar1 = Integer.MAX_VALUE;

		try {
			// System.out.println("retrieving data from sonar1... ");

			while ((line = inFromServer.readLine()) != null) {
				JSONObject jsonObject = new JSONObject(line.substring(1, line.length()));
				type = jsonObject.getString("type");
				if (!type.equals("webpage-ready") && !type.equals("collision")) {
					name = jsonObject.getJSONObject("arg").getString("sonarName");
					value = Integer.valueOf(jsonObject.getJSONObject("arg").getInt("distance"));

					// System.out.println(name + "|" + value);
					if (lastvaluesonar1 != value) {
						lastvaluesonar1 = value;
						mqttTools.publish(actor,
								"msg(roomSonarEvent,event,java,none,roomSonarEvent(" + name + "," + value + "),1)");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void retriveEventFromSonar2(QActor actor) {
		String line = null;
		String name = null;
		String type = null;
		int value;
		lastvaluesonar2 = Integer.MAX_VALUE;

		try {
			// System.out.println("retrieving data from sonar2... ");

			while ((line = inFromServer.readLine()) != null) {
				JSONObject jsonObject = new JSONObject(line.substring(1, line.length()));
				type = jsonObject.getString("type");
				if (!type.equals("webpage-ready") && !type.equals("collision")) {

					name = jsonObject.getJSONObject("arg").getString("sonarName");
					value = Integer.valueOf(jsonObject.getJSONObject("arg").getInt("distance"));

					// System.out.println(name + "|" + value);
					if (lastvaluesonar2 != value) {
						lastvaluesonar2 = value;
						mqttTools.publish(actor,
								"msg(roomSonarEvent,event,java,none,roomSonarEvent(" + name + "," + value + "),1)");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
