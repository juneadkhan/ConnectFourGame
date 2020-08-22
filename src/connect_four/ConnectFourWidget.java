package connect_four;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectFourWidget extends JPanel implements ActionListener, Listener {

	/* Enum to identify player. */

	private enum Player {
		BLACK, RED
	};

	private NoughtBoard _board; // SpotBoard playing area.
	private JLabel _message; // Label for messages.
	private boolean _gameWon; // Indicates if games was been won already
	private boolean _gameDraw; // Indicates if games was been drawn.

	private String playerName;

	private Player _nextPlayer; // Identifies who has next turn. */
	private Color _winner; // Identifies the winner

	public ConnectFourWidget() {

		/* Create SpotBoard and message label. */

		_board = new NoughtBoard_UI(7, 6);
		_message = new JLabel();

		/* Set layout and place SpotBoard at center. */

		setLayout(new BorderLayout());
		add((Component) _board, BorderLayout.CENTER);

		/* Create subpanel for message area and reset button. */

		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());

		/* Reset button. Add ourselves as the action listener. */

		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(_message, BorderLayout.CENTER);

		/* Add subpanel in south area of layout. */

		add(reset_message_panel, BorderLayout.SOUTH);

		/*
		 * Add ourselves as a spot listener for all of the spots on the spot board.
		 */
		_board.addSpotListener((Listener) this);

		/* Reset game. */
		resetGame();
	}

	/*
	 * resetGame
	 * 
	 * Resets the game by clearing all the spots on the board, picking a new secret
	 * spot, resetting game status fields, and displaying start message.
	 * 
	 */

	private void resetGame() {
		/*
		 * Clear all spots on board. Uses the fact that SpotBoard implements
		 * Iterable<Spot> to do this in a for-each loop.
		 */

		for (Nought x : _board) {
			x.unhighlight();
		}

		for (Nought s : _board) {
			s.clear();
			s.setColour(Color.BLACK);

		}

		/* Reset game won and next to play fields */
		_gameWon = false;
		_gameDraw = false;
		_nextPlayer = Player.RED;

		/* Display game start message. */

		_message.setText("Welcome to ConnectFour. Red to play");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Handles reset game button. Simply reset the game. */
		resetGame();
	}

	/*
	 * Implementation of SpotListener below. Implements game logic as responses to
	 * enter/exit/click on spots.
	 */

	public void clicked(Nought s) {

		/* If game already won, do nothing. */
		if (_gameWon) {
			return;
		}

		/*
		 * Set up player and next player name strings, and player color as local
		 * variables to be used later.
		 */

		String player_name = null;
		String next_player_name = null;
		Color player_color = null;

		if (_nextPlayer == Player.BLACK) {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "Red";
			_nextPlayer = Player.RED;
		} else {
			player_color = Color.RED;
			player_name = "Red";
			next_player_name = "Black";
			_nextPlayer = Player.BLACK;
		}

		/* Set color of spot clicked and toggle. */
		if (s.isEmpty() || s.isEmpty() == false) {
			int highest_val = -1;
			int highest_val_OG = highest_val;
			for (Nought x : _board) {
				if (x.getX() == s.getX()) {
					if (x.getY() > highest_val) {
						highest_val_OG = highest_val;
						highest_val = x.getY();
						if (_board.getSpotAt(x.getX(), highest_val).isEmpty() == true) {
							highest_val = x.getY();
						} else {
							highest_val = highest_val_OG;
						}
					}

				}
			}

			if (highest_val == -1) {
				return;
			}

			System.out.println("Highest Value is: " + highest_val);
			Nought temp = _board.getSpotAt(s.getX(), highest_val);

			temp.setColour(player_color);
			temp.toggle();

		} else {

			if (_nextPlayer == Player.BLACK) {
				player_color = Color.RED;
				player_name = "Red";
				next_player_name = "Red";
				_nextPlayer = Player.RED;

			} else {

				player_color = Color.BLACK;
				player_name = "Black";
				next_player_name = "Black";
				_nextPlayer = Player.BLACK;

			}
		}

		System.out.println("Win Checker Output: " + checkWin());
		System.out.println("Winner is : " + _winner);

		/*
		 * Check if spot clicked is secret spot. If so, mark game as won and update
		 * background of spot to show that it was the secret spot.
		 */

		/*
		 * Update the message depending on what happened. If spot is empty, then we must
		 * have just cleared the spot. Update message accordingly. If spot is not empty
		 * and the game is won, we must have just won. Calculate score and display as
		 * part of game won message. If spot is not empty and the game is not won,
		 * update message to report spot coordinates and indicate whose turn is next.
		 */

		if (s.isEmpty()) {

			_message.setText(next_player_name + " to play.");
		} else {
			if (_gameWon) {
				if (_winner == Color.BLACK) {
					playerName = "Black";
				} else {
					playerName = "Red";
				}

				_message.setText(playerName + " wins! ");

			} else {

				if (checkDraw() && (_gameWon == false)) {

					_message.setText(" Draw game.");

				} else {

					_message.setText(next_player_name + " to play.");
				}
			}
		}
	}

	public void entered(Nought s) {
		/* Highlight spot if game still going on. */

		if (_gameWon) {
			return;
		}

		int col_val = s.getX();

		for (Nought x : _board) {
			if (x.getX() == col_val) {
				x.highlight();
			}
		}

		// s.highlightSpot();
	}

	public void highlightFinal(Nought s1, Nought s2, Nought s3, Nought s4) {
		/* Highlight spot if game still going on. */

		if (_gameWon) {
			return;
		}

		for (Nought x : _board) {
			x.unhighlight();
		}

		s1.highlight();
		s2.highlight();
		s3.highlight();
		s4.highlight();

		// s.highlightSpot();
	}

	public void exited(Nought s) {
		/* Unhighlight spot. */

		if (_gameWon) {
			return;
		}

		int col_val = s.getX();

		for (Nought x : _board) {
			if (x.getX() == col_val) {
				x.unhighlight();
			}
		}

		// s.unhighlightSpot();
	}

