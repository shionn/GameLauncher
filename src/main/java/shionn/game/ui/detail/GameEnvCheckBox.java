package shionn.game.ui.detail;

import java.util.List;

import javax.swing.JCheckBox;

import shionn.game.games.Game;

public class GameEnvCheckBox extends JCheckBox {

	private static final long serialVersionUID = 4282453507338356023L;

	public GameEnvCheckBox(Game game, String label) {
		super(label);
		setSelected(game.getEnvArgs().contains(label));
		addActionListener(e -> {
			List<String> envArgs = game.getEnvArgs();
			if (isSelected()) {
				envArgs.add(label);
			} else {
				envArgs.remove(label);
			}
			game.setRunArgs(envArgs);
		});

	}

}
