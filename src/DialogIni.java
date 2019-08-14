package iTecLoader;


import javax.swing.*;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.*;
import java.io.File;




/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DialogIni extends javax.swing.JDialog {
    JPanel panel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JButton cancelButton = new JButton();
    JButton okButton = new JButton();
    JScrollPane jScrollPane1 = new JScrollPane();
    JList jList1 = new JList();
    DefaultListModel model = new DefaultListModel();
    boolean ok = false;
    File[] files = null;

    public DialogIni(String title, File[] files) {
        super(new Frame(), title, true);
        this.files = files;
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            setLocation((screenSize.width - frameSize.width) / 2,(screenSize.height - frameSize.height) / 2);

            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new DialogIni_cancelButton_actionAdapter(this));
        okButton.setText("Ок");
        okButton.addActionListener(new DialogIni_okButton_actionAdapter(this));
        panel1.setPreferredSize(new Dimension(400, 350));
        jList1.addMouseListener(new DialogIni_jList1_mouseAdapter(this));
        this.addKeyListener(new DialogIni_this_keyAdapter(this));
        jList1.addKeyListener(new DialogIni_jList1_keyAdapter(this));
        getContentPane().add(panel1);
        panel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jList1);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        for (File f : files) {
            String t = f.getName();
//            t=  t.substring(0,t.lastIndexOf(".ini"));
            model.addElement(t);
        }

        jList1.setModel(model);
        jList1.setSelectedIndex(0);
        jPanel1.add(okButton);
        jPanel1.add(cancelButton);

    }

    public void okButton_actionPerformed() {
        ok = true;
        this.dispose();
    }

    public void cancelButton_actionPerformed() {
        this.dispose();
    }

    public String getIni() {
        if (!ok) {
            return null;
        }
        return (String) jList1.getSelectedValue();
    }

    public void jList1_mouseClicked(MouseEvent e) {
        if(e.getClickCount() >=2){
            ok = true;
            this.dispose();
        }


    }

    public void this_keyPressed() {

    }

    public void jList1_keyPressed(KeyEvent e) {
        if(e.getKeyCode() ==KeyEvent.VK_ENTER){
                   ok = true;
                   this.dispose();
        }
    }
}
class DialogIni_okButton_actionAdapter implements ActionListener {
    private DialogIni adaptee;
    DialogIni_okButton_actionAdapter(DialogIni adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.okButton_actionPerformed();
    }
}


class DialogIni_cancelButton_actionAdapter implements ActionListener {
    private DialogIni adaptee;
    DialogIni_cancelButton_actionAdapter(DialogIni adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed();
    }
}


class DialogIni_jList1_mouseAdapter extends MouseAdapter {
    private DialogIni adaptee;
    DialogIni_jList1_mouseAdapter(DialogIni adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.jList1_mouseClicked(e);
    }
}


class DialogIni_this_keyAdapter extends KeyAdapter {
    private DialogIni adaptee;
    DialogIni_this_keyAdapter(DialogIni adaptee) {
        this.adaptee = adaptee;
    }

    public void keyPressed(KeyEvent e) {
        adaptee.this_keyPressed();
    }
}


class DialogIni_jList1_keyAdapter extends KeyAdapter {
    private DialogIni adaptee;
    DialogIni_jList1_keyAdapter(DialogIni adaptee) {
        this.adaptee = adaptee;
    }

    public void keyPressed(KeyEvent e) {
        adaptee.jList1_keyPressed(e);
    }
}
