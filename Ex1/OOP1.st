Object subclass: #Monom
	^exp.! !
	((exp < 0) | (self class = Integer)) ifTrue: [self error: 'invalid input']
	ifFalse: [exp := anInteger].! !
	^coef.! !
	(self class = Integer) ifTrue: [self error: 'invalid input']
	ifFalse: [coef := anInteger].! !
	(exp = 0) ifTrue: [coef := 0] ifFalse: [coef := coef * exp . exp := exp - 1]! !
	exp := 0.
	coef := 0. 
	! !
	monoms := Dictionary new.
	! !
	|newPoly tempDict newMonom|
	(aPolynom class = Polynom) ifFalse: [self error: 'invalid input'].
	newPoly := Polynom new.
	tempDict := Dictionary new.
	newMonom := Monom new.
	monoms do: [:monom | newPoly add: monom].
	tempDict := aPolynom as Dictionary.
	tempDict keysDo:[:exps | newMonom exp:exps. newMonom coef: ((tempDict at: exps) coef). newPoly addMonom: newMonom].
	^newPoly.! !
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
					newMonom coef:(aCoef). monoms add: (aMonom exp)->(aMonom coef)]].! !
	|monomsRet|
	monomsRet := Dictionary new.
	monoms keysDo: [:exps | monomsRet add: exps->((monoms at:exps) coef)].
	^monomsRet.! !
	|newPolynom newMonom|
	newMonom := Monom new.
	newPolynom := Polynom new.
	"newMonoms := Dictionary new."
	monoms keysDo: [:exps | (exps ~= 0) ifTrue: [newMonom coef: (exps* ((monoms at:exps) coef)). newMonom exp: (exps-1).  newPolynom addMonom: (exps-1)->newMonom]].
	^newPolynom.! !
	|res|
	(anInteger class = Integer) ifFalse: [self error: 'invalid input'].
	res := 0.
	monoms keysDo: [ :exps | res = res + ((monoms at: exps) coef) * (anInteger raisedTo: exps)].
	^res.! !
 	|newMonoms newMonom aExp aCoef|
	(aMonom class = Monom) ifFalse: [self error: 'invalid input'].
	aExp := aMonom exp.
	aCoef := aMonom coef.
	"monoms do: [ :coefs | coefs = coefs * (aMonom coef)]".
	monoms do: [ :monom |  monom coef: ((monom coef) * (aMonom coef))].
	newMonoms := Dictionary new.
	monoms keysDo: [ :exps | newMonom := Monom new. newMonom exp:(exps + aExp). newMonom coef:((monoms at:aExp) coef). newMonoms add: (exps+(aMonom exp))->(newMonom).].
	monoms = newMonoms.! !