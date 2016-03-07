package company;

import Forms.FRM_Workpermit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ThreadMonitor extends Thread{
    List<WorkPermitSimulation> WPSimulationList;
    FRM_Workpermit win;

    public ThreadMonitor(FRM_Workpermit win,List<WorkPermitSimulation> WPSimulationList) {
        this.WPSimulationList = WPSimulationList;
        this.win = win;
    }
   
    @Override
    public void run() {
System.out.println("-------------------->Start Monitoring!!!");
        boolean activeThread = true;
        while (activeThread){            
            activeThread = false;
            //Εάν έστω και ένα Thread είναι ενεργό συννεχίζει το
            //monitor να παρακολουθεί τα Threads και να 
            //ενημερώνει την οθόνη με τις αλλαγές.

for (WorkPermitSimulation w:WPSimulationList) System.out.println(w.getName() + " active=" + w.isAlive());              
            
            for (WorkPermitSimulation w:WPSimulationList){
                activeThread = w.isAlive();
                if (activeThread) break;
            }   
            //Ενημέρωση των κεντρικών λιστών του παραθύρου.
            win.updTables();
            try {
                //Καθυστερηση 15 sec
                sleep(15000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
System.out.println("-------------------->Stop Monitoring...");

    }
}
