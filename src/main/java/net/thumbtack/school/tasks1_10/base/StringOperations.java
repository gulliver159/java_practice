package net.thumbtack.school.tasks1_10.base;

public class StringOperations {

    public static int getSummaryLength(String[] strings) {
        int N = 0;
        for (String str : strings) {
            N += str.length();
        }
        return N;
    }

    public static String getFirstAndLastLetterString(String string) {
        return "" + (string.charAt(0)) + string.charAt(string.length() - 1);
    }

    public static boolean isSameCharAtPosition(String string1, String string2, int index) {
        return string1.charAt(index) == string2.charAt(index);
    }

    public static boolean isSameFirstCharPosition(String string1, String string2, char character) {
        return string1.indexOf(character) == string2.indexOf(character);
    }

    public static boolean isSameLastCharPosition(String string1, String string2, char character) {
        return string1.lastIndexOf(character) == string2.lastIndexOf(character);
    }

    public static boolean isSameFirstStringPosition(String string1, String string2, String str) {
        return string1.indexOf(str) == string2.indexOf(str);
    }

    public static boolean isSameLastStringPosition(String string1, String string2, String str) {
        return string1.lastIndexOf(str) == string2.lastIndexOf(str);
    }

    public static boolean isEqual(String string1, String string2) {
        return string1.equals(string2);
    }

    public static boolean isEqualIgnoreCase(String string1, String string2) {
        return isEqual(string1.toLowerCase(), string2.toLowerCase());
    }

    public static boolean isLess(String string1, String string2) {
        return string1.compareTo(string2) < 0;
    }

    public static boolean isLessIgnoreCase(String string1, String string2) {
        return isLess(string1.toLowerCase(), string2.toLowerCase());
    }

    public static String concat(String string1, String string2) {
        return string1.concat(string2);
    }

    public static boolean isSamePrefix(String string1, String string2, String prefix) {
        return string1.startsWith(prefix) && string2.startsWith(prefix);
    }

    public static boolean isSameSuffix(String string1, String string2, String suffix) {
        return string1.endsWith(suffix) && string2.endsWith(suffix);
    }

    public static String getCommonPrefix(String string1, String string2) {
//      Возвращает самое длинное общее “начало” двух строк. Если у строк нет общего начала, возвращает пустую строку.
        String leastStr = "";
        if (isLess(string1, string2)) {
            leastStr = string1;
        } else {
            leastStr = string2;
        }
        StringBuilder commonPrefix = new StringBuilder();

        for (int i = 0; i < leastStr.length(); i++) {
            if (string1.charAt(i) == string2.charAt(i)) {
                commonPrefix.append(leastStr.charAt(i));
            } else {
                return commonPrefix.toString();
            }
        }
        return commonPrefix.toString();
    }

    public static String reverse(String string) {
        return new StringBuilder(string).reverse().toString();
    }

    public static boolean isPalindrome(String string) {
        return isEqual(string, reverse(string));
    }

    public static boolean isPalindromeIgnoreCase(String string) {
        return isPalindrome(string.toLowerCase());
    }

    public static String getLongestPalindromeIgnoreCase(String[] strings) {
        String max_palin = "";
        for (String str : strings) {
            if (isPalindromeIgnoreCase(str)) {
                if (str.length() > max_palin.length())
                    max_palin = str;
            }
        }
        return max_palin;
    }

    public static boolean hasSameSubstring(String string1, String string2, int index, int length) {
        if (string1.length() < index + length || string2.length() < index + length) {
            return false;
        }
        return isEqual(string1.substring(index, index + length - 1), string2.substring(index, index + length - 1));
    }

    public static boolean isEqualAfterReplaceCharacters(String string1, char replaceInStr1, char replaceByInStr1, String string2, char replaceInStr2, char replaceByInStr2) {
        return isEqual(string1.replace(replaceInStr1, replaceByInStr1), string2.replace(replaceInStr2, replaceByInStr2));
    }

    public static boolean isEqualAfterReplaceStrings(String string1, String replaceInStr1, String replaceByInStr1, String string2, String replaceInStr2, String replaceByInStr2) {
        return isEqual(string1.replaceAll(replaceInStr1, replaceByInStr1), string2.replaceAll(replaceInStr2, replaceByInStr2));
    }

    public static boolean isPalindromeAfterRemovingSpacesIgnoreCase(String string) {
        return isPalindromeIgnoreCase(string.replace(" ", ""));
    }

    public static boolean isEqualAfterTrimming(String string1, String string2) {
        return isEqual(string1.trim(), string2.trim());
    }

    public static String makeCsvStringFromDoubles(double[] array) {
        return makeCsvStringBuilderFromDoubles(array).toString();
    }

    public static StringBuilder makeCsvStringBuilderFromDoubles(double[] array) {
        StringBuilder result = new StringBuilder();
        if (array.length != 0) {
            result.append(String.format("%.2f", array[0]));
            for (int i = 1; i < array.length; i++) {
                result.append(",").append(String.format("%.2f", array[i]));
            }
        }
        return result;
    }

    public static String makeCsvStringFromInts(int[] array) {
        return makeCsvStringBuilderFromInts(array).toString();
    }

    public static StringBuilder makeCsvStringBuilderFromInts(int[] array) {
        StringBuilder result = new StringBuilder();
        if (array.length != 0) {
            result.append(array[0]);
            for (int i = 1; i < array.length; i++) {
                result.append(",").append(array[i]);
            }
        }
        return result;
    }

    public static StringBuilder removeCharacters(String string, int[] positions) {
        StringBuilder strBul = new StringBuilder(string);
        for (int i = positions.length - 1; i >= 0; i--) {
            strBul.deleteCharAt(positions[i]);
        }
        return strBul;
    }

    public static StringBuilder insertCharacters(String string, int[] positions, char[] characters) {
        StringBuilder strBul = new StringBuilder(string);
        for (int i = positions.length - 1; i >= 0; i--) {
            strBul.insert(positions[i], characters[i]);
        }
        return strBul;
    }

}
