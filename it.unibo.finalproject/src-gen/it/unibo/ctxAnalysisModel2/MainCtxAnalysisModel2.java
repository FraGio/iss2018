/* Generated by AN DISI Unibo */ 
package it.unibo.ctxAnalysisModel2;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxAnalysisModel2  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxanalysismodel2", "./srcMore/it/unibo/ctxAnalysisModel2/reqanalysismodel2.pl", 
		"./srcMore/it/unibo/ctxAnalysisModel2/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}