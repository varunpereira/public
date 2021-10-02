package Main;

import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Color;

public class Program implements ActionListener {

	private String[] review = new String[20];
	private String[] userName = new String[10];
	private String[] userPassword = new String[10];
	private String[] movieListString = new String[20];
	private String[] movieSubmissions = new String[10];
	private String[] criticRequests = new String[10];
	private String userNameRequest;
	private String userPasswordRequest;
	private String newMovie;
	private String currentUser;
	private String removeMovie;
	private String currentMovie;
	private String submitMovie;
	private JFrame frame = new JFrame();
	private JFrame parent = new JFrame();
	private JPanel panel = new JPanel();
	private JButton searchButton = new JButton("Search");
	private JButton reviewButton = new JButton("Review");
	private JButton reviewThisMovieButton = new JButton("Review");
	private JButton signInButton = new JButton("Login to Account");
	private JButton backButton = new JButton("Previous Page");
	private JButton backButton2 = new JButton("Previous Page");
	private JButton backButton3 = new JButton("Previous Page");
	private JButton enterButton = new JButton("Enter");
	private JButton homeButton = new JButton("Home Page");
	private JButton leaveReviewButton = new JButton("Leave Review");
	private JButton showReviewsButton = new JButton("Display reviews");
	private JButton loginButton = new JButton("Login");
	private JButton registerButton = new JButton("Register");
	private JButton registerCompleteButton = new JButton("Enter");
	private JButton requestCriticLevelButton = new JButton("Request to become a critic");
	private JButton acceptCriticButton = new JButton("Authorise critic");
	private JButton declineCriticButton = new JButton("Decline");
	private JButton acceptMovieButton = new JButton("Accept movie");
	private JButton declineMovieButton = new JButton("Decline");
	private JButton adminMenuButton = new JButton("Admin Menu");
	private JButton addMoviesButton = new JButton("Add a movie");
	private JButton removeMoviesButton = new JButton("Remove a movie");
	private JButton reviewCriticRequestsButton = new JButton("Review critic requests");
	private JButton reviewMovieRequestsButton = new JButton("Review movie requests");
	private JButton logoutButton = new JButton("Logout");
	private JButton submitMovieButton = new JButton("Submit a movie");
	private JLabel titleLabel = new JLabel("BOOTLEG IMDB");
	private JLabel warningLabel = new JLabel("You may have to resize the program window after each stage");
	private JLabel warningLabel2 = new JLabel("Especially this page");
	private JLabel registerPageDetails = new JLabel(
			"Please enter a user name, password and security level (0 User, 1 PCO, 2 Critic, 3 Admin)");
	private JLabel userNameHereText = new JLabel("UserName: ");
	private JLabel passwordHereText = new JLabel("Password: ");
	private JLabel noCriticRequestsText = new JLabel("No current critic requests");
	private JLabel currentUserSecurityLevelZero = new JLabel("User Level 0");
	private JLabel currentUserSecurityLevelOne = new JLabel("PCO Level 1");
	private JLabel currentUserSecurityLevelTwo = new JLabel("Critic Level 2");
	private JLabel currentUserSecurityLevelThree = new JLabel("Admin Level 3");
	private JLabel noMovieSubmissionsLabel = new JLabel("No active movie submissions");
	private JLabel spacing = new JLabel(" ");
	private JLabel notLoggedInLabel = new JLabel("Not logged in");
	private JTextField searchField = new JTextField("Search: ");
	private JTextField reviewField = new JTextField("New Review: ");
	private JTextField userNameField = new JTextField("");
	private JTextField userPasswordField = new JTextField("");
	private JTextField userNameRegisterField = new JTextField("");
	private JTextField userPasswordRegisterField = new JTextField("");
	private JTextField securityLevelField = new JTextField("");
	private int movieSubmissionsCount = 0;
	private int reviewCount = 10;
	private int movieCount = 10;
	private int loopCount = 0;
	private int movSubmissionID;
	private int criticRequestCount = 0;
	private int securityLevel;
	private int userCount = 0;
	private boolean loggedIn = false;
	private boolean fullAccess = false;
	private boolean criticRequest = false;
	private boolean movieRequestsWaitingApproval = false;
	private JLabel movieListJLabel;

