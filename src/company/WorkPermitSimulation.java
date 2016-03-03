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
    private boolean NextTimeFin=false;
    private int LoopCounter=0;
    
    public WorkPermitSimulation(Employee emp) {
        super("WorkPermitSimulation "+emp.getLname());
        this.emp = emp;
        this.em = DBManager.em;
        u = new Utils();        
    }
    
    @Override
    public void run() {
        
System.out.println("Start... ----> Onoma:"+emp.getLname());    

        //Έλεγχος εάν ο εργαζόμενος είναι προϊστάμενος (R4.A)
        while(true) {
            if (u.chkManagerExist(emp)) {

                //Έγκριση - Απόρριψη αιτημάτων άδειας (R4.A)
                u.WorkpermitApproval(emp);
            }               

            //Τερματισμός Thread
            if (NextTimeFin) break;

            //Αναμονή 10 έως 30 sec με τυχαίο τρόπο (R4.Β).
            try {
                //Αναμονή σε milisecond. 1000msec = 1sec
                int i = 10000 + rnd.nextInt(20000);
                sleep(i);            
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkPermitSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
System.out.println("Meta anamonh ----> Onoma:"+emp.getLname());    
            //Έλεγχος εάν υπάρχει υποβληθέν αίτημα που δεν έχει ελεγθεί (R4.C).
            if (!u.chkMyWorkpermit(emp)) {
                
                LoopCounter = 0;
                
                //Αν δεν υπάρχει άλλο αίτημα, το οποίο δεν έχει ελεγχθεί 
                //τότε υποβάλλει αίτημα άδειας με τυχαίο τρόπο
System.out.println("Meta anamonh 111111----> Onoma:"+emp.getLname());    
                //Στη λίστα αυτή βρίκονται οι τύποι άδειας και τα υπόλοιπα αδείας για 
                //τον συγκεκριμένο υπάλληλο.        
                //Kρατά τα υπόλοιπα αδειών από κάθε τύπο άδειας
                List<RestWorkPermit> wp = new ArrayList<>();
                wp = u.getRestDaysByWPT(emp);
                
                //Το Thread τερματίζει εάν δεν βρεθεί καμία γραμμή στη λίστα με 
                //υπόλοιπα από κάποιο τύπο άδειας.              
                if (wp.size() > 0) {
                    //Εαν υπάρχει έστω και ένας τύπος άδειας που έχει υπόλοιπο
                    //υποβάλλω αίτημα άδειας στην τύχη.
System.out.println("Meta anamonh 2222222----> Onoma:"+emp.getLname());    
                    //Επιλέγω στην τύχη μία εγγραφή από τη λίστα wp
                    //όπου βρίσκονται τα είδη άδειας με τα υπόλοιπα 
                    //για κάθε άδεια.
                    RestWorkPermit rwp = wp.get(rnd.nextInt(wp.size()));

                    //Κάνω Εισαγωγή την άδεια στη βάση
                    u.insRestWP(rwp);
                    
                } else NextTimeFin=true; //Δεν υπάρχουν υπόλοιπα αδειών
            } LoopCounter ++;
            //else NextTimeFin=true; //Δέν έχει άλλη άδεια πρός έγκριση
            
            if (LoopCounter==5) NextTimeFin=true;
        }
System.out.println("End... ----> Onoma:"+emp.getLname());
    }
}