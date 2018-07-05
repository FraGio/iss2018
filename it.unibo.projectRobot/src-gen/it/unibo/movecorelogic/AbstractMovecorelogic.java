/* Generated by AN DISI Unibo */ 
package it.unibo.movecorelogic;
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
public abstract class AbstractMovecorelogic extends QActor { 
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
		public AbstractMovecorelogic(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/movecorelogic/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/movecorelogic/plans.txt";
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
	    	stateTab.put("waitForStart",waitForStart);
	    	stateTab.put("forwardOn",forwardOn);
	    	stateTab.put("waitForMobileObstacle",waitForMobileObstacle);
	    	stateTab.put("restartForwardOn",restartForwardOn);
	    	stateTab.put("handleVirtualSonarEvent",handleVirtualSonarEvent);
	    	stateTab.put("sonar2Detected",sonar2Detected);
	    	stateTab.put("stopPlan",stopPlan);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "movecorelogic tout : stops");  
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
	    	temporaryStr = "directionUpToDown";
	    	addRule( temporaryStr );  
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(2000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "init";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"d\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(500,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "init";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"d\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(500,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "init";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = "\"[INFO] Inizializzazione del core della logica di movimento\"";
	    	println( temporaryStr );  
	     connectToMqttServer("tcp://192.168.43.84:1883");
	    	//switchTo waitForStart
	        switchToPlanAsNextState(pr, myselfName, "movecorelogic_"+myselfName, 
	              "waitForStart",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForStart = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForStart",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForStart";  
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{stateTab.get("forwardOn") }, 
	          new String[]{"true","E","coreCmdStart" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForStart){  
	    	 println( getName() + " plan=waitForStart WARNING:" + e_waitForStart.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForStart
	    
	    StateFun forwardOn = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_forwardOn",0);
	     pr.incNumIter(); 	
	    	String myselfName = "forwardOn";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " not !?fixedObstacleFound" )) != null )
	    	{
	    	{//actionseq
	    	temporaryStr = "\"[INFO] Piano ForwardON ...\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"w\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	};//actionseq
	    	}
	    	else{ temporaryStr = "\"[INFO] Piano ForwardON : bloccato perche' trovato ostacolo fisso!\"";
	    	println( temporaryStr );  
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{stateTab.get("stopPlan"), stateTab.get("sonar2Detected"), stateTab.get("handleVirtualSonarEvent"), stateTab.get("waitForMobileObstacle"), stateTab.get("restartForwardOn") }, 
	          new String[]{"true","E","coreCmdStop", "true","E","roomSonar2Event", "true","E","virtualRobotSonarEvent", "true","E","realRobotSonarEvent", "true","E","endRoutineAvoidObstacle" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_forwardOn){  
	    	 println( getName() + " plan=forwardOn WARNING:" + e_forwardOn.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//forwardOn
	    
	    StateFun waitForMobileObstacle = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForMobileObstacle",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForMobileObstacle";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " not !?fixedObstacleFound" )) != null )
	    	{
	    	{//actionseq
	    	temporaryStr = "\"[INFO] Trovato un ostacolo dal sonar reale, valutazione se mobile ...\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"stop\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " not !?firstCheck" )) != null )
	    	{
	    	{//actionseq
	    	temporaryStr = "\"[INFO] waitForMobileObstacle - 1� controllo ...\"";
	    	println( temporaryStr );  
	    	temporaryStr = "firstCheck";
	    	addRule( temporaryStr );  
	    	};//actionseq
	    	}
	    	else{ {//actionseq
	    	temporaryStr = "firstCheck";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "fixedObstacleFound";
	    	addRule( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "fixObstacleFound(X,Y)","fixObstacleFound(X,Y)", guardVars ).toString();
	    	emit( "fixObstacleFound", temporaryStr );
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "fixedObstacleFoundEvent","fixedObstacleFoundEvent", guardVars ).toString();
	    	emit( "fixedObstacleFoundEvent", temporaryStr );
	    	};//actionseq
	    	}};//actionseq
	    	}
	    	else{ {//actionseq
	    	temporaryStr = "\"[INFO] waitForMobileObstacle : bloccato perche' attiva routine aggiramento!\"";
	    	println( temporaryStr );  
	    	};//actionseq
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{stateTab.get("restartForwardOn") }, 
	          new String[]{"true","E","endRoutineAvoidObstacle" },
	          2000, "forwardOn" );//msgTransition
	    }catch(Exception e_waitForMobileObstacle){  
	    	 println( getName() + " plan=waitForMobileObstacle WARNING:" + e_waitForMobileObstacle.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForMobileObstacle
	    
	    StateFun restartForwardOn = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("restartForwardOn",-1);
	    	String myselfName = "restartForwardOn";  
	    	temporaryStr = "\"[INFO] restartForwardOn - sblocco!\"";
	    	println( temporaryStr );  
	    	temporaryStr = "fixedObstacleFound";
	    	removeRule( temporaryStr );  
	    	//switchTo forwardOn
	        switchToPlanAsNextState(pr, myselfName, "movecorelogic_"+myselfName, 
	              "forwardOn",false, false, null); 
	    }catch(Exception e_restartForwardOn){  
	    	 println( getName() + " plan=restartForwardOn WARNING:" + e_restartForwardOn.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//restartForwardOn
	    
	    StateFun handleVirtualSonarEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleVirtualSonarEvent",-1);
	    	String myselfName = "handleVirtualSonarEvent";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " not !?justTurnedUpBottom" )) != null )
	    	{
	    	{//actionseq
	    	temporaryStr = "\"[INFO] Sonar virtuale ha trovato muro!\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"d\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(500,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "handleVirtualSonarEvent";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"w\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(250,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "handleVirtualSonarEvent";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"d\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(500,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "handleVirtualSonarEvent";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = "justTurnedUpBottom";
	    	addRule( temporaryStr );  
	    	temporaryStr = "justTurnedBottomUp";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "directionDownToUp";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "directionUpToDown";
	    	addRule( temporaryStr );  
	    	temporaryStr = "firstWait";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "secondWait";
	    	removeRule( temporaryStr );  
	    	};//actionseq
	    	}
	    	//switchTo forwardOn
	        switchToPlanAsNextState(pr, myselfName, "movecorelogic_"+myselfName, 
	              "forwardOn",false, false, null); 
	    }catch(Exception e_handleVirtualSonarEvent){  
	    	 println( getName() + " plan=handleVirtualSonarEvent WARNING:" + e_handleVirtualSonarEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleVirtualSonarEvent
	    
	    StateFun sonar2Detected = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("sonar2Detected",-1);
	    	String myselfName = "sonar2Detected";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " not !?justTurnedBottomUp" )) != null )
	    	{
	    	{//actionseq
	    	temporaryStr = "\"[INFO] Sonar2 raggiunto, non finito, SI SVOLTA!\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"a\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(500,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "sonar2Detected";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"w\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(250,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "sonar2Detected";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"a\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(500,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "sonar2Detected";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = "justTurnedBottomUp";
	    	addRule( temporaryStr );  
	    	temporaryStr = "justTurnedUpBottom";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "directionUpToDown";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "directionDownToUp";
	    	addRule( temporaryStr );  
	    	temporaryStr = "firstWait";
	    	removeRule( temporaryStr );  
	    	temporaryStr = "secondWait";
	    	removeRule( temporaryStr );  
	    	};//actionseq
	    	}
	    	//switchTo forwardOn
	        switchToPlanAsNextState(pr, myselfName, "movecorelogic_"+myselfName, 
	              "forwardOn",false, false, null); 
	    }catch(Exception e_sonar2Detected){  
	    	 println( getName() + " plan=sonar2Detected WARNING:" + e_sonar2Detected.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//sonar2Detected
	    
	    StateFun stopPlan = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("stopPlan",-1);
	    	String myselfName = "stopPlan";  
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(1000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "stopPlan";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"stop\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(500,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "stopPlan";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "coreHasFinished","coreHasFinished", guardVars ).toString();
	    	emit( "coreHasFinished", temporaryStr );
	    	temporaryStr = "\"[INFO] Corelogic fermata\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{}, 
	          new String[]{},
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_stopPlan){  
	    	 println( getName() + " plan=stopPlan WARNING:" + e_stopPlan.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//stopPlan
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
