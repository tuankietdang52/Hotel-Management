package CustomClass;

import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateParse {
    /**
     * @param inputDate: dd/MM/yyyy
     */
    public static @NotNull LocalDateTime getLocalDateTime(String inputDate) throws DateTimeException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime date;

        date = LocalDateTime.parse(inputDate, dateFormat);

        return date;
    }


    public static @NotNull LocalDate getLocalDate(String inputDate) throws DateTimeException{
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;

        date = LocalDate.parse(inputDate, dateFormat);

        return date;
    }
}
