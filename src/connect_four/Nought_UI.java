package connect_four;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

public class Nought_UI extends JPanel implements Nought, MouseListener {

	// Coordinates for Nought_UI_Element Object
	private int _x;
	private int _y;

	// Colours for Nought_UI_Element Object
	private Color _baseColour;
	private Color _highlightColour;

	// Boolean Control Variables
	private boolean _isEmpty;
	private boolean _isHighlighted;
	
	
	private NoughtBoard _board;
	private List<Listener> _listeners;


	// Constructor for Nought_UI_Element
	public Nought_UI(int x, int y, Color backgroundColour, Color originalColour,
			Color highlightColour, NoughtBoard board) {

		_x = x;
		_y = y;

		_baseColour = originalColour;
		_highlightColour = highlightColour;

		_isEmpty = true;
		_isHighlighted = false;
		
		_board = board;
		
		_listeners = new ArrayList<Listener>();
		
		addMouseListener(this);


		// Sets background to the input backgroundColour
		setBackground(backgroundColour);

	}

	public Nought_UI(Color backgroundColour, Color originalColour, Color highlightColour, NoughtBoard_UI board,
			int x, int y) {
		
		setBackground(backgroundColour);

		_x = x;
		_y = y;

		_baseColour = originalColour;
		_highlightColour = highlightColour;

		_isEmpty = true;
		_isHighlighted = false;
		
		_board = board;
		
		_listeners = new ArrayList<Listener>();
		
		addMouseListener(this);

		
		// TODO Auto-generated constructor stub
	}

	// Getter for X Component
	public int getX() {

		return _x;
	}

	// Getter for Y Component
	public int getY() {

		return _y;
	}
	
	@Override
	public NoughtBoard getBoard() {
		// TODO Auto-generated method stub
		return _board;

	}
	
	//Getter for Highlight Colour
	public Color getHighlight() {
		return _highlightColour;
	}
	
	public Color getColor() {
		return _baseColour;
	}
	
	//Sets the Colour of a Nought to the input parameter c
	public void setColour(Color c) {	
		if (c == null) throw new IllegalArgumentException("null color");

		_baseColour = c;
		trigger_update();	
	}

	// Boolean Control Variables Getters
	public boolean isHighlighted() {
		return _isHighlighted;
	}

	public boolean isEmpty() {
		return _isEmpty;
	}
	
	//Sets a Nought to being ACTIVE
	public void set() {
		_isEmpty = false;
		trigger_update();
	}

	//Clears a Nought
	public void clear() {
		_isEmpty = true;
		trigger_update();
	}
	
	//Sets the Highlight of a Nought to be ACTIVE
	public void highlight() {
		_isHighlighted = true;
		trigger_update();
	}

	//Sets the Highlight of a Nought to be INACTIVE
	public void unhighlight() {
		_isHighlighted = false;
		trigger_update();
	}
	
	public void setHighlight(Color c) {
		if (c == null) throw new IllegalArgumentException("null color");

		_highlightColour = c;
		trigger_update();
		
	}
	
	//Adds a Listener
	public void addListener(Listener l) {
		_listeners.add(l);
	}
	
	//Removes a Listener
	public void removeListener(Listener l) {
		_listeners.remove(l);
	}

	//mouse Control Methods
	@Override
	public void mouseClicked(MouseEvent e) {
		for (Listener s : _listeners) {
			s.clicked(this);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for (Listener s : _listeners) {
			s.entered(this);
		}		
	}

	@Override
	public void mouseExited(MouseEvent e) {

		for (Listener s : _listeners) {
			s.exited(this);
		}
	}
	
	//Trigger Update Method
	public void trigger_update() {		
		repaint();
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				repaint();
			}
		}).start();

	}
	//Overrides the paintComponent method of JPanel.
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		if (isHighlighted()) {
			g2d.setColor(getHighlight());
			g2d.setStroke(new BasicStroke(4));
			g2d.drawRect(0, 0, getWidth(), getHeight());
		}
		if (!isEmpty()) {
			g2d.setColor(getColor());
			g2d.fillOval(0, 0, this.getWidth()-1, this.getHeight()-1);
		}
	}





}