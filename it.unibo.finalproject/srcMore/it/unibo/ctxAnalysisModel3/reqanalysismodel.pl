%====================================================================================
% Context ctxAnalysisModel3  SYSTEM-configuration: file it.unibo.ctxAnalysisModel3.reqAnalysisModel.pl 
%====================================================================================
context(ctxanalysismodel3, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( humanoperator3 , ctxanalysismodel3, "it.unibo.humanoperator3.MsgHandle_Humanoperator3"   ). %%store msgs 
qactor( humanoperator3_ctrl , ctxanalysismodel3, "it.unibo.humanoperator3.Humanoperator3"   ). %%control-driven 
qactor( applicationlogiccmd , ctxanalysismodel3, "it.unibo.applicationlogiccmd.MsgHandle_Applicationlogiccmd"   ). %%store msgs 
qactor( applicationlogiccmd_ctrl , ctxanalysismodel3, "it.unibo.applicationlogiccmd.Applicationlogiccmd"   ). %%control-driven 
qactor( applicationlogictemp , ctxanalysismodel3, "it.unibo.applicationlogictemp.MsgHandle_Applicationlogictemp"   ). %%store msgs 
qactor( applicationlogictemp_ctrl , ctxanalysismodel3, "it.unibo.applicationlogictemp.Applicationlogictemp"   ). %%control-driven 
qactor( temperatureemitter , ctxanalysismodel3, "it.unibo.temperatureemitter.MsgHandle_Temperatureemitter"   ). %%store msgs 
qactor( temperatureemitter_ctrl , ctxanalysismodel3, "it.unibo.temperatureemitter.Temperatureemitter"   ). %%control-driven 
qactor( realrobot3 , ctxanalysismodel3, "it.unibo.realrobot3.MsgHandle_Realrobot3"   ). %%store msgs 
qactor( realrobot3_ctrl , ctxanalysismodel3, "it.unibo.realrobot3.Realrobot3"   ). %%control-driven 
qactor( virtualrobot3 , ctxanalysismodel3, "it.unibo.virtualrobot3.MsgHandle_Virtualrobot3"   ). %%store msgs 
qactor( virtualrobot3_ctrl , ctxanalysismodel3, "it.unibo.virtualrobot3.Virtualrobot3"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

