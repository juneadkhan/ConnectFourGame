package connect_four;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

public class NoughtBoard_UI extends JPanel implements NoughtBoard {

	// Set Constants
	private static final int DEFAULT_SCREEN_WIDTH = 500;
	private static final int DEFAULT_SCREEN_HEIGHT = 500;
	private static final Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f, 0.8f, 0.8f);
	private static final Color DEFAULT_BACKGROUND_DARK = new Color(0.5f, 0.5f, 0.5f); //EDITED was 0.5f

	private static final Color DEFAULT_COLOR = Color.BLACK;
	private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.YELLOW;

	// Effectively a matrix of positions for the naughts. 
	// The 2D-Array acts as the board.
	private Nought[][] _board;
	
	//Constructor for NoughtBoard_UI
	public NoughtBoard_UI(int width, int height) {

		if (width < 1 || height < 1 || width > 50 || height > 50) {
			throw new IllegalArgumentException("Illegal spot board geometry");
		}
		
		setLayout(new GridLayout(height, width));
		_board = new Nought[width][height];
		
		Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH/width, DEFAULT_SCREEN_HEIGHT/height);
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				Color backgroundColour = ((x+y)%2 == 0) ? DEFAULT_BACKGROUND_LIGHT : DEFAULT_BACKGROUND_DARK;
				_board[x][y] = new Nought_UI(x, y, backgroundColour, DEFAULT_COLOR, DEFAULT_HIGHLIGHT_COLOR, this);
				((Nought_UI)_board[x][y]).setPreferredSize(preferred_size);
				add(((Nought_UI) _board[x][y]));
			}			
		}
	}

	//Getters
	@Override
	public int getSpotWidth() {
		return _board.length;
	}
	
	@Override
	public int getSpotHeight() {
		return _board[0].length;
	}

	// Lookup method for Spot at position (x,y)
	
	@Override
	public Nought getSpotAt(int x, int y) {
		if (x < 0 || x >= getSpotWidth() || y < 0 || y >= getSpotHeight()) {
			throw new IllegalArgumentException("Illegal spot coordinates");
		}
		
		return _board[x][y];
	}
	
	// Convenience methods for (de)registering spot listeners.
	
	@Override
	public void addSpotListener(Listener spot_listener) {
		for (int x=0; x<getSpotWidth(); x++) {
			for (int y=0; y<getSpotHeight(); y++) {
				_board[x][y].addListener(spot_listener);
			}
		}
	}
	
	@Override
	public void removeSpotListener(Listener spot_listener) {
		for (int x=0; x<getSpotWidth(); x++) {
			for (int y=0; y<getSpotHeight(); y++) {
				_board[x][y].removeListener(spot_listener);
			}
		}
	}

	@Override
	public Iterator<Nought> iterator() {
		return new NoughtBoard_Iterator(this);
	}

}

