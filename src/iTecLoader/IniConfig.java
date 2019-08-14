/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iTecLoader;

/**
 *
 * @author Oleg
 */
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class IniConfig {

    private static IniConfig SINGLETON;

    public static void load(String path, String string) {
    }
    private HashMap fHashMap = null;
    private HashMap fHashMapOut = null;

    static {
        SINGLETON = new IniConfig();
    }

    public IniConfig() {
        fHashMap = new LinkedHashMap();
        fHashMapOut = new LinkedHashMap();
    }

    public static void put(String key, Object data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else {
            SINGLETON.fHashMap.put(key, data);
        }
    }

    public static String get(String key) {
        return (String) SINGLETON.fHashMap.get(key);
    }
    
    public static String getConfig(String key) {
        return (String) SINGLETON.fHashMapOut.get(key);
    }

    public static Object get(String key, Object deflt) {
        Object obj = SINGLETON.fHashMap.get(key);
        if (obj == null) {
            return deflt;
        } else {
            return obj;
        }
    }

    public static void getFile(ZipFile zippy, ZipEntry e) throws IOException {
        File fileOut = new File(PathConst.userDir, "Config");
        byte[] b = new byte[8092];
        FileOutputStream os = new FileOutputStream(fileOut);
        InputStream is = zippy.getInputStream(e);
        int n = 0;
        while ((n = is.read(b)) > 0) {
            os.write(b, 0, n);
        }
        is.close();
        os.close();
    }

    public static void load(File dir, String fname) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dir, fname))));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(";")) {
                    continue;
                }
                addProperty(line);
            }
        } finally {
            br.close();
        }
    }

    public static void loadEnCryption(String xfile) throws IOException {

        File source = new File(xfile);

        SINGLETON.fHashMapOut = (HashMap) FileLoader.loadData(source);
        SINGLETON.fHashMap.clear();

        Set set = SINGLETON.fHashMapOut.keySet();
        if (set != null) {
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = iterator.next().toString();
                String value = getConfig(key).toString();

                key = cc.decryptConfig(key, 10);
                value = cc.decryptConfig(value, 10);

                SINGLETON.fHashMap.put(key, value);
            }
        }

    }

    private static void load(File source) throws IOException {

        java.io.BufferedReader br = new java.io.BufferedReader(new InputStreamReader(new java.io.FileInputStream(source), "UTF8"));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(";")) {
                    continue;
                }
                addProperty(line);
            }
        } finally {

            br.close();

        }
    }

    public static void clear() {
        SINGLETON.fHashMap.clear();
        SINGLETON.fHashMapOut.clear();
    }

    private static void addProperty(String line) {
        int equalIndex = line.indexOf("=");
        if (equalIndex > 0) {
            String name = line.substring(0, equalIndex + 1).trim();
            String value = line.substring(equalIndex + 1).trim();

            put(name, value);
        } else if (line.startsWith("[")) {
            put(line, "");
        }
    }

    public static void EncryptionConfigFile(File source, File target) throws IOException {

        load(source);

        Set set = SINGLETON.fHashMap.keySet();
        if (set != null) {
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = iterator.next().toString();
                String value = get(key).toString();

                key = cc.encryptConfig(key, 10);
                value = cc.encryptConfig(value, 10);

                SINGLETON.fHashMapOut.put(key, value);
            }
        }

        storeData(SINGLETON.fHashMapOut, target);

    }

    private static void storeData(Serializable data, java.io.File fX) {
        try {
            ObjectOutputStream writeOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fX)));
            writeOut.writeObject(data);
            writeOut.close();

        } catch (IOException exc) {
            System.out.print(exc.getMessage());
        }
    }
}

