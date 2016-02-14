package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import uk.ac.man.cs.mig.coode.pizzafinder.model.CoffeeOntology;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Collection;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 7, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
@SuppressWarnings("all")
public class CoffeeApplication extends JFrame {

	private CoffeeOntology ontology;
	
	private CoffeeChooserPanel coffeeChooserPanel;

	private JPanel cardPanel;

	private JPanel mainPanel;

    private Action aboutAction = new AbstractAction("About...") {
		public void actionPerformed(ActionEvent e) {
			AboutDialog dlg = new AboutDialog(CoffeeApplication.this);
			dlg.show();
		}
	};

	public CoffeeApplication() {
		setupMenuBar();
		setupFrame();
		setupMainPanel();
		final LoaderPanel loaderPanel = new LoaderPanel();
		loaderPanel.startLoadingAnimation(mainPanel, BorderLayout.CENTER);
		Runnable r = new Runnable() {
			public void run() {
				// Create and load the Coffee Ontology
				ontology = new CoffeeOntology();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						setupCardPanel();
						loaderPanel.stopLoadingAnimation();
					}
				});
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	protected void setupMainPanel() {
		mainPanel = new JPanel(new BorderLayout(7, 7));
        getContentPane().add(mainPanel);
        
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
	}

	protected void setupCardPanel() {
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());

        cardPanel.add(coffeeChooserPanel = new CoffeeChooserPanel(ontology, this), "CoffeeChooserPanel");
        
        mainPanel.add(cardPanel);
	}

	protected void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu("Help");
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem(aboutAction);
		menu.add(menuItem);
	}

	protected void setupFrame() {
		setSize(800, 600);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
		getContentPane().setLayout(new BorderLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		URL url = getClass().getResource("image/coffee_logo.png");
		ImageIcon icon = new ImageIcon(url);
		setIconImage(icon.getImage());
		setTitle("Coffee");
	}

	public void showCoffeesPanel() {
		((CardLayout)cardPanel.getLayout()).first(cardPanel);
	}
	
	public static void main(String [] args) {
		System.out.println("Starting app...");
		CoffeeApplication panel = new CoffeeApplication();
		System.out.println("...created coffee app.");
		panel.show();

	}
}

