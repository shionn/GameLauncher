package shionn.game.ui.detail.gamescope;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import shionn.game.games.Game;
import shionn.game.games.Labeled;

public class GamescopeEnumComboBox<E extends Enum<?> & Labeled> extends JComboBox<E> {

	private static final long serialVersionUID = 4503474955600953196L;

	public GamescopeEnumComboBox(Game game, E[] values, Supplier<E> getter, Consumer<E> setter) {
		super();
		setModel(buildModel(values));
		setSelectedItem(getter.get());
		setRenderer((JList<? extends E> list, E value, int index, boolean isSelected,
				boolean cellHasFocus) -> new JLabel(Optional.ofNullable(value).map(e -> e.getLabel()).orElse("--")));
		setVisible(game.isGamescopeEnabled());
		addActionListener(e -> setter.accept((E) getSelectedItem()));
		game.getPcs().addPropertyChangeListener(e -> setVisible(game.isGamescopeEnabled()));
	}

	private DefaultComboBoxModel<E> buildModel(E[] values) {
		DefaultComboBoxModel<E> model = new DefaultComboBoxModel<>();
		model.addElement(null);
		Arrays.stream(values).forEach(model::addElement);
		return model;
	}

}
