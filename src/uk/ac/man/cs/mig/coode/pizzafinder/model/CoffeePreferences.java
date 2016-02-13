package uk.ac.man.cs.mig.coode.pizzafinder.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 8, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
@SuppressWarnings("all")
public class CoffeePreferences {

    private static CoffeePreferences instance;

    // default values (will not be used unless the config.xml file cannot be read)
    private String ontologyURL = "file:/D:/nf/master/psz/PizzaFinder/coffee.owl";
    
	private String coffeeClassName = "NamedCoffee";
	
	private String hotCoffeeClassName = "HotCoffee";
    private String coldCoffeeClassName = "ColdCoffee";
    private String espressoCoffeeClassName = "EspressoCoffee";
    private String filterCoffeeClassName = "FilterCoffee";
    private String colFilterCoffeeClassName = "ColombiaCoffee";
    private String honFilterCoffeeClassName = "HondurasCoffee";
    private String guaFilterCoffeeClassName = "GuatemalaCoffee";
    private String mochaCoffeeClassName = "MochaCoffee";
    
    private String extrasClassName = "Extras";
    
    private String baseClassName = "Base";
    
    private String temperatureClassName = "Temperature";
    
    private String basePropertyName = "hasBase";
    private String extrasPropertyName = "hasExtras";
    private String temperaturePropertyName = "hasTemperature";
    
    public static final String ONTOLOGY_LOCATION_ELEMENT_NAME = "OntologyLocation";
    
    public static final String COFFEE_CLASS_ELEMENT_NAME = "CoffeeClass";

    public static final String HOTCOFFEE_CLASS_ELEMENT_NAME = "HotCoffeeClass";
	public static final String COLDCOFFEE_CLASS_ELEMENT_NAME = "ColdCoffeeClass";
	
	public static final String ESPRESSOCOFFEE_CLASS_ELEMENT_NAME = "EspressoCoffeeClass";
	public static final String FILTERCOFFEE_CLASS_ELEMENT_NAME = "FilterCoffeeClass";
	public static final String COLOMBIACOFFEE_CLASS_ELEMENT_NAME = "ColombiaCoffeeClass";
	public static final String HONDURASCOFFEE_CLASS_ELEMENT_NAME = "HondurasCoffeeClass";
	public static final String GUATEMALACOFFEE_CLASS_ELEMENT_NAME = "GuatemalaCoffeeClass";
	public static final String MOCHACOFFEE_CLASS_ELEMENT_NAME = "MochaCoffeeClass";
	
    public static final String EXTRAS_CLASS_ELEMENT_NAME = "ExtrasClass";
    
    public static final String BASE_CLASS_ELEMENT_NAME = "BaseClass";
    
    public static final String TEMPERATURE_CLASS_ELEMENT_NAME = "TemperatureClass";
    
	public static final String BASE_PROPERTY_ELEMENT_NAME = "BaseProperty";
	public static final String EXTRAS_PROPERTY_ELEMENT_NAME = "ExtrasProperty";
	public static final String TEMPERATURE_PROPERTY_ELEMENT_NAME = "TemperatureProperty";
    
    public static final String FILE_NAME = "coffee_config.xml";

    private CoffeePreferences(String fileName) {
        loadPreferences(fileName);
    }

    public static synchronized CoffeePreferences getInstance() {
        if(instance == null) {
            instance = new CoffeePreferences(FILE_NAME);
        }
        return instance;
    }

    private void loadPreferences(String fileName) {
        Document doc = getDocument(fileName);
        
        loadOntologyLocation(doc);

        loadCoffeeClassName(doc);
        
        loadHotCoffeeClassName(doc);
        loadColdCoffeeClassName(doc);
        
        loadEspressoCoffeeClassName(doc);
        loadFilterCoffeeClassName(doc);
        loadColFilterCoffeeClassName(doc);
        loadHonFilterCoffeeClassName(doc);
        loadGuaFilterCoffeeClassName(doc);
        loadMochaClassName(doc);
        
        loadExtrasClassName(doc);
        
        loadBaseClassName(doc);
        
        loadTemperatureClassName(doc);
        
        loadBasePropertyName(doc);
        loadExtrasPropertyName(doc);
        loadTemperaturePropertyName(doc);
    }

    private void loadOntologyLocation(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(ONTOLOGY_LOCATION_ELEMENT_NAME).item(0);
        
			ontologyURL = element.getAttribute("url");
    }

