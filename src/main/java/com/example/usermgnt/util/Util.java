package com.example.usermgnt.util;

public class Util {
    public static boolean hasValue(String s){
        return (s != null) && !s.isEmpty() ;

    }

    /** use method to hide sensitive part of a string like a debit card digits or phone number
     * @param input input string that you want to hide it's characters
     * @return a string with the last four characters visible while substituting the rest with an asterisk (*)
     */
    public static String hideChar(String input){
        String last4Chars = input.substring(7);
        String firstChars = input.substring(0, 7);
        String hiddenChars = firstChars.replaceAll("[a-z0-9A-Z]", "*");
        return hiddenChars + last4Chars;
    }


}
