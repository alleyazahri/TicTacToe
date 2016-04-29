/**
 * TicTacToe.java
 * @author Megan Francis
 *
 * This class contains the squares manipulated 
 * by the tic-tac-toe game as well as the methods 
 * to run the game and keep track of wins and losses.
 * 
 * TicTacToe.java is originally based off 
 * MatrixPanel.java - a class written for Lab 10 in
 * the Algorithms and Datastructures class.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class TicTacToe extends JPanel implements ActionListener {
	// Variables for the TicTacToe panel
	private JButton[][] nodes; // the squares
	private final Font defaultFont; // the default button font
	private final Color defaultColor; // the default button color
	public static final int SIZE = 3; // the size of the SIZE X SIZE matrix
	private static final int MAX_RANDOM_NUMBER = 10; // the maximum random
														// number

	// Variables for keeping track of game stats:
	private int compWins;
	private int playerWins;
	private int total;

	// Variables for computer use and to pass back control from player to user
	private int[] compMove;
	public boolean compTurn;

	/**
	 * Public constructor for the tic-tac-toe game. Initializes variables and
	 * sets up the game board.
	 */
	public TicTacToe() {
		setLayout(new GridLayout(SIZE, SIZE));
		compTurn = false;
		nodes = new JButton[SIZE][SIZE];

		// Initializes the nodes as blank and to take input from user
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++) {
				add(nodes[i][j] = new JButton(""));
				nodes[i][j].addActionListener(this);
				nodes[i][j].setText("");

			}

		// get the default font for the buttons
		defaultFont = nodes[0][0].getFont();

		// set the default color
		defaultColor = Color.black;

		// setup for keeping track of games:
		compWins = 0;
		playerWins = 0;
		total = 0;

		compMove = new int[2];
	}

	/**
	 * reset the tic-tac-toe board to empty.
	 */
	public void resetValues() {
		compTurn = false;
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++) {
				nodes[i][j].setText("");
			}
	}

	/**
	 * Checks for a 'win' and displays the winner
	 */
	private boolean winner() {

		// Check rows
		String win = new String();
		for (int i = 0; i < 3; i++) {
			win = nodes[i][0].getText();
			if ((win.equals("X") || win.equals("O"))
					&& nodes[i][1].getText().equals(win)
					&& win.equals(nodes[i][2].getText())) {
				if (win.equals("X")) {
					System.out.println("You Beat the Computer!");
					total++;
					playerWins++;
				} else {
					System.out.println("The Computer Won.");
					total++;
					compWins++;
				}

				return true;
			}
		}
		// Check columns
		for (int i = 0; i < 3; i++) {
			win = nodes[0][i].getText();
			if ((win.equals("X") || win.equals("O"))
					&& nodes[1][i].getText().equals(win)
					&& win.equals(nodes[2][i].getText())) {
				if (win.equals("X")) {
					System.out.println("You Beat the Computer!");
					total++;
					playerWins++;
				} else {
					System.out.println("The Computer Won.");
					total++;
					compWins++;
				}
				return true;
			}
		}
		// Check diagonals with very long conditions...this is not my most
		// eloquent code...
		win = nodes[0][0].getText();
		if ((win.equals("X") || win.equals("O"))
				&& win.equals(nodes[1][1].getText())
				&& win.equals(nodes[2][2].getText())) {
			if (win.equals("X")) {
				System.out.println("You Beat the Computer!");
				total++;
				playerWins++;
			} else {
				System.out.println("The Computer Won.");
				total++;
				compWins++;
			}

			return true;
		}
		win = nodes[0][2].getText();
		if ((win.equals("X") || win.equals("O"))
				&& win.equals(nodes[1][1].getText())
				&& win.equals(nodes[2][0].getText())) {
			if (win.equals("X")) {
				System.out.println("You Beat the Computer!");
				total++;
				playerWins++;
			} else {
				System.out.println("The Computer Won.");
				total++;
				compWins++;
			}

			return true;
		}

		// Check for tie
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (nodes[i][j].getText().equals("X")
						|| nodes[i][j].getText().equals("O"))
					count++;
			}
		}
		if (count == 9) {
			System.out.println("Tie!!");
			total++;
			return true;
		}

		return false;
	}

	/**
	 * Code for the computer to take it's turn in tic tac toe.
	 */
	public void computerTurn() {

		/***Code for the computer to make a completely random move***
		 * int x, y; 
		 * do { 
		 * y = (int) (Math.random() * 3); 
		 * x = (int) (Math.random() * 3); 
		 * } 
		 * while (nodes[x][y].getText() == "X" ||nodes[x][y].getText() == "O"); nodes[x][y].setText("O"); 
		 * if (this.winner() == false) 
		 * compTurn = false;
		 **********************************************************************************/
		//Saves the state of the game into a string for use and manipulation
		String[][] initState = new String[3][3];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				initState[x][y] = nodes[x][y].getText();
			}
		}
		
		this.minimax(initState, 0);
		//Using a global variable, sets the computer's move.
		nodes[compMove[0]][compMove[1]].setText("O");
		
		//Changes the conditions so the player can make a move again.
		if (this.winner() == false)
			compTurn = false;
	}

	/**
	 * A private method that sets the global variable "compTurn" to one of the best possible plays to make
	 * against the player in order to win.
	 * 
	 * @param state - a given tic tac toe state
	 * @param level - used for recursion to keep track of whose 'play' it is
	 * @return
	 * Returns -1 for a computer loss
	 * Returns 1 for a computer win
	 * Returns 0 for a tie
	 * 
	 */
	private int minimax(String[][] state, int level) {
		//Variable that holds whether the current state is a terminal state (tie, win, loss) and who may have won.
		String[] result = termTest(state);
		//If at a terminal state, returns result of that state (who won)
		if (result[0].equals("t")) {
			if (result[1].equals("X"))
				return -1;
			else if (result[1].equals("O"))
				return 1;
			else
				return 0;
		} 
		//splits up all possible moves both min and max can take until it reaches a terminal state.
		else {
			if (level % 2 == 0) {
				int max = Integer.MIN_VALUE;
				Stack<String[][]> suc = successors(state, "O");
				while (suc.size() > 0) {
					String[][] success = suc.pop();
					int temp = minimax(success, level + 1);
					if (temp > max) {
						max = temp;
						if (level == 0) {
							//Copies the state into a new 2d array in order to save
							// a different state.
							for (int m = 0; m < 3; m++) {
								for (int f = 0; f < 3; f++) {
									if (success[m][f] != state[m][f]) {
										compMove[0] = m;
										compMove[1] = f;
									}
								}
							}
						}
					}
				}
				return max;
			} else {
				int min = Integer.MAX_VALUE;
				Stack<String[][]> suc = successors(state, "X");
				while (suc.size() > 0) {
					int temp = minimax(suc.pop(), level + 1);
					if (temp < min) {
						min = temp;
					}
				}
				return min;
			}
		}
	}

	/**
	 * Checks a current state of tic-tac-toe in order to see if there is a win
	 * 
	 * @param state - the current state of the game being looked at
	 * @return a string of length 2 where position 0 gives either t/f as a string
	 * and position 1 returns X/O/T depending on a player win, computer win, or tie respectively.
	 */
	private String[] termTest(String[][] state) {
		String[] result = new String[2];
		// Check rows
		String win = new String();
		for (int i = 0; i < 3; i++) {
			win = state[i][0];
			if ((win.equals("X") || win.equals("O")) && state[i][1].equals(win)
					&& win.equals(state[i][2])) {
				result[0] = "t";
				if (win.equals("X")) {
					result[1] = "X";
				} else {
					result[1] = "O";
				}
				return result;
			}
		}
		// Check columns
		for (int i = 0; i < 3; i++) {
			win = state[0][i];
			if ((win.equals("X") || win.equals("O")) && state[1][i].equals(win)
					&& win.equals(state[2][i])) {
				result[0] = "t";
				if (win.equals("X"))
					result[1] = "X";
				else
					result[1] = "O";
				return result;
			}
		}
		// Check diagonals with very long conditions...
		win = state[0][0];
		if ((win.equals("X") || win.equals("O")) && win.equals(state[1][1])
				&& win.equals(state[2][2])) {
			result[0] = "t";
			if (win.equals("X"))
				result[1] = "X";
			else
				result[1] = "O";
			return result;
		}
		win = state[0][2];
		if ((win.equals("X") || win.equals("O")) && win.equals(state[1][1])
				&& win.equals(state[2][0])) {
			result[0] = "t";
			if (win.equals("X"))
				result[1] = "X";
			else
				result[1] = "O";

			return result;
		}

		// Check for tie
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (state[i][j].equals("X") || state[i][j].equals("O"))
					count++;
			}
		}
		if (count == 9) {
			result[0] = "t";
			result[1] = "T";
			return result;
		}
		result[0] = "f";
		return result;
	}

	/**
	 * Gives all possible moves for a certain player at a given state.
	 * 
	 * @param state - state of a tic-tac-toe game
	 * @param value - the player to make the move X/O player or computer respectively
	 * @return a Stack of all the possible successor states
	 */
	private Stack<String[][]> successors(String[][] state, String value) {

		Stack<String[][]> theFuture = new Stack<String[][]>();
		//for all rows and columns
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				//if there is no value in the block
				if (!state[i][j].equals("X") && !state[i][j].equals("O")) {
					String[][] temp = new String[3][3];
					//create a new array with all positions same as the original state
					for (int x = 0; x < 3; x++) {
						for (int y = 0; y < 3; y++) {
							temp[x][y] = state[x][y];
						}
					}
					//add the new value to the empty state
					temp[i][j] = value;
					//push the new possiblity onto the stack
					theFuture.push(temp);
				}
			}
			//and repeat
		}

		return theFuture;
	}

	/**
	 * This mothod reacts to the users touch on one of the nodes.
	 */
	public void actionPerformed(ActionEvent evt) {
		//if it's the player's turn
		if (compTurn == false) {
			JButton temp = (JButton) evt.getSource();
			//and they aren't trying to overwrite another player's play
			if (temp.getText() != "X" && temp.getText() != "O") {
				//then they can put an X there
				temp.setText("X");
				//and now it's the computer's turn
				compTurn = true;
				//so if there isn't a win, we'll run the method that lets
				// the computer play
				if (this.winner() == false)
					this.computerTurn();
			}
		}
	}

	/**
	 * This method prints the statistics of the games played: total games played, 
	 * computer wins, player wins, and ties.
	 */
	public void stats() {
		//Self explanatory...
		System.out.println("Game Statistics So Far");
		System.out.println("Total Games:  " + total);
		System.out.println("   Player Wins:     " + playerWins);
		System.out.println("   Computer Wins:   " + compWins);
		System.out.println("   Ties:            "
				+ (total - (playerWins + compWins)));
	}
}
