package View;


import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Hashtable;

import javafx.scene.media.AudioClip;



/**
 * Represent the bank of all songs of the games
 *
 */
public class SongController implements Serializable{
	private static String DATA_DIR;
	private Hashtable<String, AudioClip> bank;
	private AudioClip current;
	
	static {
		DATA_DIR = "resources/sounds/";
	}
	
	/**
	 * Basic constructor
	 */
	public SongController() {
		this.current = null;
		this.bank = new Hashtable<>();
		this.loadSongs();
	}
	
	/**
	 * Load all songs from resources/sounds/
	 */
	private void loadSongs() {
		File directory = new File(DATA_DIR);
		File[] files = directory.listFiles();
		
		for (File file:files) {
			
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					if (f.isFile()) {
						//Readme obligatoire sur moodle, on skip
						if (f.getName().equals("README.md")) {
							continue;
						}
						if (f.getName().equals("README")) {
							continue;
						}
						AudioClip song = null;
						
							try {
								song = new AudioClip(f.toURI().toURL().toString());
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						if (song != null) {
							this.bank.put(f.getName(), song);
						}
					}
				}
			} else {
				if (file.getName().equals("README.md") ) {
					continue;
				}
				if (file.getName().equals("README")) {
					continue;
				}
				AudioClip song = null;
				
					try {
						song = new AudioClip(file.toURI().toURL().toString());
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				if (song != null) {
					this.bank.put(file.getName(), song);
				}
			}
		}

		System.err.println("Song bank : ");
		System.err.println(this.bank);
	}
	
	/**
	 * Play a sound
	 * @param songName the name of the soung file
	 */
	public void playSong(String songName) {
		AudioClip m = this.bank.get(songName);
		
		if (m != null) {
			if (current != null) {
				current.stop();
			}
			System.err.println("Playing song : " + songName);
			m.setCycleCount(42);
			m.play();
			this.current = m;
			
		} else {
			System.err.println("Error in playSong: " + songName + " not in dataBase");
		}
	}
	
	/**
	 * Change the volume of the current sound.
	 * @param value	Value between 0 and 1
	 */
	public void setVolume(double value) {
		if (this.current == null)	return;
		this.current.stop();
		this.current.setVolume(value);
		this.current.play();
	}
}
