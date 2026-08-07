package shionn.game.ui.detail.gamescope;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import shionn.game.games.Game;
import shionn.game.games.GameScopeWindowType;

public class GameScopeWindowComboBoxBox extends JComboBox<GameScopeWindowType> {
	private static final long serialVersionUID = 923379936578443414L;

	public GameScopeWindowComboBoxBox(Game game) {
		super(GameScopeWindowType.values());
		setRenderer((JList<? extends GameScopeWindowType> list, GameScopeWindowType value, int index,
				boolean isSelected, boolean cellHasFocus) -> new JLabel(value == null ? "--" : value.getLabel()));
//		setSelectedItem(game.get);

	}

}
