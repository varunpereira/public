package classes;
import javax.swing.JLabel;

public class Search {
//	public JLabel[] movieList = createMovieList();
	
	public String getName() {
		return "potato";
	}
	
	public JLabel[] createMovieList() {
		
		// Bellow assigns movies to a JLabel array so movies can be printed into JFrame/JPannel
		JLabel[] movieList = new JLabel[10];
		movieList[0] = new JLabel("Pirates of The Caribbean");
		movieList[1] = new JLabel("Parasite");
		movieList[2] = new JLabel("Pain and Glory");
		movieList[3] = new JLabel("Diane");
		movieList[4] = new JLabel("Hustlers");
		movieList[5] = new JLabel("Apollo 11");
		movieList[6] = new JLabel("IT");
		movieList[7] = new JLabel("Inception");
		movieList[8] = new JLabel("Nobody");
		movieList[9] = new JLabel("Tenet");
		return movieList;
	}
}
