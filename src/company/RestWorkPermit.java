//Η κλάση αυτή χρησημοποιείται για να κρατά τα υπόλοιπα
//αδειών από κάθε τύπο. 
package company;

import java.util.Date;
import model.Availableworkpermit;
import model.Employee;
import model.Workpermittype;

public class RestWorkPermit {
    private Employee emp; //Στοιχεία υπαλλήλου
    private Availableworkpermit AworkPermit; //Τι δικαιούται από άδεια
    private int ypoloipo; //Υπόλοιπο άδεις 
    private Date maxDate; //Ημνία ΈΩΣ της τελευταίας άδειας
    private Date fromDate; //Η άδεια που θα καταχωρηθεί θα αρχίζει απο fromDate
    private Date toDate;  //Η άδεια που θα καταχωρηθεί θα αρχίζει απο toDate

    public RestWorkPermit(Employee emp, Availableworkpermit AworkPermit, int sumNdays, Date maxDate) {
        this.emp = emp;
        this.AworkPermit = AworkPermit;
        this.maxDate = maxDate;
        this.ypoloipo = AworkPermit.getAvailableDays() - sumNdays;
        
        //Υπολογισμός ημ/νίας έναρξης-λήξης της άδειας που θα καταχωρηθεί.        
        calcFromToDate();
       
    }
    
    //Η μέθοδος calcFromToDate()υπολογίζει τις ημερομηνίες απο-έως που 
    //χρειάζονται για να γίνει η καταχώρηση της νέας άδειας.
    private void calcFromToDate(){
        //Από τις 365 ημέρες του χρόνου
        int YearDays = 365;
        
        //αφαιρώ όσες είναι η maxDate

    }
    
    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public Availableworkpermit getWorkPermitTypeId() {
        return AworkPermit;
    }

    public void setWorkPermitTypeId(Availableworkpermit AworkPermit) {
        this.AworkPermit = AworkPermit;
    }

    public int getYpoloipo() {
        return ypoloipo;
    }

    public void setYpoloipo(int ypoloipo) {
        this.ypoloipo = ypoloipo;
    }
    
    
}
