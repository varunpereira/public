import java.io.Serializable;

public class GenericFile implements Serializable {
	// member variables
	String internalRecordPath;
	String fileType;
	byte[] content;
	String permissions;
	int hardLinks;
	String owner;
	String group;
	int size;
	String updatedDateTime;
	String path;
	int index;
	boolean isDeleted;

	// constructor
	public GenericFile(String internalRecordPath, String fileType, byte[] content, String permissions, int hardLinks,
			String owner, String group, int size, String lastUpdateDateTime, String path) {
		this.internalRecordPath = internalRecordPath;
		this.fileType = fileType;
		this.content = content;
		this.permissions = permissions;
		this.hardLinks = hardLinks;
		this.owner = owner;
		this.group = group;
		this.size = size;
		this.updatedDateTime = lastUpdateDateTime;
		this.path = path;
		this.isDeleted = false;
	}

	// getters and setters
	public String getInternalRecordPath() {
		return internalRecordPath;
	}

	public void setInternalRecordPath(String internalRecordPath) {
		this.internalRecordPath = internalRecordPath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public int getHardLinks() {
		return hardLinks;
	}

	public void setHardLinks(int hardLinks) {
		this.hardLinks = hardLinks;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(String lastUpdateDateTime) {
		this.updatedDateTime = lastUpdateDateTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