	public void run() {
		// Bellow assigns movies to a string array so movies can be searched
		this.movieListString[0] = "Pirates of The Caribbean";
		this.movieListString[1] = "Parasite";
		this.movieListString[2] = "Pain and Glory";
		this.movieListString[3] = "Diane";
		this.movieListString[4] = "Hustlers";
		this.movieListString[5] = "Apollo 11";
		this.movieListString[6] = "IT";
		this.movieListString[7] = "Inception";
		this.movieListString[8] = "Nobody";
		this.movieListString[9] = "Tenet";

		// bellow assigns reviews to movies
		this.review[0] = "Pirates of The Caribbean is awesome.";
		this.review[1] = "Diane is my favourite movie.";
		this.review[2] = "Nobody was the worst movie ever!";
		this.review[3] = "IT was pretty scary";
		this.review[4] = "Pain and glory hurts";
		this.review[5] = "Tenet is too long 10/10";
		this.review[6] = "Parasite isnt in english I dont understand";
		this.review[7] = "Apollo 11 is very realistic";
		this.review[8] = "Apollo 11 isn't realistic";
		this.review[9] = "Nobody is a great movie, similar to John Wick";

		loadGui();

		// Bellow adds buttons to home page depending on user security level 0,1,2,3
		// level 0 = logged out / user
		// level 1 = PCO
		// level 2 = Critic
		// level 3 = Admin

		this.panel.add(this.searchButton);

		if (this.loggedIn == true) {

			if (this.securityLevel == 0 || this.securityLevel == 1) {
				this.panel.add(this.requestCriticLevelButton);

			}

			if (this.securityLevel == 1) {
				this.panel.add(this.submitMovieButton);
			}
		}

		this.panel.add(this.reviewButton);

		if (this.loggedIn == false) {
			this.panel.add(this.signInButton);
		}

		if (this.loggedIn == true) {
			if (this.fullAccess == true) {
				this.panel.add(this.adminMenuButton);
			}

			this.panel.add(this.logoutButton);

			if (this.securityLevel == 0) {
				this.panel.add(this.currentUserSecurityLevelZero);
			}
			if (this.securityLevel == 1) {
				this.panel.add(this.currentUserSecurityLevelOne);
			}
			if (this.securityLevel == 2) {
				this.panel.add(this.currentUserSecurityLevelTwo);
			}
			if (this.securityLevel == 3) {
				this.panel.add(this.currentUserSecurityLevelThree);
			}
		}

		if (this.loggedIn == false) {
			this.panel.add(this.notLoggedInLabel);
		}

		// Bellow assigns all buttons to 'ActionPerformed' class, which tailors methods
		// to each button
		// This ensures buttons can be clicked and an action is performed
		this.searchButton.addActionListener(this);
		this.reviewButton.addActionListener(this);
		this.signInButton.addActionListener(this);
		this.reviewThisMovieButton.addActionListener(this);
		this.leaveReviewButton.addActionListener(this);
		this.backButton.addActionListener(this);
		this.backButton2.addActionListener(this);
		this.enterButton.addActionListener(this);
		this.showReviewsButton.addActionListener(this);
		this.loginButton.addActionListener(this);
		this.registerButton.addActionListener(this);
		this.registerCompleteButton.addActionListener(this);
		this.backButton3.addActionListener(this);
		this.homeButton.addActionListener(this);
		this.requestCriticLevelButton.addActionListener(this);
		this.adminMenuButton.addActionListener(this);
		this.addMoviesButton.addActionListener(this);
		this.removeMoviesButton.addActionListener(this);
		this.reviewCriticRequestsButton.addActionListener(this);
		this.acceptCriticButton.addActionListener(this);
		this.declineCriticButton.addActionListener(this);
		this.logoutButton.addActionListener(this);
		this.submitMovieButton.addActionListener(this);
		this.reviewMovieRequestsButton.addActionListener(this);
		this.acceptMovieButton.addActionListener(this);
		this.declineMovieButton.addActionListener(this);

	}

	// Bellow transitions user to the logout page
	// Resets graphical interface so no more information is displayed
	public void logoutMenu() {
		loadGui();
		this.panel.add(this.userNameHereText);
		this.panel.add(this.userNameField);
		this.panel.add(this.passwordHereText);
		this.panel.add(this.userPasswordField);
		this.panel.add(this.spacing);
		this.panel.add(this.loginButton);
		this.panel.add(this.registerButton);
		this.panel.add(this.homeButton);
	}

