package albumList;

public class Album {
	String title;
	String artist;
	String genre;
	int id;
	public Album(String title, String artist, String genre, int id) {
		super();
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.id = id;
	}
	@Override
	public String toString() {
		return "[title=" + title + ", artist=" + artist + ", genre=" + genre + ", id=" + id + "] \n";
	}
	
	
}
