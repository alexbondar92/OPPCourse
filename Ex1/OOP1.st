Object subclass: #Monom	instanceVariableNames: 'exp coef'	classVariableNames: ''	poolDictionaries: ''	category: 'OOP1'!!Monom methodsFor: 'exp' stamp: 'Do 11/7/2018 13:18'!exp
	^exp.! !!Monom methodsFor: 'exp' stamp: 'Do 11/7/2018 13:55'!exp: anInteger
	((exp < 0) | (self class = Integer)) ifTrue: [self error: 'invalid input']
	ifFalse: [exp := anInteger].! !!Monom methodsFor: 'coef' stamp: 'Do 11/7/2018 13:57'!coef
	^coef.! !!Monom methodsFor: 'coef' stamp: 'Do 11/7/2018 14:02'!coef: anInteger
	(self class = Integer) ifTrue: [self error: 'invalid input']
	ifFalse: [coef := anInteger].! !!Monom methodsFor: 'derivative' stamp: 'Do 11/7/2018 14:04'!derivative
	(exp = 0) ifTrue: [coef := 0] ifFalse: [coef := coef * exp . exp := exp - 1]! !!Monom methodsFor: 'initialize-release' stamp: 'Do 11/7/2018 13:16'!initialize
	exp := 0.
	coef := 0. 
	! !Object subclass: #PolyStream	instanceVariableNames: 'block'	classVariableNames: ''	poolDictionaries: ''	category: 'OOP1'!!PolyStream methodsFor: 'methods' stamp: 'a 11/8/2018 18:21'!add: aPolyStream	|tempBlock|	(aPolyStream class = PolyStream) ifFalse: [self error: 'invalid input'].	tempBlock := block.	block := [:x :filterSet| (tempBlock value: x value: filterSet) + ((aPolyStream block) value: x value: filterSet)]! !!PolyStream methodsFor: 'methods' stamp: 'a 11/8/2018 18:18'!addCoef: coef withExp: exp	|tempBlock|	((coef isInteger) and: (exp isInteger) and: (exp >= 0)) ifFalse: [self error: 'invalid input'].	tempBlock := block.	block := [:x :filterSet|(filterSet includes: exp) ifFalse:[(tempBlock value: x value: filterSet) + (coef*(x raisedTo: exp))]												ifTrue: [(tempBlock value: x value: filterSet)]].! !!PolyStream methodsFor: 'methods' stamp: 'a 11/8/2018 17:46'!block	^block.! !!PolyStream methodsFor: 'methods' stamp: 'a 11/8/2018 18:02'!eval: anInteger	^(block value: anInteger value: (Set new)).! !!PolyStream methodsFor: 'methods' stamp: 'a 11/8/2018 18:25'!filter: aSet	|tempBlock newSet|	newSet := aSet deepCopy.	tempBlock := block.	block := [:x :filterSet| (tempBlock value: x value: (filterSet, newSet))].! !!PolyStream methodsFor: 'methods' stamp: 'a 11/12/2018 19:30'!intersectionWith: aPolyStream withinRange: anInteger willUpdate: aBoolean	|anX P1 P2 P1T P2T|	anX := 0.	P1T := self.	P2T := aPolyStream.	P1 := self block.	P2 := aPolyStream block.	(aBoolean = true) ifTrue: [^[(P1T eval: anX) = (P2T eval: anX) 															ifTrue: [anX]															ifFalse: [(anX abs > anInteger) ifTrue: [self error: 'invalid input']																							ifFalse: [(anX <= 0) ifTrue: [anX := (-1)*(anX)+1. 'wait for it...']																												ifFalse: [anX := (-1)*(anX). 'wait for it...']]]]]			ifFalse: [^[(P1 value: anX value: (Set new)) = (P2 value: anX value: (Set new)) 															ifTrue: [anX]															ifFalse: [(anX abs > anInteger) ifTrue: [self error: 'invalid input']																							ifFalse: [(anX <= 0) ifTrue: [anX := (-1)*(anX)+1. 'wait for it...']																												ifFalse: [anX := (-1)*(anX). 'wait for it...']]]]].													! !!PolyStream methodsFor: 'methods' stamp: 'a 11/12/2018 19:32'!multiplyBy: anInteger	|tempBlock|	((anInteger isInteger)) ifFalse: [self error: 'invalid input'].	tempBlock := block.	block := [:x :filterSet| anInteger*(tempBlock value: (x) value: filterSet)].! !!PolyStream methodsFor: 'methods' stamp: 'a 11/8/2018 18:18'!substitute: anInteger	|tempBlock|	((anInteger isInteger) and: (anInteger ~=0)) ifFalse: [self error: 'invalid input'].	tempBlock := block.	block := [:x :filterSet| (tempBlock value: (anInteger*x) value: filterSet)].! !!PolyStream methodsFor: 'initialize-release' stamp: 'a 11/8/2018 17:40'!initialize	block := [:x :filterSet| 0]! !Object subclass: #Polynom	instanceVariableNames: 'monoms'	classVariableNames: ''	poolDictionaries: ''	category: 'OOP1'!!Polynom methodsFor: 'initialize-release' stamp: 'Do 11/7/2018 14:16'!initialize
	monoms := Dictionary new.
	! !!Polynom methodsFor: 'as yet unclassified' stamp: 'Do 11/7/2018 17:55'!add: aPolynom
	|newPoly tempDict newMonom|
	(aPolynom class = Polynom) ifFalse: [self error: 'invalid input'].
	newPoly := Polynom new.
	tempDict := Dictionary new.
	newMonom := Monom new.
	monoms do: [:monom | newPoly add: monom].
	tempDict := aPolynom as Dictionary.
	tempDict keysDo:[:exps | newMonom exp:exps. newMonom coef: ((tempDict at: exps) coef). newPoly addMonom: newMonom].
	^newPoly.! !!Polynom methodsFor: 'as yet unclassified' stamp: 'Do 11/7/2018 17:15'!addMonom: aMonom
	|newMonom aCoef aExp|
	(aMonom class = Monom) ifFalse: [self error: 'invalid input'].
	aExp := aMonom exp.
	aCoef := aMonom coef.
	(aMonom coef ~= 0) ifTrue: [
		(monoms includesKey: (aMonom exp)) ifTrue: 
			[newMonom := Monom new. newMonom exp:(aExp). newMonom coef:(aCoef + ((monoms at:aExp) coef)).
			monoms removeKey: (aMonom exp).
		 	monoms add: (aMonom exp)->newMonom]
						"[monoms at:  (aMonom exp) put:  (aMonom coef + monoms at: (aMonom exp))]" 			
			ifFalse: [newMonom := Monom new. newMonom exp:(aExp). 				
					newMonom coef:(aCoef). monoms add: (aMonom exp)->(aMonom coef)]].! !!Polynom methodsFor: 'as yet unclassified' stamp: 'Do 11/7/2018 17:21'!asDictionary
	|monomsRet|
	monomsRet := Dictionary new.
	monoms keysDo: [:exps | monomsRet add: exps->((monoms at:exps) coef)].
	^monomsRet.! !!Polynom methodsFor: 'as yet unclassified' stamp: 'Do 11/7/2018 17:36'!derivative
	|newPolynom newMonom|
	newMonom := Monom new.
	newPolynom := Polynom new.
	"newMonoms := Dictionary new."
	monoms keysDo: [:exps | (exps ~= 0) ifTrue: [newMonom coef: (exps* ((monoms at:exps) coef)). newMonom exp: (exps-1).  newPolynom addMonom: (exps-1)->newMonom]].
	^newPolynom.! !!Polynom methodsFor: 'as yet unclassified' stamp: 'Do 11/7/2018 17:38'!eval: anInteger
	|res|
	(anInteger class = Integer) ifFalse: [self error: 'invalid input'].
	res := 0.
	monoms keysDo: [ :exps | res = res + ((monoms at: exps) coef) * (anInteger raisedTo: exps)].
	^res.! !!Polynom methodsFor: 'as yet unclassified' stamp: 'Do 11/7/2018 17:19'!multiplyByMonom: aMonom
 	|newMonoms newMonom aExp aCoef|
	(aMonom class = Monom) ifFalse: [self error: 'invalid input'].
	aExp := aMonom exp.
	aCoef := aMonom coef.
	"monoms do: [ :coefs | coefs = coefs * (aMonom coef)]".
	monoms do: [ :monom |  monom coef: ((monom coef) * (aMonom coef))].
	newMonoms := Dictionary new.
	monoms keysDo: [ :exps | newMonom := Monom new. newMonom exp:(exps + aExp). newMonom coef:((monoms at:aExp) coef). newMonoms add: (exps+(aMonom exp))->(newMonom).].
	monoms = newMonoms.! !