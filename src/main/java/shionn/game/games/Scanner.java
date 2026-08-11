package shionn.game.games;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shionn.game.Configuration;

public class Scanner {
	private Configuration configuration = new Configuration();

	public Engine buildEngine() {
		return Engine.builder().games(scanGames()).protons(scanProtons()).build();
	}

	private List<Game> scanGames() {
		List<Game> games = new ArrayList<>();
		games.addAll(scanInstalFolders());
		games = checkInstalled(games);
		return games.stream().sorted().toList();
	}

	private List<Game> scanInstalFolders() {
		List<Game> games = new ArrayList<>();
		File rootFolder = new File(configuration.instalersFolder());
		for (File letterFolder : rootFolder.listFiles(pathname -> pathname.isDirectory())) {
			for (File gameFolder : letterFolder.listFiles(pathname ->pathname.isDirectory())) {
				games
						.add(Game
								.builder()
								.letter(letterFolder.getName())
								.name(gameFolder.getName())
								.gameId(retreiveFileName(gameFolder, ".gameid"))
								.store(retreiveFileName(gameFolder, ".store"))
								.platform(retreiveFileName(gameFolder, ".platform"))
								.instalers(listAbsolutePath(gameFolder, ".exe"))
								.archives(listAbsolutePath(gameFolder, ".tar.gz"))
								.instalersImgs(listAbsolutePath(gameFolder, ".jpg"))
								.build());
			}
		}

		return games;
	}


	private String retreiveFileName(File gameFolder, String extenssion) {
		return Arrays
				.stream(gameFolder.listFiles(p -> p.getName().endsWith(extenssion)))
				.map(p -> p.getName().replace(extenssion, ""))
				.findAny()
				.orElse(null);
	}

	private List<String> listAbsolutePath(File folder, String extenssion) {
		return Arrays
				.stream(folder.listFiles(pathname -> pathname.getName().endsWith(extenssion)))
				.map(f -> f.getAbsolutePath())
				.toList();
	}

	private List<Proton> scanProtons() {
		List<Proton> protons = new ArrayList<>();
		File rootFolder = new File(configuration.protonsFolder());
		for (File protonFolder : rootFolder.listFiles(pathname -> pathname.isDirectory())) {
			protons.add(Proton.builder().name(protonFolder.getName()).path(protonFolder.getAbsolutePath()).build());
		}
		return protons;
	}

	private List<Game> checkInstalled(List<Game> games) {
		File rootFolder = new File(configuration.gamesFolder());
		for (File gameFolder : rootFolder.listFiles(pathname -> pathname.isDirectory())) {
			games.stream().filter(g -> g.getName().equals(gameFolder.getName())).findAny().ifPresent(game -> {
				game.setInstalledFolder(gameFolder.getAbsolutePath());
				game.loadConfiguration();
				game.setInstalled(true);
			});
		}
		return games;
	}

}
