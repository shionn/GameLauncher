package shionn.game.exceptions;

import lombok.Getter;

@Getter
public class MissingFolderException extends Exception {

	private static final long serialVersionUID = 7471725162778401399L;
	private String folder;

	public MissingFolderException(String conf, String folder) {
		super("Missing folder " + folder + " defined in " + conf);
		this.folder = folder;
	}

}
