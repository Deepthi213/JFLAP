package gui.help;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import automata.turing.TuringMachine;
import gui.environment.EnvironmentFrame;

import gui.menu.MenuBarCreator;

public class HelpDialog {

	public static void openHelpWindow(EnvironmentFrame frame) {
		String frameName = frame.getEnvironment().getObject().getClass().getSimpleName().toString();
		String url = "http://jflap.org/tutorial/";
		switch (frameName) {
		case "FiniteStateAutomaton": {
			url += "fa/createfa/fa.html";
			break;
		}
		case "MealyMachine": {
			url += "mealy/mealyMachines.html";
			break;
		}
		case "MooreMachine": {
			url += "mealy/mooreMachines.html";
			break;
		}
		case "PushdownAutomaton": {
			url += "pda/construct/index.html";
			break;
		}
		case "TuringMachine": {
			if (((TuringMachine) frame.getEnvironment().getObject()).tapes <= 1)
				url += "turing/changes7.1/index.html";
			else
				url += "turing/multi/index.html";
			break;
		}
		case "TuringMachineBuildingBlocks": {
			url += "turing/buildingblocks/buildingblocks.html";
			break;
		}
		case "ContextFreeGrammar": {
			url += "grammar/intro/index.htm";
			break;
		}
		case "LSystem": {
			url += "lsystem/index.html";
			break;
		}
		case "RegularExpression": {
			url += "regular/index.html";
			break;
		}
		case "RegPumpingLemmaChooser": {
			url += "pumpinglemma/regular/index.html";
			break;
		}
		case "CFPumpingLemmaChooser": {
			url += "pumpinglemma/context_free/index.html";
			break;
		}

		default:
			url += "";
		}

		Desktop desktop = java.awt.Desktop.getDesktop();
		try {
			// specify the protocol along with the URL
			URI oURL = new URI(url);
			desktop.browse(oURL);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


}
