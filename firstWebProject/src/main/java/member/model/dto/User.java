package member.model.dto;

// do(domain object) == vo(value object) == dto(data transfer object) == entity
// db 테이블의 각 컬럼값을 저장할 목적의 클래스임
public class User implements java.io.Serializable {
	private static final long serialVersionUID = -5227267593268689410L;
	private String userId;
	private String userPwd;
	private String userName;

	public User() {
		super();
	}

	public User(String userId, String userPwd, String userName) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName + "]";
	}
	
}
