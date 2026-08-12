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
		List<Game> games = scanInstalledFolder();
		games = scanInstalerFolder(games);
		return games.stream().sorted().toList();
	}

	private List<Game> scanInstalerFolder(List<Game> installeds) {
		List<Game> games = new ArrayList<>(installeds);
		File rootFolder = new File(configuration.instalersFolder());
		if (rootFolder.exists()) {
			for (File letterFolder : rootFolder.listFiles(pathname -> pathname.isDirectory())) {
				for (File gameFolder : letterFolder.listFiles(pathname -> pathname.isDirectory())) {
					games
							.stream()
							.filter(g -> g.getName().equals(gameFolder.getName()))
							.findAny()
							.ifPresentOrElse(g -> {
								g.setArchives(listAbsolutePath(gameFolder, ".tar.gz"));
								g.setInstalers(listAbsolutePath(gameFolder, ".exe"));
							}, () -> games.add(buildGame(gameFolder)));
				}
			}
		}
		return games;
	}

	private Game buildGame(File gameFolder) {
		String letter = gameFolder.getName().substring(0, 1);
		if (letter.matches("[0-9]")) {
			letter = "#";
		}
		return Game
				.builder()
				.letter(letter)
				.name(gameFolder.getName())
				.gameId(retreiveFileName(gameFolder, ".gameid"))
				.store(retreiveFileName(gameFolder, ".store"))
				.platform(retreiveFileName(gameFolder, ".platform"))
				.instalers(listAbsolutePath(gameFolder, ".exe"))
				.archives(listAbsolutePath(gameFolder, ".tar.gz"))
				.instalersImgs(listAbsolutePath(gameFolder, ".jpg"))
				.build();
	}



	private List<Proton> scanProtons() {
		List<Proton> protons = new ArrayList<>();
		File rootFolder = new File(configuration.protonsFolder());
		for (File protonFolder : rootFolder.listFiles(pathname -> pathname.isDirectory())) {
			protons.add(Proton.builder().name(protonFolder.getName()).path(protonFolder.getAbsolutePath()).build());
		}
		return protons;
	}

	private List<Game> scanInstalledFolder() {
		List<Game> games = new ArrayList<Game>();
		File rootFolder = new File(configuration.gamesFolder());
		for (File gameFolder : rootFolder.listFiles(pathname -> pathname.isDirectory() && !pathname.isHidden())) {
			Game game = buildGame(gameFolder);
			game.setInstalledFolder(gameFolder.getAbsolutePath());
			game.loadConfiguration();
			game.setInstalled(true);
			games.add(game);
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

}
