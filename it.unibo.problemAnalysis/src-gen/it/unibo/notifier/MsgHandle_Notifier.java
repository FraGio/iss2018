/* Generated by AN DISI Unibo */ 
package it.unibo.notifier;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.akka.QActorMsgQueue;

public class MsgHandle_Notifier extends QActorMsgQueue{
	public MsgHandle_Notifier(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  {
		super(actorId, myCtx, outEnvView);
	}
}
