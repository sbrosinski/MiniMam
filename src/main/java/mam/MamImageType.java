package mam;

public enum MamImageType {

	JPG("image/jpg"), 
	PNG("image/png"),
	UNDEFINED("image/undefined");
	
	private String mimeType;

	MamImageType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	String mimeType() {
		return mimeType;
	}
	
}
