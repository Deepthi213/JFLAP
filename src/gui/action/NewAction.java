/*
 *  JFLAP - Formal Languages and Automata Package
 * 
 * 
 *  Susan H. Rodger
 *  Computer Science Department
 *  Duke University
 *  August 27, 2009

 *  Copyright (c) 2002-2009
 *  All rights reserved.

 *  JFLAP is open source software. Please see the LICENSE for terms.
 *
 */

package gui.action;

import gui.environment.*;
import gui.menu.MenuBarCreator;
import gui.pumping.CFPumpingLemmaChooser;
import gui.pumping.RegPumpingLemmaChooser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serializable;
import javax.swing.*;

import automata.Automaton;
import automata.mealy.MealyMachine;
import automata.mealy.MooreMachine;
import file.Codec;

/**
 * The <CODE>NewAction</CODE> handles when the user decides to create some new
 * environment, that is, some sort of new automaton, or grammar, or regular
 * expression, or some other such editable object.
 * 
 * @author Thomas Finley
 */

public class NewAction extends RestrictedAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final String DEFAULT_TITLE = "JFLAP 7.1";

	private Boolean minifiyPanel = false;

	public JButton selectedButton = null;

	private EnvironmentFrame loadedEF = null;

	public Serializable LoadedObj = null;

	private JPanel panel1 = null;
	private JPanel panel2 = null;
	private JPanel panel3 = null;

	private static String[][] classArr = { { "FiniteStateAutomaton", "Finite Automaton", "FA"," this is Finite automaton description" },
			{ "MealyMachine", "Mealy Machine", "MeM" }, { "MooreMachine", "Moore Machine", "MM" },
			{ "PushdownAutomaton", "Pushdown Automaton", "PA" }, { "TuringMachine", "Turing Machine", "TM" },
			{ "TuringMachine", "Multi-Tape Turing Machine", "MTM" },
			{ "TuringMachineBuildingBlocks", "Turing Machine With Building Blocks", "TM(BB)" },
			{ "ContextFreeGrammar", "Grammar", "Grmr" }, { "LSystem", "L-System", "L-S" },
			{ "RegularExpression", "Regular Expression", "RE" },
			{ "RegPumpingLemmaChooser", "Regular Pumping Lemma", "RPL" },
			{ "CFPumpingLemmaChooser", "Context-Free Pumping Lemma", "CFPL" } };

	/**
	 * Instantiates a new <CODE>NewAction</CODE>.
	 */
	public NewAction() {
		super("New...", null);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, MAIN_MENU_MASK));
	}

	/**
	 * Shows the new machine dialog box.
	 * 
	 * @param event the action event
	 */
	public void actionPerformed(ActionEvent event) {
		showNew();
	}

	/**
	 * Dispose of environment dialog by Moti Ben-Ari
	 */
	public void closeNew() {
		DIALOG.dispose();
		DIALOG = null;
	}

	/**
	 * Shows the new environment dialog.
	 */
	public void showNew() {
		if (DIALOG == null)
			DIALOG = new NewDialog(this);
		DIALOG.setVisible(true);
		DIALOG.toFront();
	}

	public void showNewInstance() {
		gui.Main.main(new String[0]);
	}

	/**
	 * Hides the new environment dialog.
	 */
	public void hideNew() {
		// DIALOG.setVisible(false);
	}

	/**
	 * Called once a type of editable object is choosen. The editable object is
	 * passed in, the dialog is hidden, and the window is created.
	 * 
	 * @param object the object that we are to edit
	 */
	private static void createWindow(Serializable object) {
		// DIALOG.setVisible(false);
		FrameFactory.createFrame(object);
	}

	private void selectedButton(JButton button) {
		selectedButton = button;
		button.setBackground(Color.yellow);
	}

	public static String titleName(Serializable object) {
		String str = "";
		for (int i = 0; i < classArr.length; i++) {
			if (object.getClass().toString().contains(classArr[i][0].toString())) {
				str = classArr[i][1].toString();
				break;
			}
		}
		return str;
	}

	public void OpenFile(Serializable object, File file, Codec codec) {
		minifiyPanel = false;
		selectedButton = null;
		if (DIALOG == null)
			DIALOG = new NewDialog(null);
		DIALOG.setVisible(true);
		DIALOG.toFront();
		Environment environment = EnvironmentFactory.getEnvironment(object);
		EnvironmentFrame ef = new EnvironmentFrame(environment);
		if (object instanceof Automaton) {
			// //System.out.println("Setting Frame");
			((Automaton) object).setEnvironmentFrame(ef);
		}		
		ef.pack();
		DIALOG.setJMenuBar(gui.menu.MenuBarCreator.getMenuBar(ef,this));
		loadedEF = ef;
		/* EnvironmentFrame ef = FrameFactory.createFrame(object); */
		if (ef == null)
			return;
		ef.pack();
		loadedEF = ef;
		ef.getEnvironment().setFile(file);
		ef.getEnvironment().setEncoder(codec.correspondingEncoder());

//		JOptionPane.showMessageDialog(null, object.getClass(),"PopUp4", JOptionPane.CLOSED_OPTION);

		panel3.removeAll();
		panel3.add(environment);
		panel3.revalidate();
		Component[] comp = panel1.getComponents();

		String str = NewAction.titleName(object);
		DIALOG.setTitle(DEFAULT_TITLE + " : (" + str + ") <" + file.getName()+">");

		for (int i = 0; i < comp.length; i++) {
			if (comp[i] instanceof JButton) {
				comp[i].setBackground(null);
				if (((JButton) comp[i]).getToolTipText().toString() == str) {
					selectedButton((JButton) comp[i]);
				}
			}
		}
		changeLayout();
		DIALOG.revalidate();
	}

	public int buttonSize(int buttonHeight) {
		if (buttonHeight == 0) {
			buttonHeight = 60;
		}
		int width = 0;
		int j = 0;
		Component[] comp = panel1.getComponents();
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] instanceof JButton) {

				if (minifiyPanel) {
					((JButton) comp[i]).setText(classArr[j][2]);
					comp[i].setPreferredSize(new Dimension(75, buttonHeight));
					comp[i].setSize(75, buttonHeight);
				} else {
					((JButton) comp[i]).setText(classArr[j][1]);
					comp[i].setPreferredSize(new Dimension(240, buttonHeight));
					comp[i].setSize(240, buttonHeight);
				}
				if (width == 0) {
					width = (int) comp[i].getSize().getWidth();
				}
				j++;
			}
		}
		return width;
	}

	public void changeLayout() {
		int width = (int) DIALOG.getSize().getWidth();
		int height = (int) DIALOG.getSize().getHeight();
		int buttonHeight = (height / 12);
		int widthPanel = 0;

		int buttonWidth = buttonSize(buttonHeight);

		panel1.setPreferredSize(new Dimension(buttonWidth, height - 70));
		panel1.setSize(buttonWidth, height - 70);

		widthPanel = (int) (width - (panel1.getSize().getWidth() + panel2.getSize().getWidth() + 20));
		panel3.setPreferredSize(new Dimension(widthPanel, height - 70));
		panel3.setSize(widthPanel, height - 70);

		DIALOG.setPreferredSize(new Dimension(width, height));
		DIALOG.setSize(width, height);

		panel1.revalidate();
		panel2.revalidate();
		panel3.revalidate();
		DIALOG.pack();
		DIALOG.revalidate();
//		JOptionPane.showMessageDialog(null, panel1_.getSize(),"PopUp4", JOptionPane.CLOSED_OPTION);
	}

	/** The dialog box that allows one to create new environments. */
	class NewDialog extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a <CODE>NewDialog</CODE> instance.
		 */
		public NewDialog(NewAction newaction) {
			// super((java.awt.Frame)null, "New Document");
			super("JFLAP 7.1");
			getContentPane().setLayout(new GridLayout(0, 1));
			initMenu(newaction);
			initComponents();
			this.setSize(1000, 1000);
			// setResizable(false);
			this.pack();
			this.setLocation(50, 50);

			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent event) {
					if (loadedEF != null) {
						loadedEF.close();
						loadedEF = null;
					}
					if (Universe.numberOfFrames() > 0) {
						// NewDialog.this.setVisible(false);
					} else {
						QuitAction.beginQuit();
					}
				}
			});

			this.addWindowStateListener(new WindowAdapter() {
				public void windowStateChanged(WindowEvent e) {
					// JOptionPane.showMessageDialog(null, e.getNewState(),"PopUp3",
					// JOptionPane.CLOSED_OPTION);
					if (e.getNewState() > 0) {// this means minimized
						changeLayout();
					} else if (e.getNewState() <= 0) {// this means maximized/normal state
						changeLayout();
					}
				}
			});
			buttonSize(0);
		}

		private void initMenu(NewAction newaction) {
			// Mini menu!
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			if (Universe.CHOOSER != null) {
				MenuBarCreator.addItem(menu, new OpenAction(newaction));
				MenuBarCreator.addItem(menu, new SubmitToMoodle());
			}
			try {
				SecurityManager sm = System.getSecurityManager();
				if (sm != null)
					sm.checkExit(0);
				MenuBarCreator.addItem(menu, new QuitAction());
			} catch (SecurityException e) {
				// Well, can't exit anyway.
			}
			menuBar.add(menu);
			menu = new JMenu("Help");
			// MenuBarCreator.addItem(menu, new NewHelpAction());
			MenuBarCreator.addItem(menu, new AboutAction());
			menuBar.add(menu);
			menu = new JMenu("Batch");
			MenuBarCreator.addItem(menu, new TestAction());
			menuBar.add(menu);
			menu = new JMenu("Preferences");

			JMenu tmPrefMenu = new JMenu("Turing Machine Preferences");
			tmPrefMenu.add(Universe.curProfile.getTuringFinalCheckBox());
			tmPrefMenu.add(Universe.curProfile.getAcceptByFinalStateCheckBox());
			tmPrefMenu.add(Universe.curProfile.getAcceptByHaltingCheckBox());
			tmPrefMenu.add(Universe.curProfile.getAllowStayCheckBox());

			// MenuBarCreator.addItem(menu, new ColorChooserAction());
			MenuBarCreator.addItem(menu, new EmptyStringCharacterAction());
//            menu.add(Universe.curProfile.getTuringFinalCheckBox());
			menu.add(new SetUndoAmountAction());
			menu.add(new ColorChooserAction());

			menu.add(tmPrefMenu);

			menuBar.add(menu);

			setJMenuBar(menuBar);
		}

		private void initComponents() {
			panel1 = new JPanel();// this is to load machine names menu
			panel1.setLayout(new GridLayout(12, 1));

			panel2 = new JPanel();// this is to load min and max button 
			panel2.setLayout(new GridLayout());

			panel3 = new JPanel();// this is to load the environment selected in machine name menu
			panel3.setLayout(new GridLayout(0, 1));

			GridBagConstraints c = new GridBagConstraints();
			JButton button = null;
			// Let's hear it for sloth!

			button = new JButton(classArr[0][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(e.getSource());
					setPanel(new automata.fsa.FiniteStateAutomaton(), (JButton) e.getSource());
//					createWindow(new automata.fsa.FiniteStateAutomaton());  reference
				}
			});
			button.setToolTipText(classArr[0][1]);
			panel1.add(button);

			button = new JButton(classArr[1][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel(new MealyMachine(), (JButton) e.getSource());
//					createWindow(new MealyMachine());
				}
			});
			button.setToolTipText(classArr[1][1]);
			panel1.add(button);

			button = new JButton(classArr[2][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel(new MooreMachine(), (JButton) e.getSource());
//					createWindow(new MooreMachine());
				}
			});
			button.setToolTipText(classArr[2][1]);
			panel1.add(button);

			button = new JButton(classArr[3][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Object[] possibleValues = { "Multiple Character Input", "Single Character Input" };
					Object selectedValue = JOptionPane.showInputDialog(null, "Type of PDA Input", "Input",
							JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
					if (selectedValue == possibleValues[0]) {
						setPanel(new automata.pda.PushdownAutomaton(), (JButton) e.getSource());
//						createWindow(new automata.pda.PushdownAutomaton());						
					} else if (selectedValue == possibleValues[1]) {
						setPanel(new automata.pda.PushdownAutomaton(true), (JButton) e.getSource());
//						createWindow(new automata.pda.PushdownAutomaton(true));
					}
				}
			});
			button.setToolTipText(classArr[3][1]);
			panel1.add(button);

			button = new JButton(classArr[4][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel(new automata.turing.TuringMachine(1), (JButton) e.getSource());
//					createWindow(new automata.turing.TuringMachine(1));
				}
			});
			button.setToolTipText(classArr[4][1]);
			panel1.add(button);

			button = new JButton(classArr[5][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (INTS == null) {
						INTS = new Integer[4];
						for (int i = 0; i < INTS.length; i++)
							INTS[i] = new Integer(i + 2);
					}
					Number n = (Number) JOptionPane.showInputDialog(NewDialog.this.getContentPane(), "How many tapes?",
							"Multi-tape Machine", JOptionPane.QUESTION_MESSAGE, null, INTS, INTS[0]);
					if (n == null)
						return;
					setPanel(new automata.turing.TuringMachine(n.intValue()), (JButton) e.getSource());
//					createWindow(new automata.turing.TuringMachine(n.intValue()));
				}

				private Integer[] INTS = null;
			});
			button.setToolTipText(classArr[5][1]);
			panel1.add(button);

			button = new JButton(classArr[6][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel(new automata.turing.TuringMachineBuildingBlocks(), (JButton) e.getSource());
//					createWindow(new automata.turing.TuringMachineBuildingBlocks(1));
				}
			});
			button.setToolTipText(classArr[6][1]);
			panel1.add(button);

			button = new JButton(classArr[7][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel(new grammar.cfg.ContextFreeGrammar(), (JButton) e.getSource());
//					createWindow(new grammar.cfg.ContextFreeGrammar());
				}
			});
			button.setToolTipText(classArr[7][1]);
			panel1.add(button);

			button = new JButton(classArr[8][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel(new grammar.lsystem.LSystem(), (JButton) e.getSource());
					// createWindow(new grammar.lsystem.LSystem());
				}
			});
			button.setToolTipText(classArr[8][1]);
			panel1.add(button);

			button = new JButton(classArr[9][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel(new regular.RegularExpression(), (JButton) e.getSource());
//					createWindow(new regular.RegularExpression());
				}
			});
			button.setToolTipText(classArr[9][1]);
			panel1.add(button);

			button = new JButton(classArr[10][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel(new RegPumpingLemmaChooser(), (JButton) e.getSource());
					// createWindow(new RegPumpingLemmaChooser());
				}
			});
			button.setToolTipText(classArr[10][1]);
			panel1.add(button);

			button = new JButton(classArr[11][1]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPanel(new CFPumpingLemmaChooser(), (JButton) e.getSource());
					// createWindow(new CFPumpingLemmaChooser());
				}
			});
			button.setToolTipText(classArr[11][1]);
			panel1.add(button);

			button = new JButton("<");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					minifiyPanel = !minifiyPanel;
					changeLayout();
					((JButton) e.getSource()).setText((minifiyPanel) ? ">" : "<");
				}
			});
			button.setPreferredSize(new Dimension(42, 200));
			button.setSize(42, 200);
			panel2.add(button);

			getContentPane().setLayout(new GridBagLayout());

			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;
			getContentPane().add(panel1, c);

			c.fill = GridBagConstraints.CENTER;
			c.gridx = 1; // aligned with button 2
			c.gridwidth = 1; // 2 columns wide
			c.gridy = 0; // third row
			getContentPane().add(panel2, c);

			panel3.setPreferredSize(new Dimension(600, 592));
			panel3.setSize(600, 592);
			c.fill = GridBagConstraints.EAST;
			c.gridx = 2; // aligned with button 2
			c.gridwidth = 2; // 2 columns wide
			c.gridy = 0; // third row

			getContentPane().add(panel3, c);

			panel1.setPreferredSize(new Dimension(240, 592));
			panel1.setSize(240, 592);
		}
	}

	private void setPanel(Serializable obj, JButton jbutton) {
		if (loadedEF != null) {// this is to check the work enviroment is dirty
			loadedEF.closeDirtyFrame();
			loadedEF = null;
		}
		Environment environment = EnvironmentFactory.getEnvironment(obj);
		EnvironmentFrame frame = new EnvironmentFrame(environment);
		if (obj instanceof Automaton) {
			((Automaton) obj).setEnvironmentFrame(frame);
		}
		DIALOG.setJMenuBar(gui.menu.MenuBarCreator.getMenuBar(frame,this));
		DIALOG.setTitle(DEFAULT_TITLE + " : (" + jbutton.getToolTipText() + ") " + frame.getDescription());
		frame.pack();
		loadedEF = frame;
		LoadedObj = obj;
		panel3.removeAll();
		panel3.add(environment);
		panel3.revalidate();
		// remove the seleted button color
		Component[] comp = panel1.getComponents();
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] instanceof JButton) {
				comp[i].setBackground(null);
			}
		}
		// apply the yellow color to the selected machine
		selectedButton(jbutton);
		DIALOG.pack();
		DIALOG.repaint();
		DIALOG.revalidate();
	}

	/** The universal dialog. */
	public NewDialog DIALOG = null;
}
