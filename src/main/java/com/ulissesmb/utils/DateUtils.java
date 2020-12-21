package com.ulissesmb.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

	public static LocalDateTime setZoneBrasil(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
	}

}
