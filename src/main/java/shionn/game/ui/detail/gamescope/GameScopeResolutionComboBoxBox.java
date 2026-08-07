package shionn.game.ui.detail.gamescope;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import shionn.game.Configuration;
import shionn.game.games.Game;

public class GameScopeResolutionComboBoxBox extends JComboBox<String> {

	private static final long serialVersionUID = 5586727315315707482L;

	public GameScopeResolutionComboBoxBox(Game game, Supplier<String> getter, Consumer<String> setter) {
		super(buildModel());
		setSelectedItem(getter.get());
		setRenderer((JList<? extends String> list, String value, int index, boolean isSelected,
				boolean cellHasFocus) -> new JLabel(Optional.ofNullable(value).orElse("--")));
		setVisible(game.isGamescopeEnabled());
		addActionListener(e -> setter.accept((String) getSelectedItem()));
		game.getPcs().addPropertyChangeListener(e -> setVisible(game.isGamescopeEnabled()));
	}

	private static DefaultComboBoxModel<String> buildModel() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement(null);
		Arrays.stream(new Configuration().gamescopeResolutions()).forEach(model::addElement);
		return model;
	}

}
