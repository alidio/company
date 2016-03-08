package company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import model.Employee;


public class WorkPermitSimulation extends Thread{
    private Employee emp; //Ο Εργαζόμενος του Thread
    private Random rnd = new Random(); //Γεννήτρια τυχαίων αριθμών
    private EntityManager em;
    private Utils u; 
    private boolean NextTimeFin=false; //Τερματισμός στον επόμενο κύκλο
    private final int WaitCycles=5; //Κύκλοι που θα περιμένει το thred μεχρι να τερματίσει
    private int CyclesToWait=0; //Τερματισμός ύστερα από WaitCycles κύκλους
        
    public WorkPermitSimulation(Employee emp) {
        super("WorkPermitSimulation "+emp.getLname());
        this.emp = emp;
        this.em = DBManager.em;
        u = new Utils();
    }
    
    @Override
    public void run() {        
        //κεντρικός Βρόγχος επανάληψης
        while(true) {
System.out.println("CyclesToWait="+CyclesToWait);                    
            //Έλεγχος εάν ο εργαζόμενος είναι προϊστάμενος (R4.A)
            if (u.chkManagerExist(emp)) {                
                //Έγκριση - Απόρριψη αιτημάτων άδειας (R4.A)
                u.WorkpermitApproval(emp);
                
                //Αναμονή 5 δευτερόλεπτων
                try {
                    sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WorkPermitSimulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //Τερματισμός Thread
            if (NextTimeFin) break;
            
            //Αναμονή 10 έως 20 sec με τυχαίο τρόπο (R4.Β).
            try {
                //Αναμονή σε milisecond. 1000msec = 1sec
                int i = 10000 + rnd.nextInt(10000);
                sleep(i);
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkPermitSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Έλεγχος εάν υπάρχει υποβληθέν αίτημα που δεν έχει ελεγθεί (R4.C).
            if (!u.chkMyWorkpermit(emp)) {
                
                //Αν δεν υπάρχει άλλο αίτημα, το οποίο δεν έχει ελεγχθεί 
                //τότε υποβάλλει αίτημα άδειας με τυχαίο τρόπο

                //Στη λίστα αυτή βρίκονται οι τύποι άδειας και τα υπόλοιπα αδείας για 
                //τον συγκεκριμένο υπάλληλο.
                //Kρατά τα υπόλοιπα αδειών από κάθε τύπο άδειας
                List<RestWorkPermit> wp = new ArrayList<>();

                wp = u.getRestDaysByWPT(emp);

                //Το Thread τερματίζει εάν δεν βρεθεί καμία γραμμή στη λίστα με
                //υπόλοιπα από κάποιο τύπο άδειας.
System.out.println("wp.size="+wp.size());
                if (wp.size() > 0) {
                    
                    CyclesToWait = 0;
                    
                    //Εαν υπάρχει έστω και ένας τύπος άδειας που έχει υπόλοιπο
                    //υποβάλλω αίτημα άδειας στην τύχη.

                    //Επιλέγω στην τύχη μία εγγραφή από τη λίστα wp
                    //όπου βρίσκονται τα είδη άδειας με τα υπόλοιπα 
                    //για κάθε άδεια.

                    RestWorkPermit rwp = wp.get(rnd.nextInt(wp.size()));

                    //Κάνω Εισαγωγή την άδεια στη βάση                    
System.out.println("Κάνω Εισαγωγή:"+rwp.getEmp().getLname()+"\n"+
        rwp.getAworkPermit().getWorkPermitTypeId().getWorkPermitTypeText()+"\n"
        + " Απο:"+rwp.getFromDate()+
        "\n Εως:"+rwp.getToDate()+
        "\n Ημέρες"+rwp.getNumdays()+
        "\n Υπόλοιπο:"+rwp.getYpoloipo());

                    u.insRestWP(rwp);                    
                } 
            }else {                
                //Τερμάτισε σε WaitCycles κύκλους
                if (WaitCycles < CyclesToWait++) NextTimeFin=true; 
            }
                
        }
    }
}