    private void loadCoffeeClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(COFFEE_CLASS_ELEMENT_NAME).item(0);
        coffeeClassName = element.getAttribute("name");
    }

    private void loadHotCoffeeClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(HOTCOFFEE_CLASS_ELEMENT_NAME).item(0);
        hotCoffeeClassName = element.getAttribute("name");
    }
    private void loadColdCoffeeClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(COLDCOFFEE_CLASS_ELEMENT_NAME).item(0);
        coldCoffeeClassName = element.getAttribute("name");
    }

    private void loadEspressoCoffeeClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(ESPRESSOCOFFEE_CLASS_ELEMENT_NAME).item(0);
        espressoCoffeeClassName = element.getAttribute("name");
    }
    private void loadFilterCoffeeClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(FILTERCOFFEE_CLASS_ELEMENT_NAME).item(0);
        filterCoffeeClassName = element.getAttribute("name");
    }
    private void loadColFilterCoffeeClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(COLOMBIACOFFEE_CLASS_ELEMENT_NAME).item(0);
        colFilterCoffeeClassName = element.getAttribute("name");
    }
    private void loadHonFilterCoffeeClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(HONDURASCOFFEE_CLASS_ELEMENT_NAME).item(0);
        honFilterCoffeeClassName = element.getAttribute("name");
    }
    private void loadGuaFilterCoffeeClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(GUATEMALACOFFEE_CLASS_ELEMENT_NAME).item(0);
        guaFilterCoffeeClassName = element.getAttribute("name");
    }
    private void loadMochaClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(MOCHACOFFEE_CLASS_ELEMENT_NAME).item(0);
        mochaCoffeeClassName = element.getAttribute("name");
    }
    
    private void loadExtrasClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(EXTRAS_CLASS_ELEMENT_NAME).item(0);
        extrasClassName = element.getAttribute("name");
    }

    private void loadBaseClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(BASE_CLASS_ELEMENT_NAME).item(0);
        baseClassName = element.getAttribute("name");
    }
    
    private void loadTemperatureClassName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(TEMPERATURE_CLASS_ELEMENT_NAME).item(0);
        temperatureClassName = element.getAttribute("name");
    }
    
    private void loadBasePropertyName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(BASE_PROPERTY_ELEMENT_NAME).item(0);
        basePropertyName = element.getAttribute("name");
    }
    private void loadExtrasPropertyName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(EXTRAS_PROPERTY_ELEMENT_NAME).item(0);
        extrasPropertyName = element.getAttribute("name");
    }
    private void loadTemperaturePropertyName(Document doc) {
        Element element = (Element)doc.getDocumentElement().getElementsByTagName(TEMPERATURE_PROPERTY_ELEMENT_NAME).item(0);
        temperaturePropertyName = element.getAttribute("name");
    }
    
    private Document getDocument(String fileName) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // Attempt to load from file - if this doesn't
            // work, load the embedded file.
            File file = new File(fileName);
            if(file.exists()) {
                return documentBuilder.parse(file);
            }
            else {
                InputStream is = getClass().getResourceAsStream(fileName);
                return documentBuilder.parse(is);
            }
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
        catch(SAXException saxEx) {
            saxEx.printStackTrace();
        }
        catch(ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOntologyURL() {
        return ontologyURL;
    }

    public String getCoffeeClassName() {
		return coffeeClassName;
	}

    public String getHotCoffeeClassName() {
		return hotCoffeeClassName;
	}
	public String getColdCoffeeClassName() {
		return coldCoffeeClassName;
	}

	public String getEspressoCoffeeClassName() {
		return espressoCoffeeClassName;
	}
	public String getFilterCoffeeClassName() {
		return filterCoffeeClassName;
	}
	public String getColFilterCoffeeClassName() {
		return colFilterCoffeeClassName;
	}
	public String getHonFilterCoffeeClassName() {
		return honFilterCoffeeClassName;
	}
	public String getGuaFilterCoffeeClassName() {
		return guaFilterCoffeeClassName;
	}
	public String getMochaCoffeeClassName() {
		return mochaCoffeeClassName;
	}
	
	public String getExtrasClassName() {
		return extrasClassName;
	}

	public String getBaseClassName() {
		return baseClassName;
	}
	
	public String getTemperatureClassName() {
		return temperatureClassName;
	}
	
	public String getBasePropertyName() {
		return basePropertyName;
	}
	public String getExtrasPropertyName() {
		return extrasPropertyName;
	}
	public String getTemperaturePropertyName() {
		return temperaturePropertyName;
	}

}

