package dao;

import java.util.StringJoiner;

public class Common {

    public static String getMessage(String[] args) {
        if(args.length < 2)
            return "Hello World!";
        return joinStrings(args, " ", 1);
    }

    public static String joinStrings(String[] args, String delimiter, int startIndex) {
        int length = args.length;
        if(length ==0)
            return "";
        if(length < startIndex)
            return "";
        StringJoiner words = new StringJoiner(delimiter);
        for (int i = startIndex; i < length; i++) {
            words.add(args[i]);
        }
        return words.toString();
    }

    public static String getSeverity(String[] args) {
        if(args.length < 1)
            return "info";
        return args[0];
    }

    public static String getRouting(String[] args) {
        if(args.length < 1)
            return "anonymous.info";
        return args[0];
    }
}
