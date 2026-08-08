package shionn.game.ui.generic;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JCheckBox;

import shionn.game.games.Game;

public class SimpleCheckBox extends JCheckBox {

	private static final long serialVersionUID = 2546322693344931826L;


	public SimpleCheckBox(String label, Game game, Supplier<Boolean> getter, Consumer<Boolean> setter) {
		this(label, game, getter, setter, game::isInstalled);
	}

	public SimpleCheckBox(String label, Game game, Supplier<Boolean> getter, Consumer<Boolean> setter,
			BooleanSupplier visible) {
		super(label);
		setSelected(getter.get());
		setVisible(visible.getAsBoolean());
		addActionListener(e -> setter.accept(isSelected()));
		game.getPcs().addPropertyChangeListener(evt -> setVisible(visible.getAsBoolean()));
	}

}
