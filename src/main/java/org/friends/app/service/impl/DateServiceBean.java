package org.friends.app.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.friends.app.service.DateService;
import org.friends.app.util.DateUtil;
import org.springframework.stereotype.Service;

/**
 * Gestion des dates comportant de la logique.
 * 
 * @author michael
 *
 */
@Service
public class DateServiceBean implements DateService {

	private int HOUR_CHANGE_PARTAGE = 15;

	/**
	 * Indique le prochain jour reservable à compter de maintenant. Si c'est le
	 * matin, c'est aujourd'hui, si le jour est ouvrable. Passé une certaine
	 * heure de la journée, c'est le lendemain, ou le prochain jour ouvrable.
	 */
	@Override
	public LocalDate getWorkingDay() {
		LocalDateTime now = LocalDateTime.now(DateUtil.EUROPE_PARIS);
		LocalDate back = now.toLocalDate();
		if (now.getHour() > HOUR_CHANGE_PARTAGE || !isWorkingDay(back)) {
			back = getNextWorkingDay(now.toLocalDate());
		}
		return back;
	}

	/**
	 * TODO : Gestion des vacances
	 */
	private boolean isWorkingDay(LocalDate dateRecherche) {
		return !(DayOfWeek.SATURDAY.equals(dateRecherche.getDayOfWeek())
				|| DayOfWeek.SUNDAY.equals(dateRecherche.getDayOfWeek()));
	}

	/**
	 * Get next working day (excluding weekend), based on now. TODO : excluding
	 * holidays.
	 * 
	 * @return
	 */
	@Override
	public LocalDate getNextWorkingDay() {
		return getNextWorkingDay(DateUtil.now());
	}

	/**
	 * 
	 * @param base
	 * @return
	 */
	@Override
	public LocalDate getNextWorkingDay(LocalDate base) {
		int toAdd;
		switch (base.getDayOfWeek()) {
		case FRIDAY:
			toAdd = 3;
			break;
		case SATURDAY:
			toAdd = 2;
			break;
		default:
			toAdd = 1;
			break;
		}
		return base.plusDays(toAdd);
	}
}
