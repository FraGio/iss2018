%====================================================================================
% Context ctxAnalysisModel2  SYSTEM-configuration: file it.unibo.ctxAnalysisModel2.reqAnalysisModel.pl 
%====================================================================================
context(ctxanalysismodel2, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( humanoperator2 , ctxanalysismodel2, "it.unibo.humanoperator2.MsgHandle_Humanoperator2"   ). %%store msgs 
qactor( humanoperator2_ctrl , ctxanalysismodel2, "it.unibo.humanoperator2.Humanoperator2"   ). %%control-driven 
qactor( appl2 , ctxanalysismodel2, "it.unibo.appl2.MsgHandle_Appl2"   ). %%store msgs 
qactor( appl2_ctrl , ctxanalysismodel2, "it.unibo.appl2.Appl2"   ). %%control-driven 
qactor( realrobot2 , ctxanalysismodel2, "it.unibo.realrobot2.MsgHandle_Realrobot2"   ). %%store msgs 
qactor( realrobot2_ctrl , ctxanalysismodel2, "it.unibo.realrobot2.Realrobot2"   ). %%control-driven 
qactor( virtualrobot2 , ctxanalysismodel2, "it.unibo.virtualrobot2.MsgHandle_Virtualrobot2"   ). %%store msgs 
qactor( virtualrobot2_ctrl , ctxanalysismodel2, "it.unibo.virtualrobot2.Virtualrobot2"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

