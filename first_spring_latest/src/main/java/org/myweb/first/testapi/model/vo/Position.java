package org.myweb.first.testapi.model.vo;

public class Position implements java.io.Serializable {
	private static final long serialVersionUID = -7559633528504420572L;

	private String title;
	private String address;
	private String photoFileOriginal;
	private String photoFileRename;
	private double latitude;
	private double longitude;
	private String info;
	
	public Position() {
		super();
	}
	
	public Position(String title, double latitude, double longitude) {
		super();
		this.title = title;		
		this.latitude = latitude;
		this.longitude = longitude;		
	}

	public Position(String title, String address, String photoFileOriginal, String photoFileRename, double latitude,
			double longitude, String info) {
		super();
		this.title = title;
		this.address = address;
		this.photoFileOriginal = photoFileOriginal;
		this.photoFileRename = photoFileRename;
		this.latitude = latitude;
		this.longitude = longitude;
		this.info = info;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhotoFileOriginal() {
		return photoFileOriginal;
	}

	public void setPhotoFileOriginal(String photoFileOriginal) {
		this.photoFileOriginal = photoFileOriginal;
	}

	public String getPhotoFileRename() {
		return photoFileRename;
	}

	public void setPhotoFileRename(String photoFileRename) {
		this.photoFileRename = photoFileRename;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Position [title=" + title + ", address=" + address + ", photoFileOriginal=" + photoFileOriginal
				+ ", photoFileRename=" + photoFileRename + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", info=" + info + "]";
	}
}
