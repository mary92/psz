package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLClass;

import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeOntology;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.Selectable;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.SelectionEvent;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.SelectionListener;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
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
public class TemperaturePanel extends JPanel implements Selectable {

	private JTree tree;

	private CoffeeOntology ontology;

	private HashMap clsNodeMap;

	public TemperaturePanel(CoffeeOntology ontology) {
		this.ontology = ontology;
		clsNodeMap = new HashMap();
		createUI();

	}

	protected void createUI() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        try{
        tree = buildTree();
        }catch(OWLReasonerException e){
            e.printStackTrace();
        }


        tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				fireSelectionChangedEvent();
			}
		});
		add(new JScrollPane(tree));
	}

	protected JTree buildTree() throws OWLReasonerException {
		MutableTreeNode rootNode = new DefaultMutableTreeNode("Temperatura");
		Iterator toppingCatIt = ontology.getTemperatureCategories().iterator();
		while(toppingCatIt.hasNext()) {
			OWLClass cls = (OWLClass)toppingCatIt.next();
			addClsToTree(cls, rootNode);
		}

		JTree t = new JTree(rootNode);
		t.setShowsRootHandles(true);
		t.setCellRenderer(new OWLClassTreeCellRenderer());
		return t;
	}

	protected void addClsToTree(OWLClass cls, MutableTreeNode treeNode) {
		// add class and its subclasses
		MutableTreeNode childNode = new DefaultMutableTreeNode(cls);
		clsNodeMap.put(cls, childNode);
		treeNode.insert(childNode, 0);
        Iterator it = cls.getSubClasses(ontology.getOntology()).iterator();
       // Iterator it = cls.getInferredSubclasses().iterator();
		while(it.hasNext()) {
			OWLClass curCls = (OWLClass)it.next();
			addClsToTree(curCls, childNode);
		}
	}


	public static void main(String [] args) {
		TemperaturePanel panel = new TemperaturePanel(new CoffeeOntology());
		JFrame frm = new JFrame();
		frm.setSize(300, 400);
		frm.getContentPane().setLayout(new BorderLayout());
		frm.getContentPane().add(panel);
		frm.getContentPane().add(new JLabel("Temperatura!"), BorderLayout.NORTH);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.show();
	}

	/////////////////////////////////////////////////////////////////////////////
	//
	// An inner class that renders tree nodes.  If the user object
	// is an OWLClass the name fragment of the class is renderered,
	// otherwise, the toString method of the user object is called.
	//
	/////////////////////////////////////////////////////////////////////////////

	public class OWLClassTreeCellRenderer extends DefaultTreeCellRenderer {

		private Icon icon;

		public OWLClassTreeCellRenderer() {
			icon = Icons.getPizzaSliceIcon();
		}

		public Component getTreeCellRendererComponent(JTree tree,
		                                              Object value,
		                                              boolean sel,
		                                              boolean expanded,
		                                              boolean leaf,
		                                              int row,
		                                              boolean hasFocus) {
			JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			Object obj = ((DefaultMutableTreeNode)value).getUserObject();
			if(obj instanceof OWLClass) {
				label.setText(((OWLClass)obj).toString());
			}
			else {
				label.setText(value.toString());
			}
			return label;
		}


		public Icon getOpenIcon() {
			return icon;
		}


		public Icon getClosedIcon() {
			return icon;
		}


		public Icon getLeafIcon() {
			return icon;
		}


	}



	/////////////////////////////////////////////////////////////////////////////
	//
	// Implementation of Selectable
	//
	/////////////////////////////////////////////////////////////////////////////


	public Object getSelection() {
		TreePath treePath = tree.getSelectionPath();
		if(treePath != null) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			return treeNode.getUserObject();
		}
		return null;
	}


	public void setSelection(Object obj) {
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)clsNodeMap.get(obj);
		if(treeNode != null) {
			tree.setSelectionPath(new TreePath(treeNode.getPath()));
		}
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

