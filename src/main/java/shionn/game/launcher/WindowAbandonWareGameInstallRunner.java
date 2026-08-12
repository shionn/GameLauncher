package shionn.game.launcher;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import shionn.game.Configuration;
import shionn.game.games.Engine;
import shionn.game.games.Game;

public class WindowAbandonWareGameInstallRunner implements Runnable {
	private Configuration configuration = new Configuration();
	private Game game;
	private Engine engine;

	public WindowAbandonWareGameInstallRunner(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			int exitCode = createPrefix();
			if (exitCode == 0) {
				game.setInstalledFolder(configuration.gamesFolder() + game.getName());
				new PostInstalCommonOperation().doPostInstall(game);
				game.setInstalled(true);
			} else {
				throw new IllegalStateException("Process return " + exitCode);
			}
			game.setProcess(null);

		} catch (InterruptedException | IOException | RuntimeException e) {
			deleteIncompleteInstal();
			throw new IllegalStateException(e);
		}
	}


	private int createPrefix() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new GameProcessBuilder(engine, game)
				.wineprefix()
				.protonPath()
				.umuRun()
				.winetricks()
				.arg("list")
				.build();
		Process process = processBuilder.start();
		game.setProcess(process);
		int exitCode = process.waitFor();
		process.destroy();
		return exitCode;
	}

	private void deleteIncompleteInstal() {
		game.setProcess(null);
		try {
			FileUtils.deleteDirectory(new File(configuration.gamesFolder() + game.getName()));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

}