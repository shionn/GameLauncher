package shionn.game.ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import shionn.game.games.Engine;
import shionn.game.games.Game;
import shionn.game.games.Proton;

public class GameProtonComboBox extends JComboBox<Proton> {

	private static final long serialVersionUID = 2135901401464469556L;

	public GameProtonComboBox(Engine engine, Game game) {
		super(engine.getProtons().toArray(s -> new Proton[s]));
		setRenderer((JList<? extends Proton> list, Proton value, int index, boolean isSelected,
				boolean cellHasFocus) -> new JLabel(value == null ? "--" : value.getName()));
		setSelectedItem(engine.proton(game.getProton()));
		addActionListener(e ->			game.setProton(((Proton) getSelectedItem()).getName()));
	}
}
