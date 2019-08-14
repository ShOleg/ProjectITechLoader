/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iTecLoader;

/**
 *
 * @author ShOleg
 */
public class RunLoader {

    /**
     * @param args the command line arguments 
     */
    public static void main(String[] args) {
        String mainClass = "technopolis.RunITec";
        String iniFile = "ТехКорпорация.ini";
        boolean showListIni = true;

        if (args.length == 0) {
            mainClass = "technopolis.RunITec";
            iniFile = "ТехКорпорация.ini";
            showListIni = true;
        } else if (args.length == 1) {
            if (args[0].lastIndexOf("-K") != -1 || args[0].lastIndexOf("-k") != -1 || args[0].lastIndexOf("-К") != -1|| args[0].lastIndexOf("-к") != -1) {
                mainClass = "technopolis.Designer.RunMetaDesigner";
                iniFile = "Конструктор.ini";
                showListIni = false;
            } else if (args[0].lastIndexOf(".ini") != -1) {
                mainClass = "technopolis.RunITec";
                iniFile = args[0].substring(1, args[0].length());
                showListIni = false;
            } else {
                return;
            }
        }

        Application application = new Application(mainClass, iniFile, showListIni);
        application.RunAction();

    }
}
