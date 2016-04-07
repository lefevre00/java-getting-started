package org.friends.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * A user of the application.
 * @author michael lefevre
 */
@Entity
@Table(name = "USERS")
@NamedQueries(value = {
		@NamedQuery(name=User.QUERY_FIND_USER_BY_MAIL, query="select p from User p where emailAMDM = :email"),
		@NamedQuery(name=User.QUERY_FIND_USER_BY_TOKEN_MAIL, query="select p from User p where tokenMail = :token"),
		@NamedQuery(name=User.QUERY_FIND_USER_BY_TOKEN_PASSWORD, query="select p from User p where tokenPassword = :token")
})
public class User {
	
	public static final String QUERY_FIND_USER_BY_MAIL = "findUserByEmail";
	public static final String QUERY_FIND_USER_BY_TOKEN_MAIL = "findUserByTokenMail";
	public static final String QUERY_FIND_USER_BY_TOKEN_PASSWORD = "findUserByTokenPassword";
	
	@Id 
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="ID")
	private Integer id;
	
	@Column(name="EMAIL")
	private String emailAMDM;
	
	@Column(name="PLACE_ID")
	private Integer placeNumber;
	
	@Column(name="PASSWORD")
	private String pwd;
	
	@Column(name="TOKEN_MAIL")
	private String tokenMail;
	
	@Column(name="TOKEN_PASSWORD")
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
