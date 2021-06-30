package gui.editor;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import automata.TextLabel;
import debug.EDebug;
import gui.environment.AutomatonEnvironment;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

public class TextLabelTool extends Tool {
	/**
	 * Instantiates a new TextLabel tool.
	 */
	public TextLabelTool(AutomatonPane view, AutomatonDrawer drawer) {
		super(view, drawer);
	}

	/**
	 * Gets the tool tip for this tool.
	 * 
	 * @return the tool tip for this tool
	 */
	public String getToolTip() {
		return "Text Creator";
	}

	/**
	 * Returns the tool icon.
	 * 
	 * @return the TextLabel tool icon
	 */
	protected Icon getIcon() {
		java.net.URL url = getClass().getResource("/ICON/text.gif");
		return new ImageIcon(url);
	}

	/**
	 * When the user clicks, one creates a TextLabel.
	 * 
	 * @param event the mouse event
	 */
	public void mousePressed(MouseEvent event) {
		if (getDrawer().getAutomaton().getEnvironmentFrame() != null)
			((AutomatonEnvironment) getDrawer().getAutomaton().getEnvironmentFrame().getEnvironment()).saveStatus();
		this.textLabel=getAutomaton().createTextLabel(event.getPoint());		
		getView().repaint();
	}

	/**
	 * When the user drags, one moves the created TextLabel.
	 * 
	 * @param event the mouse event
	 */
	public void mouseDragged(MouseEvent event) {
		System.out.print(event.getPoint().x + " $ " + event.getPoint().y);
		System.out.print(textLabel);
		 /*this.textLabel.setPoint(new Point(event.getPoint().x, event.getPoint().y));
		getView().repaint();*/
		textLabel.setPoint(event.getPoint());
		getView().repaint();
	}

	public void mouseReleased(MouseEvent event) {
		getView().repaint();
	}

	/**
	 * Returns the keystroke to switch to this tool, T.
	 * 
	 * @return the keystroke for this tool
	 */
	public KeyStroke getKey() {
		return KeyStroke.getKeyStroke('t');
	}

	/** The TextLabel that was created. */
	automata.TextLabel textLabel = null;
}
