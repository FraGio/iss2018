%====================================================================================
% Context ctxProvider  SYSTEM-configuration: file it.unibo.ctxProvider.reqAnalysisModel.pl 
%====================================================================================
context(ctxprovider, "localhost",  "TCP", "8022" ).  		 
%%% -------------------------------------------
qactor( temperaturetimeprovider , ctxprovider, "it.unibo.temperaturetimeprovider.MsgHandle_Temperaturetimeprovider"   ). %%store msgs 
qactor( temperaturetimeprovider_ctrl , ctxprovider, "it.unibo.temperaturetimeprovider.Temperaturetimeprovider"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

