/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iTecLoader;

/**
 *
 * @author Oleg
 */
public class cc {

    public static String encrypt(String s, int key) {
        String returnValue = "";
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            c = encrypt(c, key);
            returnValue += String.valueOf(c);
        }
        return returnValue;
    }
    
    public static String encryptConfig(String s, int key) {
        String returnValue = "";
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            c = encryptConfig(c, i);
            returnValue += String.valueOf(c);
        }
        return returnValue;
    }

    public static byte[] encrypt(byte[] buffer, int key) {

        for (int i = 0; i < buffer.length; ++i) {
            byte c = (byte) buffer[i];
            c = encrypt(c, key);
            buffer[i] = c;
        }

        return buffer;
    }

    public static String decrypt(String s, int key) {
        String returnValue = "";
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            c = decrypt(c, key);
            returnValue += String.valueOf(c);
        }
        return returnValue;
    }
    
    public static String decryptConfig(String s, int key) {
        String returnValue = "";
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            c = decryptConfig(c, i);
            returnValue += String.valueOf(c);
        }
        return returnValue;
    }

    public static char encrypt(char c, int key) {
        if (c >= 'A' && c <= 'Z') {
            c = rotate(c, key);
        }
        return c;
    }
    
     private static char encryptConfig(char c, int key) {
        if ((c >= '!' && c <= '~') ) {
            c = rotateConfig(c, key);
        }
        return c;
    }

    public static byte encrypt(byte c, int key) {
        if ((c >= 'A' && c <= 'Z') ) {
            c = rotate(c, key);
        }
        return c;
    }

    public static char decrypt(char c, int key) {
        if (c < 'A' || c > 'Z') {
            return c;
        } else {
            return rotate(c, -key);
        }
    }
    
    private static char decryptConfig(char c, int key) {
         if (c < '!' || c > '~') {
            return c;
        } else {
            return rotateConfig(c, -key);
        }
    }

    private static char rotate(char c, int key) {
        key = key % 26;
        int n = (c - 'A' + key) % 26;
        if (n < 0) {
            n += 26;
        }
        return (char) (n + 'A');
    }
    
    private static char rotateConfig(char c, int key) {
        key = key % 94;
        int n = (c - '!' + key) % 94;
        if (n < 0) {
            n += 94;
        }
        return (char) (n + '!');
    }
    
     private static byte rotate(byte c, int key) {
        key = key % 26;
        int n = (c - 'A' + key) % 26;
        if (n < 0) {
            n += 26;
        }
        return (byte) (n + 'A');
    }
}

