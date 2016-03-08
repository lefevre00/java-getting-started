package org.friends.app.model;

public class Place {
	
	private Integer placeNumber;
	private String mail_occupant;
	private String occupation_date;
	private boolean free;
	
	public Place(int n, boolean f) {
		placeNumber = n;
		free = f;
	}	
	
	public Place(int n, boolean f, String o) {
		placeNumber = n;
		free = f;
		occupation_date = o;
	}	
	
	public Place(int n, String emailOccupant, String o) {
		placeNumber = n;
		free = emailOccupant != null ? false : true;
		occupation_date = o;
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
	 * @return the occupiedBy
	 */
	public String getOccupiedBy() {
		return mail_occupant;
	}
	
	/**
	 * @param occupiedBy the occupiedBy to set
	 */
	public void setOccupiedBy(String occupiedBy) {
		this.mail_occupant = occupiedBy;
	}
	
	/**
	 * @return the occupationDate
	 */
	public String getOccupationDate() {
		return occupation_date;
	}
	
	/**
	 * @param occupationDate the occupationDate to set
	 */
	public void setOccupationDate(String occupationDate) {
		this.occupation_date = occupationDate;
	}

	public boolean isFree() {
		return free;
	}
	
}
