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
	private static String hostName = "192.168.1.112";
	// private static String hostName = "192.168.43.84";
	private static int port = 8999;
	private static String sep = ";";
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

		String jsonString = null;

		if (msg.equals("moveForward"))
			jsonString = "{ 'type': '" + msg + "', 'arg': -1 }";
		else
			jsonString = "{ 'type': '" + msg + "', 'arg': 300 }";

		JSONObject jsonObject = new JSONObject(jsonString);
		msg = sep + jsonObject.toString() + sep;
		//System.out.println("sending msg=" + msg);
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

	public static void retriveEventFromVirtual(QActor actor) {
		String line = null;
		String type = null;
		String name = null;
		String collision = null;
		int value;

		try {
			// System.out.println("retrieving data from sonar1... ");

			while ((line = inFromServer.readLine()) != null) {
				JSONObject jsonObject = new JSONObject(line.substring(1, line.length()));
				type = jsonObject.getString("type");
				if (!type.equals("webpage-ready") && !type.equals("collision")) {
					value = Integer.valueOf(jsonObject.getJSONObject("arg").getInt("distance"));
					name = jsonObject.getJSONObject("arg").getString("sonarName");

					if (value == 3)
						mqttTools.publish(actor, "msg(coreCmdStop,event,java,none,coreCmdStop,1)");
					else {
						if (name.equals("sonar1"))
							mqttTools.publish(actor,
									"msg(roomSonar1Event,event,java,none,roomSonar1Event('" + value + "'),1)");
						if (name.equals("sonar2"))
							mqttTools.publish(actor,
									"msg(roomSonar2Event,event,java,none,roomSonar2Event('" + value + "'),1)");
					}
				}

				if (type.equals("collision")) {
					collision = jsonObject.getJSONObject("arg").getString("objectName");

					mqttTools.publish(actor,
							"msg(virtualRobotSonarEvent,event,java,none,virtualRobotSonarEvent,1)");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public static void retriveEventFromSonar1(QActor actor) {
	// String line = null;
	// String type = null;
	// String name = null;
	// int value;
	// Socket clientSocket1 = null;
	//
	// try {
	// clientSocket1 = new Socket(hostName, port);
	// BufferedReader inFromServer1 = new BufferedReader(new
	// InputStreamReader(clientSocket1.getInputStream()));
	// // System.out.println("retrieving data from sonar1... ");
	//
	// while ((line = inFromServer1.readLine()) != null) {
	// JSONObject jsonObject = new JSONObject(line.substring(1, line.length()));
	// type = jsonObject.getString("type");
	// if (!type.equals("webpage-ready") && !type.equals("collision")) {
	// value = Integer.valueOf(jsonObject.getJSONObject("arg").getInt("distance"));
	// name = jsonObject.getJSONObject("arg").getString("sonarName");
	//
	// if (name.equals("sonar1"))
	// mqttTools.publish(actor,
	// "msg(roomSonar1Event,event,java,none,roomSonar1Event(" + value + "),1)");
	//
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public static void retriveEventFromSonar2(QActor actor) {
	// String line = null;
	// String type = null;
	// String name = null;
	// int value;
	// Socket clientSocket2 = null;
	//
	// try {
	// clientSocket2 = new Socket(hostName, port);
	// BufferedReader inFromServer2 = new BufferedReader(new
	// InputStreamReader(clientSocket2.getInputStream()));
	// // System.out.println("retrieving data from sonar2... ");
	//
	// while ((line = inFromServer2.readLine()) != null) {
	// JSONObject jsonObject = new JSONObject(line.substring(1, line.length()));
	// type = jsonObject.getString("type");
	// if (!type.equals("webpage-ready") && !type.equals("collision")) {
	// value = Integer.valueOf(jsonObject.getJSONObject("arg").getInt("distance"));
	// name = jsonObject.getJSONObject("arg").getString("sonarName");
	//
	// if (name.equals("sonar2"))
	// mqttTools.publish(actor,
	// "msg(roomSonar2Event,event,java,none,roomSonar2Event(" + value + "),1)");
	//
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public static void retriveEventFromSonarRobotVirtual(QActor actor) {
	// String line = null;
	// String type = null;
	// String collision;
	//
	// try {
	// // System.out.println("retrieving data from sonar2... ");
	//
	// while ((line = inFromServer.readLine()) != null) {
	// JSONObject jsonObject = new JSONObject(line.substring(1, line.length()));
	// type = jsonObject.getString("type");
	// if (type.equals("collision")) {
	// collision = jsonObject.getJSONObject("arg").getString("objectName");
	//
	// mqttTools.publish(actor,
	// "msg(robotSonarEvent,event,java,none,robotSonarEvent(" + collision + "),1)");
	//
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
}