	public void loginRequest() {
		this.userNameRequest = ((JTextField) this.userNameField).getText();
		this.userPasswordRequest = ((JTextField) this.userPasswordField).getText();
		int loopCount = 0;

		for (int i = 0; i <= this.userCount; i++) {

			if (this.userNameRequest.equals(this.userName[i])
					&& this.userPasswordRequest.equals(this.userPassword[i])) {
				JOptionPane.showMessageDialog(this.parent, "Login Successful");
				loopCount++;
				this.loggedIn = true;
				this.currentUser = this.userNameRequest;
				run();
			}

			if (i == this.userCount && loopCount == 0) {
				JOptionPane.showMessageDialog(this.parent, "Login failed");
			}
		}
	}

	public void registerPage() {
		loadGui();
		this.registerPageDetails.setFont(new Font("Verdana", Font.PLAIN, 20));
		this.panel.add(this.registerPageDetails);
		this.panel.add(this.userNameRegisterField);
		this.panel.add(this.userPasswordRegisterField);
		this.panel.add(this.securityLevelField);
		this.panel.add(this.registerCompleteButton);
		this.panel.add(this.backButton3);
	}

	public void registerComplete() {
		JOptionPane.showMessageDialog(this.parent, "Success, register complete! Redirecting to login menu.");
		this.userName[this.userCount] = ((JTextField) this.userNameRegisterField).getText();
		this.userPassword[this.userCount] = ((JTextField) this.userPasswordRegisterField).getText();
		String securityLevel = ((JTextField) this.securityLevelField).getText();
		this.securityLevel = Integer.parseInt(securityLevel);

		if (this.securityLevel == 3) {
			this.fullAccess = true;
		}

		if (this.securityLevel == 1) {
			this.fullAccess = false;
		}

		this.userCount++;
		logoutMenu();
	}

