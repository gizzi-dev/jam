/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parte_IV;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M746225
 */
public class TestParteIV {
    
    public static void main(String[] args){
      ADSLImpl adsl = null;
         try {
            adsl = new ADSLImpl();
            java.rmi.registry.LocateRegistry.createRegistry(2000);
            System.out.println("Avviato rmi registry"); 
            Naming.rebind("rmi://127.0.0.1:2000/ADSL",adsl);
            System.out.println("Rebind Fatto!");
            
        } catch (Exception e) {
            System.err.println("Failer to bind to RMI Registry\n"+e);
            System.exit(1);            
        }        
    }
}
