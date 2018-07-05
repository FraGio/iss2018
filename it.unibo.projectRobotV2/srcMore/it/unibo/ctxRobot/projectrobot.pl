%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.projectRobot.pl 
%====================================================================================
context(ctxrobot, "192.168.1.112",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( realrobot , ctxrobot, "it.unibo.realrobot.MsgHandle_Realrobot"   ). %%store msgs 
qactor( realrobot_ctrl , ctxrobot, "it.unibo.realrobot.Realrobot"   ). %%control-driven 
qactor( virtualrobot , ctxrobot, "it.unibo.virtualrobot.MsgHandle_Virtualrobot"   ). %%store msgs 
qactor( virtualrobot_ctrl , ctxrobot, "it.unibo.virtualrobot.Virtualrobot"   ). %%control-driven 
qactor( led , ctxrobot, "it.unibo.led.MsgHandle_Led"   ). %%store msgs 
qactor( led_ctrl , ctxrobot, "it.unibo.led.Led"   ). %%control-driven 
qactor( ledhuelamp , ctxrobot, "it.unibo.ledhuelamp.MsgHandle_Ledhuelamp"   ). %%store msgs 
qactor( ledhuelamp_ctrl , ctxrobot, "it.unibo.ledhuelamp.Ledhuelamp"   ). %%control-driven 
qactor( sonar1 , ctxrobot, "it.unibo.sonar1.MsgHandle_Sonar1"   ). %%store msgs 
qactor( sonar1_ctrl , ctxrobot, "it.unibo.sonar1.Sonar1"   ). %%control-driven 
qactor( sonar2 , ctxrobot, "it.unibo.sonar2.MsgHandle_Sonar2"   ). %%store msgs 
qactor( sonar2_ctrl , ctxrobot, "it.unibo.sonar2.Sonar2"   ). %%control-driven 
qactor( robotsonar , ctxrobot, "it.unibo.robotsonar.MsgHandle_Robotsonar"   ). %%store msgs 
qactor( robotsonar_ctrl , ctxrobot, "it.unibo.robotsonar.Robotsonar"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh1,ctxrobot,"it.unibo.ctxRobot.Evh1","robotCmd").  
eventhandler(evh2,ctxrobot,"it.unibo.ctxRobot.Evh2","robotCmd").  
%%% -------------------------------------------

