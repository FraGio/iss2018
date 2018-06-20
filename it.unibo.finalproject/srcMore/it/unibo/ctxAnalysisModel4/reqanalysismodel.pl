%====================================================================================
% Context ctxAnalysisModel4  SYSTEM-configuration: file it.unibo.ctxAnalysisModel4.reqAnalysisModel.pl 
%====================================================================================
context(ctxanalysismodel4, "localhost",  "TCP", "8010" ).  		 
%%% -------------------------------------------
qactor( humanoperator4 , ctxanalysismodel4, "it.unibo.humanoperator4.MsgHandle_Humanoperator4"   ). %%store msgs 
qactor( humanoperator4_ctrl , ctxanalysismodel4, "it.unibo.humanoperator4.Humanoperator4"   ). %%control-driven 
qactor( applicationlogiccmd , ctxanalysismodel4, "it.unibo.applicationlogiccmd.MsgHandle_Applicationlogiccmd"   ). %%store msgs 
qactor( applicationlogiccmd_ctrl , ctxanalysismodel4, "it.unibo.applicationlogiccmd.Applicationlogiccmd"   ). %%control-driven 
qactor( applicationlogictemp , ctxanalysismodel4, "it.unibo.applicationlogictemp.MsgHandle_Applicationlogictemp"   ). %%store msgs 
qactor( applicationlogictemp_ctrl , ctxanalysismodel4, "it.unibo.applicationlogictemp.Applicationlogictemp"   ). %%control-driven 
qactor( applicationlogictime , ctxanalysismodel4, "it.unibo.applicationlogictime.MsgHandle_Applicationlogictime"   ). %%store msgs 
qactor( applicationlogictime_ctrl , ctxanalysismodel4, "it.unibo.applicationlogictime.Applicationlogictime"   ). %%control-driven 
qactor( temperatureemitter , ctxanalysismodel4, "it.unibo.temperatureemitter.MsgHandle_Temperatureemitter"   ). %%store msgs 
qactor( temperatureemitter_ctrl , ctxanalysismodel4, "it.unibo.temperatureemitter.Temperatureemitter"   ). %%control-driven 
qactor( timemitter , ctxanalysismodel4, "it.unibo.timemitter.MsgHandle_Timemitter"   ). %%store msgs 
qactor( timemitter_ctrl , ctxanalysismodel4, "it.unibo.timemitter.Timemitter"   ). %%control-driven 
qactor( realrobot4 , ctxanalysismodel4, "it.unibo.realrobot4.MsgHandle_Realrobot4"   ). %%store msgs 
qactor( realrobot4_ctrl , ctxanalysismodel4, "it.unibo.realrobot4.Realrobot4"   ). %%control-driven 
qactor( virtualrobot4 , ctxanalysismodel4, "it.unibo.virtualrobot4.MsgHandle_Virtualrobot4"   ). %%store msgs 
qactor( virtualrobot4_ctrl , ctxanalysismodel4, "it.unibo.virtualrobot4.Virtualrobot4"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

