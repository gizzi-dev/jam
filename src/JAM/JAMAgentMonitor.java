/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author M746225
 */
public class JAMAgentMonitor extends JFrame implements Observer{
    private JAMAgent agent;
    boolean initStatus;
    
    private JButton initButton;
    private JButton startButton;
    private JButton destroyButton;
    private JButton exitButton;
    
    private JLabel scritta;
    private JTextArea console;
    private JScrollPane scroll;
    
    private JPanel panel;
    private JPanel upPanel;
    private JPanel downPanel;
    private JPanel lateralPanel;
    
    
    
    public JAMAgentMonitor(JAMAgent ag){
        super("JAM Agent Monitor");
        this.agent =ag;
        agent.addObserver(this);
        initStatus = false;
        
        initButton = new JButton("Init");
        startButton = new JButton("Start");
        destroyButton = new JButton("Destroy");
        exitButton = new JButton ("Exit");
        scritta = new JLabel ("<html>Connection <br> Console</html");
        console =new JTextArea(25,20);
        console.setEditable(false);        
        scroll = new JScrollPane(console,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
                
        panel = new JPanel();
        upPanel = new JPanel();
        downPanel = new JPanel();
        lateralPanel = new JPanel();
        
        
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(upPanel);
        panel.add(downPanel);
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.LINE_AXIS));
            upPanel.setBorder(new EmptyBorder(0,0,5,0));
            upPanel.add(scritta);
            scritta.setBorder(new EmptyBorder(10,10,10,10));    
            upPanel.add(scroll);
            upPanel.add(lateralPanel);
                lateralPanel.setLayout(new BoxLayout(lateralPanel, BoxLayout.Y_AXIS));
                lateralPanel.setBorder(new EmptyBorder(10,10,10,10));
                    lateralPanel.add(initButton);
                    initButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, initButton.getMinimumSize().height));
                    lateralPanel.add(startButton);
                    startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, startButton.getMinimumSize().height));
                    lateralPanel.add(destroyButton); 
                    destroyButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,destroyButton.getMinimumSize().height));
        downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
        downPanel.add(exitButton);
        exitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, exitButton.getMinimumSize().height));
                
        add(panel);
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        initButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    agent.init();
                    JOptionPane.showMessageDialog(null, "MessageBox inserita correttamente nell'adsl","Info",JOptionPane.INFORMATION_MESSAGE);
                    initStatus=true;
                } catch (JAMADSLException ex) {
                    JOptionPane.showMessageDialog(null, "ADSL non avviata!","Error",JOptionPane.ERROR_MESSAGE);                  
                }
            }
          });
          
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(initStatus == false){
                    JOptionPane.showMessageDialog(null, "Devi prima avviare Init","Error",JOptionPane.ERROR_MESSAGE);                    
                }
                else{                   
                        //agent.start();
                }
            }
          });
          
        destroyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    agent.destroy();
                    JOptionPane.showMessageDialog(null, "JAMagent distrutto!","info",JOptionPane.INFORMATION_MESSAGE);                           
                } catch (JAMADSLException ex) {
                    JOptionPane.showMessageDialog(null, "Devi prima fare lo start dell'agente!","Error",JOptionPane.ERROR_MESSAGE);                    
                }
            }
              
          });
        
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               System.exit(1);
            }              
        });
    }
    
    /**
     * Stampa nella console le variazioni della classe JAMAgent
     * 
     * @param ob oggetto da osservare
     * @param extra_arg argomenti extra
     */
    public void update(Observable ob, Object extra_arg){
         console.append((String) extra_arg + "\n");        
    }
    
   
}
