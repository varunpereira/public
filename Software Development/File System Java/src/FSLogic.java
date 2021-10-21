import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.Comparator;

// only testing, report is left

//custom commands, comments, mc
//copyin and mkdir mean updating FS/record, so
//only they need tobe careful in validating user input
//validate all user input, esp common one fsrecordpath
// no literals
// HAVE MORE TRY CATCH FOR custom err msg
//run it via cli not as program input (able to do unix commands)
//note fs here also means internal
//defrag order based on directory recursion, and alphabetical
//inbetween dirfiles for only all(record), copyin (record,if),mkdir(record,id),copyout(ef)
//testing + report(explain all) + hd + comments + ss to boos marks + instructions
// in rmit server, they need to refresh their current dir before try to access anything in it, inc cmd
// cmds only be done inside src dir is safer
// hd+ done: .gz files work like normal files

@SuppressWarnings("unchecked")
public class FSLogic {

	private List<GenericFile> internalData = new ArrayList<GenericFile>();
	private final int success = 0;
	private final int failure = 1;

	public int start(String[] commandArguments) throws Exception {
		// get internal data from previous command
		String internalDataPath = "InternalData.ser";
		if (Files.exists(Paths.get(internalDataPath))) {
			FileInputStream fis = new FileInputStream(internalDataPath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			internalData = (ArrayList) ois.readObject();
			ois.close();
			fis.close();
		}
		// if valid num of args then procceed to check if args' values are valid
		if (commandArguments[0].equals("list") && commandArguments.length == 2) {
			listIsValid(commandArguments[1]);
		} else if (commandArguments[0].equals("copyin") && commandArguments.length == 4) {
			copyinNotValid(commandArguments[1], commandArguments[2], commandArguments[3]);
		} else if (commandArguments[0].equals("copyout") && commandArguments.length == 4) {
			copyoutNotValid(commandArguments[1], commandArguments[2], commandArguments[3]);
		} else if (commandArguments[0].equals("mkdir") && commandArguments.length == 3) {
			mkdirNotValid(commandArguments[1], commandArguments[2]);
		} else if (commandArguments[0].equals("rm") && (commandArguments.length == 3)) {
			rmNotValid(commandArguments[1], commandArguments[2]);
		} else if (commandArguments[0].equals("rmdir") && (commandArguments.length == 3)) {
			rmdirNotValid(commandArguments[1], commandArguments[2]);
		} else if (commandArguments[0].equals("defrag") && (commandArguments.length == 2)) {
			defragIsValid(commandArguments[1]);
		} else if (commandArguments[0].equals("index") && (commandArguments.length == 2)) {
			indexIsValid(commandArguments[1]);
		} else {
			System.err.println("'java VSFS " + commandArguments[0] + "'command doesn't exist or wrong number of args.");
			return failure;
		}
		// return success
		return success;
	}

	public int fsNotValid(String internalRecordPath) throws Exception {
		// assuming its valid dir1/dir2/record.notes, need to check for ends with /
		// create internal record if doesnt exist, else update existing
		try {
			if (!Files.exists(Paths.get(internalRecordPath))) {
				File internalRecord = new File(internalRecordPath);
				// created nonexisting intermediary dirs
				if (internalRecord.getParentFile() != null) {
					internalRecord.getParentFile().mkdirs();
				}
				// HD+ Level: create FS (including .gz)
				FileWriter fsRecordWrite = new FileWriter(internalRecord, true);
				fsRecordWrite.write("NOTES V1.0\n");
				fsRecordWrite.close();
			}
		} catch (Exception e) {
			System.err.println("Error creating FS.");
			return failure;
		}
		return success;
	}

	public int createInternalMissingParentDirectoryFiles(String internalRecordPath, String internalDirectoryFilePath)
			throws Exception {
		// assuming in correct path format eg dir1/dir2/dir3/
		// removing starting '/' for easier formatting
		if (internalDirectoryFilePath.startsWith("/")) {
			internalRecordPath = internalDirectoryFilePath.substring(1, internalDirectoryFilePath.length());
		}
		int numDirFiles = 0;
		for (int i = 0; i < internalDirectoryFilePath.length(); i++) {
			if (internalDirectoryFilePath.substring(i, i + 1).equals("/")) {
				numDirFiles += 1;
			}
		}
		if (numDirFiles == 0) {
			String dirFiles[] = internalDirectoryFilePath.split("/");
			int count = 0;
			String searchTerm = dirFiles[count] + "/";
			for (int j = 0; j < numDirFiles; j++) {
				mkdirIsValid(internalRecordPath, searchTerm);
				if (j != numDirFiles - 1) {
					count += 1;
					searchTerm += dirFiles[count] + "/";
				}
			}
		} else {
			String dirFiles[] = internalDirectoryFilePath.split("/");
			int count = 0;
			String searchTerm = dirFiles[count] + "/";
			for (int j = 0; j < numDirFiles; j++) {
				// check internal directory files exists
				boolean internalGenericFileExists = false;
				for (GenericFile record : internalData) {
					if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
						if (searchTerm.equals(record.getPath())) {
							internalGenericFileExists = true;
						}
					}
				}
				if (!internalGenericFileExists) {
					mkdirIsValid(internalRecordPath, searchTerm);
				}
				if (j != numDirFiles - 1) {
					count += 1;
					searchTerm += dirFiles[count] + "/";
				}
			}
		}
		return success;
	}

