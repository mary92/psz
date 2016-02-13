package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModel;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeOntology;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;


/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
@SuppressWarnings("all")
public class CoffeeChooserPanel extends JPanel {

	private CoffeeOntology ontology;

	private CoffeeChoiceModel temperatureChoiceModel;
	private CoffeeChoiceModel baseChoiceModel;
	private CoffeeChoiceModel choiceModel;
	
	private TemperaturePanel tempPanel;
	private BasePanel basePanel;
	private ExtrasPanel extrasPanel;
	
	private TemperatureListPanel tempListPanel;
	private BaseListPanel baseListPanel;
	private ExtrasListPanel includeListPanel;
	private ExtrasListPanel excludeListPanel;
	private ResultsListPanel resultsListPanel;

	private CoffeeApplication application;

    public CoffeeChooserPanel(CoffeeOntology ontology, CoffeeApplication coffeeApplication) {
		this.ontology = ontology;
		this.application = coffeeApplication;
		temperatureChoiceModel = new CoffeeChoiceModel(ontology);
		baseChoiceModel = new CoffeeChoiceModel(ontology);
		choiceModel = new CoffeeChoiceModel(ontology);
		createUI();
	}

	protected void createUI() {
		setLayout(new BorderLayout(7, 7));

        JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(250);

		Box boxLeft = new Box(BoxLayout.Y_AXIS);
		{
			JSplitPane hSplitLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			hSplitLeft.setDividerLocation(200);
			
			Box boxTopLeft = new Box(BoxLayout.Y_AXIS);
			{
				tempPanel = new TemperaturePanel(ontology);
				boxTopLeft.add(tempPanel);
				basePanel = new BasePanel(ontology);
				boxTopLeft.add(basePanel);
			}
			hSplitLeft.setTopComponent(boxTopLeft);
			
			extrasPanel = new ExtrasPanel(ontology);
			hSplitLeft.setBottomComponent(extrasPanel);
			
			boxLeft.add(hSplitLeft);
		}
		splitPane.setLeftComponent(boxLeft);
		
		Box boxRight = new Box(BoxLayout.Y_AXIS);
		{
			
			JSplitPane hSplitRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			
			Box boxRightUpper = new Box(BoxLayout.X_AXIS);
			{
				tempListPanel = new TemperatureListPanel(tempPanel, temperatureChoiceModel);
				boxRightUpper.add(tempListPanel);

				baseListPanel = new BaseListPanel(basePanel, baseChoiceModel);
				boxRightUpper.add(baseListPanel);
			}
			hSplitRight.setTopComponent(boxRightUpper);
			
			//boxRight.add(boxRightUpper);
		
			Box boxRightLower = new Box(BoxLayout.Y_AXIS);
			{
				JSplitPane hSplitRight2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				
				Box boxRightCenter = new Box(BoxLayout.Y_AXIS);
				{
					includeListPanel = new IncludedExtrasListPanel(extrasPanel, choiceModel);
					boxRightCenter.add(includeListPanel);
					
					excludeListPanel = new ExcludedExtrasListPanel(extrasPanel, choiceModel);
					boxRightCenter.add(excludeListPanel);
				}
				hSplitRight2.setTopComponent(boxRightCenter);
				
				//this constructor needs to be changed to accept a collection of sorts only
				resultsListPanel = new ResultsListPanel(ontology,
														baseListPanel,
														tempListPanel,
														extrasPanel,
														choiceModel);
				hSplitRight2.setBottomComponent(resultsListPanel);
				
				boxRightLower.add(hSplitRight2);
			}
			hSplitRight.setBottomComponent(boxRightLower);
			
			boxRight.add(hSplitRight);
			
			boxRight.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		}
		
		splitPane.setRightComponent(boxRight);
		
		add(splitPane);
		
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

}

