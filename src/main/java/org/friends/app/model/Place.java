package org.friends.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.friends.app.model.Place.PlacePK;

@Entity
@IdClass(PlacePK.class)
@Table(name = "PLACES", uniqueConstraints = {@UniqueConstraint(columnNames={"EMAIL_OCCUPANT", "OCCUPATION_DATE"})})
@NamedQueries(value = {
		@NamedQuery(name=Place.QUERY_RESERVE_PLACE, query="update Place p set mailOccupant = :email where placeNumber = :placeNumber and occupationDate = :date")
})
public class Place {
	
	public static final int HEURE_CHANGEMENT_JOUR_RECHERCHE = 18;
	public static final String QUERY_RESERVE_PLACE = "reservePlace";

	@Id 
	@Column(name="ID")
	private Integer placeNumber;
	
	@Column(name="EMAIL_OCCUPANT")
	private String mailOccupant;

	@Id
	@Column(name="OCCUPATION_DATE")
	private String occupationDate;
	
	// Hibernate
	public Place() {}
	
	public Place(Integer number, String date) {
		placeNumber = number;
		occupationDate = date;
	}	
	
	public Place(Integer number, String emailOccupant, String date) {
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
	
	@Override
	public String toString() {
		return "Place [placeNumber=" + placeNumber + ", mailOccupant=" + mailOccupant + ", occupationDate="
				+ occupationDate + "]";
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
	
	@Embeddable
	public static class PlacePK implements Serializable {
		private static final long serialVersionUID = 1L;
		protected Integer placeNumber;
	    protected String occupationDate;

	    public PlacePK() {}

	    public PlacePK(Integer placeNumber, String occupationDate) {
	        this.placeNumber = placeNumber;
	        this.occupationDate = occupationDate;
	    }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((occupationDate == null) ? 0 : occupationDate.hashCode());
			result = prime * result + ((placeNumber == null) ? 0 : placeNumber.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PlacePK other = (PlacePK) obj;
			if (occupationDate == null) {
				if (other.occupationDate != null)
					return false;
			} else if (!occupationDate.equals(other.occupationDate))
				return false;
			if (placeNumber == null) {
				if (other.placeNumber != null)
					return false;
			} else if (!placeNumber.equals(other.placeNumber))
				return false;
			return true;
		}

		public Integer getPlaceNumber() {
			return placeNumber;
		}

		public void setPlaceNumber(Integer placeNumber) {
			this.placeNumber = placeNumber;
		}

		public String getOccupationDate() {
			return occupationDate;
		}

		public void setOccupationDate(String occupationDate) {
			this.occupationDate = occupationDate;
		}
	}
}
