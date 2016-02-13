package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import org.semanticweb.owl.model.OWLClass;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModel;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModelChangedEvent;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModelListener;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeOntology;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.Selectable;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.SelectionEvent;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.SelectionListener;
import uk.ac.man.cs.mig.coode.pizzafinder.ui.ExtrasListPanel.OWLClassListCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

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
public class ResultsListPanel extends JPanel implements Selectable {

	private JList list;
	private String title;
	private CoffeeChoiceModel choiceModel;

	private CoffeeOntology ontology;
	private BaseListPanel baseListPanel;
	private TemperatureListPanel tempListPanel;
	
	private Selectable selectable;

	private Action getAction = new AbstractAction("Get") {
        public void actionPerformed(ActionEvent e) {
        	Collection c = ontology.getCoffees(choiceModel.getIncluded(),
        										choiceModel.getExcluded(),
        										baseListPanel.getBase(),
        										tempListPanel.getTemperature()
        										);
            
        	//go through the collection and add results to the list
        	updateInterface(c);
        }
	};
	
	public ResultsListPanel(final CoffeeOntology ontology,
								final BaseListPanel  baseListPanel,
								final TemperatureListPanel  tempListPanel,
								final Selectable selectable,
	                            final CoffeeChoiceModel choiceModel) {
		this.title = "Results:";
		
		this.ontology = ontology;
		this.baseListPanel = baseListPanel;
		this.tempListPanel = tempListPanel;
		
		this.selectable = selectable;
		this.choiceModel = choiceModel;
		createUI();
	}
	
	protected void createUI() {
		getAction = getGetAction();
		JPanel panel = new JPanel(new BorderLayout(7, 7));
		{
			panel.add(new JButton(getAction), BorderLayout.WEST);
			
			list = new JList(new DefaultListModel());
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			list.setCellRenderer(new OWLClassListCellRenderer());
			panel.add(new JScrollPane(list));
	
			setLayout(new BorderLayout(7, 7));
		}
		add(panel);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
		                                             BorderFactory.createEmptyBorder(7, 7, 7, 7)));
		getAction.setEnabled(true);

		//change this
		selectable.addSelectionListener(new SelectionListener() {
			public void selectionChanged(SelectionEvent e) {
				if(selectable.getSelection() != null) {
					getAction.setEnabled(true);
				}
				else {
					getAction.setEnabled(false);
				}
			}
		});
	}

	protected void updateInterface(Collection c) {
		DefaultListModel model = (DefaultListModel)list.getModel();
		model.removeAllElements();
		Iterator it = c.iterator();
		while(it.hasNext()) {
			model.addElement(it.next());
		}
	}

	/////////////////////////////////////////////////////////////////////////////
	//
	// Renderer for OWLClass items
	//
	/////////////////////////////////////////////////////////////////////////////

	public class OWLClassListCellRenderer extends DefaultListCellRenderer {

		private Icon icon = Icons.getPizzaSliceIcon();

		public Component getListCellRendererComponent(JList list,
		                                              Object value,
		                                              int index,
		                                              boolean isSelected,
		                                              boolean cellHasFocus) {

			JLabel label = (JLabel)  super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setText(((OWLClass)value).toString());
			label.setIcon(icon);
			return label;
		}
	}

	/////////////////////////////////////////////////////////////////////////////
	//
	// Implementation of Selectable
	//
	/////////////////////////////////////////////////////////////////////////////


	public Object getSelection() {
		return list.getSelectedValue();
	}

	public void setSelection(Object obj) {

	}

	private ArrayList selectionListeners = new ArrayList();

	public void addSelectionListener(SelectionListener lsnr) {
		selectionListeners.add(lsnr);
	}

	public void removeSelectionListener(SelectionListener lsnr) {
		selectionListeners.remove(lsnr);
	}

	protected void fireSelectionChangedEvent() {
		Iterator it = selectionListeners.iterator();
		SelectionEvent e = new SelectionEvent(this);
		while(it.hasNext()) {
			((SelectionListener)it.next()).selectionChanged(e);
		}
	}
	
	protected Collection getListItems() {
		return choiceModel.getIncluded();
	}

	protected Action getGetAction() {
		return getAction;
	}

}

