package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import org.semanticweb.owl.model.OWLClass;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModel;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModelChangedEvent;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModelListener;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.Selectable;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.SelectionEvent;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.SelectionListener;
import uk.ac.man.cs.mig.coode.pizzafinder.ui.ExtrasListPanel.OWLClassListCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
public class TemperatureListPanel extends JPanel implements Selectable {

	private JList list;
	private String title;
	private CoffeeChoiceModel choiceModel;

	private String temperature = "";
	
	private Selectable selectable;

	private Action addAction = new AbstractAction("Add") {
			public void actionPerformed(ActionEvent e) {
				Object selObj = selectable.getSelection();
				if(selObj != null && selObj instanceof OWLClass) {
					choiceModel.setIncluded((OWLClass) selObj);
					temperature = ((OWLClass)selObj).toString();
				}
			}
		};

	private Action removeAction = new AbstractAction("Rem") {
			public void actionPerformed(ActionEvent e) {
				Object selObj = getSelection();
				if(selObj != null && selObj instanceof OWLClass) {
					choiceModel.removeIncluded((OWLClass) selObj);
					temperature = "";
				}
			}
		};

	public String getTemperature() {
		return temperature;
	}
		
	public TemperatureListPanel(final Selectable selectable,
	                               final CoffeeChoiceModel choiceModel2) {
		this.title = "Chosen temperature:";
		this.selectable = selectable;
		choiceModel2.addChoiceModelListener(new CoffeeChoiceModelListener() {
			public void modelChanged(CoffeeChoiceModelChangedEvent e) {
				updateInterface();
			}
		});
		this.choiceModel = choiceModel2;
		this.selectable = selectable;
		createUI();
	}
	
	protected void createUI() {
		addAction = getAddAction();
		removeAction = getRemoveAction();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		{
			list = new JList(new DefaultListModel());
			list.setCellRenderer(new OWLClassListCellRenderer());
			panel.add(new JScrollPane(list));
			
			panel.add(Box.createVerticalStrut(7));
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			
			buttonPanel.add(new JButton(addAction));
			buttonPanel.add(Box.createHorizontalStrut(7));
			buttonPanel.add(new JButton(removeAction));
			
			panel.add(buttonPanel);
			setLayout(new BorderLayout(7, 7));
		}
		add(panel);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
		                                             BorderFactory.createEmptyBorder(7, 7, 7, 7)));
		removeAction.setEnabled(false);
		addAction.setEnabled(false);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			 if(list.getSelectedValue() != null) {
				 TemperatureListPanel.this.removeAction.setEnabled(true);
			}
			else {
				TemperatureListPanel.this.removeAction.setEnabled(false);
			}
			}
		});
		selectable.addSelectionListener(new SelectionListener() {
			public void selectionChanged(SelectionEvent e) {
				if(selectable.getSelection() != null) {
					addAction.setEnabled(true);
				}
				else {
					addAction.setEnabled(false);
				}
			}
		});
	}

	protected void updateInterface() {
		DefaultListModel model = (DefaultListModel)list.getModel();
		model.removeAllElements();
		Iterator it = getListItems().iterator();
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
	//end from ExtrasListPanel
	
	



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

