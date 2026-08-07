package shionn.game.ui.generic;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JCheckBox;

public class SimpleCheckBox extends JCheckBox {

	private static final long serialVersionUID = 2546322693344931826L;

	public SimpleCheckBox(String label, Supplier<Boolean> getter, Consumer<Boolean> setter) {
		super(label);
		setSelected(getter.get());
		addActionListener(e -> setter.accept(isSelected()));
	}

}
