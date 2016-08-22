package org.friends.app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.friends.app.util.DateUtil;
import org.friends.app.view.Application;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class StartParking {

	private static final String EMAIL_PHILIPPE = "philippe.martins@amdm.fr";
	private static final String EMAIL_JPC = "jean-pierre.cluzel@amdm.fr";
	private static final String EMAIL_DAMIEN = "damien.urvoix@amdm.fr";

	private static int userIndex = 1;

	public static void main(String[] args) {
		System.setProperty("PORT", "9090");
		createTestData();
		new Application().start(DeployMode.TEST);
	}

	private static void createTestData() {
		Path folderPath = Paths.get("target", "test-classes", "config");
		folderPath.toFile().mkdirs();
		Path filePath = folderPath.resolve("db-test-data.sql");
		try {
			Files.deleteIfExists(filePath);
			try (FileWriter fw = new FileWriter(filePath.toFile())) {
				try (BufferedWriter bw = new BufferedWriter(fw)) {
					for (String line : getSql()) {
						bw.write(line);
					}
				} catch (IOException e) {
					throw e;
				}
			} catch (IOException e) {
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot create inid db script");
			System.exit(1);
		}
	}

	private static List<String> getSql() {
		List<String> lines = new ArrayList<>();
		lines.add(createUser("abdel.tamditi@amdm.fr", "at", 133));
		lines.add(createUser("william.verdeil@amdm.fr", "wv", 141));
		lines.add(createUser("michael.lefevre@amdm.fr", "ml", 87));
		lines.add(createUser(EMAIL_DAMIEN, "du"));
		lines.add(createUser(EMAIL_JPC, "jpc"));
		lines.add(createUser("marc.coniglio@amdm.fr", "mc", 33));
		lines.add(createUser("jean-yves.sironneau@amdm.fr", "jpc"));
		lines.add(createUser(EMAIL_PHILIPPE, "pm", 94));
		lines.add(createUser("thibaut.bourelly@amdm.fr", "tb", 14));
		lines.add(createUser("dominique.gay@amdm.fr", "dg", 66));
		lines.add(createUser("vincent.mathon@amdm.fr", "vm", 99));
		lines.add(createUser("sylvain.verneau@amdm.fr", "sv", 144));

		LocalDate timePoint = DateUtil.now();
		String today = DateUtil.dateToString(timePoint);
		String tomorrow = DateUtil.dateToString(timePoint.plusDays(1));
		String yesterday = DateUtil.dateToString(timePoint.minusDays(1));

		// Place libre aujourd'hui
		lines.add(insertPlace(1, today));
		// place occupée aujourd'hui
		lines.add(insertPlace(2, today, EMAIL_DAMIEN));
		// Place libre hier
		lines.add(insertPlace(34, yesterday));
		// Place libre demain
		lines.add(insertPlace(35, tomorrow));
		// Place occupee hier
		lines.add(insertPlace(36, yesterday, EMAIL_DAMIEN));
		// Place libre demain
		lines.add(insertPlace(37, tomorrow));

		lines.add(insertPlace(133, yesterday, EMAIL_DAMIEN));
		lines.add(insertPlace(132, yesterday, EMAIL_JPC));
		lines.add(insertPlace(131, yesterday, EMAIL_PHILIPPE));
		lines.add(insertPlace(130, yesterday));

		lines.add(insertPlace(80, today));
		lines.add(insertPlace(81, today));
		lines.add(insertPlace(82, today));
		lines.add(insertPlace(133, today, EMAIL_DAMIEN));

		lines.add(insertPlace(120, tomorrow, EMAIL_DAMIEN));
		lines.add(insertPlace(121, tomorrow));
		lines.add(insertPlace(122, tomorrow, EMAIL_PHILIPPE));
		lines.add(insertPlace(133, tomorrow));

		return lines;
	}

	private static String insertPlace(int place, String date) {
		return insertPlace(place, date, null);
	}

	private static String insertPlace(int place, String date, String email) {
		return "insert into Places (ID, OCCUPATION_DATE, EMAIL_OCCUPANT) values (" + place + ", '" + date + "', "
				+ (email != null ? "'" + email + "'" : "null") + ");";
	}

	private static String createUser(String userName, String secret) {
		return createUser(userName, secret, null);
	}

	private static String createUser(String userName, String secret, Integer place) {
		return "insert into Users (ID, EMAIL, PASSWORD, PLACE_ID) values (" + userIndex++ + ", '" + userName + "', '"
				+ md5(secret) + "', " + (place != null ? place : "null") + ");";
	}

	private static String md5(String pwd) {
		HashFunction hf = Hashing.md5();
		HashCode hc = hf.newHasher().putString(pwd, Charset.defaultCharset()).hash();
		return hc.toString();
	}

}
