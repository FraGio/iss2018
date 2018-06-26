package it.unibo.iss2018support.utils;
import it.unibo.qactors.akka.QActor;

public class robotMixMoves { 
  
	public static void moveRobotAndAvatar(QActor qa, String move, String speedStr, String timeStr) {
		try { 
			int moveTime  = Integer.parseInt(timeStr);
			int moveSpeed = Integer.parseInt(speedStr);			
			switch( move ) {
				case "forward" :{
					it.unibo.iss2018support.rover.mbotConnArduino.mbotForward(qa);
					qa.execUnity("rover","forward", moveTime, moveSpeed, 0); 
					break;
				}
				case "backward" :{
 					it.unibo.iss2018support.rover.mbotConnArduino.mbotBackward(qa);
 					qa.execUnity("rover","backward", moveTime, moveSpeed, 0);
					break;
				}
				case "left" :{ 
					it.unibo.iss2018support.rover.mbotConnArduino.mbotLeft(qa);
					qa.execUnity("rover","left", moveTime, moveSpeed, 0);
					break;
				}
				case "right" :{
					it.unibo.iss2018support.rover.mbotConnArduino.mbotRight(qa);
					qa.execUnity("rover","right", moveTime, moveSpeed, 0);
					break;
				}
				case "stop" :{
					it.unibo.iss2018support.rover.mbotConnArduino.mbotStop(qa);
					qa.execUnity("rover","stop", moveTime, moveSpeed, 0);
					break;
				}	 				  
			}						
 		} catch (Exception e) { e.printStackTrace(); } 
	}
 }
