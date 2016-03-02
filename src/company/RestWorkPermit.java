//Η κλάση αυτή χρησημοποιείται για να κρατά τα υπόλοιπα
//αδειών από κάθε τύπο. 
package company;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import model.Availableworkpermit;
import model.Employee;

public class RestWorkPermit {
    private Employee emp; //Στοιχεία υπαλλήλου
    private Availableworkpermit AworkPermit; //Τι δικαιούται από άδεια
    private int ypoloipo; //Υπόλοιπο άδειας
    private Date maxDate; //Ημνία ΈΩΣ της τελευταίας άδειας
    private Date fromDate; //Η άδεια που θα καταχωρηθεί θα αρχίζει απο fromDate
    private Date toDate;  //Η άδεια που θα καταχωρηθεί θα αρχίζει απο toDate
    private final Random rnd = new Random(); //Παραγωγή τυχαίων αριθμών
    private int numdays; //Πόσες ημέρες άδεια θα καταχωρηθεί (τυχαίο νουμερο με max το ypoloipo)

    public RestWorkPermit(Employee emp, Availableworkpermit AworkPermit, int sumNdays, Date maxDate) {
        this.emp = emp;
        this.AworkPermit = AworkPermit;
        this.maxDate = fixmaxDate(maxDate);
        this.ypoloipo = AworkPermit.getAvailableDays() - sumNdays;
        
        //Υπολογισμός ημ/νίας έναρξης-λήξης της άδειας που θα καταχωρηθεί.        
        calcFromToDate();      
        
System.out.println("sumNdays=" + sumNdays +
        " ypoloipo=" + ypoloipo + 
        " maxDate=" + maxDate +
        " numdays=" + numdays + 
        " \t"+  emp.getLname() + 
        "\t" +this.AworkPermit.getWorkPermitTypeId().getWorkPermitTypeText());

    }
    
    //Εάν η ημερομηνία είναι null επιστρέφει 01/01/Τρέχοντος έτους
    private Date fixmaxDate(Date maxDate){
        
        if (!(maxDate == null)) return maxDate;
        
        Date retDate;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        cal.set(year, 1, 1);
        retDate = cal.getTime();
        return retDate;   
    }
    
    
    //Η μέθοδος calcFromToDate()υπολογίζει τις ημερομηνίες απο-έως που 
    //χρειάζονται για να γίνει η καταχώρηση της νέας άδειας.
    private void calcFromToDate(){
        
        //From Date = max date + random number(x)
        //To Date = From Date + random number(y)
        //x->Τυχαίο νούμερο σε ημέρες μετά την τελευταία άδεια 
        //και πρίν το τέλος του χρόνου
        //y->Τυχαίο νούμερο σε ημέρες ανάλογα με το υπόλοιπο του 
        //τύπου άδειας.
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(maxDate);
        //Στην τελευταία άδεια, προσθέτω ένα αριθμό ημερών μεταξύ της τελευταίας άδειας
        //και το τέλος του χρόνου
        int daysAfterLastWorkpermit = 1 + rnd.nextInt(365 - cal.get(Calendar.DAY_OF_YEAR) - ypoloipo);
        cal.add(Calendar.DAY_OF_MONTH, daysAfterLastWorkpermit);
        this.fromDate = cal.getTime();
        
        //Στην from date προσθέτω ένα τυχαίο άριθμό από το 1 έως το
        //υπόλοιπο άδειας που έχει για να προσθέσω την άδεια.

        numdays = 1+rnd.nextInt(ypoloipo);
        cal.add(Calendar.DAY_OF_MONTH, numdays);
        this.toDate = cal.getTime();
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

    public Availableworkpermit getAworkPermit() {
        return AworkPermit;
    }

    public void setAworkPermit(Availableworkpermit AworkPermit) {
        this.AworkPermit = AworkPermit;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getNumdays() {
        return numdays;
    }

    public void setNumdays(int numdays) {
        this.numdays = numdays;
    }
    
    
}
