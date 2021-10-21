import java.util.Comparator;

class PathRecursion implements Comparator<String> {
	public int compare(String pathA, String pathB) {
		String[] directoriesA = pathA.split("/");
		String[] directoriesB = pathB.split("/");
		if (directoriesA.length > directoriesB.length) {
			return 1;
		} else if (directoriesA.length < directoriesB.length) {
			return -1;
		} else {
			String lastDirectoryA = directoriesA[directoriesA.length - 1];
			String lastDirectoryB = directoriesB[directoriesB.length - 1];
			return lastDirectoryA.compareTo(lastDirectoryB);
		}
	}
}
