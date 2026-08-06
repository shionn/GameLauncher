package shionn.game.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lombok.RequiredArgsConstructor;
import shionn.game.games.Engine;
import shionn.game.games.Scanner;
import shionn.game.ui.list.GameListPanel;

@RequiredArgsConstructor
public class Window {
	private final Engine engine;

	public static void main(String[] args)
			throws ReflectiveOperationException, UnsupportedLookAndFeelException {
		Engine engine = new Scanner().buildEngine();
		new Window(engine).build();
	}

	private void build() throws ReflectiveOperationException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
		buildFrame();
	}

	private void buildFrame() {
		JFrame frame = new JFrame("Shionn Game Launcher");
		frame.setLayout(new OverlayLayout(frame));
		frame.setContentPane(buildLayeredPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private JLayeredPane buildLayeredPanel() {
		JLayeredPane panel = new JLayeredPane();
		panel.setLayout(new OverlayLayout(panel));
		panel.add(buildBackgrounPanel(), JLayeredPane.DEFAULT_LAYER);
		return panel;
	}

	private JPanel buildBackgrounPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(buildScrollGamePanel(), BorderLayout.CENTER);
		return panel;
	}

	private JScrollPane buildScrollGamePanel() {
		JScrollPane panel = new JScrollPane(buildGameListPanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.getVerticalScrollBar().setUnitIncrement(30);
		return panel;
	}

	private JPanel buildGameListPanel() {
		return new GameListPanel(engine);
	}

}
