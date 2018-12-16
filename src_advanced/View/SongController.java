package src_advanced.View;


import java.io.File;
import java.net.MalformedURLException;
import java.util.Hashtable;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;




public class SongController {
	private static String DATA_DIR;
	private Hashtable<String, AudioClip> bank;
	private AudioClip current;
	
	static {
		DATA_DIR = "resources/Songs/";
	}
	
	public SongController() {
		this.current = null;
		this.bank = new Hashtable<>();
		this.loadSongs();
	}
	
	private void loadSongs() {
		File directory = new File(DATA_DIR);
		File[] files = directory.listFiles();
		
		for (File file:files) {
			
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					if (f.isFile()) {
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
	
	public void setVolume(double value) {
		if (this.current == null)	return;
		this.current.stop();
		this.current.setVolume(value);
		this.current.play();
	}
}