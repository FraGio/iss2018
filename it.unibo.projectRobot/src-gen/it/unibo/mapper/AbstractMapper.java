/* Generated by AN DISI Unibo */ 
package it.unibo.mapper;
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
public abstract class AbstractMapper extends QActor { 
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
		public AbstractMapper(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/mapper/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/mapper/plans.txt";
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
	    	stateTab.put("waitForObstacle",waitForObstacle);
	    	stateTab.put("updateMap",updateMap);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "mapper tout : stops");  
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
	    	temporaryStr = "\"[INFO] Inizializzazione mapper...\"";
	    	println( temporaryStr );  
	     connectToMqttServer("tcp://192.168.1.112:1883");
	    	//switchTo waitForObstacle
	        switchToPlanAsNextState(pr, myselfName, "mapper_"+myselfName, 
	              "waitForObstacle",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForObstacle = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForObstacle",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForObstacle";  
	    	//bbb
	     msgTransition( pr,myselfName,"mapper_"+myselfName,false,
	          new StateFun[]{stateTab.get("updateMap") }, 
	          new String[]{"true","E","fixObstacleFound" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForObstacle){  
	    	 println( getName() + " plan=waitForObstacle WARNING:" + e_waitForObstacle.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForObstacle
	    
	    StateFun updateMap = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("updateMap",-1);
	    	String myselfName = "updateMap";  
	    	temporaryStr = "\"[INFO] Aggiornamento mappa con ostacolo fisso\"";
	    	println( temporaryStr );  
	    	repeatPlanNoTransition(pr,myselfName,"mapper_"+myselfName,false,true);
	    }catch(Exception e_updateMap){  
	    	 println( getName() + " plan=updateMap WARNING:" + e_updateMap.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//updateMap
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}