	// Bellow loads the main graphical user interface to remove duplicate code
	public void loadGui() {
		this.panel.removeAll();
		this.panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 30));
		this.panel.setPreferredSize(new Dimension(700, 1000));
		this.panel.setLayout(new GridLayout(0, 1));
		this.panel.setBackground(Color.yellow);
		this.titleLabel.setFont(new Font("Verdana", Font.PLAIN, 55));
		this.panel.add(this.titleLabel);
		this.warningLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.panel.add(this.warningLabel);
		this.panel.add(this.spacing);
		this.frame.add(panel, BorderLayout.CENTER);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Our Gui");
		this.frame.pack();
		this.frame.setVisible(true);
	}

	public void search() {
		loadGui();
		// this.searchLabel.setFont(new Font("Verdana", Font.PLAIN, 45));
		this.warningLabel2.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.panel.add(warningLabel2);
		this.panel.add(searchField);
		this.panel.add(this.enterButton);
		this.panel.add(this.backButton);

		// Bellow's for loop prints all movies on the system
		for (int i = 0; i <= this.movieListString.length - 1; i++) {
			if (i <= this.movieCount + 1) {
				this.movieListJLabel = new JLabel(this.movieListString[i]);
				this.panel.add(this.movieListJLabel);
			}
		}
	}

	public void searchMovieList() {
		String search = ((JTextField) this.searchField).getText();
		this.loopCount = 0;
		// Bellow checks system if movie being searched exists
		for (int i = 0; i <= movieListString.length - 1; i++) {
			if (this.movieListString[i] != null) {
				if (search.contains(this.movieListString[i])) {
					JLabel movs = new JLabel(this.movieListString[i]);
					this.panel.add(movs);
					JOptionPane.showMessageDialog(this.parent, "Movie found in the system.");
					this.panel.add(this.reviewThisMovieButton);
					this.currentMovie = movieListString[i];
					this.loopCount = 1;
				}

			}
			if ((i == this.movieListString.length - 1) && this.loopCount == 0) {
				JOptionPane.showMessageDialog(this.parent, "Movie not in system");
			}
		}

		this.loopCount = 0;
	}

	public void reviewPage() {
		loadGui();
		JLabel notice = new JLabel("Enter movie name in review");
		this.panel.add(notice);
		this.panel.add(this.reviewField);
		this.panel.add(this.leaveReviewButton);
		this.panel.add(this.showReviewsButton);
		this.panel.add(this.backButton);
	}

	public void leaveReview() {
		this.parent.pack();
		this.parent.setVisible(true);
		String review = ((JTextField) this.reviewField).getText();
		this.loopCount = 0;
		// Bellow checks if the review contains a movie that exists within the system

		if (this.securityLevel == 2 || this.securityLevel == 3) {
			for (int i = 0; i <= this.movieListString.length - 1; i++) {
				if (this.movieListString[i] != null) {
					if (review.contains(this.movieListString[i])) {
						JOptionPane.showMessageDialog(this.parent, "Review successfully uploaded");
						JOptionPane.showMessageDialog(this.parent, review);
						this.review[this.reviewCount] = review;
						this.reviewCount++;
						this.loopCount = 1;
					}
				}

				if ((i == this.movieListString.length - 1) && this.loopCount == 0) {
					JOptionPane.showMessageDialog(this.parent,
							"Review does not contain movie name, or movie not in system");
				}
			}
		} else {
			JOptionPane.showMessageDialog(this.parent, "You don't have access to this feature");
		}
		this.loopCount = 0;
	}

	public void showReviews() {
		loadGui();
		this.panel.add(this.backButton2);
		// Bellow's for loop prints all reviews for movies
		for (int i = 0; i <= this.review.length - 1; i++) {
			JLabel reviews = new JLabel(this.review[i]);
			this.panel.add(reviews);
		}
	}

	// Class bellow was created so users can add reviews from multiple locations
	// This can be done through searching a movie, then reviewing it,
	// or by going through the review page
	public void reviewMovieFromSearch() {
		loadGui();
		JLabel currentMovie = new JLabel(this.currentMovie);
		this.panel.add(currentMovie);
		this.panel.add(this.reviewField);
		this.panel.add(leaveReviewButton);
		this.panel.add(this.backButton);
		this.review[this.reviewCount] = ((JTextField) this.reviewField).getText();
		this.reviewCount++;
	}

	public void requestCritic() {
		JOptionPane.showMessageDialog(this.parent, "Success! Request Submitted");
		this.criticRequests[this.criticRequestCount] = this.currentUser;
		this.criticRequestCount++;
		this.criticRequest = true;
		this.criticRequestCount++;
	}

	public void adminMenu() {
		loadGui();
		this.panel.add(this.addMoviesButton);
		this.panel.add(this.removeMoviesButton);
		this.panel.add(this.reviewCriticRequestsButton);
		this.panel.add(this.reviewMovieRequestsButton);
		this.panel.add(this.homeButton);
	}

	public void addMovies() {
		this.newMovie = JOptionPane.showInputDialog(this.parent, "Add by entering movie title");
		this.movieListString[this.movieCount] = this.newMovie;
		this.movieCount++;
		JOptionPane.showMessageDialog(this.parent, "Movie added to system. Redirecting to movie list.");
		search();
	}

	public void removeMovies() {
		this.removeMovie = JOptionPane.showInputDialog(this.parent, "Remove by entering movie title");
		boolean found = false;
		for (int i = 0; i <= this.movieListString.length - 1; i++) {
			if (this.removeMovie.equals(this.movieListString[i])) {
				JOptionPane.showMessageDialog(this.parent, "Movie found and deleted. Redirecting to movie list.");
				found = true;
				if (found == true) {
					this.movieListString[i] = null;
					this.movieCount--;
					search();
				}

			}
			if (i == (this.movieListString.length - 1) && found == false) {
				JOptionPane.showMessageDialog(this.parent, "Failed. Movie not found in system.");
			}
		}
	}

	public void reviewCriticRequests() {
		loadGui();

		for (int i = 0; i <= this.criticRequestCount; i++) {
			if (this.criticRequest == true) {
				JLabel criticRequestsJLabel = new JLabel(this.criticRequests[i]);
				this.panel.add(criticRequestsJLabel);
			}
			if (i == this.criticRequestCount && criticRequest == false) {
				this.noCriticRequestsText.setFont(new Font("Verdana", Font.PLAIN, 35));
				this.panel.add(this.noCriticRequestsText);
			}
		}
		if (this.criticRequest == true) {
			this.panel.add(this.acceptCriticButton);
			this.panel.add(this.declineCriticButton);
		}
		this.panel.add(this.adminMenuButton);
	}

	public void declineCriticRequest() {
		JOptionPane.showMessageDialog(this.parent, "User declined critic status");
		this.criticRequests[this.criticRequestCount - 1] = null;
		this.criticRequest = false;
		reviewCriticRequests();

	}

	public void acceptCriticRequest() {
		JOptionPane.showMessageDialog(this.parent, "Success! User promoted to critic");
		this.criticRequests[this.criticRequestCount - 1] = null;
		this.criticRequest = false;
		reviewCriticRequests();

	}

	public void logout() {
		JOptionPane.showMessageDialog(this.parent, "User logged out. Directing to Home Page");
		this.currentUser = null;
		this.loggedIn = false;
		run();
	}

	public void submitMovie() {
		this.submitMovie = JOptionPane.showInputDialog(this.parent, "Please submit your movie bellow!");
		this.movieSubmissions[this.movieSubmissionsCount] = this.submitMovie;
		JOptionPane.showMessageDialog(this.parent, "Success! Movie submitted for approval.");
		this.movieSubmissionsCount++;
		this.movieRequestsWaitingApproval = true;

	}

	public void reviewMovieRequests() {
		loadGui();

		if (this.movieRequestsWaitingApproval == true) {
			for (int i = 0; i <= this.movieSubmissionsCount; i++) {

				JLabel movieSubmissionsJLabel = new JLabel(this.movieSubmissions[i]);
				panel.add(movieSubmissionsJLabel);
				this.panel.add(this.acceptMovieButton);
				this.panel.add(this.declineMovieButton);
				this.movSubmissionID = i;

			}
		} else {
			this.noMovieSubmissionsLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
			this.panel.add(noMovieSubmissionsLabel);
			this.panel.remove(this.acceptMovieButton);
			this.panel.remove(this.declineMovieButton);
			this.movieRequestsWaitingApproval = false;
		}

		this.panel.add(this.adminMenuButton);
	}

	public void acceptMovie() {
		JOptionPane.showMessageDialog(this.parent, "Success! Movie submitted to IMDB. Redirecting to search page");
		this.movieListString[this.movieCount - 1] = this.submitMovie;
		this.movieSubmissions[this.movSubmissionID - 1] = null;
		this.movieCount++;
		this.panel.remove(this.acceptMovieButton);
		this.panel.remove(this.declineMovieButton);
		this.movieRequestsWaitingApproval = false;
		search();

	}

	public void declineMovie() {
		JOptionPane.showMessageDialog(this.parent, "Movie dismissed!");
		this.movieSubmissions[this.movSubmissionID] = null;
		reviewMovieRequests();
	}

	// Bellow is an 'ActionListener' which makes buttons functional and assigns them
	// to methods using if statements
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == searchButton) {
			search();
		}

		if (ae.getSource() == signInButton) {
			logoutMenu();
		}

		if (ae.getSource() == reviewButton) {
			reviewPage();
		}

		if (ae.getSource() == backButton) {
			run();
		}

		if (ae.getSource() == backButton2) {
			reviewPage();
		}

		if (ae.getSource() == backButton3) {
			logoutMenu();
		}

		if (ae.getSource() == enterButton) {
			searchMovieList();
		}

		if (ae.getSource() == leaveReviewButton) {
			leaveReview();
		}
		if (ae.getSource() == showReviewsButton) {
			showReviews();
		}

		if (ae.getSource() == reviewThisMovieButton) {
			reviewMovieFromSearch();
		}

		if (ae.getSource() == loginButton) {
			loginRequest();
		}

		if (ae.getSource() == registerButton) {
			registerPage();
		}
		if (ae.getSource() == registerCompleteButton) {
			registerComplete();
		}

		if (ae.getSource() == homeButton) {
			run();
		}

		if (ae.getSource() == requestCriticLevelButton) {
			requestCritic();
		}

		if (ae.getSource() == adminMenuButton) {
			adminMenu();
		}

		if (ae.getSource() == addMoviesButton) {
			addMovies();
		}

		if (ae.getSource() == removeMoviesButton) {
			removeMovies();
		}

		if (ae.getSource() == reviewCriticRequestsButton) {
			reviewCriticRequests();
		}

		if (ae.getSource() == reviewMovieRequestsButton) {
			reviewMovieRequests();
		}

		if (ae.getSource() == acceptCriticButton) {
			acceptCriticRequest();
		}

		if (ae.getSource() == declineCriticButton) {
			declineCriticRequest();
		}

		if (ae.getSource() == acceptMovieButton) {
			acceptMovie();
		}

		if (ae.getSource() == declineMovieButton) {
			declineMovie();
		}

		if (ae.getSource() == logoutButton) {
			logout();
		}

		if (ae.getSource() == submitMovieButton) {
			submitMovie();
		}
	}

	// Main method bellow separated
	public static void main(String[] args) {
		new Program().run();
	}

}
