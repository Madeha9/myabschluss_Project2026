package at.madeha.intelliinvoice.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* will implement in the service class
 * Helper methods for date calculations
 * Used in service/validation will extract the lon method to a separated class here
 */
public class DateUtils {

    // convert LocalDate to String
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    // convert String to LocalDate
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
    }
}

