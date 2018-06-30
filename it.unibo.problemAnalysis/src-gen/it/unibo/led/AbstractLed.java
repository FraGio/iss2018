/* Generated by AN DISI Unibo */ 
package it.unibo.led;
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
public abstract class AbstractLed extends QActor { 
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
		public AbstractLed(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/led/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/led/plans.txt";
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
	    	stateTab.put("waitForBlink",waitForBlink);
	    	stateTab.put("ledOnPlan",ledOnPlan);
	    	stateTab.put("ledOffPlan",ledOffPlan);
	    	stateTab.put("stopLed",stopLed);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "led tout : stops");  
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
	    	parg = "consult(\"./resourceModel.pl\")";
	    	//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    	solveGoal( parg ); //sept2017
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(1000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "init";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = "\"[INFO] Inizializzazione led\"";
	    	println( temporaryStr );  
	     connectToMqttServer("tcp://localhost:1883");
	    	//switchTo waitForBlink
	        switchToPlanAsNextState(pr, myselfName, "led_"+myselfName, 
	              "waitForBlink",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForBlink = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForBlink",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForBlink";  
	    	//bbb
	     msgTransition( pr,myselfName,"led_"+myselfName,false,
	          new StateFun[]{stateTab.get("ledOnPlan") }, 
	          new String[]{"true","E","ledCmdBlink" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForBlink){  
	    	 println( getName() + " plan=waitForBlink WARNING:" + e_waitForBlink.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForBlink
	    
	    StateFun ledOnPlan = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("ledOnPlan",-1);
	    	String myselfName = "ledOnPlan";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?realrobot" )) != null ){
	    	temporaryStr = "\"[INFO] Led ON\"";
	    	temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    	println( temporaryStr );  
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"led_"+myselfName,true,
	          new StateFun[]{}, 
	          new String[]{},
	          500, "ledOffPlan" );//msgTransition
	    }catch(Exception e_ledOnPlan){  
	    	 println( getName() + " plan=ledOnPlan WARNING:" + e_ledOnPlan.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//ledOnPlan
	    
	    StateFun ledOffPlan = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_ledOffPlan",0);
	     pr.incNumIter(); 	
	    	String myselfName = "ledOffPlan";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?realrobot" )) != null ){
	    	temporaryStr = "\"[INFO] Led OFF\"";
	    	temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    	println( temporaryStr );  
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"led_"+myselfName,true,
	          new StateFun[]{stateTab.get("stopLed") }, 
	          new String[]{"true","E","ledCmdStop" },
	          500, "ledOnPlan" );//msgTransition
	    }catch(Exception e_ledOffPlan){  
	    	 println( getName() + " plan=ledOffPlan WARNING:" + e_ledOffPlan.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//ledOffPlan
	    
	    StateFun stopLed = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("stopLed",-1);
	    	String myselfName = "stopLed";  
	    	temporaryStr = "\"[INFO] Led finisce fase di blink\"";
	    	println( temporaryStr );  
	    	repeatPlanNoTransition(pr,myselfName,"led_"+myselfName,false,false);
	    }catch(Exception e_stopLed){  
	    	 println( getName() + " plan=stopLed WARNING:" + e_stopLed.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//stopLed
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
