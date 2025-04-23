/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author local_user
 */
public class IdManager {
    // Generates next sequential ID
     public static String nextId(String currentId) {
        Pattern pattern = Pattern.compile("^([A-Za-z]+)(\\d+)$");
        Matcher matcher = pattern.matcher(currentId);
        
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid ID format: " + currentId);
        }
        
        String prefix = matcher.group(1);
        String numberStr = matcher.group(2);
        int number = Integer.parseInt(numberStr);
        int paddingLength = numberStr.length();
        
        return String.format("%s%0" + paddingLength + "d", prefix, ++number);
    }

    // Creates new ID from prefix and number
    public static String createId(String prefix, int number) {
        if (prefix == null || prefix.isEmpty()) {
            throw new IllegalArgumentException("Prefix cannot be null or empty");
        }
        if (number < 0) {
            throw new IllegalArgumentException("Number cannot be negative");
        }
        return String.format("%s%03d", prefix, number);
    }

    // Combines multiple string components
    public static String combineId(String... components) {
        StringBuilder sb = new StringBuilder();
        for (String component : components) {
            if (component != null) {
                sb.append(component);
            }
        }
        return sb.toString();
    }
}
