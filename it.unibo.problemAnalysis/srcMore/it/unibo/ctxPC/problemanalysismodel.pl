%====================================================================================
% Context ctxPC  SYSTEM-configuration: file it.unibo.ctxPC.problemAnalysisModel.pl 
%====================================================================================
context(ctxpc, "localhost",  "TCP", "8020" ).  		 
%%% -------------------------------------------
qactor( pc , ctxpc, "it.unibo.pc.MsgHandle_Pc"   ). %%store msgs 
qactor( pc_ctrl , ctxpc, "it.unibo.pc.Pc"   ). %%control-driven 
qactor( user , ctxpc, "it.unibo.user.MsgHandle_User"   ). %%store msgs 
qactor( user_ctrl , ctxpc, "it.unibo.user.User"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

