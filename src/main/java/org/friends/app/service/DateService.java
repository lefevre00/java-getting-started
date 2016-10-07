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
package org.friends.app.service;

import java.time.LocalDate;

public interface DateService {

	/**
	 * Indique le prochain jour reservable à compter de maintenant. Si c'est le
	 * matin, c'est aujourd'hui, si le jour est ouvrable. Passé une certaine
	 * heure de la journée, c'est le lendemain, ou le prochain jour ouvrable.
	 */
	LocalDate getWorkingDay();

	/**
	 * Get next working day (excluding weekend), based on now. TODO : excluding
	 * holidays.
	 * 
	 * @return
	 */
	LocalDate getNextWorkingDay();

	/**
	 * 
	 * @param base
	 * @return
	 */
	LocalDate getNextWorkingDay(LocalDate base);
	
	/**
	 * Renvoie true si la date passé en paramètre est une date 'reservable'
	 * @param date
	 * @return
	 */
	boolean isSearchDateValid(LocalDate date);

}