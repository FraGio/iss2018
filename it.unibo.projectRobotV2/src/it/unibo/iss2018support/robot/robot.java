package it.unibo.iss2018support.robot;

import java.util.Random;

import it.unibo.iss2018support.supportVirtualEnv.supportVirtualEnv;
import it.unibo.qactors.akka.QActor;

public class robot {
	//rappresenta lo stato del robot (on o off)
	private static boolean status = false;
	
	private static void doMove(QActor qactor) {
		Random rand = new Random();
		int x = rand.nextInt(4);
		
		switch(x) {
			case 0:
				supportVirtualEnv.mbotRight(qactor);
			case 1:
				supportVirtualEnv.mbotForward(qactor);
			case 2:
				supportVirtualEnv.mbotForward(qactor);
			case 3: 
				supportVirtualEnv.mbotForward(qactor);
			case 4:
				supportVirtualEnv.mbotRight(qactor);
		}
		
	}
	public synchronized static void startActivity(QActor qactor) {
		setStatus(true);
		
		while (status) {
			//ciclo attività robot
			
			try {
				doMove(qactor);
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("!!! Attività robot arrestata !!!");
	}
	
	public synchronized static void stopActivity(QActor qactor) {
		setStatus(false);
		supportVirtualEnv.mbotStop(qactor);
	}

	public static boolean isStatus() {
		return status;
	}

	public static void setStatus(boolean status) {
		robot.status = status;
	}
	
	
}
