package gui.action;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

public class SubmitToMoodle extends RestrictedAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubmitToMoodle(String string, Icon icon) {
		super(string, icon);
		// TODO Auto-generated constructor stub
	}

	public SubmitToMoodle() {
		super("Submit to moodle...", null);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, MAIN_MENU_MASK));
		putValue(Action.SHORT_DESCRIPTION, toolTip());
	}
	public String toolTip() {
		return "This is submit to moodle tooltip";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Desktop desktop = java.awt.Desktop.getDesktop();
		try {
			// specify the protocol along with the URL
			URI oURL = new URI("https://classes.cs.siue.edu/spring-2021/");
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
