%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.reqAnalysisModel.pl 
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( robotmind , ctxrobot, "it.unibo.robotmind.MsgHandle_Robotmind"   ). %%store msgs 
qactor( robotmind_ctrl , ctxrobot, "it.unibo.robotmind.Robotmind"   ). %%control-driven 
qactor( sonar2 , ctxrobot, "it.unibo.sonar2.MsgHandle_Sonar2"   ). %%store msgs 
qactor( sonar2_ctrl , ctxrobot, "it.unibo.sonar2.Sonar2"   ). %%control-driven 
qactor( sonar1 , ctxrobot, "it.unibo.sonar1.MsgHandle_Sonar1"   ). %%store msgs 
qactor( sonar1_ctrl , ctxrobot, "it.unibo.sonar1.Sonar1"   ). %%control-driven 
qactor( led , ctxrobot, "it.unibo.led.MsgHandle_Led"   ). %%store msgs 
qactor( led_ctrl , ctxrobot, "it.unibo.led.Led"   ). %%control-driven 
qactor( ledhuelamp , ctxrobot, "it.unibo.ledhuelamp.MsgHandle_Ledhuelamp"   ). %%store msgs 
qactor( ledhuelamp_ctrl , ctxrobot, "it.unibo.ledhuelamp.Ledhuelamp"   ). %%control-driven 
qactor( realrobot , ctxrobot, "it.unibo.realrobot.MsgHandle_Realrobot"   ). %%store msgs 
qactor( realrobot_ctrl , ctxrobot, "it.unibo.realrobot.Realrobot"   ). %%control-driven 
qactor( virtualrobot , ctxrobot, "it.unibo.virtualrobot.MsgHandle_Virtualrobot"   ). %%store msgs 
qactor( virtualrobot_ctrl , ctxrobot, "it.unibo.virtualrobot.Virtualrobot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

