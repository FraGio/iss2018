/* Generated by AN DISI Unibo */ 
package it.unibo.robot;
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
public abstract class AbstractRobot extends QActor { 
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
		public AbstractRobot(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/robot/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/robot/plans.txt";
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
	    	stateTab.put("waitForCmd",waitForCmd);
	    	stateTab.put("robotCmdHandler",robotCmdHandler);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "robot tout : stops");  
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
	    	temporaryStr = "\"robot START\"";
	    	println( temporaryStr );  
	     connectToMqttServer("tcp://localhost");
	    	//switchTo waitForCmd
	        switchToPlanAsNextState(pr, myselfName, "robot_"+myselfName, 
	              "waitForCmd",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForCmd",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForCmd";  
	    	temporaryStr = "\"Robot in attesa di comandi\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"robot_"+myselfName,false,
	          new StateFun[]{stateTab.get("robotCmdHandler") }, 
	          new String[]{"true","E","robotCmd" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForCmd){  
	    	 println( getName() + " plan=waitForCmd WARNING:" + e_waitForCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForCmd
	    
	    StateFun robotCmdHandler = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("robotCmdHandler",-1);
	    	String myselfName = "robotCmdHandler";  
	    	temporaryStr = "\"Valutazione TEMPERATURA ed ORARIO\"";
	    	println( temporaryStr );  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotCmd(\"START\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("robotCmd") && 
	    		pengine.unify(curT, Term.createTerm("robotCmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "\"Robot attivia'� avviata\"";
	    			println( temporaryStr );  
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?isRealRobot" )) != null ){
	    			temporaryStr = "\"Robot fisico: blink led\"";
	    			temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    			println( temporaryStr );  
	    			}
	    			else{ temporaryStr = "\"Robot virtuale: blink Red-Hue-Lamp\"";
	    			temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    			println( temporaryStr );  
	    			}//delay  ( no more reactive within a plan)
	    			aar = delayReactive(5000,"" , "");
	    			if( aar.getInterrupted() ) curPlanInExec   = "robotCmdHandler";
	    			if( ! aar.getGoon() ) return ;
	    			temporaryStr = "\"Trovato ostacolo fisso, cerco di evitarlo\"";
	    			println( temporaryStr );  
	    			//delay  ( no more reactive within a plan)
	    			aar = delayReactive(5000,"" , "");
	    			if( aar.getInterrupted() ) curPlanInExec   = "robotCmdHandler";
	    			if( ! aar.getGoon() ) return ;
	    			temporaryStr = "\"Trovato ostacolo mobile, cerco di evitarlo\"";
	    			println( temporaryStr );  
	    			//delay  ( no more reactive within a plan)
	    			aar = delayReactive(5000,"" , "");
	    			if( aar.getInterrupted() ) curPlanInExec   = "robotCmdHandler";
	    			if( ! aar.getGoon() ) return ;
	    			temporaryStr = "\"Trovato ostacolo inevitabile, mi arresto\"";
	    			println( temporaryStr );  
	    			temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(X)","robotCmd(\"STOP\")", guardVars ).toString();
	    			emit( "robotCmd", temporaryStr );
	    			};//actionseq
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotCmd(\"STOP\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("robotCmd") && 
	    		pengine.unify(curT, Term.createTerm("robotCmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "\"Robot attivia'� arrestata\"";
	    			println( temporaryStr );  
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?isRealRobot" )) != null ){
	    			temporaryStr = "\"Robot fisico: STOP blink led\"";
	    			temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    			println( temporaryStr );  
	    			}
	    			else{ temporaryStr = "\"Robot virtuale: STOP blink Red-Hue-Lamp\"";
	    			temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    			println( temporaryStr );  
	    			}};//actionseq
	    	}
	    	//switchTo waitForCmd
	        switchToPlanAsNextState(pr, myselfName, "robot_"+myselfName, 
	              "waitForCmd",false, false, null); 
	    }catch(Exception e_robotCmdHandler){  
	    	 println( getName() + " plan=robotCmdHandler WARNING:" + e_robotCmdHandler.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//robotCmdHandler
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
