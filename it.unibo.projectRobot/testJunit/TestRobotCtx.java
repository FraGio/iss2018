package it.unibo.iss2018support.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import alice.tuprolog.SolveInfo;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

import it.unibo.ctxRobot.*;

public class TestRobotCtx {
	private QActor mind = null;
	private QActor movecorelogic = null;
	private QActor fixedobstaclemanager = null;
	private QActor led = null;
	private QActor ledhuelamp = null;
	private QActor realrobot = null;
	private QActor virtualrobot = null;
	private QActor notifier = null;
	private QActor mapper = null;
	private QActor sonar1 = null;
	private QActor sonar2 = null;

	@Before
	public void systemSetUp() throws Exception {
		System.out.println("============== test: inizio inizializzazione");
		MainCtxRobot.initTheContext();

		while (mind == null || movecorelogic == null || fixedobstaclemanager == null || led == null
				|| ledhuelamp == null || realrobot == null || virtualrobot == null || notifier == null || mapper == null
				|| sonar1 == null || sonar2 == null) {
			Thread.sleep(250);

			mind = QActorUtils.getQActor("mind_ctrl");
			movecorelogic = QActorUtils.getQActor("movecorelogic_ctrl");
			fixedobstaclemanager = QActorUtils.getQActor("fixedobstaclemanager_ctrl");
			led = QActorUtils.getQActor("led_ctrl");
			ledhuelamp = QActorUtils.getQActor("ledhuelamp_ctrl");
			realrobot = QActorUtils.getQActor("realrobot_ctrl");
			virtualrobot = QActorUtils.getQActor("virtualrobot_ctrl");
			notifier = QActorUtils.getQActor("mind_ctrl");
			mapper = QActorUtils.getQActor("mapper_ctrl");
			sonar1 = QActorUtils.getQActor("sonar1_ctrl");
			sonar2 = QActorUtils.getQActor("sonar2_ctrl");

		}

		Thread.sleep(10000);
		System.out.println("============== test: tutto inizializzato");
	}

	@After
	public void terminate() {
		System.out.println("============== test: terminato");
	}

	@Test
	public void aTestKB() {
		System.out.println("============== test: kb mind robot");
		try {

			// testo che inizialmente il led fisico sia spento
			SolveInfo sol = mind.solveGoal("model( type(actuator,leds), name(ledfisico), value(off) )");
			if (sol.isSuccess()) {
				System.out.println("Led fisico spento");
				assertTrue("", sol.isSuccess());
			} else {
				System.out.println("WARNING ... ");
				fail("aTest STRANGE BEHAVIOUR");
			}

			// testo che inizialmente la led hue lamp sia spenta
			sol = mind.solveGoal("model( type(actuator,leds), name(ledhuelamp), value(off) )");
			if (sol.isSuccess()) {
				System.out.println("Led hue lamp spenta");
				assertTrue("", sol.isSuccess());
			} else {
				System.out.println("WARNING ... ");
				fail("aTest STRANGE BEHAVIOUR");
			}

		} catch (Exception e) {
			System.out.println("ERROR=" + e.getMessage());
			fail("aTest " + e.getMessage());
		}
	}

	@Test
	public void aTestTempoEdOra() {
		System.out.println("============== test: valori tempo ed ora");
		try {
			String API_KEY = "dc281ecb96110b29542c3e8bbb8cfcb4";
			OWM owm = new OWM(API_KEY);
			CurrentWeather cwd = owm.currentWeatherByCityName("Bologna");
			double currVal = (cwd.getMainData().getTempMax()-273.15);
			
			
			// testo che l'orario sia coerente con quello corrente
			SolveInfo sol = mind.solveGoal(
					"model( type(data,timer), name(timevalue), value(" + LocalDateTime.now().getHour() + ") )");
			if (sol.isSuccess()) {
				System.out.println("Tempo ok");
				assertTrue("", sol.isSuccess());
			} else {
				System.out.println("WARNING ... ");
				fail("aTest STRANGE BEHAVIOUR");
			}

			// testo che la temperatura sia coerente con quella data
			// ATTENZIONE !!!! METTI LA TEMPERATURA CORRENTE!!!!
			sol = mind.solveGoal("model( type(data,temperature), name(temperaturevalue), value("+currVal+") )");
			if (sol.isSuccess()) {
				System.out.println("Temperatura ok");
				assertTrue("", sol.isSuccess());
			} else {
				System.out.println("WARNING ... ");
				fail("aTest STRANGE BEHAVIOUR");
			}
		} catch (Exception e) {
			System.out.println("ERROR=" + e.getMessage());
			fail("aTest " + e.getMessage());
		}
	}

