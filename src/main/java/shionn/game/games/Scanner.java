package shionn.game.games;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shionn.game.Configuration;
import shionn.game.exceptions.MissingFolderException;

public class Scanner {
	private Configuration configuration = new Configuration();

	public Engine buildEngine() throws MissingFolderException {
		return Engine.builder().games(scanGames()).protons(scanProtons()).build();
	}

	private List<Game> scanGames() throws MissingFolderException {
		List<Game> games = scanInstalledFolder();
		games = scanInstalerFolder(games);
		return games.stream().sorted().toList();
	}

	private List<Game> scanInstalerFolder(List<Game> installeds) throws MissingFolderException {
		List<Game> games = new ArrayList<>(installeds);
		File rootFolder = new File(configuration.instalersFolder());
		if (!rootFolder.exists()) {
			throw new MissingFolderException("folder.instalers", configuration.instalersFolder());
		}
		if (rootFolder.exists()) {
			for (File letterFolder : rootFolder
					.listFiles(pathname -> pathname.isDirectory() && !pathname.isHidden()
							&& !pathname.getName().startsWith("@"))) {
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
		String simpleName = gameFolder.getName();
		if (simpleName.startsWith("The ")) {
			simpleName = simpleName.substring(4);
		}
		String letter = simpleName.substring(0, 1);
		if (letter.matches("[0-9]")) {
			letter = "#";
		}
		return Game
				.builder()
				.name(gameFolder.getName())
				.simplifiedName(simpleName)
				.letter(letter)
				.gameId(retreiveFileName(gameFolder, ".gameid"))
				.store(retreiveFileName(gameFolder, ".store"))
				.platform(retreiveFileName(gameFolder, ".platform"))
				.instalers(listAbsolutePath(gameFolder, ".exe"))
				.archives(listAbsolutePath(gameFolder, ".tar.gz"))
				.instalersImgs(listAbsolutePath(gameFolder, ".jpg", ".png"))
				.build();
	}



	private List<Proton> scanProtons() throws MissingFolderException {
		List<Proton> protons = new ArrayList<>();
		for (String conf : configuration.protonsFolder()) {
			File rootFolder = new File(conf);
			if (!rootFolder.exists()) {
				throw new MissingFolderException("folder.proton", conf);
			}
			for (File protonFolder : rootFolder.listFiles(pathname -> pathname.isDirectory())) {
				protons.add(Proton.builder().name(protonFolder.getName()).path(protonFolder.getAbsolutePath()).build());
			}
		}
		return protons;
	}

	private List<Game> scanInstalledFolder() throws MissingFolderException {
		List<Game> games = new ArrayList<Game>();
		File rootFolder = new File(configuration.gamesFolder());
		if (!rootFolder.exists()) {
			throw new MissingFolderException("folder.games", configuration.gamesFolder());
		}
		for (File gameFolder : rootFolder
				.listFiles(pathname -> pathname.isDirectory() && !pathname.isHidden())) {
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

	private List<String> listAbsolutePath(File folder, String... extenssions) {
		return Arrays.stream(folder.listFiles(pathname -> Scanner.matchByExtensions(pathname, extenssions)))
				.map(f -> f.getAbsolutePath())
				.toList();
	}

	public static boolean matchByExtensions(File pathname, String... extenssions) {
		return Arrays.stream(extenssions).anyMatch(e -> pathname.getName().endsWith(e));
	}

}
