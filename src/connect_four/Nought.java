package connect_four;

import java.awt.Color;

//A Nought represents an individual piece
public interface Nought {

	//Returns X/Y Coordinates
	int getX();
	int getY();
	
	public boolean isEmpty();
	NoughtBoard getBoard();

	void trigger_update();		

	
	void addListener(Listener l);
	void removeListener(Listener l);
	
	default void toggleHighlight() {
		if (isHighlighted()) {
			unhighlight();
		} else {
			highlight();
		}
	}
	
	default void toggle() {
		if (isEmpty()) {
			set();
		} else {
			clear();
		}
	}

	default String getCoordString() {
		return "(" + getX() + ", " + getY() + ")";
	}
	void set();
	void clear();
	
	void setColour(Color colour);
	Color getColor();

	void highlight();
	void unhighlight();
	
	void setBackground(Color c);
	Color getBackground();
	
	void setHighlight(Color c);
	Color getHighlight();
	
	public boolean isHighlighted();


	
	
}