//Η κλάση αυτή χρησημοποιείται για να κρατά τα υπόλοιπα
//αδειών για κάθε τύπο άδειας. Υπολογίζει αυτόματα τις ημερομηνίες από έως 
//για την καταχώριση της άδειας.
package company;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import model.Availableworkpermit;
import model.Employee;
import model.Publicholidays;

public class RestWorkPermit {
    private Employee emp; //Στοιχεία υπαλλήλου
    private Availableworkpermit AworkPermit; //Τι δικαιούται από άδεια
    private int ypoloipo; //Υπόλοιπο άδειας
    private Date maxDate; //Ημνία ΈΩΣ της τελευταίας άδειας
    private Date fromDate; //Η άδεια που θα καταχωρηθεί θα αρχίζει απο fromDate
    private Date toDate;  //Η άδεια που θα καταχωρηθεί θα αρχίζει απο toDate
    private final Random rnd = new Random(); //Παραγωγή τυχαίων αριθμών
    private int numdays; //Πόσες ημέρες άδεια θα καταχωρηθεί (τυχαίο νουμερο με max το ypoloipo)
    private Utils u; //Βοηθητικές μέθοδοι
    List<Publicholidays> PList;
    

    public RestWorkPermit(Employee emp, Availableworkpermit AworkPermit, int sumNdays, Date maxDate) {
        this.emp = emp;
        this.AworkPermit = AworkPermit;
        this.maxDate = fixmaxDate(maxDate);
        this.ypoloipo = AworkPermit.getAvailableDays() - sumNdays;
                
        //Λίστα με το εορτολόγιο για την αποφυγή να δωθεί άδεια
        //ημέρα εορτής.
        u = new Utils(); 
        PList = u.GetPublicholidaysList();
        
        //Υπολογισμός ημ/νίας έναρξης-λήξης της άδειας που θα καταχωρηθεί.
        calcFromToDate();

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
        
        //Στην ημερομηνία Έως της τελευταίας άδειας προσθέτω ένα αριθμό
        //ημερών 1-15(αυθέρετη επιλογή) όπου θα αρχίζει η επόμενη άδεια.        
        maxDate = addDays(maxDate,rnd.nextInt(15));
        
        //Επιστρέφει την επόμενη εργάσιμη ημέρα εάν η ημέρα
        //εισόδου είναι ημέρα διακοπών ή Σαββατοκύριακο.
        fromDate = chkIsHoliday(maxDate);
        
        //Στην from date προσθέτω ένα τυχαίο άριθμό από το 1 έως το
        //υπόλοιπο άδειας που έχει για να προσθέσω την άδεια.
        numdays = rnd.nextInt(ypoloipo);
        if (numdays==0) numdays = 1;

        toDate = ComputeToDateExcludeWeekends(fromDate,numdays);
        
        //Επιστρέφει την επόμενη εργάσιμη ημέρα εάν η ημέρα
        //εισόδου είναι ημέρα διακοπών ή Σαββατοκύριακο.
        toDate = chkIsHoliday(toDate);        
        
System.out.println("fromDate="+this.fromDate+" toDate="+toDate+" numdays="+numdays);
        
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
    
    //Στο διάστημα από έως προσθέτει 2 ημέρες για κάθε σαββατοκύριακο
    //ο κωδικας προέρχεται από:
    //http://www.coderanch.com/t/374845/java/java/Add-days-weekend-days
    public Date ComputeToDateExcludeWeekends(Date FromDate, int days) {

        Calendar time = new GregorianCalendar();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);

        time.setTime(FromDate);
        for (int promise_days=days ; promise_days!=0 ; promise_days--) {
            time.add(Calendar.DATE, 1);
            if (time.get(Calendar.DAY_OF_WEEK)==7) {
                //Saturday always comes first
                time.add(Calendar.DATE, 2);
            }
        }
        return time.getTime();
    }
    
    //Εαν η ημέρα εισόδου είναι Σάββατο ή κυρική, επιστρέφεται 
    //η επόμενη εργάσιμη
    public Date chkIsWeekend(Date insDate) {
        Calendar time = new GregorianCalendar();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        time.setTime(insDate);
        if (time.get(Calendar.DAY_OF_WEEK)==7) time.add(Calendar.DATE, 1);
        if (time.get(Calendar.DAY_OF_WEEK)==6) time.add(Calendar.DATE, 2);
        return time.getTime();
    }
    
    //Κάνει έλεγχο εάν η ημερομηνία είναι ημερομηνία διακοπών 
    //και εάν είναι τότε προσθέτει μια ημέρα,
    //κάνει τον ίδιο έλεγχο για την επόμενη ημέρα.
    public Date chkIsHoliday(Date inDate){        
        boolean addAday;        
        {
            addAday = false;            
            for(Publicholidays p:PList){
                if (inDate.equals(p.getHolidaydate())) addAday = true;
            }
            if (addAday) inDate = addDays(inDate,1);
            
            //Ελέγχος εάν είναι Σαββατοκύριακο η επόμενη ημερα
            inDate = chkIsWeekend(inDate);
            
        } while (addAday);        
        
        return inDate;        
    }
    
    //Προσθέτει αριθμό ημερών σε μία ημερομηνία
    public Date addDays(Date inDate, int numDays){        
        if (numDays<=0) return inDate;        
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(inDate);
        cal.add(Calendar.DAY_OF_MONTH, numDays);        
        
        return cal.getTime();
    }
}