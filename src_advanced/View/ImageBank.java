package src_advanced.View;

import java.io.File;

import java.util.Hashtable;

import javafx.scene.image.Image;

/**
 * Represent the bank of images of the game
 *
 */
public class ImageBank {
	private static String DATA_DIR;
	private Hashtable<String, Image> bank;
	
	static {
		DATA_DIR = "resources/Images/";
	}
	
	public ImageBank() {
		this.bank = new Hashtable<>();
		loadImages();
	}
	
	private void loadImages() {
		File directory = new File(DATA_DIR);
		File[] files = directory.listFiles();
		
		for (File file:files) {
			
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					if (f.isFile()) {
						this.bank.put(f.getName(), new Image("file:" + f.getName()));
					}
				}
			} else {
				this.bank.put(file.getName(), new Image("file:" + file.getName()));
				
			}
		}
		System.err.println("Image bank : ");
		System.err.println(this.bank);
	}
}