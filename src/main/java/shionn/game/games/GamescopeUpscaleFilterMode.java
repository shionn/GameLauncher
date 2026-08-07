package shionn.game.games;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * --filter option
 */
@Getter
@RequiredArgsConstructor
public enum GamescopeUpscaleFilterMode implements Labeled {
	linear("Linéaire", "linear"),
	nearest("Au plus proche", "nearest"),
	fsr("AMD FSR", ""),
	nis("NVIDIA Image Scaling", ""),
	pixel("Pixel entier", "");

	private final String label;
	private final String value;

}
