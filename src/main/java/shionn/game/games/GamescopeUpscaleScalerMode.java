package shionn.game.games;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GamescopeUpscaleScalerMode implements Labeled {
	auto("Auto"),
	integer("Entière"),
	fit("Adapter (fit)"),
	fill("Remplir (fill)"),
	stretch("Étirer");
	private final String label;
}
