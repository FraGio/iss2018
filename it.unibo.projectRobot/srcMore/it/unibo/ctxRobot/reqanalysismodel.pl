%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.reqAnalysisModel.pl 
%====================================================================================
context(ctxrobot, "192.168.1.112",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( mind , ctxrobot, "it.unibo.mind.MsgHandle_Mind"   ). %%store msgs 
qactor( mind_ctrl , ctxrobot, "it.unibo.mind.Mind"   ). %%control-driven 
qactor( movecorelogic , ctxrobot, "it.unibo.movecorelogic.MsgHandle_Movecorelogic"   ). %%store msgs 
qactor( movecorelogic_ctrl , ctxrobot, "it.unibo.movecorelogic.Movecorelogic"   ). %%control-driven 
qactor( fixedobstaclemanager , ctxrobot, "it.unibo.fixedobstaclemanager.MsgHandle_Fixedobstaclemanager"   ). %%store msgs 
qactor( fixedobstaclemanager_ctrl , ctxrobot, "it.unibo.fixedobstaclemanager.Fixedobstaclemanager"   ). %%control-driven 
qactor( led , ctxrobot, "it.unibo.led.MsgHandle_Led"   ). %%store msgs 
qactor( led_ctrl , ctxrobot, "it.unibo.led.Led"   ). %%control-driven 
qactor( ledhuelamp , ctxrobot, "it.unibo.ledhuelamp.MsgHandle_Ledhuelamp"   ). %%store msgs 
qactor( ledhuelamp_ctrl , ctxrobot, "it.unibo.ledhuelamp.Ledhuelamp"   ). %%control-driven 
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
qactor( mapper , ctxrobot, "it.unibo.mapper.MsgHandle_Mapper"   ). %%store msgs 
qactor( mapper_ctrl , ctxrobot, "it.unibo.mapper.Mapper"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxrobot,"it.unibo.ctxRobot.Evh","coreCmdStop").  
eventhandler(evh1,ctxrobot,"it.unibo.ctxRobot.Evh1","robotCmd").  
eventhandler(evh2,ctxrobot,"it.unibo.ctxRobot.Evh2","robotCmd").  
%%% -------------------------------------------

