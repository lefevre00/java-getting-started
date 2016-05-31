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

}