/* Generated by AN DISI Unibo */ 
package it.unibo.mind;
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
public abstract class AbstractMind extends QActor { 
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
		public AbstractMind(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/mind/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/mind/plans.txt";
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
	    	stateTab.put("initTempTime",initTempTime);
	    	stateTab.put("doWork",doWork);
	    	stateTab.put("updateValues",updateValues);
	    	stateTab.put("robotCmdHandler",robotCmdHandler);
	    	stateTab.put("handleRobotSonarEvent",handleRobotSonarEvent);
	    	stateTab.put("handleRoomSonarEvent",handleRoomSonarEvent);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "mind tout : stops");  
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
	    	aar = delayReactive(3000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "init";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "requestNotifier","requestNotifier", guardVars ).toString();
	    	emit( "requestNotifier", temporaryStr );
	     connectToMqttServer("tcp://localhost:1883");
	    	//switchTo initTempTime
	        switchToPlanAsNextState(pr, myselfName, "mind_"+myselfName, 
	              "initTempTime",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun initTempTime = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_initTempTime",0);
	     pr.incNumIter(); 	
	    	String myselfName = "initTempTime";  
	    	//bbb
	     msgTransition( pr,myselfName,"mind_"+myselfName,false,
	          new StateFun[]{stateTab.get("updateValues") }, 
	          new String[]{"true","E","temperatureTimeRequest" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_initTempTime){  
	    	 println( getName() + " plan=initTempTime WARNING:" + e_initTempTime.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//initTempTime
	    
	    StateFun doWork = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_doWork",0);
	     pr.incNumIter(); 	
	    	String myselfName = "doWork";  
	    	//bbb
	     msgTransition( pr,myselfName,"mind_"+myselfName,false,
	          new StateFun[]{stateTab.get("robotCmdHandler"), stateTab.get("updateValues"), stateTab.get("handleRobotSonarEvent"), stateTab.get("handleRoomSonarEvent") }, 
	          new String[]{"true","E","userCmd", "true","E","temperatureTimeRequest", "true","E","robotSonarEvent", "true","E","roomSonarEvent" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_doWork){  
	    	 println( getName() + " plan=doWork WARNING:" + e_doWork.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//doWork
	    
	    StateFun updateValues = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("updateValues",-1);
	    	String myselfName = "updateValues";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("temperatureTimeRequest(V,T)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("temperatureTimeRequest") && 
	    		pengine.unify(curT, Term.createTerm("temperatureTimeRequest(V,T)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="changeModelItems(timer,timevalue,T,temperature,temperaturevalue,V)";
	    			/* PHead */
	    			parg =  updateVars( Term.createTerm("temperatureTimeRequest(V,T)"), 
	    			                    Term.createTerm("temperatureTimeRequest(V,T)"), 
	    				    		  	Term.createTerm(currentEvent.getMsg()), parg);
	    				if( parg != null ) {
	    				    aar = QActorUtils.solveGoal(this,myCtx,pengine,parg,"",outEnvView,86400000);
	    					//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    					if( aar.getInterrupted() ){
	    						curPlanInExec   = "updateValues";
	    						if( aar.getTimeRemained() <= 0 ) addRule("tout(demo,"+getName()+")");
	    						if( ! aar.getGoon() ) return ;
	    					} 			
	    					if( aar.getResult().equals("failure")){
	    						if( ! aar.getGoon() ) return ;
	    					}else if( ! aar.getGoon() ) return ;
	    				}
	    	}
	    	//switchTo doWork
	        switchToPlanAsNextState(pr, myselfName, "mind_"+myselfName, 
	              "doWork",false, false, null); 
	    }catch(Exception e_updateValues){  
	    	 println( getName() + " plan=updateValues WARNING:" + e_updateValues.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//updateValues
	    
	    StateFun robotCmdHandler = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("robotCmdHandler",-1);
	    	String myselfName = "robotCmdHandler";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("userCmd(\"START\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("userCmd") && 
	    		pengine.unify(curT, Term.createTerm("userCmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "\"!!!!! Ricevuto da utente comando di avvio\"";
	    			println( temporaryStr );  
	    			temporaryStr = QActorUtils.unifyMsgContent(pengine, "coreCmd(Z)","coreCmd(\"START\")", guardVars ).toString();
	    			emit( "coreCmd", temporaryStr );
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?realrobot" )) != null ){
	    			{//actionseq
	    			parg = "changeModelItem(leds,ledfisico,blink)";
	    			//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    			solveGoal( parg ); //sept2017
	    			parg = "changeModelItem(robot,realRobotStatus,on)";
	    			//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    			solveGoal( parg ); //sept2017
	    			};//actionseq
	    			}
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?virtualrobot" )) != null ){
	    			{//actionseq
	    			parg = "changeModelItem(leds,ledhuelamp,blink)";
	    			//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    			solveGoal( parg ); //sept2017
	    			parg = "changeModelItem(robot,virtualRobotStatus,on)";
	    			//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    			solveGoal( parg ); //sept2017
	    			};//actionseq
	    			}
	    			};//actionseq
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("userCmd(\"STOP\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("userCmd") && 
	    		pengine.unify(curT, Term.createTerm("userCmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "\"Ricevuto da utente comando di stop\"";
	    			println( temporaryStr );  
	    			temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"STOP\")", guardVars ).toString();
	    			emit( "robotCmd", temporaryStr );
	    			temporaryStr = QActorUtils.unifyMsgContent(pengine, "coreCmd(Z)","coreCmd(\"STOP\")", guardVars ).toString();
	    			emit( "coreCmd", temporaryStr );
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?realrobot" )) != null ){
	    			{//actionseq
	    			parg = "changeModelItem(leds,ledfisico,off)";
	    			//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    			solveGoal( parg ); //sept2017
	    			parg = "changeModelItem(robot,realRobotStatus,off)";
	    			//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    			solveGoal( parg ); //sept2017
	    			};//actionseq
	    			}
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?virtualrobot" )) != null ){
	    			{//actionseq
	    			parg = "changeModelItem(leds,ledhuelamp,off)";
	    			//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    			solveGoal( parg ); //sept2017
	    			parg = "changeModelItem(robot,virtualRobotStatus,off)";
	    			//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    			solveGoal( parg ); //sept2017
	    			};//actionseq
	    			}
	    			};//actionseq
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"mind_"+myselfName,false,true);
	    }catch(Exception e_robotCmdHandler){  
	    	 println( getName() + " plan=robotCmdHandler WARNING:" + e_robotCmdHandler.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//robotCmdHandler
	    
	    StateFun handleRobotSonarEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleRobotSonarEvent",-1);
	    	String myselfName = "handleRobotSonarEvent";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotSonarEvent(DISTANCE)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("robotSonarEvent") && 
	    		pengine.unify(curT, Term.createTerm("robotSonarEvent(DISTANCE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg = "\"Ostacolo rilevato, iniziata routine per evitarlo...\"";
	    			/* Print */
	    			parg =  updateVars( Term.createTerm("robotSonarEvent(DISTANCE)"), 
	    			                    Term.createTerm("robotSonarEvent(DISTANCE)"), 
	    				    		  	Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) println( parg );
	    	}
	    	//switchTo doWork
	        switchToPlanAsNextState(pr, myselfName, "mind_"+myselfName, 
	              "doWork",false, false, null); 
	    }catch(Exception e_handleRobotSonarEvent){  
	    	 println( getName() + " plan=handleRobotSonarEvent WARNING:" + e_handleRobotSonarEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleRobotSonarEvent
	    
	    StateFun handleRoomSonarEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleRoomSonarEvent",-1);
	    	String myselfName = "handleRoomSonarEvent";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("roomSonarEvent(\"ROOM_S2\",DISTANCE)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("roomSonarEvent") && 
	    		pengine.unify(curT, Term.createTerm("roomSonarEvent(NAME,DISTANCE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "\"Robot rilevato da sonar stanza n.2...fermo il robot\"";
	    			println( temporaryStr );  
	    			temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"STOP\")", guardVars ).toString();
	    			emit( "robotCmd", temporaryStr );
	    			temporaryStr = QActorUtils.unifyMsgContent(pengine, "coreCmd(Z)","coreCmd(\"STOP\")", guardVars ).toString();
	    			emit( "coreCmd", temporaryStr );
	    			};//actionseq
	    	}
	    	//switchTo doWork
	        switchToPlanAsNextState(pr, myselfName, "mind_"+myselfName, 
	              "doWork",false, false, null); 
	    }catch(Exception e_handleRoomSonarEvent){  
	    	 println( getName() + " plan=handleRoomSonarEvent WARNING:" + e_handleRoomSonarEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleRoomSonarEvent
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}