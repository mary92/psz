package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import org.semanticweb.owl.model.OWLClass;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModel;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.Selectable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 6, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
@SuppressWarnings("all")
public class IncludedExtrasListPanel extends ExtrasListPanel {

	private CoffeeChoiceModel choiceModel;

	private Selectable selectable;

	private Action addAction = new AbstractAction("Add") {
			public void actionPerformed(ActionEvent e) {
				Object selObj = selectable.getSelection();
				if(selObj != null && selObj instanceof OWLClass) {
					choiceModel.addIncluded((OWLClass) selObj);
				}
			}
		};

	private Action removeAction = new AbstractAction("Rem") {
			public void actionPerformed(ActionEvent e) {
				Object selObj = getSelection();
				if(selObj != null && selObj instanceof OWLClass) {
					choiceModel.removeIncluded((OWLClass) selObj);
				}
			}
		};

	public IncludedExtrasListPanel(final Selectable selectable,
	                               final CoffeeChoiceModel choiceModel) {
		super("Ukljuceni dodaci:", selectable, choiceModel);
		this.choiceModel = choiceModel;
		this.selectable = selectable;
		createUI();
	}


	protected Collection getListItems() {
		return choiceModel.getIncluded();
	}


	protected Action getAddAction() {
		return addAction;
	}


	protected Action getRemoveAction() {
		return removeAction;
	}
}

