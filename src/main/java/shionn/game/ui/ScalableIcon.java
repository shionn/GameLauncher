package shionn.game.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;

public class ScalableIcon implements Icon {

	private BufferedImage image;


	public ScalableIcon(String filename) {
		try {
			this.image = ImageIO.read(new File(filename));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		int width = c.getWidth();
		int height = c.getHeight();
		Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		g.drawImage(scaledImage, x, y, null);
	}

	@Override
	public int getIconWidth() {
		return 0;
	}

	@Override
	public int getIconHeight() {
		return 0;
	}

}
