package src_advanced.View;



import java.applet.Applet;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Hashtable;




public class SongController {
	private static String DATA_DIR;
	private Hashtable<String, java.applet.AudioClip> bank;
	
	static {
		DATA_DIR = "resources/Songs/";
	}
	
	public SongController() {
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
						java.applet.AudioClip song = null;
						try {
							song = Applet.newAudioClip(f.toURI().toURL());
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
				java.applet.AudioClip song = null;
				try {
					song = Applet.newAudioClip(file.toURI().toURL());
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
		java.applet.AudioClip m = this.bank.get(songName);
		
		if (m != null) {
			System.err.println("Playing song : " + songName);
			m.play();
		} else {
			System.err.println("Error in playSong: " + songName + " not in dataBase");
		}
	}
}
