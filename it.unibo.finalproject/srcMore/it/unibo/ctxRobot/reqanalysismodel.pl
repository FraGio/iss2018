%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.reqAnalysisModel.pl 
%====================================================================================
context(ctxpc, "localhost",  "TCP", "8020" ).  		 
context(ctxrobot, "localhost",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( pc , ctxpc, "it.unibo.pc.MsgHandle_Pc"   ). %%store msgs 
qactor( pc_ctrl , ctxpc, "it.unibo.pc.Pc"   ). %%control-driven 
qactor( robot , ctxrobot, "it.unibo.robot.MsgHandle_Robot"   ). %%store msgs 
qactor( robot_ctrl , ctxrobot, "it.unibo.robot.Robot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

