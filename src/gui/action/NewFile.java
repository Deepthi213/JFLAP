package gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import gui.environment.FrameFactory;

public class NewFile extends RestrictedAction {

	public NewFile() {
		super("New...", null);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, MAIN_MENU_MASK));
	}

	public NewFile(String string, Icon icon) {
		super(string, icon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		if (NewAction.LoadedObj != null) {
//			FrameFactory.createFrame(NewAction.LoadedObj);
//		}
		NewAction na=new NewAction();
		na.showNewInstance();
	}
}
