%====================================================================================
% Context ctxAnalysisModel  SYSTEM-configuration: file it.unibo.ctxAnalysisModel.reqAnalysisModel.pl 
%====================================================================================
context(ctxanalysismodel, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( humanoperator , ctxanalysismodel, "it.unibo.humanoperator.MsgHandle_Humanoperator"   ). %%store msgs 
qactor( humanoperator_ctrl , ctxanalysismodel, "it.unibo.humanoperator.Humanoperator"   ). %%control-driven 
qactor( appl , ctxanalysismodel, "it.unibo.appl.MsgHandle_Appl"   ). %%store msgs 
qactor( appl_ctrl , ctxanalysismodel, "it.unibo.appl.Appl"   ). %%control-driven 
qactor( robot , ctxanalysismodel, "it.unibo.robot.MsgHandle_Robot"   ). %%store msgs 
qactor( robot_ctrl , ctxanalysismodel, "it.unibo.robot.Robot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

