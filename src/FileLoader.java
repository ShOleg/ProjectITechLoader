/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iTecLoader;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;

public class FileLoader {
    public static Object loadData(File inputFile) {
        Object returnValue = null;

        if (inputFile.exists()) {
            if (inputFile.isFile()) {
                if (inputFile.length() > 0) {
                    try {
                        ObjectInputStream readIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
                        returnValue = readIn.readObject();
                        readIn.close();
                    }
                    catch (ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Обратитесь к администратору", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    catch (java.io.StreamCorruptedException ex1) {
                        JOptionPane.showMessageDialog(null, "Обратитесь к администратору", ex1.getMessage(), JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    catch (IOException ex2) {
                        JOptionPane.showMessageDialog(null, "Обратитесь к администратору", ex2.getMessage(), JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                } else {
                    return returnValue;
                }
            } else {
                System.err.println(inputFile + " is a directory.");
            }
        } else {
//      JOptionPane.showMessageDialog(technopolis.MainFrame.getDeskTop(), "Файл " + inputFile + "не найден. Обратитесь к администратору", "Ошибка открытия профиля ", JOptionPane.ERROR_MESSAGE);
        }
        return returnValue;
    }


    
}
