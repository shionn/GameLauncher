package shionn.game.ui.detail;

import java.util.List;

import javax.swing.JCheckBox;

import shionn.game.games.Game;

public class GameArgCheckBox extends JCheckBox {

	private static final long serialVersionUID = 4282453507338356023L;

	public GameArgCheckBox(Game game, String label) {
		super(label);
		setSelected(game.getRunArgs().contains(label));
		addActionListener(e -> {
			List<String> runArgs = game.getRunArgs();
			if (isSelected()) {
				runArgs.add(label);
			} else {
				runArgs.remove(label);
			}
			game.setRunArgs(runArgs);
		});

	}

}
