/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 *
 * @author Gianmarco
 */
public class ADSLMonitor extends JFrame implements ConsoleChangeListener{
    private int port; // viene valorizzato solo quando l'utente clicca sul bottone "start reg"
    private ADSLImpl adsl;
    private boolean giaRegistrato;
    
    // componenti grafici
    private JLabel label;
    private JLabel label2;    
    private JPanel panel; 
    private JPanel orizontale1;
    private JPanel orizontale2;
    private JPanel orizontale3;
    private JPanel orizontale4;
    private JPanel content;
    private JPanel lateralPanelRight;
    private JTextField portTextField;
    private JButton startRegButton;
    private JButton startAdslButton;
    private JButton stopAdslButton;
    private JButton exitButton;
    private JTextArea console;
    private JScrollPane scroll;
    private JSeparator separatore;
    
    public ADSLMonitor() {
        super("Agent Direcotry Service Layer Monitor");
        giaRegistrato = false;
        label = new JLabel("Port:");
        label2 = new JLabel("<html>Connection<br>console:</html>");
        portTextField = new JTextField("1099",20);
        startRegButton = new JButton("Start reg");
        startAdslButton = new JButton("Start up");
        stopAdslButton = new JButton("Shutdown");
        exitButton = new JButton("Exit");
        console = new JTextArea(25, 20);
        console.setEditable(false);
        separatore = new JSeparator();
        scroll=new JScrollPane(console);

        panel = new JPanel();
        orizontale1 = new JPanel();
        orizontale2 = new JPanel();
        orizontale3 = new JPanel();
        orizontale4 = new JPanel();
        lateralPanelRight = new JPanel(); 
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(orizontale1);
        panel.add(orizontale2);
        panel.add(orizontale3);
        panel.add(orizontale4);
                
        startRegButton.addActionListener(new buttonAction(this));                
        startAdslButton.addActionListener(new buttonAction(this));
        stopAdslButton.addActionListener(new buttonAction(this));
        exitButton.addActionListener(new buttonAction(this));

        orizontale1.setLayout(new BoxLayout(orizontale1, BoxLayout.LINE_AXIS));
        orizontale1.setBorder(new EmptyBorder(5,40,5,5));
        orizontale1.add(label);
        orizontale1.add(portTextField);
        label.setBorder(new EmptyBorder(10,16,10,10));
        orizontale1.add(startRegButton);
        startRegButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, startRegButton.getMinimumSize().height));

        orizontale2.setLayout(new BoxLayout(orizontale2, BoxLayout.LINE_AXIS));
        orizontale2.add(separatore);
        orizontale2.setBorder(new EmptyBorder(0,50,0,50));        

        orizontale3.setLayout(new BoxLayout(orizontale3, BoxLayout.LINE_AXIS));
        orizontale3.setBorder(new EmptyBorder(5,5,10,5));
        orizontale3.add(label2);
        label2.setBorder(new EmptyBorder(10,16,10,10));
        orizontale3.add(scroll);
        orizontale3.add(lateralPanelRight);

        lateralPanelRight.setLayout(new BoxLayout(lateralPanelRight, BoxLayout.Y_AXIS));
        lateralPanelRight.add(startAdslButton);
        startAdslButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, startAdslButton.getMinimumSize().height));
        lateralPanelRight.add(stopAdslButton);
        stopAdslButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, stopAdslButton.getMinimumSize().height));       

        orizontale4.add(exitButton);
        orizontale4.setLayout(new BoxLayout(orizontale4, BoxLayout.X_AXIS));
        orizontale4.setBorder(new EmptyBorder(0,5,5,5));
        exitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, exitButton.getMinimumSize().height));        

        add(panel);
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void StartReg(ADSLMonitor a) {
        if(!this.giaRegistrato){
            port = Integer.parseInt(portTextField.getText());
            try {
                adsl = new ADSLImpl("adsl", port);
                try {
                    adsl.addConsoleChangeListener(a);
                } catch (TooManyListenersException ex) {
                    System.out.println(ex);
                }
                adsl.startRMIRegistry();
            } catch (RemoteException ex) {
                System.out.println(ex);
            }            
            portTextField.setEditable(false);
            giaRegistrato = true;
        }
    }
    
    public void StartUp(){
        if(this.giaRegistrato && this.adsl != null){
            try {
                adsl.startADSL();
            } catch (RemoteException | MalformedURLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    public void shutDown(){
         if(this.giaRegistrato && this.adsl != null){
             try {
                 adsl.stopADSL();
             } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                 System.out.println(ex);
             }
         }
    }
    
    public void exit(){        
        System.exit(0);
    }
    
    public void ConsoleChange(ConsoleEvent evt) {
        console.append("-->"+evt.getText()+"\n");
    }

    public static void main(String[] args) throws TooManyListenersException {
        ADSLMonitor gui = new ADSLMonitor();
        gui.setVisible(true);

    }    
    
}

class buttonAction implements ActionListener{
    ADSLMonitor a;
    
    public buttonAction(ADSLMonitor a){
        this.a=a;
    }
    
    public void actionPerformed(ActionEvent e) {        
        String command = e.getActionCommand();      
        if(command.equals("Start reg")) a.StartReg(a);        
        else if(command.equals("Start up"))a.StartUp();       
        else if(command.equals("Shutdown"))a.shutDown();
        else if(command.equals("Exit")) a.exit();
    }

}
