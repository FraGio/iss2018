%====================================================================================
% Context ctxPC  SYSTEM-configuration: file it.unibo.ctxPC.reqAnalysisModel.pl 
%====================================================================================
context(ctxpc, "localhost",  "TCP", "8010" ).  		 
context(ctxrobot, "localhost",  "TCP", "8011" ).  		 
%%% -------------------------------------------
qactor( pc , ctxpc, "it.unibo.pc.MsgHandle_Pc"   ). %%store msgs 
qactor( pc_ctrl , ctxpc, "it.unibo.pc.Pc"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

