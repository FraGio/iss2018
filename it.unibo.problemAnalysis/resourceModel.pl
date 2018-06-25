/*
===============================================================
resourceModel.pl
===============================================================
*/

/*
le risorse si dividono in due tipi, sensori ed attuatori
*/

model( type(actuator,leds), name(ledfisico), value(off) ).
model( type(actuator,leds), name(ledhuelamp), value(off) ).
model( type(data,timer), name(timevalue), value(1) ).
model( type(data,temperature), name(temperaturevalue), value(25) ).

realRobot :- true.
virtualRobot :- true.

getModelItem( TYPE, CATEG, NAME, VALUE ) :-
		model( type(TYPE, CATEG), name(NAME), value(VALUE) ).
		
changeModelItems(CATEG1, NAME1, VALUE1,CATEG2, NAME2, VALUE2 ) :-
		%%output( "updating values in resource model..." ),
		changeModelItem( CATEG1, NAME1, VALUE1 ),
		changeModelItem( CATEG2, NAME2, VALUE2 ).
		
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



/*  		
	changedModelAction( temperature, t1, V  ):-
 		    eval( ge, V , 30 ), !,  
 		    changemodelitem( leds, led1, on).		     
 	changedModelAction( temperature, t1, V  ):-	 
 			changemodelitem( leds, led1, off).     			 			
 	changedModelAction( leds, led1, V  ):-
 			emitevent( ctrlEvent, ctrlEvent( leds, led1, V) ).
 */
