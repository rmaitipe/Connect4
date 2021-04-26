package com.bean;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class GamePieceBlack extends Component{
	
	private BufferedImage bufferedImage;
	
	GamePieceBlack() throws IOException{
		bufferedImage = ImageIO.read(GamePieceBlack.class.getResource("/Black2.jpg"));
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}	

	public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
	    Image img = icon.getImage();  
	    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
	    return new ImageIcon(resizedImage);
	}
}
