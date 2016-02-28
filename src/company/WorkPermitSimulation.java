package company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import model.Employee;


public class WorkPermitSimulation extends Thread{
    private Employee emp;
    private Random rnd = new Random();
    private EntityManager em;
    private Utils u;
    
    public WorkPermitSimulation(Employee emp) {
        super("WorkPermitSimulation");
        this.emp = emp;
        this.em = DBManager.em;
        u = new Utils();
        
    }
    
    @Override
    public void run() {
        System.out.println(emp.getLname() + " ---> Αρχή");
        
        //Έλεγχος εάν ο εργαζόμενος είναι προϊστάμενος (R4.A)
        if (u.chkManagerExist(emp)) {
            System.out.println(emp.getLname() + " ---> IamManager");
            //Έγκριση - Απόρριψη αιτημάτων άδειας (R4.A)
            u.WorkpermitApproval(emp);
        }               
        
        //Αναμονή 10 έως 30 sec με τυχαίο τρόπο (R4.Β).
        try {
            //Αναμονή σε milisecond. 1000msec = 1sec
            int i = 10000 + rnd.nextInt(20000);
            sleep(i);            
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkPermitSimulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Έλεγχος εάν υπάρχει υποβληθέν αίτημα που δεν έχει ελεγθεί (R4.C).
        if (!u.chkMyWorkpermit(emp)) {        
 System.out.println(emp.getLname() + " --- > ΔΕΝ υπάρχει υποβληθέν αίτημα που δεν έχει ελεγθεί (R4.C).");
            //Αν δεν υπάρχει άλλο αίτημα, το οποίο δεν έχει ελεγχθεί 
            //τότε υποβάλλει αίτημα άδειας με τυχαίο τρόπο
            
            //Στη λίστα αυτή βρίκονται οι τύποι άδειας και τα υπόλοιπα αδείας για 
            //τον συγκεκριμένο υπάλληλο.        
            //Kρατά τα υπόλοιπα αδειών από κάθε τύπο άδειας
            List<RestWorkPermit> wp = new ArrayList<>();
            wp = u.getRestDaysByWPT(emp);
            
            if (wp.size() > 0) {
                //Εαν υπάρχει έστω και ένας τύπος άδειας που έχει υπόλοιπο
                //υποβάλλω αίτημα άδειας στην τύχη.
                for (RestWorkPermit r:wp) {
 System.out.println(r.getEmp().getFname() + " " + r.getWorkPermitTypeId().getWorkPermitTypeId().getWorkPermitTypeText()+" "+r.getYpoloipo()+" "+r.getMaxDate());
                }
                //Επιλέγω στην τύχη μία εγγραφή από τη λίστα wp
                //όπου βρίσκονται τα είδη άδειας με τα υπόλοιπα 
                //για κάθε άδεια.
                RestWorkPermit rwp = wp.get(rnd.nextInt(wp.size()));      
 System.out.println("Στην Τύχη:" +rwp.getEmp().getFname() + " " + rwp.getWorkPermitTypeId().getWorkPermitTypeId().getWorkPermitTypeText()+" "+rwp.getYpoloipo()+" "+rwp.getMaxDate());
                
                
 
            }            
        }
        
 System.out.println(emp.getLname() + " ---> Τέλος");
    }    
    
}
