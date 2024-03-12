package example.model.vo;

public class ExampleVO implements java.io.Serializable {
	private static final long serialVersionUID = 5278639638924896323L;
	
	private String userName;
	private String gender;
	private int age;
	private String myPhoto;
	
	public ExampleVO() {
		super();
	}

	public ExampleVO(String userName, String gender, int age, String myPhoto) {
		super();
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.myPhoto = myPhoto;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMyPhoto() {
		return myPhoto;
	}

	public void setMyPhoto(String myPhoto) {
		this.myPhoto = myPhoto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ExampleVO [userName=" + userName + ", gender=" + gender 
				+ ", age=" + age + ", myPhoto=" + myPhoto + "]";
	}
}
