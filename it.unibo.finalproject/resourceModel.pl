/*
===============================================================
resourceModel.pl
===============================================================
*/
model( type(actuator, leds),      name(ledFisico), value(off) ).
model( type(sensor, temperature), name(tempAmbiente),   value(25)  ).
model( type(timer, timee), name(timeData),   value(8)  ).
model( type(robot, robotStatus), name(status),   value(off)  ).

getModelItem( TYPE, CATEG, NAME, VALUE ) :-
		model( type(TYPE, CATEG), name(NAME), value(VALUE) ).
changeModelItem( CATEG, NAME, VALUE ) :-
 		replaceRule( 
			model( type(TYPE, CATEG), name(NAME), value(_) ),  
			model( type(TYPE, CATEG), name(NAME), value(VALUE) ) 		
		),!,
		%%output( changedModelAction(CATEG, NAME, VALUE) ),
		( changedModelAction(CATEG, NAME, VALUE) %%to be defined by the appl designer
		  ; true ).		%%to avoid the failure if no changedModelAction is defined
		
eval( ge, X, X ) :- !. 
eval( ge, X, V ) :- eval( gt, X , V ) .
 
emitevent( EVID, EVCONTENT ) :- 
	actorobj( Actor ), 
	%%output( emit( Actor, EVID, EVCONTENT ) ),
	Actor <- emit( EVID, EVCONTENT ).
%%%  initialize
initResourceTheory :- output("initializing the initResourceTheory ...").
:- initialization(initResourceTheory).