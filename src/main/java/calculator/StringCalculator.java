package calculator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : 김윤호
 * @version : 1.0
 * @date : 2019-11-01 01:31
 */
public class StringCalculator {

    private static final String EMPTY_STRING = "";
    private static final String DEFAULT_DELIMITER = ",|:";
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.)\n(.*)");
    private static final Pattern NUMERIC = Pattern.compile("^[0-9]+$");
    private static final int CUSTOM_DELIMITER_INDEX = 1;
    private static final int CUSTOM_DELIMITER_REMOVE_INDEX = 2;

    public static int add(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        String[] values = split(removeCustomDelimiter(text), extractCustomDelimiter(text));
        isValid(values);

        return sum(values);
    }

    private static int sum(String[] values) {
        return Arrays.stream(values).map(value -> Integer.parseInt(value)).reduce(0, Integer::sum);
    }

    private static String[] split(String text, String customDelimiter) {
        if (customDelimiter.isEmpty()) {
            return text.split(DEFAULT_DELIMITER);
        }

        return text.split(String.format("%s|\\%s", DEFAULT_DELIMITER, customDelimiter));
    }

    private static String removeCustomDelimiter(String text) {
        Matcher matcher = getMatcher(text, CUSTOM_DELIMITER_PATTERN);
        if (matcher.find()) {
            return matcher.group(CUSTOM_DELIMITER_REMOVE_INDEX);
        }

        return text;
    }

    private static String extractCustomDelimiter(String text) {
        Matcher matcher = getMatcher(text, CUSTOM_DELIMITER_PATTERN);
        if (matcher.find()) {
            return matcher.group(CUSTOM_DELIMITER_INDEX);
        }

        return EMPTY_STRING;
    }

    private static void isValid(String... values) {
        for (String value : values) {
            positiveNumberElseThrow(value);
        }
    }

    private static void positiveNumberElseThrow(String value) {
        if (!getMatcher(value, NUMERIC).matches()) {
            throw new IllegalArgumentException("0 이상의 숫자만 입력이 가능합니다.");
        }
    }

    private static Matcher getMatcher(String text, Pattern pattern) {
        return pattern.matcher(text);
    }
}