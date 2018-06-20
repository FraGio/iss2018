%====================================================================================
% Context ctxAnalysisModel5  SYSTEM-configuration: file it.unibo.ctxAnalysisModel5.reqAnalysisModel.pl 
%====================================================================================
context(ctxanalysismodel5, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( humanoperator5 , ctxanalysismodel5, "it.unibo.humanoperator5.MsgHandle_Humanoperator5"   ). %%store msgs 
qactor( humanoperator5_ctrl , ctxanalysismodel5, "it.unibo.humanoperator5.Humanoperator5"   ). %%control-driven 
qactor( applicationlogiccmd , ctxanalysismodel5, "it.unibo.applicationlogiccmd.MsgHandle_Applicationlogiccmd"   ). %%store msgs 
qactor( applicationlogiccmd_ctrl , ctxanalysismodel5, "it.unibo.applicationlogiccmd.Applicationlogiccmd"   ). %%control-driven 
qactor( applicationlogictemp , ctxanalysismodel5, "it.unibo.applicationlogictemp.MsgHandle_Applicationlogictemp"   ). %%store msgs 
qactor( applicationlogictemp_ctrl , ctxanalysismodel5, "it.unibo.applicationlogictemp.Applicationlogictemp"   ). %%control-driven 
qactor( applicationlogictime , ctxanalysismodel5, "it.unibo.applicationlogictime.MsgHandle_Applicationlogictime"   ). %%store msgs 
qactor( applicationlogictime_ctrl , ctxanalysismodel5, "it.unibo.applicationlogictime.Applicationlogictime"   ). %%control-driven 
qactor( temperatureemitter , ctxanalysismodel5, "it.unibo.temperatureemitter.MsgHandle_Temperatureemitter"   ). %%store msgs 
qactor( temperatureemitter_ctrl , ctxanalysismodel5, "it.unibo.temperatureemitter.Temperatureemitter"   ). %%control-driven 
qactor( timemitter , ctxanalysismodel5, "it.unibo.timemitter.MsgHandle_Timemitter"   ). %%store msgs 
qactor( timemitter_ctrl , ctxanalysismodel5, "it.unibo.timemitter.Timemitter"   ). %%control-driven 
qactor( realrobot5 , ctxanalysismodel5, "it.unibo.realrobot5.MsgHandle_Realrobot5"   ). %%store msgs 
qactor( realrobot5_ctrl , ctxanalysismodel5, "it.unibo.realrobot5.Realrobot5"   ). %%control-driven 
qactor( virtualrobot5 , ctxanalysismodel5, "it.unibo.virtualrobot5.MsgHandle_Virtualrobot5"   ). %%store msgs 
qactor( virtualrobot5_ctrl , ctxanalysismodel5, "it.unibo.virtualrobot5.Virtualrobot5"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

