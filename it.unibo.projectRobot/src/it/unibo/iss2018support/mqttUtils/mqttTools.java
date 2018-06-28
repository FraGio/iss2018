package it.unibo.iss2018support.mqttUtils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import it.unibo.qactors.akka.QActor;

public class mqttTools {
	private static String topic = "unibo/qasys";
	private static int qos = 0;
	private static String broker = "tcp://192.168.1.112:1883";
//	private static String broker = "tcp://192.168.43.84:1883";
	// private static String broker = "tcp://iot.eclipse.org:1883";
//	private static String broker = "tcp://broker.hivemq.com:1883";
	private static String clientId = "MqttUtils";
	private static MemoryPersistence persistence = new MemoryPersistence();
	private static MqttClient sampleClient;

	public static void init(QActor actor) {
		try {
			sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("MqttUtils connecting to broker: " + broker);
			sampleClient.connect(connOpts);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public static void publish(QActor actor,String content) {
		try {
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
