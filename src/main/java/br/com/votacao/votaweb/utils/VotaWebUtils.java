package br.com.votacao.votaweb.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class VotaWebUtils {

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

	public static String format(String sourceDate, Long minuto) throws ParseException {
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(tz);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance(tz);
		calendar.setTime(sdf.parse(sourceDate));
		calendar.add(Calendar.MINUTE, converLongToInt(minuto));
		return sdf.format(calendar.getTime());
	}

	public static int converLongToInt(Long numberLong) {
		int numerInt = numberLong.intValue();
		return numerInt;
	}

	public static Long convertIntToLong(int id) {
		return Long.valueOf(id);
	}
	
	public static int convertLongToId(Long id) {
		Long l= new Long(id);  
		return l.intValue();  
	}
}
