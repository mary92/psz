package uk.ac.man.cs.mig.coode.pizzafinder.ui;



import org.semanticweb.owl.model.OWLClass;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModel;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModelChangedEvent;
import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeChoiceModelListener;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.Selectable;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.SelectionEvent;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.SelectionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
public abstract class ExtrasListPanel extends JPanel implements Selectable {

	private JList list;
	private Action addAction;
	private Action removeAction;
	private String title;
	private Selectable selectable;

	public ExtrasListPanel(String title,
	                        Selectable selectable,
	                         CoffeeChoiceModel choiceModel) {
		this.title = title;
		this.selectable = selectable;
		choiceModel.addChoiceModelListener(new CoffeeChoiceModelListener() {
			public void modelChanged(CoffeeChoiceModelChangedEvent e) {
				updateInterface();
			}
		});


	}

	protected void createUI() {
		addAction = getAddAction();
		removeAction = getRemoveAction();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		{
			list = new JList(new DefaultListModel());
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			list.setCellRenderer(new OWLClassListCellRenderer());
			panel.add(new JScrollPane(list));
			
			panel.add(Box.createHorizontalStrut(7));
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
			
			buttonPanel.add(new JButton(addAction));
			buttonPanel.add(Box.createVerticalStrut(7));
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
				ExtrasListPanel.this.removeAction.setEnabled(true);
			}
			else {
				ExtrasListPanel.this.removeAction.setEnabled(false);
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

	protected abstract Collection getListItems();

	protected abstract Action getAddAction();

	protected abstract Action getRemoveAction();

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
}

