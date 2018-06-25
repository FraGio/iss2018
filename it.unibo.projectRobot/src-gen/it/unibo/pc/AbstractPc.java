/* Generated by AN DISI Unibo */ 
package it.unibo.pc;
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
public abstract class AbstractPc extends QActor { 
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
		public AbstractPc(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/pc/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/pc/plans.txt";
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
	    	stateTab.put("authenticateUser",authenticateUser);
	    	stateTab.put("waitForUserCommand",waitForUserCommand);
	    	stateTab.put("userCmdHandler",userCmdHandler);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "pc tout : stops");  
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
	    	temporaryStr = "\"pc START\"";
	    	println( temporaryStr );  
	     connectToMqttServer("tcp://localhost:1883");
	    	//switchTo authenticateUser
	        switchToPlanAsNextState(pr, myselfName, "pc_"+myselfName, 
	              "authenticateUser",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun authenticateUser = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("authenticateUser",-1);
	    	String myselfName = "authenticateUser";  
	    	temporaryStr = "\"Autenticazione tramite servizio esterno . . . \"";
	    	println( temporaryStr );  
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(1000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "authenticateUser";
	    	if( ! aar.getGoon() ) return ;
	    	temporaryStr = "\"Autenticazione riuscita\"";
	    	println( temporaryStr );  
	    	//switchTo waitForUserCommand
	        switchToPlanAsNextState(pr, myselfName, "pc_"+myselfName, 
	              "waitForUserCommand",false, false, null); 
	    }catch(Exception e_authenticateUser){  
	    	 println( getName() + " plan=authenticateUser WARNING:" + e_authenticateUser.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//authenticateUser
	    
	    StateFun waitForUserCommand = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForUserCommand",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForUserCommand";  
	    	temporaryStr = "\"Attesa di comandi utente da GUI\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"pc_"+myselfName,false,
	          new StateFun[]{stateTab.get("userCmdHandler") }, 
	          new String[]{"true","E","mockUserCmd" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForUserCommand){  
	    	 println( getName() + " plan=waitForUserCommand WARNING:" + e_waitForUserCommand.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForUserCommand
	    
	    StateFun userCmdHandler = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("userCmdHandler",-1);
	    	String myselfName = "userCmdHandler";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("mockUserCmd(\"START\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("mockUserCmd") && 
	    		pengine.unify(curT, Term.createTerm("mockUserCmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "\"Eseguito comando START\"";
	    			println( temporaryStr );  
	    			temporaryStr = QActorUtils.unifyMsgContent(pengine, "userCmd(X)","userCmd(\"START\")", guardVars ).toString();
	    			emit( "userCmd", temporaryStr );
	    			};//actionseq
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("mockUserCmd(\"STOP\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("mockUserCmd") && 
	    		pengine.unify(curT, Term.createTerm("mockUserCmd(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "\"Eseguito comando STOP\"";
	    			println( temporaryStr );  
	    			temporaryStr = QActorUtils.unifyMsgContent(pengine, "userCmd(X)","userCmd(\"STOP\")", guardVars ).toString();
	    			emit( "userCmd", temporaryStr );
	    			};//actionseq
	    	}
	    	//switchTo waitForUserCommand
	        switchToPlanAsNextState(pr, myselfName, "pc_"+myselfName, 
	              "waitForUserCommand",false, false, null); 
	    }catch(Exception e_userCmdHandler){  
	    	 println( getName() + " plan=userCmdHandler WARNING:" + e_userCmdHandler.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//userCmdHandler
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
