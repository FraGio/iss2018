/* Generated by AN DISI Unibo */ 
package it.unibo.mapper;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.akka.QActorMsgQueue;

public class MsgHandle_Mapper extends QActorMsgQueue{
	public MsgHandle_Mapper(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  {
		super(actorId, myCtx, outEnvView);
	}
}
