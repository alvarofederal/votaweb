package br.com.votacao.votaweb.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvetDates {
    public String convertLocalDateTime(LocalDateTime localDateTime) {
        LocalDateTime datetime1 = localDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = datetime1.format(format);
        return formatDateTime;
    }
}
