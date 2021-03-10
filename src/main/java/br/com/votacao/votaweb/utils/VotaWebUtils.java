package br.com.votacao.votaweb.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class VotaWebUtils {

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	public static Timestamp convertStringToTimestamp(String timestampString) {
		Timestamp ts = Timestamp.valueOf(timestampString);
		return ts;
	}

	public static String nowString() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(new Date(currentDate.getTime()));
	}
	
	public static String convertToDatabaseColumn(LocalDateTime value) {
		return (value != null) ? value.format(dateTimeFormatter) : null;
	}

	public static String format(String sourceDate, Long hours) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate));
		calendar.add(Calendar.MINUTE, converLongToInt(hours));
		return sdf.format(calendar.getTime());
	}

	public static int converLongToInt(Long numberLong) {
		int numerInt = numberLong.intValue();
		return numerInt;
	}
}
