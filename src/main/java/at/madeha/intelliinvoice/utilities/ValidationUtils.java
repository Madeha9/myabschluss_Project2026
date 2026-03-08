package at.madeha.intelliinvoice.utilities;

/*
 * no need objects to use them you can call them without object
 * Helper methods for common checks in the validation when the methods so long is ı can extract to external class here
 */
public class ValidationUtils {

    // check if a string is null or empty
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // check if a number is positive
    public static boolean isPositive(double number) {
        return number > 0;
    }
}
