/*
===============================================================
resourceModel.pl
===============================================================
*/

model( type(leds), name(ledFisico), value(off) ).
model( type(leds), name(ledHueLamp), value(off) ).
model( type(timer), name(timeValue), value(1) ).
model( type(temperature), name(temperatureValue), value(25) ).


%%isRealRobot.
		
changeModelItem( CATEG, NAME, VALUE ) :-
 		replaceRule( 
			model( type(CATEG), name(NAME), value(_) ),  
			model( type(CATEG), name(NAME), value(VALUE) ) 		
		),!,
		output( changedModelAction(CATEG, NAME, VALUE) ),
		( changedModelAction(CATEG, NAME, VALUE) %%to be defined by the appl designer
		  ; true ).		%%to avoid the failure if no changedModelAction is defined
		
		limitTemperatureValue( 25 ).
		
		changedModelAction( temperature, tempAmbiente, V  ):-
				limitTemperatureValue( MAX ), 
	 		    eval( ge, V , MAX ), !,   
	 		    output('temperatura sopra limite'),
	 			emitevent( robotCmd,  robotCmd ('STOP') ).
	 		
	 			
	 	limitTimeValueMin( 7 ).
		limitTimeValueMax( 10 ).
		
	 	changedModelAction(timee,timeData, T ):-
				limitTimeValueMin( MIN ),
				limitTimeValueMax( MAX ),
	 		    eval( le, T , MIN ), !,  
	 		    output('tempo fuori range'),
	 			emitevent( robotCmd,  robotCmd ('STOP') ).
	 			
	 	changedModelAction(timee,timeData, T ):-
				limitTimeValueMin( MIN ),
				limitTimeValueMax( MAX ),
	 		    eval( ge, T , MAX ), !,   
	 		    output('tempo fuori range'),
	 			emitevent( robotCmd,  robotCmd ('STOP') ).
eval( ge, X, X ) :- !. 
eval( ge, X, V ) :- eval( gt, X , V ) .
 
emitevent( EVID, EVCONTENT ) :- 
	actorobj( Actor ), 
	%%output( emit( Actor, EVID, EVCONTENT ) ),
	Actor <- emit( EVID, EVCONTENT ).
%%%  initialize
initResourceTheory :- output("initializing the initResourceTheory ...").
:- initialization(initResourceTheory).



/*  		
	changedModelAction( temperature, t1, V  ):-
 		    eval( ge, V , 30 ), !,  
 		    changemodelitem( leds, led1, on).		     
 	changedModelAction( temperature, t1, V  ):-	 
 			changemodelitem( leds, led1, off).     			 			
 	changedModelAction( leds, led1, V  ):-
 			emitevent( ctrlEvent, ctrlEvent( leds, led1, V) ).
 */
