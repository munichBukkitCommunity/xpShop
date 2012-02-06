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
    private JRadioButton Radio1;
    private JRadioButton Radio2;
    private BorderLayout borderLayout;
    private ButtonGroup gruppe;
    private int panelaktuell;
    private GroupLayout layout;
    private GroupLayout.SequentialGroup horizontalegruppe1;
    
    public PanelControl(xpShop au) {
        super();
        auc = au;
        setTitle("Default options");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        borderLayout = new BorderLayout();
        gruppe = new ButtonGroup();
        layout = new GroupLayout(getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        horizontalegruppe1 = layout.createSequentialGroup();
        
        hauptPanel = init();
        this.getContentPane().add(hauptPanel);
    }
    
    public JPanel hauptpanel() {
        final JPanel panel = new JPanel(borderLayout);
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
        Button2 = new JButton("I will edit the config.yml");
        Button3 = new JButton("Weiter");
        Button3.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent event) {
                if (xpShop.debug) {
                    System.out.println("Button2 get" + event.toString());
                    System.out.println("Source: " + event.getSource());
                }
                panel.remove(getPanel(panelaktuell + 1));
                panelaktuell++;
                panel.add(getPanel(panelaktuell + 1));
            }
        });
        Button2.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent event) {
                if (xpShop.debug) {
                    System.out.println("Button2 get" + event.toString());
                    System.out.println("Source: " + event.getSource());
                }
                auc.getConfig().set("firstRun", false);
                PanelControl.this.remove(hauptPanel);
                PanelControl.this.repaint();
                PanelControl.this.dispose();
            }
        });
        JPanel panelunten = new JPanel();
        panelunten.add(Button1, BorderLayout.WEST);
        if (auc.getConfig().getBoolean("firstRun")) {
            panelunten.add(Button2, BorderLayout.CENTER);
        }
        panelunten.add(Button3, BorderLayout.EAST);
        
        if (panelaktuell == 0) {
            JPanel mixed = new JPanel();
            mixed.add(getPanel(1), BorderLayout.NORTH);
            mixed.add(getPanel(2), BorderLayout.SOUTH);
            panel.add(mixed);
        } else {
            panel.add(getPanel(panelaktuell + 1), BorderLayout.CENTER);
        }
        panel.add(panelunten, BorderLayout.SOUTH);
        return panel;
    }
    
    public JPanel getPanel(int panelindex) {
        if (xpShop.debug) {
            xpShop.Logger("" + panelindex, "Debug");
        }
        if (panelindex == 1) {
            JPanel panel = new JPanel();
            final JLabel label = new JLabel("Select your language: ");
            Radio1 = new JRadioButton("en");
            Radio2 = new JRadioButton("de");
            Radio1.setSelected(auc.getConfig().getString("language").equalsIgnoreCase("en"));
            Radio2.setSelected(auc.getConfig().getString("language").equalsIgnoreCase("de"));
            Radio2.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent event) {
                    if (xpShop.debug) {
                        System.out.println("Button2 get" + event.toString());
                        System.out.println("Source: " + event.getSource());
                    }
                    auc.getConfig().set("language", "en");
                    auc.saveConfig();
                    auc.reloadConfig();
                    auc.config.loadStrings();
                }
            });
            Radio2.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent event) {
                    if (xpShop.debug) {
                        System.out.println("Button2 get" + event.toString());
                        System.out.println("Source: " + event.getSource());
                    }
                    auc.getConfig().set("language", "de");
                    auc.saveConfig();
                    auc.reloadConfig();
                    auc.config.loadStrings();
                }
            });
            panel.add(label);
            gruppe.add(Radio1);
            gruppe.add(Radio2);
            panel.add(Radio1);
            panel.add(Radio2);
            return panel;
        } else if (panelindex == 2) {
            JPanel panel = new JPanel();
            final JLabel label = new JLabel("Select if debug should be aktivated: ");
            JCheckBox Haken = new JCheckBox("debug mode");
            Haken.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent event) {
                    if (xpShop.debug) {
                        System.out.println("Button2 get" + event.toString());
                        System.out.println("Source: " + event.getSource());
                    }
                    auc.getConfig().set("debug", !auc.getConfig().getBoolean("debug"));
                    auc.saveConfig();
                    auc.reloadConfig();
                    auc.reloaddebug();
                    auc.config.loadStrings();
                }
            });
            panel.add(label);
            panel.add(Haken);
            return panel;
        } else {
            return null;
        }
    }

    /**
     * Komponenten initialisieren
     */
    private JPanel init() {
        JPanel j = hauptpanel();
        return j;
    }
}
