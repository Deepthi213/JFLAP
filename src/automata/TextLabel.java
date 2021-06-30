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

package automata;

import gui.viewer.AutomatonPane;
import java.awt.Point;

import javax.swing.JTextArea;

import automata.event.AutomataTextLabelEvent;

/**
 * A class that represents TextLabels on the JFLAP canvas.
 */
public class TextLabel extends JTextArea {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean moving = false;
	protected Point initialPointState;
	protected Point initialPointClick;
	protected AutomatonPane myView;

	String name = null;

	/** The Textlabel ID. */
	int id;

	/** The automaton this TextLabel belongs to. */
	private Automaton automaton = null;

	/** The point where this Textlabel is to be drawn. */
	private Point point;

	/**
	 * Instantiates a new TextLabel.
	 * 
	 * @param id        the TextLabel id, used for generating
	 * @param point     the point that the center of the TextLabel will be at in the
	 *                  canvas
	 * @param automaton the automaton this belongs to
	 */
	public TextLabel(int id, Point point, Automaton automaton) {
		this.point = point;
		this.id = id;
		this.automaton = automaton;
	}

	/**
	 * Creates an instance of <CODE>TextLabel</CODE> with a specified message.
	 */
	public TextLabel(String message) {
		this.setText(message);
	}

	/**
	 * Returns the automaton that this TextLabel belongs to.
	 * 
	 * @return the automaton that this TextLabel belongs to
	 */
	public Automaton getAutomaton() {
		return automaton;
	}

	/**
	 * Sets the point for this TextLabel.
	 * 
	 * @param point the point this TextLabel is moving to
	 * @see #getPoint()
	 */
	public void setPoint(Point point) {
		this.point = point;
		getAutomaton().distributeTextLabelEvent(new AutomataTextLabelEvent(getAutomaton(), this, false, true, true));
	}

	/**
	 * Returns the TextLabel ID for this TextLabel.
	 * 
	 * @return the ID of the TextLabel
	 */
	public int getID() {
		return id;
	}

	/**
	 * Sets the ID for this TextLabel.
	 * 
	 * @param id the new ID for this TextLabel.
	 */
	protected void setID(int id) {
		System.out.println("(\"t\" + this.id).equals(name) : " + name);
		if (("t" + this.id).equals(name))
			name = null;
		this.id = id;
		getAutomaton().distributeTextLabelEvent(new AutomataTextLabelEvent(getAutomaton(), this, false, false, true));
	}

	/**
	 * Returns the point this TextLabel is centered on in the canvas.
	 * 
	 * @see #setPoint(Point)
	 * @return the point this TextLabel is centered on in the canvas
	 */
	public Point getPoint() {
		return point;
		// return myAutoPoint;
	}

	public void setAutomaton(Automaton auto) {
		this.automaton = auto;
	}

	public String getName() {
		if (name == null) {
			System.out.println(">>name : " + name);
			name = "-val " + Integer.toString(getID());
		}
		return name;
	}

	/*
	 * For the undo part of cloning, we need a way to store the view without
	 * becoming visible / active.
	 */
	public void setView(AutomatonPane view) {
		myView = view;
	}

	/**
	 * Gets the AutomatonPane that the TextLabel belongs to.
	 * 
	 * @return the AutomatonPane that the TextLabel belongs to.
	 */
	public AutomatonPane getView() {
		return myView;
	}

	public void setLocation(Point p) {
		if (moving) {
			if (myView != null) {
				point = p;
				super.setLocation(p);
			}
		}
	}

	public void setLocation(int x, int y) {
		if (moving) {
			super.setLocation(x, y);
		}
		moving = false;
	}

	/**
	 * Sets the name for this TextLabel. A parameter of <CODE>null</CODE> will reset
	 * this to the default.
	 * 
	 * @param name the new name for the TextLabel
	 */
	public void setName(String name) {
		this.name = name;
		getAutomaton().distributeTextLabelEvent(new AutomataTextLabelEvent(getAutomaton(), this, false, false, true));
	}

	public int specialHash() {
//        EDebug.print(myAutoPoint.hashCode() + getText().hashCode());
		return point == null ? -1 : point.hashCode() + this.getText().hashCode();
	}

	private boolean selected = false;

	public void setSelect(boolean select) {
		selected = select;
	}

	public boolean isSelected() {
		return selected;
	}

}