	@Test
	public void aTestCoreLogic() {
		System.out.println("============== test: core start-stop");
		try {
			// testo che la corelogic si avvii e si fermi
			mind.emit("coreCmdStart", "coreCmdStart");
			Thread.sleep(4000); // pulisce ...
			mind.emit("coreCmdStop", "coreCmdStop");
			Thread.sleep(4000);
			assertTrue(mind.getOutputView().getCurVal().replaceAll("\"", "").equals("[INFO] Corelogic fermata"));

		} catch (Exception e) {
			System.out.println("ERROR=" + e.getMessage());
			fail("aTest " + e.getMessage());
		}
	}

	@Test
	public void aTestUserCmdStart() {
		System.out.println("============== test: user cmd");
		try {

			// !!! NON PUOI FARLO ASSIEME A aTestCoreLogic per quesitone di stati ... una
			// volta finito, giustamente non torni indietro!!!
			// !!! se vuoi provarlo commenta aTestCoreLogic

			// // testo che dopo il comando di start il robot sia in movimento
			// sonar1.emit("roomSonar1Event", "roomSonar1Event(X)");
			// Thread.sleep(3000);
			// mind.emit("userCmd", "userCmd('START')");
			// Thread.sleep(3000);
			//
			// SolveInfo sol = mind.solveGoal("model( type(status,robot),
			// name(virtualrobotstatus), value(on))");
			// if (sol.isSuccess()) {
			// System.out.println("Robot on ok");
			// assertTrue("", sol.isSuccess());
			// } else {
			// System.out.println("WARNING ... ");
			// fail("aTest STRANGE BEHAVIOUR");
			// }
			//
			// // testo che dopo il comando di start il robot sia in movimento
			// mind.emit("userCmd", "userCmd('STOP')");
			// Thread.sleep(3000);
			//
			// sol = mind.solveGoal("model( type(status,robot), name(virtualrobotstatus),
			// value(off))");
			// if (sol.isSuccess()) {
			// System.out.println("Robot on off");
			// assertTrue("", sol.isSuccess());
			// } else {
			// System.out.println("WARNING ... ");
			// fail("aTest STRANGE BEHAVIOUR");
			// }

		} catch (Exception e) {
			System.out.println("ERROR=" + e.getMessage());
			fail("aTest " + e.getMessage());
		}
	}

	@Test
	public void aTestRobotFisico() {
		System.out.println("============== test: robot fisico");
		try {
			// testo il robot fisico
			movecorelogic.emit("robotCmd", "robotCmd('w')");
			Thread.sleep(2000);

			// sempre null.. error?
			System.out.println("--------------------------- " + virtualrobot.getCurrentEvent());

		} catch (Exception e) {
			System.out.println("ERROR=" + e.getMessage());
			fail("aTest " + e.getMessage());
		}
	}

	@Test
	public void aTestRobotVirtual() {
		System.out.println("============== test: robot virtual");
		try {
			// testo il robot virtuale
			movecorelogic.emit("robotCmd", "robotCmd('w')");
			Thread.sleep(2000);

			// sempre null.. error?
			System.out.println("--------------------------- " + realrobot.getCurrentEvent());

		} catch (Exception e) {
			System.out.println("ERROR=" + e.getMessage());
			fail("aTest " + e.getMessage());
		}
	}

}
