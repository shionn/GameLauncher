package shionn.game.ui.generic;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Predicate;

import javax.swing.JLabel;

import shionn.game.games.Game;

public class ConditionVisibleLabel extends JLabel implements PropertyChangeListener {

	private static final long serialVersionUID = -6634051240325546825L;
	private Game game;
	private Predicate<Game> condition;

	public ConditionVisibleLabel(String text, Game game, Predicate<Game> condition) {
		super(text);
		this.game = game;
		this.condition = condition;
		setVisible(condition.test(game));
		game.getPcs().addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setVisible(condition.test(game));
		revalidate();
	}

}
