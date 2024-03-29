/* Generated by AN DISI Unibo */ 
package it.unibo.sonar2;
import it.unibo.qactors.PlanRepeat;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.StateExecMessage;
import it.unibo.qactors.QActorUtils;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.action.IMsgQueue;
import it.unibo.qactors.akka.QActor;
import it.unibo.qactors.StateFun;
import java.util.Stack;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.action.ActorTimedAction;
public abstract class AbstractSonar2 extends QActor { 
	protected AsynchActionResult aar = null;
	protected boolean actionResult = true;
	protected alice.tuprolog.SolveInfo sol;
	protected String planFilePath    = null;
	protected String terminationEvId = "default";
	protected String parg="";
	protected boolean bres=false;
	protected IActorAction action;
	 
	
		protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
			return outEnvView;
		}
		public AbstractSonar2(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/sonar2/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/sonar2/plans.txt";
	  	}
		@Override
		protected void doJob() throws Exception {
			String name  = getName().replace("_ctrl", "");
			mysupport = (IMsgQueue) QActorUtils.getQActor( name ); 
			initStateTable(); 
	 		initSensorSystem();
	 		history.push(stateTab.get( "init" ));
	  	 	autoSendStateExecMsg();
	  		//QActorContext.terminateQActorSystem(this);//todo
		} 	
		/* 
		* ------------------------------------------------------------
		* PLANS
		* ------------------------------------------------------------
		*/    
	    //genAkkaMshHandleStructure
	    protected void initStateTable(){  	
	    	stateTab.put("handleToutBuiltIn",handleToutBuiltIn);
	    	stateTab.put("init",init);
	    	stateTab.put("emituserCmd",emituserCmd);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "sonar2 tout : stops");  
	    		repeatPlanNoTransition(pr,myselfName,"application_"+myselfName,false,false);
	    	}catch(Exception e_handleToutBuiltIn){  
	    		println( getName() + " plan=handleToutBuiltIn WARNING:" + e_handleToutBuiltIn.getMessage() );
	    		QActorContext.terminateQActorSystem(this); 
	    	}
	    };//handleToutBuiltIn
	    
	    StateFun init = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("init",-1);
	    	String myselfName = "init";  
	    	temporaryStr = "\"sonar 2 START\"";
	    	println( temporaryStr );  
	    	//switchTo emituserCmd
	        switchToPlanAsNextState(pr, myselfName, "sonar2_"+myselfName, 
	              "emituserCmd",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun emituserCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("emituserCmd",-1);
	    	String myselfName = "emituserCmd";  
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(15000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "emituserCmd";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = "\"Sonar2: robot rilevato molto vicino!\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "userCmd(X)","userCmd(\"STOP\")", guardVars ).toString();
	    	emit( "userCmd", temporaryStr );
	    	repeatPlanNoTransition(pr,myselfName,"sonar2_"+myselfName,false,false);
	    }catch(Exception e_emituserCmd){  
	    	 println( getName() + " plan=emituserCmd WARNING:" + e_emituserCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//emituserCmd
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
