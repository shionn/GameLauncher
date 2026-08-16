package shionn.game.launcher;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import shionn.game.Configuration;
import shionn.game.games.Engine;
import shionn.game.games.Game;

public class WindowArchivedGameInstallRunner implements Runnable {
	private Configuration configuration = new Configuration();
	private Game game;
	private Engine engine;

	public WindowArchivedGameInstallRunner(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			int exitCode = createPrefix();
			exitCode += extractArchive();

			if (exitCode == 0) {
				game.setInstalledFolder(configuration.gamesFolder() + game.getName());
				game.setRunfile(new TryToFindRunFile().searchExe(game));
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

	private String makeGameFolder() {
		String pathname = configuration.gamesFolder() + game.getName() + "/drive_c/" + getStoreFolder();
		new File(pathname).mkdirs();
		return pathname;
	}

	private String getStoreFolder() {
		return switch (game.getStore()) {
		case "steam" -> "Steam Games";
		case "egs" -> "Epic Games";
		default -> throw new IllegalArgumentException("Unknow store " + game.getPlatform());
		};
	}

	private int extractArchive() throws IOException, InterruptedException {
		String pathname = makeGameFolder();
		ProcessBuilder processBuilder = new GameProcessBuilder(engine, game)
				.arg("tar")
				.arg("-xvzf")
				.arg(game.getArchives().get(0))
				.arg("-C")
				.arg(pathname)
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