package org.friends.app.model;

public class Place {
	
	private Integer placeNumber;
	private String occupiedBy;
	private String occupationDate;
	private boolean free;
	
	public Place(int n, boolean f) {
		placeNumber = n;
		free = f;
	}	
	
	public Place(int n, boolean f, String o) {
		placeNumber = n;
		free = f;
		occupationDate = o;
	}	
	
	public Place(int n, String emailOccupant, String o) {
		placeNumber = n;
		free = emailOccupant != null ? false : true;
		occupationDate = o;
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
		return occupiedBy;
	}
	
	/**
	 * @param occupiedBy the occupiedBy to set
	 */
	public void setOccupiedBy(String occupiedBy) {
		this.occupiedBy = occupiedBy;
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

	public boolean isFree() {
		return free;
	}
	
}
