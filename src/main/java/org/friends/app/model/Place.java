package org.friends.app.model;

public class Place {
	
	private Integer placeNumber;
	private String mailOccupant;
	private String occupationDate;
	
	public Place(int number, String date) {
		placeNumber = number;
		occupationDate = date;
	}	
	
	public Place(int number, String emailOccupant, String date) {
		placeNumber = number;
		mailOccupant = emailOccupant;
		occupationDate = date;
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
		return mailOccupant;
	}
	
	/**
	 * @param occupiedBy the occupiedBy to set
	 */
	public void setOccupiedBy(String occupiedBy) {
		this.mailOccupant = occupiedBy;
	}
	
	/**
	 * @return the occupationDate
	 */
	public String getOccupationDate() {
		return occupationDate;
	}
	
	/**
	 * @param occupationDate the occupationDate to set
	 */
	public void setOccupationDate(String occupationDate) {
		this.occupationDate = occupationDate;
	}
}
