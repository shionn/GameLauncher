package shionn.game.games;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Engine {

	private List<Game> games;
	private List<Proton> protons;

	public Proton proton(String proton) {
		return protons.stream().filter(p -> p.getName().equals(proton)).findAny().orElse(null);
	}

}
