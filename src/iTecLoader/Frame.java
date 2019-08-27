package iTecLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Frame extends JFrame {
  JPanel contentPane;
  JProgressBar progress = new JProgressBar();

  String PATH_APP_SERVER = new String();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  //Construct the frame
  public Frame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(gridBagLayout1);
    this.setSize(new Dimension(498, 207));
    this.setTitle("Обновление библиотек");

    this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("iTecLoader/java_jar.png")));

    progress.setPreferredSize(new Dimension(148, 30));

    jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("Идёт процесс доставки JAR - файлов");
    jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 20));
    jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel2.setText("Тех.Корпорация");

    jLabel3.setIcon( new ImageIcon( this.getClass().getResource("iTecLoader/java_jar.png")));

    contentPane.add(progress,   new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(35, 0, 0, 0), 350, 0));
    contentPane.add(jLabel1,       new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(15, 10, 0, 0), 0, 0));
    contentPane.add(jLabel2,    new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 0), 0, 0));
    contentPane.add(jLabel3,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 10, 0, 0), 0, 0));
  }

  //Overridden so we can exit when window is closed
    @Override
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  public JProgressBar getProgress() {
    return progress;
  }
}


