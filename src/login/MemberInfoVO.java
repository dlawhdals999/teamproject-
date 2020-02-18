package login;


// MemberInfoVO , SinUPVO 합침
public class MemberInfoVO {
	
	private int userNo;
	private String userID;
	private String userPW;
	private String userPWCheck;
	private String nickName;
	private String userName;
	private Boolean gender;
	private Boolean Gflag;
	
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPW() {
		return userPW;
	}
	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}
	public String getUserPWCheck() {
		return userPWCheck;
	}
	public void setUserPWCheck(String userPWCheck) {
		this.userPWCheck = userPWCheck;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public Boolean getGflag() {
		return Gflag;
	}
	public void setGflag(Boolean gflag) {
		Gflag = gflag;
	}
	
	
}
