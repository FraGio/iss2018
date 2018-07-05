%====================================================================================
% Context ctxRobotMind  SYSTEM-configuration: file it.unibo.ctxRobotMind.project2Robot.pl 
%====================================================================================
context(ctxrobotmind, "192.168.1.112",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( mind , ctxrobotmind, "it.unibo.mind.MsgHandle_Mind"   ). %%store msgs 
qactor( mind_ctrl , ctxrobotmind, "it.unibo.mind.Mind"   ). %%control-driven 
qactor( movecorelogic , ctxrobotmind, "it.unibo.movecorelogic.MsgHandle_Movecorelogic"   ). %%store msgs 
qactor( movecorelogic_ctrl , ctxrobotmind, "it.unibo.movecorelogic.Movecorelogic"   ). %%control-driven 
qactor( fixedobstaclemanager , ctxrobotmind, "it.unibo.fixedobstaclemanager.MsgHandle_Fixedobstaclemanager"   ). %%store msgs 
qactor( fixedobstaclemanager_ctrl , ctxrobotmind, "it.unibo.fixedobstaclemanager.Fixedobstaclemanager"   ). %%control-driven 
qactor( notifier , ctxrobotmind, "it.unibo.notifier.MsgHandle_Notifier"   ). %%store msgs 
qactor( notifier_ctrl , ctxrobotmind, "it.unibo.notifier.Notifier"   ). %%control-driven 
qactor( mapper , ctxrobotmind, "it.unibo.mapper.MsgHandle_Mapper"   ). %%store msgs 
qactor( mapper_ctrl , ctxrobotmind, "it.unibo.mapper.Mapper"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxrobotmind,"it.unibo.ctxRobotMind.Evh","coreCmdStop").  
%%% -------------------------------------------