	public int updateInternalDataAndInternalFS(String internalRecordPath) throws Exception {
		// store internal data
		FileOutputStream fos = new FileOutputStream("InternalData.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(internalData);
		oos.close();
		fos.close();
		// update FS content
		String fsContent = "NOTES V1.0\n";
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath)) {
				if (record.getFileType().equals("normal")) {
					String charFileRecord = "@";
					String charContentRecord = " ";
					if (record.getIsDeleted()) {
						charFileRecord = "#";
						charContentRecord = "#";
					}
					fsContent += charFileRecord + record.getPath() + "\n";
					String recordContentString = new String(record.getContent());
					String recordContentLines[] = recordContentString.split("\\r?\\n");
					String recordContent = "";
					for (String line : recordContentLines) {
						recordContent += charContentRecord + line + "\n";
					}
					if (recordContent.length() <= 255) {
						fsContent += recordContent;
					} else {
						fsContent += recordContent.substring(0, 254) + "\n";
					}
				} else if (record.getFileType().equals("directory") && !record.getIsDeleted()) {
					fsContent += "=" + record.getPath() + "\n";
				} else if (record.getFileType().equals("directory") && record.getIsDeleted()) {
					fsContent += "#" + record.getPath() + "\n";
				}
			}
		}
		// update .notes file from the updated internal data
		try {
			File file = new File(internalRecordPath);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fsContent);
			bw.close();
		} catch (Exception e) {
			System.err.println("Rebuilding FS error.");
			return failure;
		}
		return success;
	}

	public int listIsValid(String internalRecordPath) throws Exception {
		fsNotValid(internalRecordPath);
		int count = 0;
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				count += 1;
				System.out.println(record.getPermissions() + " " + record.getHardLinks() + " " + record.getOwner() + " "
						+ record.getGroup() + " " + record.getSize() + " " + record.getUpdatedDateTime() + " "
						+ record.getPath());
			}
		}
		System.out.println("Total Number of Records in FS: " + count);
		return success;
	}

	// extra careful what goes in
	public int copyinNotValid(String internalRecordPath, String externalGenericFilePath, String internalGenericFilePath)
			throws Exception {
		fsNotValid(internalRecordPath);
		// record string limit 255 inclusive, inc @
		if (internalGenericFilePath.length() >= 254) {
			System.err.println("IF Path gone over 255 char limit.");
			return failure;
		}
		// check if starts with correct substring
		if (internalGenericFilePath.startsWith(".") && internalGenericFilePath.startsWith("..")
				&& internalGenericFilePath.startsWith("/")) {
			System.err.println("IF can't start with those chars.");
			return failure;
		}
		// check last path part is a dirfile
		String ifPathExtension = internalGenericFilePath.substring(internalGenericFilePath.length() - 1);
		if (ifPathExtension.equals("/")) {
			System.err.println("IF Path can not end with a directory.");
			return failure;
		}
		// check if IF already exists, hence can't replace it (need to delete first)
		boolean internalPathExists = false;
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath)
					&& record.getPath().equals(internalGenericFilePath)) {
				if (!record.getIsDeleted()) {
					internalPathExists = true;
				} else if (record.getIsDeleted()) {
					internalData.remove(record);
				}
			}
		}
		if (internalPathExists) {
			System.err.println("IF already exists in FS.");
			return failure;
		}
		// create IF intermediary dirs
		createInternalMissingParentDirectoryFiles(internalRecordPath, internalGenericFilePath);
		// check if EF is a dir
		String efPathExtension = internalGenericFilePath.substring(internalGenericFilePath.length() - 1);
		if (efPathExtension.equals("/")) {
			System.err.println("EF Path can not end with normal file.");
			return failure;
		}
		// check if EF already exists, else can't copy from non existing file
		if (!Files.exists(Paths.get(externalGenericFilePath))) {
			System.err.println("EF does not exist.");
			return failure;
		}
		// finally user input is valid
		copyinIsValid(internalRecordPath, externalGenericFilePath, internalGenericFilePath);
		return success;
	}

	public int copyinIsValid(String internalRecordPath, String externalGenericFilePath, String internalGenericFilePath)
			throws Exception {
		// find internalnormalfile info
		String fileType = "normal";
		String updatedDateTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		int numHardLinks = (int) java.nio.file.Files.getAttribute(Paths.get(externalGenericFilePath), "unix:nlink");
		PosixFileAttributes posix = Files.readAttributes(Paths.get(externalGenericFilePath), PosixFileAttributes.class);
		int size = (int) posix.size();
		String permissions = PosixFilePermissions.toString(posix.permissions());
		// HD Level: reading EF works with binary and text file types
		// default buffer size is 8KB, decoding binary base64 file here
		byte[] content = "".getBytes();
		try {
			content = Files.readAllBytes(Paths.get(externalGenericFilePath));
			// System.out.println(Arrays.toString(content));
		} catch (IOException e) {
			System.err.println("Error reading EF.");
			return failure;
		}
		// create internalnormalfile
		GenericFile normalFile = new GenericFile(internalRecordPath, fileType, content, permissions, numHardLinks,
				posix.owner().toString(), posix.group().toString(), size, updatedDateTime, internalGenericFilePath);
		internalData.add(normalFile);
		// store Data, then use it to get and update FS content
		updateInternalDataAndInternalFS(internalRecordPath);
		// return success
		return success;
	}

	// won't make IF intermediary dirs, since youll never copying anything to EF
	// can only copy from something that alrdy exists like in any FS
	public int copyoutNotValid(String internalRecordPath, String internalGenericFilePath,
			String externalGenericFilePath) throws Exception {
		fsNotValid(internalRecordPath);
		// check if if type is dirfile
		if (internalGenericFilePath.endsWith("/")) {
			System.err.println("IF can't start with those chars.");
			return failure;
		}
		// check if IF intermediary dirs exist
		boolean ifPathExists = false;
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				if (record.getPath().equals(internalGenericFilePath)) {
					ifPathExists = true;
				}
			}
		}
		if (!ifPathExists) {
			System.err.println("IF Path does not exist.");
			return failure;
		}
		// check if EF exists, else make dirs
		if (!Files.exists(Paths.get(externalGenericFilePath))) {
			File externalGenericFile = new File(externalGenericFilePath);
			// created nonexisting intermediary dirs
			if (externalGenericFile.getParentFile() != null) {
				externalGenericFile.getParentFile().mkdirs();
			}
		}
		// finally valid
		copyoutIsValid(internalRecordPath, internalGenericFilePath, externalGenericFilePath);
		return success;
	}

	public int copyoutIsValid(String internalRecordPath, String internalGenericFilePath, String externalGenericFilePath)
			throws Exception {
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				if (record.getPath().equals(internalGenericFilePath)) {
					// HD level: write to EF of binary or text file type, encoding base64
					try {
						// create EF
						FileOutputStream fos = new FileOutputStream(new File(externalGenericFilePath));
						BufferedOutputStream writer = new BufferedOutputStream(fos);
						writer.write(record.getContent());
						writer.flush();
						writer.close();
					} catch (IOException ex) {
						System.err.println("Error writing to EF.");
						return failure;
					}
				}

			}
		}
		// return success
		return success;
	}

	// extra careful what goes in - remaking a deleted dir?
	public int mkdirNotValid(String internalRecordPath, String internalGenericFilePath) throws Exception {
		fsNotValid(internalRecordPath);
		// removing starting '/' for easier formatting
		if (internalGenericFilePath.startsWith("/")) {
			internalRecordPath = internalGenericFilePath.substring(1, internalGenericFilePath.length());
		}
		// check file type is dirfile
		if (!internalGenericFilePath.endsWith("/")) {
			System.err.println("InternalGenericFile must be of extension type directory.");
			return failure;
		}
		// record string limit 255 inclusive, inc =
		if (internalGenericFilePath.length() >= 254) {
			System.err.println("IF Path gone over 255 char limit.");
			return failure;
		}
		// check if exists
		boolean internalGenericFileExists = false;
		int removedRecordIndex = 0;
		boolean idDeleted = false;
		for (int i = 0; i < internalData.size(); i += 1) {
			if (internalData.get(i).getInternalRecordPath().equals(internalRecordPath)
					&& internalData.get(i).getPath().equals(internalGenericFilePath)) {
				if (!internalData.get(i).getIsDeleted()) {
					internalGenericFileExists = true;
				} else if (internalData.get(i).getIsDeleted()) {
					idDeleted = true;
					removedRecordIndex = i;
				}
			}
		}
		if (idDeleted) {
			internalData.remove(removedRecordIndex);
		}
		if (internalGenericFileExists) {
			System.err.println("IF already exists in the FS.");
			return failure;
		}
		// create dir files, if don't already exist
		createInternalMissingParentDirectoryFiles(internalRecordPath, internalGenericFilePath);
		// return success
		return success;
	}

	public int mkdirIsValid(String internalRecordPath, String internalGenericFilePath) throws Exception {
		// find internalnormalfile info
		String fileType = "directory";
		byte[] content = "".getBytes();
		String permissions = "drwxr-xr-x";
		int numHardLinks = 0;
		String owner = "root";
		String group = "root";
		int size = 0;
		String updatedDateTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		// create internaldirfile then store it in internaldata
		GenericFile dirFile = new GenericFile(internalRecordPath, fileType, content, permissions, numHardLinks, owner,
				group, size, updatedDateTime, internalGenericFilePath);
		internalData.add(dirFile);
		// update internal data
		FileOutputStream fos = new FileOutputStream("InternalData.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(internalData);
		oos.close();
		fos.close();
		// update FS content
		updateInternalDataAndInternalFS(internalRecordPath);
		// return success
		return success;
	}

	// eg dir1/dir2/file1.txt
	public int rmNotValid(String internalRecordPath, String internalGenericFilePath) throws Exception {
		fsNotValid(internalRecordPath);
		// remove first substring '/', easier format
		if (internalGenericFilePath.substring(0).equals("/")) {
			internalRecordPath = internalGenericFilePath.substring(1, internalGenericFilePath.length());
		}
		// check if normalfile exists, can't remove something that doesn't exist yet!
		boolean internalGenericFileExists = false;
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				if (record.equals(internalGenericFilePath)) {
					internalGenericFileExists = true;
				}
			}
		}
		if (!internalGenericFileExists) {
			System.err.println("IF doesn't exist in FS, so it can't be removed.");
			return failure;
		}
		// finally valid
		rmIsValid(internalRecordPath, internalGenericFilePath);
		return success;
	}

	// eg dir1/dir2/file1.txt
	public int rmIsValid(String internalRecordPath, String internalGenericFilePath) throws Exception {
		// remove from data storage
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				boolean isDeleted = true;
				record.setIsDeleted(isDeleted);
			}
		}
		// update FS content
		updateInternalDataAndInternalFS(internalRecordPath);
		return success;
	}

	// eg dir1/dir2/
	// use is directory for all so dont get any wrong chars inbetween for copyin
	public int rmdirNotValid(String internalRecordPath, String internalGenericFilePath) throws Exception {
		fsNotValid(internalRecordPath);
		// remove first char /, easier format
		if (internalGenericFilePath.substring(0).equals("/")) {
			internalRecordPath = internalGenericFilePath.substring(1, internalGenericFilePath.length());
		}
		// check if dir exists, can't remove something that doesn't exist yet!
		boolean internalDirectoryFileExists = false;
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				if (record.getPath().equals(internalGenericFilePath)) {
					internalDirectoryFileExists = true;
				}
			}
		}
		if (!internalDirectoryFileExists) {
			System.err.println("ID can't be removed because it doesn't exist in FS.");
			return failure;
		}
		// finally valid
		rmdirIsValid(internalRecordPath, internalGenericFilePath);
		return success;
	}

	// don't work with binary(base64) and gz files?
	// convert string to bytes at end
	public int rmdirIsValid(String internalRecordPath, String internalGenericFilePath) throws Exception {
		// mark files/dirs as deleted in internal data (of all .notes)
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				if (record.getPath().startsWith(internalGenericFilePath)) {
					boolean isDeleted = true;
					record.setIsDeleted(isDeleted);
					System.out.println(record.getPath());
				}
			}
		}
		// update FS content
		updateInternalDataAndInternalFS(internalRecordPath);
		return success;
	}

	// list directories recursively using tree, removing deleted records from FS
	public int defragIsValid(String internalRecordPath) throws Exception {
		// check FS is valid
		fsNotValid(internalRecordPath);
		// find num records that exist in FS
		int numRecordPaths = 0;
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				numRecordPaths += 1;
			}
		}
		// save all paths as an array string
		String[] allRecordPaths = new String[numRecordPaths];
		for (int j = 0; j < allRecordPaths.length; j += 1) {
			if (internalRecordPath.equals(internalData.get(j).getInternalRecordPath())) {
				allRecordPaths[j] = internalData.get(j).getPath();
			}
		}
		// change array 2 ways: ascending alphanumerically, and recursive paths
		System.out.println("Before: " + Arrays.toString(allRecordPaths));
		Comparator<String> makePathsRecursive = new PathRecursion();
		Arrays.sort(allRecordPaths, makePathsRecursive);
		System.out.println("After: " + Arrays.toString(allRecordPaths));
		// update the internaldatarecord
		List<GenericFile> newInternalData = new ArrayList<GenericFile>();
		for (int j = 0; j < allRecordPaths.length; j += 1) {
			for (int i = 0; i < internalData.size(); i += 1) {
				// System.out.println(allRecordPaths[j]);
				if (allRecordPaths[j].equals(internalData.get(i).getPath())
						&& internalRecordPath.equals(internalData.get(i).getInternalRecordPath())) {
					newInternalData.add(internalData.get(i));
					System.out.println(newInternalData.get(j).getPath());
				}
				// for other FS
				else if (!internalRecordPath.equals(internalData.get(i).getInternalRecordPath())) {
					newInternalData.add(internalData.get(i));
				}
			}
		}
		// now its done, make it member of class
		internalData = newInternalData;
		// update internaldata file
		FileOutputStream fos = new FileOutputStream("InternalData.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(internalData);
		oos.close();
		fos.close();
		// updated the record from tree/increasing alphanumeric + sort, deleted files
		// never existed in internaldata list anyway
		String content = "NOTES V1.0\n";
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				if (record.getFileType().equals("normal")) {
					content += "@" + record.getPath() + "\n";
					String recordContentString = new String(record.getContent());
					String recordContentLines[] = recordContentString.split("\\r?\\n");
					String recordContent = "";
					for (String line : recordContentLines) {
						recordContent += " " + line + "\n";
					}
					if (recordContent.length() <= 255) {
						content += recordContent;
					} else {
						content += recordContent.substring(0, 254) + "\n";
					}
				} else if (record.getFileType().equals("directory")) {
					content += "=" + record.getPath() + "\n";
				}
			}
		}
		// write to FS with updated internal data
		try {
			File file = new File(internalRecordPath);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (Exception e) {
			System.err.println("Rebuilding FS error.");
			return failure;
		}
		return success;
	}

	// set an index (unique number) to each record in the FS
	public int indexIsValid(String internalRecordPath) throws Exception {
		fsNotValid(internalRecordPath);
		int index = -1;
		for (GenericFile record : internalData) {
			if (record.getInternalRecordPath().equals(internalRecordPath) && !record.getIsDeleted()) {
				index += 1;
				record.setIndex(index);
			}
		}
		return success;
	}

}