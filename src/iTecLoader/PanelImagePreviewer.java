package iTecLoader;

import java.beans.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PanelImagePreviewer extends JPanel {
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();

  JFileChooser chooser;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public PanelImagePreviewer(JFileChooser chooser) {
    this.chooser = chooser;

    try {
      jbInit();
    }
    catch (Exception ex) {
    }
  }

  final void jbInit() throws Exception {
    jLabel2.setLayout(new BorderLayout());
    jLabel2.setPreferredSize(new Dimension(100, 100));

    chooser.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
      public void propertyChange(PropertyChangeEvent event) {
        if ( (String) event.getPropertyName() == (String) JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
          File f = (File) event.getNewValue();

          if (f != null) {

            ImageIcon icon = new ImageIcon(f.getPath());

            int h = icon.getIconHeight();
            int w = icon.getIconWidth();

            if (icon.getIconWidth() > getWidth()) {
              icon = new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
            }

            String x = "Размер:" + w + " x " + h;

            jLabel1.setText(x);
            jLabel2.setIcon(icon);

          }
        }
      }
    });

    this.setLayout(gridBagLayout1);
    chooser.setBorder(BorderFactory.createEtchedBorder());

    jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 10));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);

    this.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

  }
}
