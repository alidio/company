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
    private int LoopCounter=0; //Αναμονή μερικών κύκλων ελέγχων πρίν τερματίσει το Thread
    
    public WorkPermitSimulation(Employee emp) {
        super("WorkPermitSimulation "+emp.getLname());
        this.emp = emp;
        this.em = DBManager.em;
        u = new Utils();
    }
    
    @Override
    public void run() {
System.out.println("1111111111111111111111111111111");
        //Έλεγχος εάν ο εργαζόμενος είναι προϊστάμενος (R4.A)
        while(true) {
            if (u.chkManagerExist(emp)) {
System.out.println("22222222222222222222222222222222");
                //Έγκριση - Απόρριψη αιτημάτων άδειας (R4.A)
                u.WorkpermitApproval(emp);
System.out.println("333333333333333333333333333333333");                
            }               

            //Τερματισμός Thread
            if (NextTimeFin) break;
System.out.println("4444444444444444444444444444444444");
            //Αναμονή 10 έως 30 sec με τυχαίο τρόπο (R4.Β).
            try {
                //Αναμονή σε milisecond. 1000msec = 1sec
                int i = 10000 + rnd.nextInt(20000);
System.out.println("555555555555555555555555555555555");                
                sleep(i);
System.out.println("666666666666666666666666666666666");                
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkPermitSimulation.class.getName()).log(Level.SEVERE, null, ex);
System.out.println("77777777777777777777777777777777777");                
            }
            //Έλεγχος εάν υπάρχει υποβληθέν αίτημα που δεν έχει ελεγθεί (R4.C).
            if (!u.chkMyWorkpermit(emp)) {
System.out.println("88888888888888888888888888888888888888888");
                LoopCounter = 0;

                //Αν δεν υπάρχει άλλο αίτημα, το οποίο δεν έχει ελεγχθεί 
                //τότε υποβάλλει αίτημα άδειας με τυχαίο τρόπο

                //Στη λίστα αυτή βρίκονται οι τύποι άδειας και τα υπόλοιπα αδείας για 
                //τον συγκεκριμένο υπάλληλο.
                //Kρατά τα υπόλοιπα αδειών από κάθε τύπο άδειας
                List<RestWorkPermit> wp = new ArrayList<>();
System.out.println("99999999999999999999999999999999999999999");                
                wp = u.getRestDaysByWPT(emp);
System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                //Το Thread τερματίζει εάν δεν βρεθεί καμία γραμμή στη λίστα με
                //υπόλοιπα από κάποιο τύπο άδειας.

System.out.println("wp.size()="+wp.size());    
System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                if (wp.size() > 0) {
                    //Εαν υπάρχει έστω και ένας τύπος άδειας που έχει υπόλοιπο
                    //υποβάλλω αίτημα άδειας στην τύχη.

                    //Επιλέγω στην τύχη μία εγγραφή από τη λίστα wp
                    //όπου βρίσκονται τα είδη άδειας με τα υπόλοιπα 
                    //για κάθε άδεια.
System.out.println("ccccccccccccccccccccccccccccccccccccccccccccc");                    
                    RestWorkPermit rwp = wp.get(rnd.nextInt(wp.size()));

                    //Κάνω Εισαγωγή την άδεια στη βάση
                    
System.out.println("Κάνω Εισαγωγή:"+rwp.getEmp().getLname()+" "+
rwp.getAworkPermit().getWorkPermitTypeId().getWorkPermitTypeText()+" "+
rwp.getFromDate()+" "+
rwp.getToDate()+" "+
rwp.getNumdays()+" "+
rwp.getYpoloipo());

                    u.insRestWP(rwp);
System.out.println("dddddddddddddddddddddddddddddddddddddddddddddd");                    
                } else NextTimeFin=true; //Δεν υπάρχουν υπόλοιπα αδειών
            }else LoopCounter ++;//Δέν έχει άλλη άδεια πρός έγκριση
System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");            
            //Αναμονή 5 κύκλους πρίν τον τερματισμό για την πιθανότητα 
            //που υπάρξει άδεια πρός έγκριση εάν είναι προιστάμενος
            if (LoopCounter==5) NextTimeFin=true;
System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffff");            
        }
    }
}