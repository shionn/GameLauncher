package shionn.game.games;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GameTest {

	private Path tempDir;

	private Game buildGame(String store, String platform, List<String> archives, String proton, boolean installed, String runfile) {
		return Game.builder()
				.store(store)
				.platform(platform)
				.installedFolder(tempDir.toAbsolutePath().toString())
				.archives(archives)
				.proton(proton)
				.installed(installed)
				.runfile(runfile)
				.build();
	}

	private Path newTempDir() {
		try {
			return Files.createTempDirectory("game-test");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testRecompGameIsInstallableWhenArchivePresent() {
		tempDir = newTempDir();
		Game game = buildGame("recomp-game", "linux", Arrays.asList("/tmp/game.tar.gz"), null, false, null);

		// WHEN
		boolean installable = game.isInstallable();

		// THEN installable
		assertThat(installable).as("archive présente + linux").isTrue();
	}

	@Test
	public void testRecompGameIsNotInstallableWhenNoArchive() {
		tempDir = newTempDir();
		Game game = buildGame("recomp-game", "linux", List.of(), null, false, null);

		// WHEN
		boolean installable = game.isInstallable();

		// THEN non installable
		assertThat(installable).as("aucune archive").isFalse();
	}

	@Test
	public void testRecompGameIsRunnableWhenInstalledAndRunfile() {
		tempDir = newTempDir();
		Game game = buildGame("recomp-game", "linux", Arrays.asList("/tmp/game.tar.gz"), null, true, "/tmp/game");

		// WHEN
		boolean runnable = game.isRunnable();

		// THEN runnable
		assertThat(runnable).as("installé + runfile").isTrue();
	}

	@Test
	public void testRecompGameIsNotRunnableWhenNotInstalled() {
		tempDir = newTempDir();
		Game game = buildGame("recomp-game", "linux", Arrays.asList("/tmp/game.tar.gz"), null, false, null);

		// WHEN
		boolean runnable = game.isRunnable();

		// THEN non runnable
		assertThat(runnable).as("non installé").isFalse();
	}

	@Test
	public void testRecompGameIsNotInstallableOnWindowsPlatform() {
		tempDir = newTempDir();
		Game game = buildGame("recomp-game", "windows", Arrays.asList("/tmp/game.tar.gz"), null, false, null);

		// WHEN
		boolean installable = game.isInstallable();

		// THEN non installable : recomp-game exige linux
		assertThat(installable).as("recomp-game exige linux").isFalse();
	}

	// --- Régression stores existants ---

	@Test
	public void testGogIsInstallableWhenProtonAndInstalerPresent() {
		tempDir = newTempDir();
		Game game = buildGame("gog", "windows", List.of(), "proton8", false, null);
		game.setInstalers(Arrays.asList("/tmp/installer.exe"));

		assertThat(game.isInstallable()).as("gog + proton + instaler").isTrue();
	}

	@Test
	public void testGogIsNotInstallableWithoutProton() {
		tempDir = newTempDir();
		Game game = buildGame("gog", "windows", List.of(), null, false, null);
		game.setInstalers(Arrays.asList("/tmp/installer.exe"));

		assertThat(game.isInstallable()).as("gog sans proton").isFalse();
	}

	@Test
	public void testSteamIsInstallableOnWindows() {
		tempDir = newTempDir();
		Game game = buildGame("steam", "windows", Arrays.asList("/tmp/game.tar.gz"), "proton8", false, null);

		assertThat(game.isInstallable()).as("steam windows + proton + archive").isTrue();
	}

	@Test
	public void testSteamIsNotInstallableOnLinux() {
		tempDir = newTempDir();
		Game game = buildGame("steam", "linux", Arrays.asList("/tmp/game.tar.gz"), "proton8", false, null);

		assertThat(game.isInstallable()).as("steam linux (ne pas utiliser proton)").isFalse();
	}

	@Test
	public void testAbandonwareIsInstallableWhenProtonPresent() {
		tempDir = newTempDir();
		Game game = buildGame("abandonware", "windows", List.of(), "proton8", false, null);

		assertThat(game.isInstallable()).as("abandonware + proton").isTrue();
	}

	@Test
	public void testUnknownStoreIsNotInstallable() {
		tempDir = newTempDir();
		Game game = buildGame("unknown-store", "windows", List.of(), "proton8", false, null);

		assertThat(game.isInstallable()).as("store inconnu").isFalse();
	}

	@Test
	public void testSetGamescopeMangoappPersistsToConfiguration() {
		tempDir = newTempDir();
		Game game = buildGame("recomp-game", "linux", Arrays.asList("/tmp/game.tar.gz"), null, true, "/tmp/game");

		// WHEN on active l'option mangoapp sur un jeu installé
		game.setGamescopeMangoapp(true);

		Path configFile = tempDir.resolve("configuration.properties");
		assertThat(configFile).as("fichier de config créé").exists();

		Game reloaded = Game.builder()
				.store(game.getStore())
				.platform(game.getPlatform())
				.installedFolder(tempDir.toAbsolutePath().toString())
				.archives(game.getArchives())
				.proton(game.getProton())
				.runfile(game.getRunfile())
				.installed(true)
				.build();
		reloaded.loadConfiguration();

		String content;
		try {
			content = new String(Files.readAllBytes(configFile));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		assertThat(content).as("gamescopeMangoapp écrit à true").contains("gamescopeMangoapp=true");
		assertThat(reloaded.isGamescopeMangoapp()).as("mangoapp relue depuis la config").isTrue();
	}

}
