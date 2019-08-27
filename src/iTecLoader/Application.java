package iTecLoader;

import java.awt.*;
import java.awt.Frame;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
//import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.*;

/**
 * <p>
 * Title: </p>123
 * <p>
 * Description: </p>
 * <p>
 * Copyright: Copyright (c) 2004</p>
 * <p>
 * Company: </p>
 *
 * @author ShOleg
 * @version 1.0
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public final class Application {

    public static java.net.URLClassLoader cloader;
    private int n = 0;
    private File currenDirLib;
    private String iniFile = "";
    private iTecLoader.Frame frame = null;
    private String startTask = "";
    private File currentDir = PathConst.userDir;
    private final boolean showListIni;

    public ClassLoader classLoader = Application.this.getClass().getClassLoader();

    @SuppressWarnings("UseSpecificCatch")
    public Application(String mainClass, String iniFile, boolean showListIni) {
        this.startTask = mainClass;
        this.iniFile = iniFile;
        this.showListIni = showListIni;
    }

    public void RunAction() {

        if (showListIni) {
            File[] files = getListIniFile();
            if (files != null && files.length > 1) {
                String title = "Выберите конфигурацию " + ("ТехКорпорации");
                DialogIni dialog = new DialogIni(title, files);
                dialog.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
                dialog.setVisible(true);
                this.iniFile = dialog.getIni();
                if (this.iniFile == null) {
                    System.exit(0);
                    return;
                }
            }
        }



        if (!new File(currentDir, iniFile).exists()) {
            initPropDialog();
        } else {
            INIParameters.Load(iniFile);

            if (CheckPathAccess()) {
                File userDirLibs = new java.io.File(currentDir, "/Lib");

                if (userDirLibs.exists()) {
                    СинронизацияБиблиотек();
                } else {
                    userDirLibs.mkdir();
                }
                
                ОбновитьБиблиотеки();
                
            }
        }
    }

    private File[] getListIniFile() {
        File[] files;
        files = currentDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("Тех") && name.endsWith(".ini");
            }
        });
        if (files != null) {
            java.util.Arrays.sort(files);
        }
        return files;
    }

    private void initPropDialog() throws HeadlessException {
        final JFrameProperty fp = new JFrameProperty(iniFile, true, false);
        fp.setVisible(true);
    }

    @SuppressWarnings("ConstantConditions")
    void ОбновитьБиблиотеки()  {

        ArrayList<File> arrayLib = getLib();
        if (arrayLib.size() > 0) {
            frame = new iTecLoader.Frame();
            boolean packFrame = false;
            frame.validate();
            //Center the window
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = frame.getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
            frame.setVisible(true);

            updateLib(arrayLib, frame.getProgress());

        }

        loadJars();

        start();

    }

    void loadJars() {
        String classPathSeparator = System.getProperty("path.separator");
        String classpath = "mail.jar" + classPathSeparator + "beansbinding.jar" + classPathSeparator;

        File[] arrayTechLib = currenDirLib.listFiles(jarFilter);
        assert arrayTechLib != null;
        for (File f : arrayTechLib) {
            classpath += f.getPath() + classPathSeparator;
        }

        File[] fsOO = getOOFiles();

        for (File aFsOO : fsOO) {
            classpath += aFsOO.getPath() + classPathSeparator;
        }

        System.setProperty("java.class.path", System.getProperty("java.class.path") + classPathSeparator + classpath);

        try {
            java.net.URL[] tmp = new java.net.URL[arrayTechLib.length + fsOO.length];
            int count = 0;
            for (File file : arrayTechLib) {
                tmp[count++] = file.toURI().toURL();
            }

            for (File aFsOO : fsOO) {
                tmp[count++] = aFsOO.toURI().toURL();
            }
            //  tmp[count++] = filesOO3_3.toURI().toURL();
            final ClassLoader xclassLoader = Application.this.getClass().getClassLoader();

            cloader = new java.net.URLClassLoader(tmp, xclassLoader);

            Thread.currentThread().setContextClassLoader(cloader);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void start() {
        try {
            Class cl = Class.forName(startTask, false, cloader);
            @SuppressWarnings("unchecked")
            Constructor constr = cl.getConstructor(String.class);
            constr.newInstance(iniFile);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            System.out.println(ex.getMessage());
        }

        /*
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            }
        });*/
    }

    private File[] getOOFiles() {
        File oo = new File(IniSettings.get(IniConst.DIR_OO) + File.separator + "program" + File.separator + "classes");
        if (!oo.exists()) {
            String mess = "В файле ТехКорпорация.ini нет пути доступа к библиотекам Office.\\n Отчёты работать не будут.";
            JOptionPane.showMessageDialog(null, mess, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        ArrayList<File> array = new ArrayList<File>();
        list(oo, array);
        array.add(oo);
        return array.toArray(new File[array.size()]);
    }

    private void list(File oo, final ArrayList<File> array) {
        File[] files = oo.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    list(f, array);
                } else {
                    if (f.getName().endsWith("jar")) {
                        array.add(f);
                    }
                }
            }
        }
    }
    final FilenameFilter jarFilter = (File dir, String name) -> name.endsWith("jar");

    ArrayList<File> getLib() {
        ArrayList<File> array = new ArrayList<File>();
        File dirLibs = new java.io.File(IniSettings.get(IniConst.APP_SERVER) + "/Bin");
        File[] arrayTechLib = dirLibs.listFiles((File dir, String name) -> name.endsWith("jar"));

        currenDirLib = new File(currentDir, "Lib");
        if (!currenDirLib.exists()) {
            currenDirLib.mkdir();
        }
        if (arrayTechLib == null) {
            //throw new java.io.IOException("Не удалось получить доступ к библиотекам");
              System.exit(0);
        }
        for (File fileSource : arrayTechLib) {
            File fileTarget = new java.io.File(currenDirLib, fileSource.getName());

            long modifFileSorce = fileSource.lastModified();
            long modifFileTarget = fileTarget.lastModified();

            long sizeFileSorce = fileSource.length();
            long sizeFileTarget = fileTarget.length();

            if (modifFileSorce > modifFileTarget || sizeFileSorce != sizeFileTarget || !fileTarget.exists()) {
                array.add(fileSource);
            }

        }
        return array;
    }

    private void СинронизацияБиблиотек() {
        File userDirLibs = new java.io.File(currentDir, "/Lib");
        File[] arrayUserTechLib = userDirLibs.listFiles((File dir, String name) -> name.endsWith("jar"));

        for (File file : Objects.requireNonNull(arrayUserTechLib)) {
            String z = file.getName();
            File x = new File(IniSettings.get(IniConst.APP_SERVER) + "/Bin/" + z);
            if (!x.exists()) {
                File y = new File(currentDir + "/Lib/" + z);
                y.delete();
            }
        }
    }

    void updateLib(ArrayList<File> arrayLib, final JProgressBar progress) {
        final int constnt = 100 / arrayLib.size();
        for (File f : arrayLib) {
            copyJars(f);
            SwingUtilities.invokeLater(() -> {
                progress.setValue(n = n + (constnt));
            });
        }
        frame.dispose();
    }

    void copyJars(File fileSourse) {
        try {

            Path source = Paths.get(fileSourse.getAbsolutePath());

            File t = new File(currenDirLib, fileSourse.getName());
            Path target = Paths.get(t.getAbsolutePath());

            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

            t.setLastModified(fileSourse.lastModified());

            //   copyFile(fileSourse, new File(currenDirLib, fileSourse.getName()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Идёт процесс доставки JARs файлов " + fileSourse, "Ошибка", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /*  public boolean copyFile(File filesource, File filetarget) throws IOException {
        long lastModified;
        lastModified = filesource.lastModified();
        if (filesource.exists() && filesource.isFile()) {
            filetarget.createNewFile();
            if (filetarget.exists() && filetarget.isFile()) {
                byte[] data;
                FileInputStream fis = new FileInputStream(filesource);
                FileOutputStream fos = new FileOutputStream(filetarget);
                data = new byte[fis.available()];
                while (fis.read(data) > 0) {
                    fos.write(data);
                    data = new byte[fis.available()];
                }

                fis.close();
                fos.close();
                filetarget.setLastModified(lastModified);
            } else {
                throw new IOException(filetarget + " is not a file");
            }
        } else {
            throw new IOException(filesource + " is not a file");
        }
        return (true);
    }*/

 /*   public boolean copyFile(String source, String target) throws IOException {
        long lastModified;
        File filesource = new File(source);
        lastModified = filesource.lastModified();
        if (filesource != null) {
            if (filesource.exists() && filesource.isFile()) {
                File filetarget = new File(target);
                if (filetarget != null) {
                    filetarget.createNewFile();
                    if (filetarget.exists() && filetarget.isFile()) {
                        byte[] data;
                        FileInputStream fis = new FileInputStream(filesource);
                        FileOutputStream fos = new FileOutputStream(filetarget);
                        data = new byte[fis.available()];
                        while (fis.read(data) > 0) {
                            fos.write(data);
                            data = new byte[fis.available()];
                        }

                        fis.close();
                        fos.close();
                        filetarget.setLastModified(lastModified);
                    } else {
                        throw new IOException(target + " is not a file");
                    }
                } else {
                    throw new IOException("Can't instatiate class File with String parameter " + target);
                }
            } else {
                throw new IOException(source + " is not a file");
            }
        } else {
            throw new IOException("Can't instatiate class File with String parameter " + source);
        }
        return (true);
    }*/
    boolean CheckPathAccess() {
        if (!IniSettings.get("AppServer=").equals("")) {
            if (!new File(IniSettings.get("AppServer=")).exists()) {
                JOptionPane.showMessageDialog(null, "Отсутствует сеть или ошибка в пути доступа (AppServer) " + IniSettings.get("AppServer="), "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (!IniSettings.get("DataServer=").equals("")) {
            if (!new File(IniSettings.get("DataServer=")).exists()) {
                JOptionPane.showMessageDialog(null, "Отсутствует сеть или ошибка в пути доступа (DataServer) " + IniSettings.get("DataServer="), "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
