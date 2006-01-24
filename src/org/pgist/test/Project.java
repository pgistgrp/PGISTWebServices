package org.pgist.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Project
    extends JFrame {
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  ImageIcon image1 = new ImageIcon(org.pgist.test.Project.class.getResource(
      "openFile.png"));
  ImageIcon image2 = new ImageIcon(org.pgist.test.Project.class.getResource(
      "closeFile.png"));
  ImageIcon image3 = new ImageIcon(org.pgist.test.Project.class.getResource(
      "help.png"));
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JButton jButton1 = new JButton();
  public Project() {
    try {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }


  public static void main (String args[]){
    new Project();
  }

  /**
   * Component initialization.
   *
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    contentPane = (JPanel) getContentPane();
    contentPane.setLayout(gridBagLayout1);
    setSize(new Dimension(400, 300));
    setTitle("New project");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new Project_jMenuFileExit_ActionAdapter(this));
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new Project_jMenuHelpAbout_ActionAdapter(this));
    jLabel1.setText("Name");
    jTextField1.setText("jTextField1");
    jLabel2.setText("Description");
    jTextField2.setText("jTextField2");
    jButton1.setText("jButton1");
    jButton1.addActionListener(new Project_jButton1_actionAdapter(this));
    jMenuBar1.add(jMenuFile);
    jMenuFile.add(jMenuFileExit);
    jMenuBar1.add(jMenuHelp);
    jMenuHelp.add(jMenuHelpAbout);
    contentPane.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    contentPane.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    contentPane.add(jTextField1, new GridBagConstraints(1, 0, 1, 2, 0.0, 0.0
        , GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
        new Insets(0, 53, 0, 44), 59, 0));
    contentPane.add(jButton1, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    contentPane.add(jTextField2, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 118, 0));
    setJMenuBar(jMenuBar1);
  }

  /**
   * File | Exit action performed.
   *
   * @param actionEvent ActionEvent
   */
  void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
    System.exit(0);
  }

  /**
   * Help | About action performed.
   *
   * @param actionEvent ActionEvent
   */
  void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
    Project_AboutBox dlg = new Project_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.show();
  }

  public void jButton1_actionPerformed(ActionEvent e) {

  }
}

class Project_jButton1_actionAdapter
    implements ActionListener {
  private Project adaptee;
  Project_jButton1_actionAdapter(Project adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class Project_jMenuFileExit_ActionAdapter
    implements ActionListener {
  Project adaptee;

  Project_jMenuFileExit_ActionAdapter(Project adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent) {
    adaptee.jMenuFileExit_actionPerformed(actionEvent);
  }
}

class Project_jMenuHelpAbout_ActionAdapter
    implements ActionListener {
  Project adaptee;

  Project_jMenuHelpAbout_ActionAdapter(Project adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent) {
    adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
  }
}
