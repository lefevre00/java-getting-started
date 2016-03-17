package org.friends.app.model;

/**
 * A user of the application.
 * @author michael lefevre
 */
public class User {
	
	private Integer id;
	private String emailAMDM;
	private Integer placeNumber;
	private String pwd;
	private String tokenMail;
	private String tokenPwd;
	
	public User() {}
	
	public User(String email, String mdp) {
		emailAMDM = email;
		pwd = mdp;
	}
	
	public User(String email, String mdp, Integer placeNum) {
		emailAMDM = email;
		pwd = mdp;
		placeNumber = placeNum;
	}
	
	/**
	 * @return the idUser
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param idUser the idUser to set
	 */
	public void setId(Integer idUser) {
		this.id = idUser;
	}
	
	/**
	 * @return the emailAMDM
	 */
	public String getEmailAMDM() {
		return emailAMDM;
	}
	
	/**
	 * @param emailAMDM the emailAMDM to set
	 */
	public void setEmailAMDM(String emailAMDM) {
		this.emailAMDM = emailAMDM;
	}
	
	/**
	 * @return the placeNumber
	 */
	public Integer getPlaceNumber() {
		return placeNumber;
	}
	
	/**
	 * @param placeNumber the placeNumber to set
	 */
	public void setPlaceNumber(Integer placeNumber) {
		this.placeNumber = placeNumber;
	}
	
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public void setTokenMail(String token) {
		this.tokenMail = cleanToken(token);
	}

	private String cleanToken(String token) {
		return token != null ? token.replace("-", "") : null;
	}
	
	public String getTokenMail() {
		return tokenMail;
	}

	public String getTokenPwd() {
		return tokenPwd;
	}

	public void setTokenPwd(String token) {
		this.tokenPwd = cleanToken(token);
	}
}
