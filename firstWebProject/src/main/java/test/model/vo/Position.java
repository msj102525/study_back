package test.model.vo;

public class Position implements java.io.Serializable {
	private static final long serialVersionUID = -2062951298846718309L;

	private String title;
	private double latitude;
	private double longitude;
	
	public Position() {
		super();
	}

	public Position(String title, double latitude, double longitude) {
		super();
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Position [title=" + title + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}	
}
