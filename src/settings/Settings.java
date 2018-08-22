package settings;


public class Settings {
	
	static int maxBooksStudent=2;
	static int maxBookFaculty=5;
	
	public int getMaxBooksStudent() {
		return maxBooksStudent;
	}
	public void setMaxBooksStudent(int maxBooksStudent) {
		Settings.maxBooksStudent = maxBooksStudent;
	}
	public int getMaxBookFaculty() {
		return maxBookFaculty;
	}
	public void setMaxBookFaculty(int maxBookFaculty) {
		Settings.maxBookFaculty = maxBookFaculty;
	}

}
