package br.com.votacao.votaweb.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvetDates {
    public static String convertLocalDateTime(LocalDateTime localDateTime) {
        LocalDateTime datetime1 = localDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = datetime1.format(format);
        return formatDateTime;
    }

//    public static SimpleDateFormat convertFormatDate(LocalDateTime dataRecebida){
//        DateTime Formatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.parse(entry.getValue(), formatter);
//        String formatDateTime = now.format(formatter);
//        Date d = (Date) formatter.parse(formatDateTime);
//        DateTime
//        return dateFormat.format(date);
//    }
}
