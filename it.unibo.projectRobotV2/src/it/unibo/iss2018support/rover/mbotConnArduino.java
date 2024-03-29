package it.unibo.iss2018support.rover;
import org.apache.commons.lang3.math.NumberUtils;

import it.unibo.iss2018support.mbot.serial.JSSCSerialComm;
import it.unibo.iss2018support.mbot.serial.SerialPortConnSupport;
import it.unibo.iss2018support.mqttUtils.mqttTools;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

//Event robotSonarEvent : robotSonarEvent ( DISTANCE ) //NAME: NOME SONAR, DISTANCE: DISTANZA DALL'OGGETTO RILEVATO
//msg(robotSonarEvent,event,java,none,robotSonarEvent("+DISTANZA+"),1)

public class mbotConnArduino {
private static SerialPortConnSupport conn = null;
private static JSSCSerialComm serialConn;
private static double dataSonar = 0;
private static String curDataFromArduino;
private static QActor curActor ;

	public static void initRasp(QActor actor)   {
		init( "/dev/ttyUSB0" );
		curActor = actor;
	}
	public static void initPc(QActor actor)   {
		init( "COM6" );
		curActor = actor;
	}

 	private static void init(String port)   {
		try {
	 		System.out.println("---->MbotConnArduino starts");
			serialConn = new JSSCSerialComm(null);
			conn = serialConn.connect(port);	//returns a SerialPortConnSupport
			if( conn == null ) {
				System.out.println("CONNECTION WITH ARDUINO NULL!!");
				return;
			}
 			curDataFromArduino = conn.receiveALine();
 			System.out.println("---->CONNECTION WITH ARDUINO ALL RIGHT!!");
			System.out.println("---->MbotConnArduino received:" + dataSonar);
 			getDataFromArduino();
		}catch( Exception e) {
			System.out.println("MbotConnArduino ERROR" + e.getMessage());
		}
	}
	
	private static  void getDataFromArduino() {
		new Thread() {
			public void run() {
				
				try {
					double lastValue = 0D;
					System.out.println("MbotConnArduino getDataFromArduino STARTED"  );
					while(true) {
						try {
							curDataFromArduino = conn.receiveALine();
 	 						//System.out.println("MbotConnArduino received:" + curDataFromArduino );
							if(NumberUtils.isNumber(curDataFromArduino.trim())) {
								
	 							double v = Double.parseDouble(curDataFromArduino.trim());
//	 							System.out.println("MbotConnArduino222 sonar:" + v);
								//handle too fast change
	 							double delta =  Math.abs( v - dataSonar);
	 							if( delta < 15 && delta > 0.5 ) {
	 								
										dataSonar = v;
										//System.out.println("MbotConnArduino sonar:" + dataSonar);
										
										// PUBLISH ON MQTT BROKER 
										mqttTools.publish(null,"msg(realRobotSonarEvent,event,java,none,realRobotSonarEvent("+dataSonar+"),1)");
		//								QActorUtils.raiseEvent(curActor, curActor.getName(), "realSonar", 
		//										"sonar( DISTANCE )".replace("DISTANCE", ""+dataSonar ));
										lastValue = dataSonar;
	 								
	 							}
							}
						}catch (NumberFormatException e1) {
							System.out.println("MbotConnArduino ERROR NUMBERFORMAT:" + e1.getMessage());
						} catch (Exception e) {
 							System.out.println("MbotConnArduino ERROR:" + e.getMessage());
						}
					}
				} catch (Exception e) {
  					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public static void mbotForward(QActor actor) {
 		try { if( conn != null ) conn.sendCmd("w"); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotBackward(QActor actor) {
		try { if( conn != null ) conn.sendCmd("s"); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotLeft(QActor actor) {
		try { if( conn != null ) conn.sendCmd("a"); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotRight( QActor actor ) {
		try { if( conn != null ) conn.sendCmd("d"); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotStop(QActor actor) {
		try { if( conn != null ) conn.sendCmd("h"); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotLinefollow( QActor actor ) {
		try { if( conn != null ) conn.sendCmd("f"); } catch (Exception e) {e.printStackTrace();}
	}
}
