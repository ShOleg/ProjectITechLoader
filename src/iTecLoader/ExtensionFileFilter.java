package iTecLoader;



import java.io.*;
import java.util.*;

public class ExtensionFileFilter extends javax.swing.filechooser.FileFilter {
  private String description = "";
  private ArrayList extension = new ArrayList();

  public ExtensionFileFilter() {
  }

  public void addExtension(String extend) {
    if (!extend.startsWith(".")) {
      extend = "." + extend;
    }
    extension.add(extend.toLowerCase());
  }

    @Override
  public boolean accept(File f) {
    if (f.isDirectory()) {
      return true;
    }
    String name = f.getName().toLowerCase();
    for (int i = 0; i < extension.size(); i++) {
      if (name.endsWith( (String) extension.get(i))) {
        return true;
      }
    }
    return false;
  }

    @Override
  public String getDescription() {
    return description;
  }

  public void setDescription(String des) {
    description = des;
  }

}
