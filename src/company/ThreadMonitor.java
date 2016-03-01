package company;

import Forms.FRM_Workpermit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


public class ThreadMonitor extends Thread{
    List<WorkPermitSimulation> WPSimulationList;
    FRM_Workpermit win;

    public ThreadMonitor(FRM_Workpermit win,List<WorkPermitSimulation> WPSimulationList) {
        this.WPSimulationList = WPSimulationList;
        this.win = win;
    }

   
    @Override
    public void run() {
System.out.println("Starting... -->>> in ThreadMonitor <<<--");
        
        boolean activeThread = true;        
        while (activeThread){
            activeThread = false;
            for (WorkPermitSimulation w:WPSimulationList){
                System.out.println("Name:"+w.getId()+" // w.isAlive()="+w.isAlive());
                activeThread = w.isAlive();
                if (activeThread) break;
            }            
            win.fillTBSyg();
            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
System.out.println("End!!! -->>> in ThreadMonitor <<<--");        
    }
}
