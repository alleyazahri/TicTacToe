/**
 * ControlPanel.java
 * @author Megan Francis
 *
 * This panel contains the buttons for reseting a 
 * tic tac toe panel, and printing statistics
 * for the games played.
 * 
 * ControlPanel.java is originally based off of
 * ControlPanel.java - a class written for Lab 10
 * in the Algorithms and Datastructures class.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class ControlPanel extends JPanel implements ActionListener
{
	private JButton resetButton;	// the reset button
	private TicTacToe matrix;	// the panel containing the squares
	private JButton printStats; //Prints the statistics of all the games you've played in a round
								// on to the console.
	/**
	 * This panel contains the buttons to reset a tic tac toe
	 * panel and to get the game statistics for games
	 * played
	 * 
	 * @param matrix The panel for the squares for the game.
	 */
	public ControlPanel(TicTacToe matrix) {
		this.matrix = matrix;

		add(resetButton = new JButton("Reset"));
		resetButton.addActionListener(this);
		
		add(printStats = new JButton("Game Statistics"));
		printStats.addActionListener(this);


		Border etched = BorderFactory.createEtchedBorder();
		Border titled = BorderFactory.createTitledBorder(etched, "Choices");
		setBorder(titled);
	}

	/**
	 * The ActionListener for the "Reset" button.
	 * Restores the default empty game board.
	 */
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource()  == resetButton) {
			matrix.resetValues();
		}
		if (evt.getSource() == printStats)
			matrix.stats();
	}	

	/**
	 * Sets the total field based upon the chosen algorithm.
	 *
	 * @param total the value to set the total field to.
	 */
	public void setTotal(int total) {
//		totalField.setText(new Integer(total).toString());
	}
}
