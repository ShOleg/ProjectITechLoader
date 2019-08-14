package iTecLoader;



import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class IniSettings {
    private static IniSettings SINGLETON;
    private HashMap fHashMap = null;

    static {
        SINGLETON = new IniSettings();
    }

    public IniSettings() {
        fHashMap = new LinkedHashMap();
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

    public static Object get(String key, Object deflt) {
        Object obj = SINGLETON.fHashMap.get(key);
        if (obj == null) {
            return deflt;
        } else {
            return obj;
        }
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
        }
        finally {
            br.close();
        }
    }

    public static void clear() {
        SINGLETON.fHashMap.clear();
    }

    private static void addProperty(String line) {
        int equalIndex = line.indexOf("=");
        if (equalIndex > 0) {
            String name = line.substring(0, equalIndex + 1).trim();
            String value = line.substring(equalIndex + 1).trim();

            // потом убрать
//            if (name.equals("DataBaseServer=")) {
//                name = "DataSource=";
//            }

            put(name, value);
        } else if (line.startsWith("[")) {
            put(line, "");
        }

    }

    public static final void save(File dir, String nameFile) throws FileNotFoundException, IOException {
        FileOutputStream fOut = new FileOutputStream(new File(dir, nameFile));
        PrintWriter oFOut = new PrintWriter(fOut);
        Set set = SINGLETON.fHashMap.keySet();
        if (set != null) {
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = iterator.next().toString();
                String nameText = get(key).toString();
                oFOut.println(key + nameText);
            }
        }
        oFOut.close();
        fOut.close();
    }


}
