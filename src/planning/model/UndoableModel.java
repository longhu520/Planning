package planning.model;

import javax.swing.event.UndoableEditListener;

public interface UndoableModel {
	public void addUndoableEditListener(UndoableEditListener listener);
	public void removeUndoableEditListener(UndoableEditListener listener);
}
