import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Sat Feb 06 23:25:32 CET 2016
 */



/**
 * @author Marija Civovic
 */
public class CoffeeMain extends JFrame {
	public CoffeeMain() {
		initComponents();
	}
//./coffee.owl
	//  <OntologyLocation url="file:/D:/workspace%20mars/CoffeeFinder/coffee_new.owl"></OntologyLocation>
	public static void main(String [] args){
		new CoffeeMain().setVisible(true);;
	}
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Marija Civovic
		LeftPanel = new JPanel();
		VrstaScrollPane = new JScrollPane();
		VrstaList = new JList();
		BazsScrollPane = new JScrollPane();
		BazaList = new JList();
		DodaciScrollPane = new JScrollPane();
		DodaciList = new JList();
		RightPanel = new JPanel();
		TopRightPanel = new JPanel();
		OdabranaVrstaPanel = new JPanel();
		OdabranaVrstaLabel = new JLabel();
		OdabranaVrstaTextField = new JTextField();
		OdabranaVrstaButtonsPanell = new JPanel();
		OdabranaVrstaButtonAdd = new JButton();
		OdabranaVrstaButtonRem = new JButton();
		OdabranaBazaPanel = new JPanel();
		OdabranaBazaLabel = new JLabel();
		OdabranaBazaTextField = new JTextField();
		OdabranaBazaButtonsPanell = new JPanel();
		OdabraanaBazaButtonAdd = new JButton();
		OdabraanaBazaButtonRem = new JButton();
		CenterRightPanel = new JPanel();
		UkljuceniPanel = new JPanel();
		UkljuceniTextPanel = new JPanel();
		UkljuceniTextArea = new JTextArea();
		UkljuceniButtonsPanel = new JPanel();
		UkljuceniAddButton = new JButton();
		UkljuceniRemButton = new JButton();
		UkljuceniLabel = new JLabel();
		IskljuceniPanel = new JPanel();
		IskljuceniLabel = new JLabel();
		IskljuceniTextPanel = new JPanel();
		IskljuceniTextArea = new JTextArea();
		IskljuceniButtonsPanel = new JPanel();
		IskljuceniAddButton = new JButton();
		IskljuceniRemButton = new JButton();
		BottomRightPanel = new JPanel();
		GetButton = new JButton();
		GetTextArea = new JTextArea();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== LeftPanel ========
		{

			// JFormDesigner evaluation mark
			LeftPanel.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), LeftPanel.getBorder())); LeftPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			LeftPanel.setLayout(new GridLayout(3, 1));

			//======== VrstaScrollPane ========
			{
				VrstaScrollPane.setViewportView(VrstaList);
			}
			LeftPanel.add(VrstaScrollPane);

			//======== BazsScrollPane ========
			{
				BazsScrollPane.setViewportView(BazaList);
			}
			LeftPanel.add(BazsScrollPane);

			//======== DodaciScrollPane ========
			{
				DodaciScrollPane.setViewportView(DodaciList);
			}
			LeftPanel.add(DodaciScrollPane);
		}
		contentPane.add(LeftPanel, BorderLayout.LINE_START);

		//======== RightPanel ========
		{
			RightPanel.setLayout(new GridLayout(3, 1));

			//======== TopRightPanel ========
			{
				TopRightPanel.setLayout(new GridLayout(1, 2));

				//======== OdabranaVrstaPanel ========
				{
					OdabranaVrstaPanel.setLayout(new GridLayout(3, 1));

					//---- OdabranaVrstaLabel ----
					OdabranaVrstaLabel.setText("Odabrana vrsta");
					OdabranaVrstaPanel.add(OdabranaVrstaLabel);
					OdabranaVrstaPanel.add(OdabranaVrstaTextField);

					//======== OdabranaVrstaButtonsPanell ========
					{
						OdabranaVrstaButtonsPanell.setLayout(new FlowLayout());

						//---- OdabranaVrstaButtonAdd ----
						OdabranaVrstaButtonAdd.setText("Add");
						OdabranaVrstaButtonsPanell.add(OdabranaVrstaButtonAdd);

						//---- OdabranaVrstaButtonRem ----
						OdabranaVrstaButtonRem.setText("Rem");
						OdabranaVrstaButtonsPanell.add(OdabranaVrstaButtonRem);
					}
					OdabranaVrstaPanel.add(OdabranaVrstaButtonsPanell);
				}
				TopRightPanel.add(OdabranaVrstaPanel);

				//======== OdabranaBazaPanel ========
				{
					OdabranaBazaPanel.setLayout(new GridLayout(3, 1));

					//---- OdabranaBazaLabel ----
					OdabranaBazaLabel.setText("Odabrana baza");
					OdabranaBazaPanel.add(OdabranaBazaLabel);
					OdabranaBazaPanel.add(OdabranaBazaTextField);

					//======== OdabranaBazaButtonsPanell ========
					{
						OdabranaBazaButtonsPanell.setLayout(new FlowLayout());

						//---- OdabraanaBazaButtonAdd ----
						OdabraanaBazaButtonAdd.setText("Add");
						OdabranaBazaButtonsPanell.add(OdabraanaBazaButtonAdd);

						//---- OdabraanaBazaButtonRem ----
						OdabraanaBazaButtonRem.setText("Rem");
						OdabranaBazaButtonsPanell.add(OdabraanaBazaButtonRem);
					}
					OdabranaBazaPanel.add(OdabranaBazaButtonsPanell);
				}
				TopRightPanel.add(OdabranaBazaPanel);
			}
			RightPanel.add(TopRightPanel);

			//======== CenterRightPanel ========
			{
				CenterRightPanel.setLayout(new GridLayout(2, 0));

				//======== UkljuceniPanel ========
				{
					UkljuceniPanel.setLayout(new BorderLayout());

					//======== UkljuceniTextPanel ========
					{
						UkljuceniTextPanel.setLayout(new BorderLayout());
						UkljuceniTextPanel.add(UkljuceniTextArea, BorderLayout.CENTER);

						//======== UkljuceniButtonsPanel ========
						{
							UkljuceniButtonsPanel.setLayout(new BoxLayout(UkljuceniButtonsPanel, BoxLayout.Y_AXIS));

							//---- UkljuceniAddButton ----
							UkljuceniAddButton.setText("Add");
							UkljuceniAddButton.setPreferredSize(new Dimension(60, 23));
							UkljuceniAddButton.setActionCommand("Rem");
							UkljuceniAddButton.setAlignmentX(0.5F);
							UkljuceniAddButton.setMinimumSize(new Dimension(60, 23));
							UkljuceniAddButton.setMaximumSize(new Dimension(60, 23));
							UkljuceniButtonsPanel.add(UkljuceniAddButton);

							//---- UkljuceniRemButton ----
							UkljuceniRemButton.setText("Rem");
							UkljuceniRemButton.setAlignmentX(0.5F);
							UkljuceniRemButton.setMaximumSize(new Dimension(60, 23));
							UkljuceniRemButton.setMinimumSize(new Dimension(60, 23));
							UkljuceniRemButton.setPreferredSize(new Dimension(60, 23));
							UkljuceniButtonsPanel.add(UkljuceniRemButton);
						}
						UkljuceniTextPanel.add(UkljuceniButtonsPanel, BorderLayout.EAST);
					}
					UkljuceniPanel.add(UkljuceniTextPanel, BorderLayout.CENTER);

					//---- UkljuceniLabel ----
					UkljuceniLabel.setText("Ukljuceni dodaci");
					UkljuceniPanel.add(UkljuceniLabel, BorderLayout.NORTH);
				}
				CenterRightPanel.add(UkljuceniPanel);

				//======== IskljuceniPanel ========
				{
					IskljuceniPanel.setLayout(new BorderLayout());

					//---- IskljuceniLabel ----
					IskljuceniLabel.setText("Iskljuceni dodaci");
					IskljuceniPanel.add(IskljuceniLabel, BorderLayout.NORTH);

					//======== IskljuceniTextPanel ========
					{
						IskljuceniTextPanel.setLayout(new BorderLayout());
						IskljuceniTextPanel.add(IskljuceniTextArea, BorderLayout.CENTER);

						//======== IskljuceniButtonsPanel ========
						{
							IskljuceniButtonsPanel.setLayout(new BoxLayout(IskljuceniButtonsPanel, BoxLayout.Y_AXIS));

							//---- IskljuceniAddButton ----
							IskljuceniAddButton.setText("Add");
							IskljuceniAddButton.setPreferredSize(new Dimension(60, 23));
							IskljuceniAddButton.setMaximumSize(new Dimension(60, 23));
							IskljuceniAddButton.setMinimumSize(new Dimension(60, 23));
							IskljuceniButtonsPanel.add(IskljuceniAddButton);

							//---- IskljuceniRemButton ----
							IskljuceniRemButton.setText("Rem");
							IskljuceniRemButton.setMaximumSize(new Dimension(60, 23));
							IskljuceniRemButton.setMinimumSize(new Dimension(60, 23));
							IskljuceniRemButton.setPreferredSize(new Dimension(60, 23));
							IskljuceniButtonsPanel.add(IskljuceniRemButton);
						}
						IskljuceniTextPanel.add(IskljuceniButtonsPanel, BorderLayout.EAST);
					}
					IskljuceniPanel.add(IskljuceniTextPanel, BorderLayout.CENTER);
				}
				CenterRightPanel.add(IskljuceniPanel);
			}
			RightPanel.add(CenterRightPanel);

			//======== BottomRightPanel ========
			{
				BottomRightPanel.setLayout(new BoxLayout(BottomRightPanel, BoxLayout.X_AXIS));

				//---- GetButton ----
				GetButton.setText("Get");
				BottomRightPanel.add(GetButton);

				//---- GetTextArea ----
				GetTextArea.setText("Some text to be determinend\nMore text\nAnd some more");
				BottomRightPanel.add(GetTextArea);
			}
			RightPanel.add(BottomRightPanel);
		}
		contentPane.add(RightPanel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Marija Civovic
	private JPanel LeftPanel;
	private JScrollPane VrstaScrollPane;
	private JList VrstaList;
	private JScrollPane BazsScrollPane;
	private JList BazaList;
	private JScrollPane DodaciScrollPane;
	private JList DodaciList;
	private JPanel RightPanel;
	private JPanel TopRightPanel;
	private JPanel OdabranaVrstaPanel;
	private JLabel OdabranaVrstaLabel;
	private JTextField OdabranaVrstaTextField;
	private JPanel OdabranaVrstaButtonsPanell;
	private JButton OdabranaVrstaButtonAdd;
	private JButton OdabranaVrstaButtonRem;
	private JPanel OdabranaBazaPanel;
	private JLabel OdabranaBazaLabel;
	private JTextField OdabranaBazaTextField;
	private JPanel OdabranaBazaButtonsPanell;
	private JButton OdabraanaBazaButtonAdd;
	private JButton OdabraanaBazaButtonRem;
	private JPanel CenterRightPanel;
	private JPanel UkljuceniPanel;
	private JPanel UkljuceniTextPanel;
	private JTextArea UkljuceniTextArea;
	private JPanel UkljuceniButtonsPanel;
	private JButton UkljuceniAddButton;
	private JButton UkljuceniRemButton;
	private JLabel UkljuceniLabel;
	private JPanel IskljuceniPanel;
	private JLabel IskljuceniLabel;
	private JPanel IskljuceniTextPanel;
	private JTextArea IskljuceniTextArea;
	private JPanel IskljuceniButtonsPanel;
	private JButton IskljuceniAddButton;
	private JButton IskljuceniRemButton;
	private JPanel BottomRightPanel;
	private JButton GetButton;
	private JTextArea GetTextArea;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
