%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.reqAnalysisModel.pl 
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( mind , ctxrobot, "it.unibo.mind.MsgHandle_Mind"   ). %%store msgs 
qactor( mind_ctrl , ctxrobot, "it.unibo.mind.Mind"   ). %%control-driven 
qactor( led , ctxrobot, "it.unibo.led.MsgHandle_Led"   ). %%store msgs 
qactor( led_ctrl , ctxrobot, "it.unibo.led.Led"   ). %%control-driven 
qactor( ledhuelamp , ctxrobot, "it.unibo.ledhuelamp.MsgHandle_Ledhuelamp"   ). %%store msgs 
qactor( ledhuelamp_ctrl , ctxrobot, "it.unibo.ledhuelamp.Ledhuelamp"   ). %%control-driven 
qactor( movecorelogic , ctxrobot, "it.unibo.movecorelogic.MsgHandle_Movecorelogic"   ). %%store msgs 
qactor( movecorelogic_ctrl , ctxrobot, "it.unibo.movecorelogic.Movecorelogic"   ). %%control-driven 
qactor( realrobot , ctxrobot, "it.unibo.realrobot.MsgHandle_Realrobot"   ). %%store msgs 
qactor( realrobot_ctrl , ctxrobot, "it.unibo.realrobot.Realrobot"   ). %%control-driven 
qactor( virtualrobot , ctxrobot, "it.unibo.virtualrobot.MsgHandle_Virtualrobot"   ). %%store msgs 
qactor( virtualrobot_ctrl , ctxrobot, "it.unibo.virtualrobot.Virtualrobot"   ). %%control-driven 
qactor( sonar1 , ctxrobot, "it.unibo.sonar1.MsgHandle_Sonar1"   ). %%store msgs 
qactor( sonar1_ctrl , ctxrobot, "it.unibo.sonar1.Sonar1"   ). %%control-driven 
qactor( sonar2 , ctxrobot, "it.unibo.sonar2.MsgHandle_Sonar2"   ). %%store msgs 
qactor( sonar2_ctrl , ctxrobot, "it.unibo.sonar2.Sonar2"   ). %%control-driven 
qactor( robotsonar , ctxrobot, "it.unibo.robotsonar.MsgHandle_Robotsonar"   ). %%store msgs 
qactor( robotsonar_ctrl , ctxrobot, "it.unibo.robotsonar.Robotsonar"   ). %%control-driven 
qactor( notifier , ctxrobot, "it.unibo.notifier.MsgHandle_Notifier"   ). %%store msgs 
qactor( notifier_ctrl , ctxrobot, "it.unibo.notifier.Notifier"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxrobot,"it.unibo.ctxRobot.Evh","coreCmd").  
%%% -------------------------------------------

