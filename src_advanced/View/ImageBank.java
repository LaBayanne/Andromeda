package src_advanced.View;

import java.io.File;
import java.net.MalformedURLException;
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
		DATA_DIR = "resources/images/";
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
						Image im = null;
						try {
							im = new Image(f.toURI().toURL().toString());
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						if (im.isError()) {
							System.err.println("Image error !");
							System.exit(1);
						}
						this.bank.put(f.getName(), im);
					}
				}
			} else {
				Image im = null;
				try {
					im = new Image(file.toURI().toURL().toString());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				if (im.isError()) {
					System.exit(1);
				}
				this.bank.put(file.getName(), im);
				
			}
		}
		System.err.println("Image bank : ");
		System.err.println(this.bank);
	}
	
	public Image getImage(String name) {
		return this.bank.get(name);
	}
}