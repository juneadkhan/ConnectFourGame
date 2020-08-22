package connect_four;

/*
 * SpotListener
 * 
 * Listener interface supported by Spot to report click, enter, and exit events.
 * 
 */

public interface Listener {

	void clicked(Nought spot);
	void entered(Nought spot);
	void exited(Nought spot);
}
