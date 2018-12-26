package View;

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
	private static Hashtable<String, Image> bank;
	
	static {
		DATA_DIR = "/resources/images/";
		bank = new Hashtable<>();
		loadImagesMoodleVersion();
	}

	public static String getRessourcePathByName(String name) {
		System.err.println((DATA_DIR + name).toString());
		return ImageBank.class.getResource(DATA_DIR + name).toString();

	}
	
	/**
	 * Quickfix for moodle
	 */
	private static void loadImagesMoodleVersion() {
		
		String [][]imageName = {
				{
					"avast.png",
					"avast/"
				},
				{
					"avast.jpg",
					"avast/"
				},
				{
					"avastStarship_00.png",
					"avast/"
				},
				{
					"default_background.jpg",
					"background/"
				},
				{
					"cursor_00_00.png",
					"cursors/"
				},
				{
					"cursor_00_01.png",
					"cursors/"
				},
				{
					"cursor_00_02.png",
					"cursors/"
				},
				{
					"cursor_00_03.png",
					"cursors/"
				},
				{
					"cursor_00_04.png",
					"cursors/"
				},
				{
					"cursor_01_00.png",
					"cursors/"
				},
				{
					"cursor_01_01.png",
					"cursors/"
				},
				{
					"cursor_01_02.png",
					"cursors/"
				},
				{
					"cursor_01_03.png",
					"cursors/"
				},
				{
					"cursor_01_04.png",
					"cursors/"
				},
				{
					"cursor_02_00.png",
					"cursors/"
				},
				{
					"cursor_02_01.png",
					"cursors/"
				},
				{
					"cursor_02_02.png",
					"cursors/"
				},
				{
					"cursor_02_03.png",
					"cursors/"
				},
				{
					"cursor_02_04.png",
					"cursors/"
				},
				{
					"computer_00.png",
					"icons/"
				},
				{
					"file_00.png",
					"icons/"
				},
				{
					"folder_00.png",
					"icons/"
				},
				{
					"zoom_00.png",
					"icons/"
				},
				{
					"mainMenu.png",
					"Menus/"
				},
				{
					"mainMenuPlayersChoice.png",
					"Menus/"
				},
				{
					"pauseMenu.png",
					"Menus/"
				},
				{
					"taskBar.png",
					"Menus/"
				}
		};
		
		for (String[] s : imageName) {
			bank.put(
					s[0], 
					new Image(getRessourcePathByName(s[1] + s[0])));
		}

	}
	
	/**
	 * Load all images in resources/images
	 */
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
	
	/**
	 * Return an image of the bank.
	 * @param name	The image filename
	 * @return	the reference of the image in the bank.
	 */
	public Image getImage(String name) {
		return this.bank.get(name);
	}
}