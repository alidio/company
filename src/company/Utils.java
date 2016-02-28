package company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.*;


public class Utils {
    
    private EntityManager em; //Entity manager
    private final static Random rnd = new Random();
   
    public Utils(){
        this.em = DBManager.em;
    }
    
    //Η μέθοδος εισάγει τις ημέρες άδειας που δικαιούται ο
    //Employee κατά την καταχωρησή του (Νέος),
    //με την προυπόθεση ότι αυτή είναι καταχωρημένη στη βάση δεδομένων.
    public void insWorkpermit(Employee e){
        
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        try {
            
            List<Workpermittype> WPTList = GetWorkPermitById();
            for (Workpermittype w:WPTList){
            
                if (w.getWorkPermitTypeText().contentEquals("ΚΑΝΟΝΙΚΗ ΑΔΕΙΑ")){
                    Availableworkpermit Awp1 = new Availableworkpermit();
                    Awp1.setAvailableDays(25);
                    Awp1.setWorkPermitTypeId(w);
                    Awp1.setEmployeeId(e);
                    em.persist(Awp1);
                }
                if (w.getWorkPermitTypeText().contentEquals("ΑΔΕΙΑ ΜΗΤΡΟΤΗΤΑΣ")){
                    Availableworkpermit Awp2 = new Availableworkpermit();
                    Awp2.setAvailableDays(58);
                    Awp2.setWorkPermitTypeId(w);
                    Awp2.setEmployeeId(e);
                    em.persist(Awp2);
                }
                if (w.getWorkPermitTypeText().contentEquals("ΕΚΠΑΙΔΕΥΤΙΚΗ ΑΔΕΙΑ")){
                    Availableworkpermit Awp3 = new Availableworkpermit();
                    Awp3.setAvailableDays(20);
                    Awp3.setWorkPermitTypeId(w);
                    Awp3.setEmployeeId(e);
                    em.persist(Awp3);
                }               
            }             
            em.getTransaction().commit(); 
        } catch (Exception ex) {
            ex.printStackTrace();            
            em.getTransaction().rollback();
        }        
    }
    
    //Επιστρέφει σε λίστα όλες τις εγγραφές των τύπων των αδειών
    public List<Workpermittype> GetWorkPermitById() {
        //ερωτημα
        String sqlqry = "select w from Workpermittype w";

        Query qry = em.createQuery(sqlqry, Workpermittype.class);

        //Εκτέλεση ερωτήματος
        List<Workpermittype> WPList = qry.getResultList();

        return WPList;                     
    }    
    
    //Απάντάει εάν υπάρχει ως Manager ο Employee της παραμέτρου
    public boolean chkManagerExist(Employee emp) {
        boolean retval=false;
        
        //ερωτημα
        String sqlqry = "select e from Employee e";

        Query qry = em.createQuery(sqlqry, Employee.class);

        //Εκτέλεση ερωτήματος
        List<Employee> EList = qry.getResultList();
        
        for (Employee e:EList){
            if (Objects.equals(e.getManagerId(), emp)) retval = true;
        } 
        
        return retval;
    }        

    //Διαγράφει τις άδειες που δικαιούται ο Empoloyee 
    //έστι ωστε να μποεί να διαγραφεί και ο ίδιος μετά
    public void delEmployeeWorkPermit(Employee emp) {
        
        Query query = em.createQuery("DELETE FROM Availableworkpermit a WHERE a.employeeId = :emp");
        int deletedCount = query.setParameter("emp", emp).executeUpdate();               

    }
    
    
    //Η μέθοδος αυτή αναζητά τις προς έγριση άδειες των υφισταμένων του emp 
    //και με τυχαίο τρόπο τις ενημερώνει με 'έγκριση' ή 'απόρριψη'. 
    //Μετά τις καταχωρεί πάλι στη βάση.
    public void WorkpermitApproval(Employee emp){
               
        String sqlqry = "select w from Workpermit w, Employee e " +
                        "where w.employeeId = e " +
                        "and w.approved is null "+
                        "and e.managerId = :manager ";
        
        Query qry = em.createQuery(sqlqry,Workpermit.class).setParameter("manager", emp);

        //Εκτέλεση ερωτήματος
        List<Workpermit> WPList = qry.getResultList();
        
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }        
            for (Workpermit w:WPList){
                em.persist(w);
                w.setApproved(rnd.nextInt(2));
            }
            em.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();            
            em.getTransaction().rollback();
        } 
    }
    
    //Ελέγχει, αν υπάρχει υποβληθέν από τον Employee αίτημα 
    //το οποίο δε έχει ελεγχθεί
    public boolean chkMyWorkpermit(Employee emp){
        boolean retval=false;
        
        //ερωτημα
        String sqlqry = "select w from Workpermit w "
                      + "where w.employeeId = :emp "
                      + "and w.approved is null ";

        Query qry = em.createQuery(sqlqry, Workpermit.class).setParameter("emp", emp);

        //Εκτέλεση ερωτήματος
        List<Workpermit> WPList = qry.getResultList();
        
        if (WPList.size()>0) return true; else  return false;
    }

 



///test-------------------------
    public List<RestWorkPermit> getRestDaysByWPT(Employee emp){
                        
        //Aναζητεί πόσες ημέρες άδειας δικαιούται για κάθε τύπο άδειας
        //για τον emp
        Query awpQuery = em.createQuery(  "select awp  " +
                                          "from Availableworkpermit awp " +
                                          "where awp.employeeId = :emp");
        awpQuery.setParameter("emp", emp); 
        
        List<Availableworkpermit> AWPList = awpQuery.getResultList();
        
        
        //Kρατά τα υπόλοιπα αδειών από κάθε τύπο άδειας
        List<RestWorkPermit> wp = new ArrayList<>();
        
        for (Availableworkpermit awp:AWPList){
            //Βρίσκει πόσες ημέρες έχει πάρει για κάθε τύπο άδειας που 
            //έχει δικαίωμα να δηλώσει     
            Query wpQuery = em.createQuery(  "select coalesce(sum(wp.numdays),0), " +
                                             "max(wp.todate) " +
                                             "from Workpermit wp " +
                                             "where wp.employeeId = :emp " +
                                             "and wp.approved = 1 " +
                                             "and wp.workPermitTypeId = :wptype " +
                                             "group by wp.numdays ",int.class);

                wpQuery.setParameter("emp", emp);
                wpQuery.setParameter("wptype", awp.getWorkPermitTypeId());

                List sumNdaysList = wpQuery.getResultList();
                
                //Προσθέτω στη λίστα το αντικείμενο που έχει μέσα του τον υπάλληλο,
                //τον τύπο της άδειας και τις ημέρες που έχουν καταναλωθεί απο αυτόν τον
                //τυπο άδειας. Το υπόλοιπο υπολογίζεται αυτόματα μεσα στην κλάση.
                //Επίσης στη λίστα βάζω την μεγαλύτερη ημερομηνία που έχει παρθεί η
                //άδεια για να υπολογίσω τη νέα άδεια απο την ημέρα αυτή και μετά.
                int sumNdays;
                Date maxDate=null;
                if (!sumNdaysList.isEmpty()) {
                    sumNdays=(int)((Object[])sumNdaysList.get(0))[0];
                    maxDate=(Date)((Object[])sumNdaysList.get(0))[1];
                } else sumNdays=0;
                    
                wp.add(new RestWorkPermit(emp,awp,sumNdays,maxDate));
                
                
        }
        
        return wp;
        
    }
    
}

