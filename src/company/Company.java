package company;

import Forms.*;


public class Company {

    public static void main(String[] args) throws Exception{     
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FRM_Menu MainScr = new FRM_Menu();
                MainScr.setVisible(true); 
            }
        });    
    }    
}
