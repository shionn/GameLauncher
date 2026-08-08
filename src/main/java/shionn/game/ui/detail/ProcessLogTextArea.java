package shionn.game.ui.detail;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import shionn.game.games.Game;

public class ProcessLogTextArea extends JTextArea implements PropertyChangeListener {

	private static final long serialVersionUID = 5027249346655556789L;
	private Game game;
	private Thread stdoutThread;

	public ProcessLogTextArea(Game game) {
		this.game = game;
		setVisible(game.getProcess() != null);
		game.getPcs().addPropertyChangeListener(this);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
		setPreferredSize(new Dimension(1000, 400));
		setAutoscrolls(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Process process = game.getProcess();
		setVisible(game.getProcess() != null);
		setEditable(false);
		if (game.getProcess() != null && stdoutThread == null) {
			stdoutThread = new Thread(() -> {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
					String line;
					while ((line = reader.readLine()) != null) {
						System.out.println(line);
						appendToTextArea(line);
					}
					stdoutThread = null;
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			});
			stdoutThread.setDaemon(true);
			stdoutThread.start();
		}
	}

	private void appendToTextArea(String text) {
		SwingUtilities.invokeLater(() -> {
			try {
				getDocument().insertString(0, text + "\n", null);
			} catch (BadLocationException e) {
				throw new IllegalStateException(e);
			}
		});
	}

}
