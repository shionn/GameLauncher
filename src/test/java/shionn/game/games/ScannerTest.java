package shionn.game.games;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ScannerTest {

	@Test
	public void testMatchByExtensionsAcceptsPng() throws IOException {
		Path dir = Files.createTempDirectory("scanner-test");
		createImage(dir, "game", ".png");

		boolean matched = Scanner.matchByExtensions(new File(dir.toAbsolutePath().toString() + "/game.png"), ".jpg", ".png");

		assertThat(matched).as(".png reconnu avec jpg+png").isTrue();
	}

	@Test
	public void testMatchByExtensionsAcceptsJpgStill() throws IOException {
		Path dir = Files.createTempDirectory("scanner-test");
		createImage(dir, "game", ".jpg");

		boolean matched = Scanner.matchByExtensions(new File(dir.toAbsolutePath().toString() + "/game.jpg"), ".jpg", ".png");

		assertThat(matched).as(".jpg toujours reconnu").isTrue();
	}

	@Test
	public void testMatchByExtensionsRejectsOtherExtension() throws IOException {
		Path dir = Files.createTempDirectory("scanner-test");
		createImage(dir, "game", ".txt");

		boolean matched = Scanner.matchByExtensions(new File(dir.toAbsolutePath().toString() + "/game.txt"), ".jpg", ".png");

		assertThat(matched).as(".txt non reconnu").isFalse();
	}

	@Test
	public void testMatchByExtensionsWithSingleExtensionPng() throws IOException {
		Path dir = Files.createTempDirectory("scanner-test");
		createImage(dir, "game", ".png");

		boolean matched = Scanner.matchByExtensions(new File(dir.toAbsolutePath().toString() + "/game.png"), ".png");

		assertThat(matched).as(".png reconnu avec png seul").isTrue();
	}

	private void createImage(Path dir, String name, String ext) throws IOException {
		Path file = dir.resolve(name + ext);
		Files.write(file, new byte[0]);
	}

}
