package uk.ac.man.cs.mig.coode.pizzafinder.model;


import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.semanticweb.owl.vocab.XSDVocabulary;
import uk.ac.manchester.cs.factplusplus.owlapi.Reasoner;

import javax.swing.*;
import java.net.URI;
import java.util.*;


/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * This class contains the methods that wrap up the pizza ontology
 * into a more convenient format for querying the ontology and obtaining
 * the important classes such as the pizza topping classes, the vegetarian
 * pizza class and the hot pizza class.
 */
@SuppressWarnings("all")
public class CoffeeOntology {

    public static final CoffeePreferences PREFERENCES;

    private  Reasoner reasoner;
    private  OWLOntology ontology;
    private OWLOntologyManager manager;

    private Map<String, URI> classnameCache;
    private Map<String, URI> objPropNameCache;
    private Map<String, URI> dataPropNameCache;

    static {
        PREFERENCES = CoffeePreferences.getInstance();
    }

    public CoffeeOntology() {
        loadOntology();
        setupReasoner();
    }

    protected void loadOntology() {
        try {

            manager = OWLManager.createOWLOntologyManager();

            URI physicalURI = URI.create(PREFERENCES.getOntologyURL());

            // Now do the loading
            ontology = manager.loadOntologyFromPhysicalURI(physicalURI);

            manager.setPhysicalURIForOntology(ontology,physicalURI);

        }
        catch(final Exception e) {
            Runnable runnable = new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(null,
                                                  "Could not create the ontology.  (This probably happened\n" +
                                                  "because the ontology could not be accessed due to network\n" +
                                                  "problems.)\n" +
                                                  "[" + e.getMessage() + "]",
                                                  "Error",
                                                  JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            };
            SwingUtilities.invokeLater(runnable);

        }
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    /**
     * setup fact++ reasoner
     */
    protected void setupReasoner() {
        // run reasoner

        try {
            reasoner = new Reasoner(manager);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            if (reasoner != null) {
                reasoner.loadOntologies(manager.getOntologies());
                reasoner.classify();
            }
        }
        catch(final OWLReasonerException e) {
            Runnable runnable = new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(null,
                                                  "A reasoner error has ocurred.\n" +
                                                  "[" +e.getMessage() + "]",
                                                  "Reasoner Error",
                                                  JOptionPane.ERROR_MESSAGE);
                }
            };
        };
    }

    /**
     * getReasoner
     * @return  reasoner
     */
    public Reasoner getReasoner() {
        return reasoner;
    }
    
    /**
     * Gets the main extras categories.  This actually returns
     * the direct named subclasses of the Extras class.
     * @return A <code>Collection</code> of <code>OWLNamedClasses</code>
     */
    public Collection getCoffeeExtrasCategories() throws OWLReasonerException{

        OWLClass  extrasCls = this.getNamedClass(PREFERENCES.getExtrasClassName());
        return extrasCls.getSubClasses(ontology);
    }
    
    /**
     * Gets the main base categories.  This actually returns
     * the direct named subclasses of the Bases class.
     * @return A <code>Collection</code> of <code>OWLNamedClasses</code>
     */
    public Collection getCoffeeBasesCategories() throws OWLReasonerException{

        OWLClass  basesCls = this.getNamedClass(PREFERENCES.getBaseClassName());
        return basesCls.getSubClasses(ontology);
    }
     
    /**
     * Gets the main temperature categories.  This actually returns
     * the direct named subclasses of the Temperature class.
     * @return A <code>Collection</code> of <code>OWLNamedClasses</code>
     */
    public Collection getTemperatureCategories() throws OWLReasonerException{

        OWLClass  temperatureCls = this.getNamedClass(PREFERENCES.getTemperatureClassName());
        return temperatureCls.getSubClasses(ontology);
    }
    
    /**
     *  Gets OWL class by given name
     * @param className
     * @return
     * @throws OWLReasonerException
     */
    public OWLClass getNamedClass(String className)  {

        if (classnameCache == null){
            classnameCache = new HashMap<String, URI>();
            for (OWLOntology ont : reasoner.getLoadedOntologies()){
                for (OWLClass cls : ont.getReferencedClasses()){
                    classnameCache.put(cls.toString(), cls.getURI());
                }
            }
        }

        OWLClass namedCls = null;
        URI uri = classnameCache.get(className);
        if (uri != null) {
            namedCls =  manager.getOWLDataFactory().getOWLClass(uri);
        }
        else{
            System.err.println("Cannot find class: " + className + " in loaded ontologies");
        }

        return namedCls;
    }

    /**
     *
     * @param entity
     * @return
     */
    public String render(OWLEntity entity) {
        for (OWLOntology ont : manager.getOntologies()){
            for (OWLAnnotation annot : entity.getAnnotations(ont)){
                if (annot.getAnnotationURI().equals(OWLRDFVocabulary.RDFS_LABEL.getURI())){
                    if (annot instanceof OWLConstantAnnotation){
                        return ((OWLConstantAnnotation)annot).getAnnotationValue().getLiteral();
                    }
                }
            }
        }
        return entity.getURI().getFragment();
    }

    /**
     * Tests to see if the specified class is a AlcoholicCoffee - i.e.
     * a subclass of the AlcoholCoffee class.
     * @param coffeeClass The class to be tested.
     * @return <code>true</code> if the specified class is a alcoholic coffee
     * (subclass of the alcohol coffee class), or <code>false</code> if the
     * specified class is not a alcoholic coffee (not a subclass of the alcohol
     * coffee class).
     */
    public boolean isHotCoffee(OWLClass coffeeClass) throws OWLReasonerException {
        return  this.filterClasses(reasoner.getSuperClasses(coffeeClass)).contains(getHotCoffeeClass());
    }

    /**
     * check to see if specified class is Named coffee
     * @param coffeeClass
     * @return
     * @throws OWLReasonerException
     */
    public boolean isNamedCoffee(OWLClass coffeeClass) throws OWLReasonerException {
        return  this.filterClasses(reasoner.getSuperClasses(coffeeClass)).contains(getCoffeeClass());
    }
    
    /**
     * Gets the Coffee class.
     * @return The named class that represents things that are coffees.
     */
    public OWLClass getCoffeeClass() {
        return this.getNamedClass(PREFERENCES.getCoffeeClassName());
    }

    /**
     * Gets the coffees that match the requirement for included and excluded
     * extras.
     * @param includeExtras The extras that the coffee should have.
     * @param excludeExtras The extras that the coffee should NOT have.
     * @return A <code>Collection</code> of classes that represent the coffee
     * classes that satisfy the description of the required extras.
     */
    public Collection getCoffees(Set<OWLClass>  includeExtras, Set<OWLClass> excludeExtras,String base, String temperature) {

        @SuppressWarnings("rawtypes")
		Collection c;
        // Temporarily create a description (class) that will have the required
        // coffees (the coffees with the included extras but not the excluded extras).
        // OWLDescription extrasDesc = createCoffeeDescription(includeExtras, excludeExtras,"Espresso", "Hot");
        //OWLDescription extrasDesc = createCoffeeDescription(includeExtras, excludeExtras);

        OWLDescription includedExtrasDesc = createIncludeCoffeeDescription(includeExtras);
        OWLDescription excludedExtrasDesc = createExcludeCoffeeDescription(excludeExtras);

        Set<Set<OWLClass>> cTotal;
        Set<Set<OWLClass>> cExcluded;
        
        try {
			cTotal = reasoner.getSubClasses(includedExtrasDesc);
			cExcluded = reasoner.getSubClasses(excludedExtrasDesc);
	        
			cTotal.removeAll(cExcluded);
        
			// Ask the reasoner for the subclasses of the temp description
    		if(temperature.equals("Topla") || temperature.equals("Hladna"))
			{
    			if (base.equals("Espresso") ||
    				base.equals("Filter") ||
    				base.equals("KolumbijaFilter") ||
    				base.equals("HondurasFilter") ||
    				base.equals("GvatemalaFilter") ||
    				base.equals("Moka")
    				)
    			{
    				c = this.filterClassesTemperature(
    						this.filterClassesBase(
    							this.filterClasses(cTotal),
    							base),
    						temperature);
    			}
    			else
    			{
    				c = this.filterClassesTemperature(
    						this.filterClasses(cTotal),
    						temperature);
    			}
			}
			else
			{
    			if (base.equals("Espresso") ||
    				base.equals("Filter") ||
    				base.equals("KolumbijaFilter") ||
    				base.equals("HondurasFilter") ||
    				base.equals("GvatemalaFilter") ||
    				base.equals("Moka")
    				)
    			{
    				c = this.filterClassesBase(
    						this.filterClasses(cTotal),
    						base);
    			}
    			else
    			{
    				c = this.filterClasses(cTotal);
    			}
			}
		}
        catch(OWLReasonerException e) {
            c = Collections.EMPTY_LIST;
        }

        return c;
    }

    public OWLClass getHotCoffeeClass() {
        return this.getNamedClass(PREFERENCES.getHotCoffeeClassName());
    }
    public OWLClass getColdCoffeeClass() {
        return this.getNamedClass(PREFERENCES.getColdCoffeeClassName());
    }
    public OWLClass getEspressoCoffeeClass() {
        return this.getNamedClass(PREFERENCES.getEspressoCoffeeClassName());
    }
    public OWLClass getFilterCoffeeClass() {
        return this.getNamedClass(PREFERENCES.getFilterCoffeeClassName());
    }
    public OWLClass getColFilterCoffeeClass() {
        return this.getNamedClass(PREFERENCES.getColFilterCoffeeClassName());
    }
    public OWLClass getHonFilterCoffeeClass() {
        return this.getNamedClass(PREFERENCES.getHonFilterCoffeeClassName());
    }
    public OWLClass getGuaFilterCoffeeClass() {
        return this.getNamedClass(PREFERENCES.getGuaFilterCoffeeClassName());
    }
    public OWLClass getMochaCoffeeClass() {
        return this.getNamedClass(PREFERENCES.getMochaCoffeeClassName());
    }
    
    protected OWLClass getTemperatureCoffeeClass(String temperature) throws OWLReasonerException
    {
    	if(temperature.equals("Topla")) return getHotCoffeeClass();
    	if(temperature.equals("Hladna")) return getColdCoffeeClass();
    	return null;
    }
    protected OWLClass getBaseCoffeeClass(String base) throws OWLReasonerException
    {
    	if(base.equals("Espresso")) return getEspressoCoffeeClass();
    	if(base.equals("Filter")) return getFilterCoffeeClass();
    	if (base.equals("KolumbijaFilter")) return getColFilterCoffeeClass();
    	if (base.equals("HondurasFilter")) return getHonFilterCoffeeClass();
    	if (base.equals("GvatemalaFilter")) return getGuaFilterCoffeeClass();
    	if(base.equals("Moka")) return getMochaCoffeeClass();
    	return null;
    }
    
    protected Set<OWLClass> filterClassesBase(Set<OWLClass> original, String base) throws OWLReasonerException {
        Set<OWLClass> result = new HashSet<OWLClass>();
       
            for (Iterator<OWLClass> it = original.iterator(); it.hasNext();) {
                OWLClass cls = it.next();
                if(this.filterClasses(reasoner.getSuperClasses(cls)).contains(getBaseCoffeeClass(base)))
                	result.add(cls);
            }
        
        return result;
    }
    protected Set<OWLClass> filterClassesTemperature(Set<OWLClass> original, String temperature) throws OWLReasonerException {
        Set<OWLClass> result = new HashSet<OWLClass>();
       
            for (Iterator<OWLClass> it = original.iterator(); it.hasNext();) {
                OWLClass cls = it.next();
                if(this.filterClasses(reasoner.getSuperClasses(cls)).contains(getTemperatureCoffeeClass(temperature)))
                	result.add(cls);
            }
        
        return result;
    }

    private OWLDescription  createIncludeCoffeeDescription(Set<OWLClass> includeExtras) {
        OWLObjectProperty prop = this.getNamedObjectProperty(PREFERENCES.getExtrasPropertyName());   //has_extras

        Set<OWLDescription> classes = new HashSet<OWLDescription>();
        classes.add(getCoffeeClass());        //get OWL class for Coffee
        
        for (OWLClass extra : includeExtras) {
            classes.add(manager.getOWLDataFactory().getOWLObjectSomeRestriction(prop,extra));
        }
        
        return  manager.getOWLDataFactory().getOWLObjectIntersectionOf(classes);
    }
    private OWLDescription  createExcludeCoffeeDescription(Set<OWLClass> excludeExtras) {
        OWLObjectProperty prop = this.getNamedObjectProperty(PREFERENCES.getExtrasPropertyName());   //has_extras

        Set<OWLDescription> classes = new HashSet<OWLDescription>();
        // Everything must be a coffee
        
        for (OWLClass extra : excludeExtras) {
            classes.add(manager.getOWLDataFactory().getOWLObjectSomeRestriction(prop,extra));
        }
        
        // are looking for.
        return  manager.getOWLDataFactory().getOWLObjectUnionOf(classes);
    }
 
    /**
     * Creates OWLDescription (query) by given included extras and excluded extras
     * @param includeExtras
     * @param excludeExtras
     * @return
     */
    private OWLDescription  createCoffeeDescription(Set<OWLClass> includeExtras, Set<OWLClass> excludeExtras) {

        // Include means existential restrictions
        // Exclude means negated existential restrictions
        OWLObjectProperty prop = this.getNamedObjectProperty(PREFERENCES.getExtrasPropertyName());   //has_extras

        // Create a hash set to store the components (existential restrictions)
        // of our description
        Set<OWLDescription> classes = new HashSet<OWLDescription>();
        // Everything must be a coffee
        classes.add(getCoffeeClass());        //get OWL class for Coffee
        
        // Create the existential restrictions that represent the extras
        // that we want to include.
        for (OWLClass extra : includeExtras) {
        	// e.g. hasExtras some ex_A , hasExtras some ex_B
            classes.add(manager.getOWLDataFactory().getOWLObjectSomeRestriction(prop,extra));
        }

        // Create the negated existential restrictions of the extras that we
        // want to exclude
        for (OWLClass excludeExtra : excludeExtras) {
        	// has_topping some topping_A
            OWLDescription restriction = manager.getOWLDataFactory().getOWLObjectSomeRestriction(prop, excludeExtra);
            // not (has_topping some topping_A)
            OWLObjectComplementOf neg = manager.getOWLDataFactory().getOWLObjectComplementOf(restriction);
            classes.add(neg);
        }

        // Bind the whole thing up in an intersection class
        // to create a concept description of the coffee we
        // are looking for.
        return  manager.getOWLDataFactory().getOWLObjectIntersectionOf(classes);
    }

    public OWLObjectProperty getBaseProperty(){
    	return getNamedObjectProperty(PREFERENCES.getBasePropertyName());
    }
    public OWLObjectProperty getExtrasProperty(){
    	return getNamedObjectProperty(PREFERENCES.getExtrasPropertyName());
    }
    public OWLObjectProperty getTemperatureProperty(){
    	return getNamedObjectProperty(PREFERENCES.getTemperaturePropertyName());
    }

    /**  Gets the property by given name, e.g. has_topping
     *
     * @param propName
     * @return
     */
    public OWLObjectProperty getNamedObjectProperty(String propName)  {

        if (objPropNameCache == null){
            objPropNameCache = new HashMap<String, URI>();
            for (OWLOntology ont : reasoner.getLoadedOntologies()){
                for (OWLObjectProperty prop : ont.getReferencedObjectProperties()){
                    objPropNameCache.put(prop.toString(), prop.getURI());
                }

            }
        }

        // search the HashMap
        OWLObjectProperty namedProp = null;
        URI uri = objPropNameCache.get(propName);
        if (uri != null) {
            namedProp =  manager.getOWLDataFactory().getOWLObjectProperty(uri);
        }
        else{
            System.err.println("Cannot find object property: " + propName + " in loaded ontologies");
        }
        return namedProp;
    }

    /**
     * filters the result of e.g. getSubclasses which is  Set<Set<OWLClass>>  To  Set<OWLClass>
     * @param original
     * @return
     * @throws OWLReasonerException
     */
    protected Set<OWLClass> filterClasses(Set<Set<OWLClass>> original) throws OWLReasonerException {
        Set<OWLClass> result = new HashSet<OWLClass>();
        for (Set<OWLClass> set : original) {
            for (Iterator<OWLClass> it = set.iterator(); it.hasNext();) {
                OWLClass cls = it.next();
                if (cls.getURI().equals(OWLRDFVocabulary.OWL_NOTHING) ) {
                    it.remove();
                }
                else {
                    result.add(cls);
                }
            }
        }
        return result;
    }

}

