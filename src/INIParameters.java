/*
 * Copyright © «Технополис» 2005. Авторские права принадлежат ООО «Технополис» г.Красноярск. Директор Козяривский Игорь Анатольевич.
 */
package iTecLoader;

import java.io.*;
import javax.swing.JOptionPane;


/**
 *
 * @author ShOleg
 */
public class INIParameters {

    private String nametask;
    public static boolean isParameterConfig;

    public INIParameters(String nametask) {
        this.nametask = nametask;
    }

    public static boolean isIsParameterConfig() {
        return isParameterConfig;
    }

    public void RunAction() throws FileNotFoundException, IOException {

        boolean isFind = isFileINIfind();

        if (isFind == false) {
            JOptionPane.showMessageDialog(null , "Программа: Файл " + PathConst.userDir + File.separator + nametask + " не найден.", "Внимание", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        isParameterConfig = isParameterConfig();

        if (isParameterConfig == false) {
            LoadNoConfig();
        } else {
            LoadConfig();
        }

    }

    private boolean isFileINIfind() {
        File fileINI = new File(PathConst.userDir, nametask);

        if (fileINI.exists()) {
            return true;
        } else {
            return false;
        }

    }

    private boolean isParameterConfig() throws FileNotFoundException, IOException {
        boolean ret = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(PathConst.userDir, nametask))));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(";")) {
                    continue;
                }
                if (line.indexOf("Config") != -1) {
                    ret = true;
                    break;
                }
            }
        } finally {
            br.close();
        }
        return ret;
    }

    private void LoadNoConfig() throws IOException {
        IniSettings.load(PathConst.userDir, nametask );
    }

    private void LoadConfig() throws IOException {
        IniSettings.load(PathConst.userDir, nametask );

        if ( IniSettings.get(IniConst.CONFIG) != null && ! IniSettings.get(IniConst.CONFIG).equals("")) {
           
            IniConfig.loadEnCryption(IniSettings.get(IniConst.CONFIG));
            
            IniSettings.put(IniConst.APP_SERVER, IniConfig.get(IniConst.APP_SERVER));
            IniSettings.put(IniConst.DATA_SERVER, IniConfig.get(IniConst.DATA_SERVER));
            IniSettings.put(IniConst.FILE_SERVER, IniConfig.get(IniConst.FILE_SERVER));
        }

    }

    public static void Load(String nametask) {
        INIParameters load = new INIParameters(nametask);
        try {
            load.RunAction();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