//NEEDS TO BE EDITED

	public boolean checkWin() {

		if (_gameWon) {
			return true;
		}

		// final int HEIGHT = _board.getHeight();
		final int HEIGHT = 7;
		final int WIDTH = 6;

		// final int WIDTH = _board.getWidth();
		for (int x = 0; x < 7; x++) { // iterate rows, bottom to top
			for (int y = 0; y < 6; y++) { // iterate columns, left to right
				System.out.println("Empty Spots: r = " + x + "c = " + y);
				Nought player = _board.getSpotAt(x, y);

				if (player.isEmpty()) {
					continue; // don't check empty slots
				}

				System.out.println("r = " + x + "c = " + y);

				if (x + 3 <= WIDTH) {
					if (

					player.getColor() == _board.getSpotAt(x + 1, y).getColor() && // look RIGHT
							player.getColor() == _board.getSpotAt(x + 2, y).getColor()
							&& player.getColor() == _board.getSpotAt(x + 3, y).getColor()) {
						System.out.println("CHECKING WIDTH for " + player.getX() + player.getY());

						if (_board.getSpotAt(x + 3, y).isEmpty() == false
								&& _board.getSpotAt(x + 2, y).isEmpty() == false
								&& _board.getSpotAt(x + 1, y).isEmpty() == false) {
							_winner = player.getColor();
							System.out.println("CHECKING WIDTH for " + player.getX() + player.getY());
							player.highlight();
							_board.getSpotAt(x + 1, y).highlight();
							_board.getSpotAt(x + 2, y).highlight();
							_board.getSpotAt(x + 3, y).highlight();

							highlightFinal(player, _board.getSpotAt(x + 1, y), _board.getSpotAt(x + 2, y),
									_board.getSpotAt(x + 3, y));

							_gameWon = true;
							return true;

						}
					}

				}

				if (y - 3 >= 0) {
					System.out.println("CHECKING UP");
					System.out.println(player.getColor());
					System.out.println(_board.getSpotAt(x, y - 1).getColor());
					System.out.println(_board.getSpotAt(x, y - 2).getColor());
					System.out.println(_board.getSpotAt(x, y - 3).getColor());

					if (player.getColor() == _board.getSpotAt(x, y - 1).getColor() && // look UP
							player.getColor() == _board.getSpotAt(x, y - 2).getColor()
							&& player.getColor() == _board.getSpotAt(x, y - 3).getColor()) {
						if (_board.getSpotAt(x, y - 3).isEmpty() == false) {
							System.out.println("CHECK UP PASSED");
							_winner = player.getColor();
							highlightFinal(player, _board.getSpotAt(x, y - 1), _board.getSpotAt(x, y - 2),
									_board.getSpotAt(x, y - 3));
							_gameWon = true;

							return true;
						}
					}

					if (x + 3 < WIDTH && player.getColor() == _board.getSpotAt(x + 1, y - 1).getColor() && // look UP &
																											// RIGHT
							player.getColor() == _board.getSpotAt(x + 2, y - 2).getColor()
							&& player.getColor() == _board.getSpotAt(x + 3, y - 3).getColor()) {
						if (_board.getSpotAt(x + 1, y - 1).isEmpty() == false
								&& _board.getSpotAt(x + 2, y - 2).isEmpty() == false
								&& _board.getSpotAt(x + 3, y - 3).isEmpty() == false) {
							_winner = player.getColor();
							highlightFinal(player, _board.getSpotAt(x + 1, y - 1), _board.getSpotAt(x + 2, y - 2),
									_board.getSpotAt(x + 3, y - 3));
							_gameWon = true;
							return true;
						}
					}
				}

				if (x - 3 >= 0 && y - 3 >= 0) {
					if (player.getColor() == _board.getSpotAt(x - 1, y - 1).getColor() && // look UP & LEFT
							player.getColor() == _board.getSpotAt(x - 2, y - 2).getColor()
							&& player.getColor() == _board.getSpotAt(x - 3, y - 3).getColor()) {
						if (_board.getSpotAt(x - 1, y - 1).isEmpty() == false
								&& _board.getSpotAt(x - 2, y - 2).isEmpty() == false
								&& _board.getSpotAt(x - 3, y - 3).isEmpty() == false) {
							_winner = player.getColor();
							highlightFinal(player, _board.getSpotAt(x - 1, y - 1), _board.getSpotAt(x - 2, y - 2),
									_board.getSpotAt(x - 3, y - 3));
							_gameWon = true;
							return true;
						}
					}
				}

			}
		}

		_winner = null;
		return false; // no winner found
	}

	public boolean checkDraw() {
		// UPDATE TO INCLUDE DURING GAME
		int count = 0;

		for (int x = 0; x < 7; x++) {

			for (int y = 0; y < 6; y++) {

				if ((_board.getSpotAt(x, y).getColor() == Color.BLACK && _board.getSpotAt(x, y).isEmpty() == false
						|| _board.getSpotAt(x, y).getColor() == Color.RED)) {
					count++;
				}

//			else if (_board.getSpotAt(x, y).getSpotColor() == Color.BLACK && (_board.getSpotAt(x, y).isEmpty() == false)) {
//				
//				countBlacks++;
//			}

			}

			System.out.println("Total " + count);
//		System.out.println("Whites " + countWhites);

			if (count == 42) {

				_gameDraw = true;
				_gameWon = false;

				return true;

			}

		}
		return false;

	}

}
	