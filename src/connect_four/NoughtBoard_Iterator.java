package connect_four;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class NoughtBoard_Iterator implements Iterator<Nought> {

	private NoughtBoard _board;
	int _x;
	int _y;
	
	public NoughtBoard_Iterator(NoughtBoard board) {
		_board = board;
		_x = 0;
		_y = 0;
	}

	@Override
	public boolean hasNext() {
		return (_y < _board.getSpotHeight());
	}

	@Override
	public Nought next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Nought s = _board.getSpotAt(_x, _y);
		if (_x < _board.getSpotWidth()-1) {
			_x++;
		} else {
			_x = 0;
			_y++;
		}
		return s;
	}

}
