/**
 * LoadGame.java
 * @author Megan Francis
 * 
 * This class loads the tic-tac-toe game.
 * 
 * LoadGame.java is originally based off of
 * TheMatrix.java - a class written for Lab 10
 * in the Algorithms and Datastructures class.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoadGame extends JFrame {
	
	public LoadGame(){
		setSize(500, 500);
		TicTacToe matrixPanel = new TicTacToe();
		ControlPanel controlPanel = new ControlPanel(matrixPanel);
		getContentPane().add(controlPanel, "South");
		getContentPane().add(matrixPanel, "Center");

		/**
		 * This allows the window to close
		 */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		JFrame frame = new LoadGame();
		frame.setTitle("Tic Tac Toe");
		frame.setVisible(true);

	}

}
