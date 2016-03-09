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
        boolean activeThread = true;
        while (activeThread){            
            activeThread = false;
            //Εάν έστω και ένα Thread είναι ενεργό συννεχίζει το
            //monitor να παρακολουθεί τα Threads και να 
            //ενημερώνει την οθόνη με τις αλλαγές.

            
            //Ενημέρωση της οθόνης με την πληροφορία εάν τα Threads είναι ενεργά
            for (WorkPermitSimulation w:WPSimulationList) {
                //Ενημέρωση του TextArea της οθόνης            
                String str = (w.isAlive()) ? "Ενεργό" : "Σταματημένο";
                win.updSimLog(w.getName() + ":" + str + "\n");                
            }
            win.updSimLog("-----------------------------------------------\n");
            
            for (WorkPermitSimulation w:WPSimulationList){
                activeThread = w.isAlive();
                if (activeThread) break;
            }   
            //Ενημέρωση των κεντρικών λιστών του παραθύρου.
            win.updTables();
            
            try {
                //Καθυστερηση 15 sec
                sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }
}
