%====================================================================================
% Context ctxAnalysisModel  SYSTEM-configuration: file it.unibo.ctxAnalysisModel.reqAnalysisModel.pl 
%====================================================================================
context(ctxanalysismodel, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( humanoperator , ctxanalysismodel, "it.unibo.humanoperator.MsgHandle_Humanoperator"   ). %%store msgs 
qactor( humanoperator_ctrl , ctxanalysismodel, "it.unibo.humanoperator.Humanoperator"   ). %%control-driven 
qactor( appl , ctxanalysismodel, "it.unibo.appl.MsgHandle_Appl"   ). %%store msgs 
qactor( appl_ctrl , ctxanalysismodel, "it.unibo.appl.Appl"   ). %%control-driven 
qactor( realrobot , ctxanalysismodel, "it.unibo.realrobot.MsgHandle_Realrobot"   ). %%store msgs 
qactor( realrobot_ctrl , ctxanalysismodel, "it.unibo.realrobot.Realrobot"   ). %%control-driven 
qactor( virtualrobot , ctxanalysismodel, "it.unibo.virtualrobot.MsgHandle_Virtualrobot"   ). %%store msgs 
qactor( virtualrobot_ctrl , ctxanalysismodel, "it.unibo.virtualrobot.Virtualrobot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

