/* Generated by AN DISI Unibo */ 
package it.unibo.applicationlogictemp;
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
public abstract class AbstractApplicationlogictemp extends QActor { 
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
		public AbstractApplicationlogictemp(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/applicationlogictemp/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/applicationlogictemp/plans.txt";
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
	    	stateTab.put("waitForEvents",waitForEvents);
	    	stateTab.put("handleEvent",handleEvent);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "applicationlogictemp tout : stops");  
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
	    	temporaryStr = "\"applicationlogic2 START\"";
	    	println( temporaryStr );  
	    	//switchTo waitForEvents
	        switchToPlanAsNextState(pr, myselfName, "applicationlogictemp_"+myselfName, 
	              "waitForEvents",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForEvents = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForEvents",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForEvents";  
	    	//bbb
	     msgTransition( pr,myselfName,"applicationlogictemp_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleEvent") }, 
	          new String[]{"true","E","temperatureData" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForEvents){  
	    	 println( getName() + " plan=waitForEvents WARNING:" + e_waitForEvents.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForEvents
	    
	    StateFun handleEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleEvent",-1);
	    	String myselfName = "handleEvent";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("temperatureData(V)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("temperatureData") && 
	    		pengine.unify(curT, Term.createTerm("temperatureData(X)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="changeModelItem(temperature,tempAmbiente,V)";
	    			/* PHead */
	    			parg =  updateVars( Term.createTerm("temperatureData(X)"), 
	    			                    Term.createTerm("temperatureData(V)"), 
	    				    		  	Term.createTerm(currentEvent.getMsg()), parg);
	    				if( parg != null ) {
	    				    aar = QActorUtils.solveGoal(this,myCtx,pengine,parg,"",outEnvView,86400000);
	    					//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    					if( aar.getInterrupted() ){
	    						curPlanInExec   = "handleEvent";
	    						if( aar.getTimeRemained() <= 0 ) addRule("tout(demo,"+getName()+")");
	    						if( ! aar.getGoon() ) return ;
	    					} 			
	    					if( aar.getResult().equals("failure")){
	    						if( ! aar.getGoon() ) return ;
	    					}else if( ! aar.getGoon() ) return ;
	    				}
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"applicationlogictemp_"+myselfName,false,true);
	    }catch(Exception e_handleEvent){  
	    	 println( getName() + " plan=handleEvent WARNING:" + e_handleEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleEvent
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
