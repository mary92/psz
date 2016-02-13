package uk.ac.man.cs.mig.coode.pizzafinder.model;

import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntologyManager;
import uk.ac.manchester.cs.factplusplus.owlapi.Reasoner;

import java.util.*;

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
public class CoffeeChoiceModel {

	CoffeeOntology ontology;

	private HashSet included;
	private HashSet excluded;

	private ArrayList listeners;

    OWLOntologyManager manager;
    public static  CoffeePreferences PREFERENCES ;

    public CoffeeChoiceModel(CoffeeOntology ontology2) {
		this.ontology = ontology2;
		listeners = new ArrayList();
		included = new HashSet();
		excluded = new HashSet();
	}

	public Set getIncluded() {
		return included;
	}

	public Set getExcluded() {
		return excluded;
	}

	/**
	 * Adds the specified class to the list of included
	 * classes. This will remove any descendants of the
	 * class from the included list (as they are subsumed by
	 * the specified class).  It also remove any ancestor classes
	 * of the specified class (as they have been replaced by something
	 * more specific) and also removes the class and
	 * its ancestor classes from the list of excluded list.
	 * @param cls The class to add
	 */
	public void addIncluded(OWLClass cls) {
		try {
			Reasoner  reasoner = getReasoner();
            System.out.print(reasoner.getLoadedOntologies());
            boolean changed = false;
			changed |= included.add(cls);
			changed |= included.removeAll(reasoner.getDescendantClasses(cls));
			changed |= included.removeAll(reasoner.getAncestorClasses(cls));
			changed |= excluded.remove(cls);
			changed |= excluded.removeAll(reasoner.getAncestorClasses(cls));
			if(changed) {
				fireModelChangedEvent();
			}
		}
		catch(OWLReasonerException e) {
			e.printStackTrace();
		}

	}

	public void setIncluded(OWLClass cls) {
		Reasoner  reasoner = getReasoner();
		System.out.print(reasoner.getLoadedOntologies());
		boolean changed = false;
		included.clear();
		changed |= included.add(cls);
		if(changed) {
			fireModelChangedEvent();
		}
	}
	
	/**
	 * Removes the specified class from the list of
	 * included classes.
	 * @param cls The class to be removed.
	 */
	public void removeIncluded(OWLClass cls) {
		if(included.remove(cls)) {
			fireModelChangedEvent();
		}
	}

	/**
	 * Adds the specified class to the list of excluded
	 * classes. This will remove any descendants of the
	 * class from the excluded list.  This will also
	 * remove the class and its descendants from the list
	 * of included classes.
	 * @param cls
	 */
	public void addExcluded(OWLClass cls) {
		try {
			Reasoner reasoner = getReasoner();
			boolean changed = false;
			changed |= excluded.add(cls);
			changed |= excluded.removeAll(reasoner.getAncestorClasses(cls));
			changed |= excluded.removeAll(reasoner.getDescendantClasses(cls));
			changed |= included.remove(cls);
			changed |= included.removeAll(reasoner.getDescendantClasses(cls));
			if(changed) {
				fireModelChangedEvent();
			}
		}
		catch(OWLReasonerException e) {
			e.printStackTrace();
		}
	}

	public void removeExcluded(OWLClass cls) {
		if(excluded.remove(cls)) {
			fireModelChangedEvent();
		}
	}

	public void addChoiceModelListener(CoffeeChoiceModelListener lsnr) {
		listeners.add(lsnr);
	}

	public void removeChoiceModelListener(CoffeeChoiceModelListener lsnr) {
		listeners.remove(lsnr);
	}

	protected void fireModelChangedEvent() {
		Iterator it = new ArrayList(listeners).iterator();
		while(it.hasNext()) {
			((CoffeeChoiceModelListener)it.next()).modelChanged(new CoffeeChoiceModelChangedEvent(this));
		}
	}

	private Reasoner getReasoner() {
       return  ontology.getReasoner();
	}

}

