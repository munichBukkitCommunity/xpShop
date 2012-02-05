package me.ibhh.xpShop;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelControl extends JFrame {

    private JPanel hauptPanel;
    private xpShop auc;
    private JButton Button1;
    private JButton Button2;
    private JButton Button3;
    private JTextField text;
    private JCheckBox Haken;
    private BorderLayout borderLayout;

    public PanelControl(xpShop au) {
        super();
        auc = au;
        setTitle("Default options");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        borderLayout = new BorderLayout();
        hauptPanel = init();
        this.getContentPane().add(hauptPanel);
    }

    public JPanel hauptpanel(){
        JPanel panel = new JPanel(borderLayout);
        Button1 = new JButton("Abbruch");
        Button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (xpShop.debug) {
                    System.out.println("Button2 get" + event.toString());
                    System.out.println("Source: " + event.getSource());
                }
                PanelControl.this.remove(hauptPanel);
                PanelControl.this.repaint();
                PanelControl.this.dispose();
            }
        });
        Button3 = new JButton("Weiter");
        Button3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (xpShop.debug) {
                    System.out.println("Button2 get" + event.toString());
                    System.out.println("Source: " + event.getSource());
                }
            }
        });
        JPanel panelunten = new JPanel();
        panelunten.add(Button1, BorderLayout.SOUTH);
        panelunten.add(Button3, BorderLayout.SOUTH);
        panel.add(firstPanel(), BorderLayout.CENTER);
        panel.add(panelunten, BorderLayout.SOUTH);
        return panel;
    }
    
    public JPanel secondPanel() {
        JPanel panel = new JPanel();
        final JLabel label = new JLabel("SQLUser: ");
        text = new JTextField("User");

        Button2 = new JButton("Weiter");
        Button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (xpShop.debug) {
                    System.out.println("Button1 get");
                    System.out.println("Source: " + event.getSource());
                }
                auc.getConfig().set("dbUser", text.getText());
                if (xpShop.debug) {
                    System.out.println("User: " + text.getText());
                    System.out.println("Weiter");
                }
                auc.getConfig().set("firstRun", false);
            }
        });
        panel.add(label);
        panel.add(text);
        panel.add(Button2);
        PanelControl.this.repaint();
        return panel;
    }

    public JPanel firstPanel() {
        JPanel panel = new JPanel();
        final JLabel label = new JLabel("Use MYSQL?");
        Haken = new JCheckBox();
        Haken.setSelected(auc.getConfig().getBoolean("SQL"));
        Haken.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (xpShop.debug) {
                    System.out.println("Button2 get" + event.toString());
                    System.out.println("Source: " + event.getSource());
                }
                boolean temp = auc.getConfig().getBoolean("SQL");
                auc.getConfig().set("SQL", !temp);
                auc.saveConfig();
                auc.reloadConfig();
                if (xpShop.debug) {
                    System.out.println("temp: " + temp);
                    System.out.println("Selected: " + Haken.isSelected());
                    System.out.println("Neue Config: " + auc.getConfig().getBoolean("SQL"));
                    System.out.println("Weiter");
                }
            }
        });
        panel.add(label);
        panel.add(Haken);
        return panel;
    }

    /**
     * Komponenten initialisieren
     */
    private JPanel init() {
        JPanel j = hauptpanel();
        return j;
    }
}
