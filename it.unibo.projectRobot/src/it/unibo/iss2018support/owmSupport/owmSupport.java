package it.unibo.iss2018support.owmSupport;

import java.time.LocalDateTime;

import it.unibo.iss2018support.mqttUtils.mqttTools;
import it.unibo.qactors.akka.QActor;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

public class owmSupport {
    public static OWM owm;
	public static void init(QActor actor) {
	
		String API_KEY = "dc281ecb96110b29542c3e8bbb8cfcb4";
		owm = new OWM(API_KEY);
	}
	
	@SuppressWarnings("deprecation")
	public static void acquireValues(QActor actor) {
        try {
        	
			CurrentWeather cwd = owm.currentWeatherByCityName("Bologna");
//	        System.out.println("City: " + cwd.getCityName());
//	       	        
//	        System.out.println("Temperature: " + (cwd.getMainData().getTempMax()-273.15)
//                    + "\'C H:"+cwd.getDateTime().getHours());

			mqttTools.publish(actor,"msg(temperatureTimeRequest,event,java,none,temperatureTimeRequest"
	        		+ "("+(cwd.getMainData().getTempMax()-273.15) + "," + LocalDateTime.now().getHour() +"),1)");

		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
