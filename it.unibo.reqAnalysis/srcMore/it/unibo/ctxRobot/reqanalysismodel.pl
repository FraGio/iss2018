%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.reqAnalysisModel.pl 
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( robot , ctxrobot, "it.unibo.robot.MsgHandle_Robot"   ). %%store msgs 
qactor( robot_ctrl , ctxrobot, "it.unibo.robot.Robot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

