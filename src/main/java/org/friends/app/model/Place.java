/**
 * EcoParking v1.2
 * 
 * Application that allows management of shared parking 
 * among multiple users.
 * 
 * This file is copyrighted in LGPL License (LGPL)
 * 
 * Copyright (C) 2016 M. Lefevre, A. Tamditi, W. Verdeil
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.friends.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PLACES")
@NamedQueries(value = {
		@NamedQuery(name = Place.QUERY_RESERVE_PLACE, query = "update Place p set usedBy = :email where id = :id") })
public class Place {

	public static final int HEURE_CHANGEMENT_JOUR_RECHERCHE = 18;
	public static final String QUERY_RESERVE_PLACE = "reservePlace";

	@Id
	@Embedded
	private PlacePK id;

	@Column(name = "EMAIL_OCCUPANT")
	private String usedBy;

	// Hibernate
	public Place() {
	}

	public Place(Integer number, String date) {
		id = new PlacePK(number, date);
	}

	public Place(Integer number, String date, String email) {
		this(number, date);
		usedBy = email;
	}

	/**
	 * @return the placeNumber
	 */
	public Integer getPlaceNumber() {
		return id.placeNumber;
	}

	@Override
	public String toString() {
		return "Place [placeNumber=" + getPlaceNumber() + ", usedBy=" + usedBy + ", occupationDate="
				+ getOccupationDate() + "]";
	}

	/**
	 * @return the occupiedBy
	 */
	public String getUsedBy() {
		return usedBy;
	}

	/**
	 * @param occupiedBy
	 *            the occupiedBy to set
	 */
	public void setUsedBy(String occupiedBy) {
		this.usedBy = occupiedBy;
	}

	/**
	 * @return the occupationDate
	 */
	public String getOccupationDate() {
		return id.occupationDate;
	}

	@Embeddable
	public static class PlacePK implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "ID")
		protected Integer placeNumber;

		@Column(name = "OCCUPATION_DATE")
		protected String occupationDate;

		public PlacePK() {
		}

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

	public boolean isFree() {
		return usedBy == null || "".equalsIgnoreCase(usedBy.trim());
	}
}
