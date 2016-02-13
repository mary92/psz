package uk.ac.man.cs.mig.coode.pizzafinder.model;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 6, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class CoffeeChoiceModelChangedEvent {
	private CoffeeChoiceModel source;


	public CoffeeChoiceModelChangedEvent(CoffeeChoiceModel choiceModelCoffee) {
		this.source = choiceModelCoffee;
	}


	public CoffeeChoiceModel getSource() {
		return source;
	}
}

