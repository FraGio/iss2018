/* Generated by AN DISI Unibo */ 
package it.unibo.realrobot;
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
public abstract class AbstractRealrobot extends QActor { 
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
		public AbstractRealrobot(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/realrobot/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/realrobot/plans.txt";
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
	    	stateTab.put("executionRobotCmdHandler",executionRobotCmdHandler);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "realrobot tout : stops");  
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
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?realRobot" )) != null ){
	    	temporaryStr = "\"[INFO] Accensione del real robot completata!\"";
	    	temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    	println( temporaryStr );  
	    	}
	     connectToMqttServer("tcp://192.168.43.84:1883");
	    	//switchTo waitForCmd
	        switchToPlanAsNextState(pr, myselfName, "realrobot_"+myselfName, 
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
	    	//bbb
	     msgTransition( pr,myselfName,"realrobot_"+myselfName,false,
	          new StateFun[]{stateTab.get("executionRobotCmdHandler") }, 
	          new String[]{"true","E","robotCmd" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForCmd){  
	    	 println( getName() + " plan=waitForCmd WARNING:" + e_waitForCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForCmd
	    
	    StateFun executionRobotCmdHandler = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("executionRobotCmdHandler",-1);
	    	String myselfName = "executionRobotCmdHandler";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotCmd(\"w\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("robotCmd") && 
	    		pengine.unify(curT, Term.createTerm("robotCmd(Y)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?realRobot" )) != null ){
	    			it.unibo.iss2018support.utils.robotMixMoves.moveRobotAndAvatar( myself ,"forward", "0", "0"  );
	    			}
	    			};//actionseq
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotCmd(\"s\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("robotCmd") && 
	    		pengine.unify(curT, Term.createTerm("robotCmd(Y)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?realRobot" )) != null ){
	    			it.unibo.iss2018support.utils.robotMixMoves.moveRobotAndAvatar( myself ,"backward", "0", "0"  );
	    			}
	    			};//actionseq
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotCmd(\"a\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("robotCmd") && 
	    		pengine.unify(curT, Term.createTerm("robotCmd(Y)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?realRobot" )) != null ){
	    			it.unibo.iss2018support.utils.robotMixMoves.moveRobotAndAvatar( myself ,"left", "0", "0"  );
	    			}
	    			};//actionseq
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotCmd(\"d\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("robotCmd") && 
	    		pengine.unify(curT, Term.createTerm("robotCmd(Y)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?realRobot" )) != null ){
	    			it.unibo.iss2018support.utils.robotMixMoves.moveRobotAndAvatar( myself ,"right", "0", "0"  );
	    			}
	    			};//actionseq
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotCmd(\"stop\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("robotCmd") && 
	    		pengine.unify(curT, Term.createTerm("robotCmd(Y)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " !?realRobot" )) != null ){
	    			it.unibo.iss2018support.utils.robotMixMoves.moveRobotAndAvatar( myself ,"stop", "0", "0"  );
	    			}
	    			};//actionseq
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"realrobot_"+myselfName,false,true);
	    }catch(Exception e_executionRobotCmdHandler){  
	    	 println( getName() + " plan=executionRobotCmdHandler WARNING:" + e_executionRobotCmdHandler.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//executionRobotCmdHandler
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
