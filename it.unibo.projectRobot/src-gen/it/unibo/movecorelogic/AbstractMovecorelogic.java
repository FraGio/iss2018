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
	    	stateTab.put("executionCoreCommand",executionCoreCommand);
	    	stateTab.put("move1",move1);
	    	stateTab.put("move2",move2);
	    	stateTab.put("move3",move3);
	    	stateTab.put("move4",move4);
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
	    	temporaryStr = "\"Inizializzazione del core della logica di movimento\"";
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
	    	temporaryStr = "\"logica di movimento FERMA\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{stateTab.get("executionCoreCommand") }, 
	          new String[]{"true","E","coreCmd" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForStart){  
	    	 println( getName() + " plan=waitForStart WARNING:" + e_waitForStart.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForStart
	    
	    StateFun executionCoreCommand = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_executionCoreCommand",0);
	     pr.incNumIter(); 	
	    	String myselfName = "executionCoreCommand";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("coreCmd(\"START\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("coreCmd") && 
	    		pengine.unify(curT, Term.createTerm("coreCmd(Z)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"d\")", guardVars ).toString();
	    			emit( "robotCmd", temporaryStr );
	    			if( (guardVars = QActorUtils.evalTheGuard(this, " not !?robotCanMove" )) != null )
	    			{
	    			temporaryStr = "robotCanMove";
	    			temporaryStr = QActorUtils.substituteVars(guardVars,temporaryStr);
	    			addRule( temporaryStr );  
	    			}
	    			//delay  ( no more reactive within a plan)
	    			aar = delayReactive(1000,"" , "");
	    			if( aar.getInterrupted() ) curPlanInExec   = "executionCoreCommand";
	    			if( ! aar.getGoon() ) return ;
	    			};//actionseq
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("coreCmd(\"STOP\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("coreCmd") && 
	    		pengine.unify(curT, Term.createTerm("coreCmd(Z)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "robotCanMove";
	    			removeRule( temporaryStr );  
	    			//delay  ( no more reactive within a plan)
	    			aar = delayReactive(1000,"" , "");
	    			if( aar.getInterrupted() ) curPlanInExec   = "executionCoreCommand";
	    			if( ! aar.getGoon() ) return ;
	    			};//actionseq
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{stateTab.get("executionCoreCommand") }, 
	          new String[]{"true","E","coreCmd" },
	          2000, "move1" );//msgTransition
	    }catch(Exception e_executionCoreCommand){  
	    	 println( getName() + " plan=executionCoreCommand WARNING:" + e_executionCoreCommand.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//executionCoreCommand
	    
	    StateFun move1 = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_move1",0);
	     pr.incNumIter(); 	
	    	String myselfName = "move1";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?robotCanMove" )) != null ){
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"w\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{stateTab.get("executionCoreCommand") }, 
	          new String[]{"true","E","coreCmd" },
	          2000, "move2" );//msgTransition
	    }catch(Exception e_move1){  
	    	 println( getName() + " plan=move1 WARNING:" + e_move1.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//move1
	    
	    StateFun move2 = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_move2",0);
	     pr.incNumIter(); 	
	    	String myselfName = "move2";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?robotCanMove" )) != null ){
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"a\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{stateTab.get("executionCoreCommand") }, 
	          new String[]{"true","E","coreCmd" },
	          2000, "move3" );//msgTransition
	    }catch(Exception e_move2){  
	    	 println( getName() + " plan=move2 WARNING:" + e_move2.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//move2
	    
	    StateFun move3 = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_move3",0);
	     pr.incNumIter(); 	
	    	String myselfName = "move3";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?robotCanMove" )) != null ){
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"w\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{stateTab.get("executionCoreCommand") }, 
	          new String[]{"true","E","coreCmd" },
	          2000, "move4" );//msgTransition
	    }catch(Exception e_move3){  
	    	 println( getName() + " plan=move3 WARNING:" + e_move3.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//move3
	    
	    StateFun move4 = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_move4",0);
	     pr.incNumIter(); 	
	    	String myselfName = "move4";  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?robotCanMove" )) != null ){
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "robotCmd(Y)","robotCmd(\"d\")", guardVars ).toString();
	    	emit( "robotCmd", temporaryStr );
	    	}
	    	//bbb
	     msgTransition( pr,myselfName,"movecorelogic_"+myselfName,false,
	          new StateFun[]{stateTab.get("executionCoreCommand") }, 
	          new String[]{"true","E","coreCmd" },
	          2000, "executionCoreCommand" );//msgTransition
	    }catch(Exception e_move4){  
	    	 println( getName() + " plan=move4 WARNING:" + e_move4.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//move4
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
