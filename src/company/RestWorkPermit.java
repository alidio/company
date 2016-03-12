//Η κλάση αυτή χρησημοποιείται για να κρατά τα υπόλοιπα
//αδειών για κάθε τύπο άδειας. Υπολογίζει αυτόματα τις ημερομηνίες από έως 
//για την καταχώριση της άδειας.
package company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        cal.set(year, Calendar.JANUARY, 1);
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

        //Η ημερομηνία έως προκείπτει έαν ελέγξουμε όλες τις ενδιάμεσες
        //ημερομινίες για το εάν είναι σαββατοκύριακο ή ημέρα αργίας.
        //Σε τέτοια περπιπτωση επιστρέφεται η επόμενη ημερομηνία.
        //Ο κώδικας παρακάτω με τον τρόπο αυτό προσθέτει numdays 
        //εργάσιμες ημέρες.
        toDate = fromDate;
        for (int i=0;i<numdays;i++){
            toDate = chkIsHoliday(addDays(toDate,1));
        }
        
        //Επιστρέφει την επόμενη εργάσιμη ημέρα εάν η ημέρα
        //εισόδου είναι ημέρα διακοπών ή Σαββατοκύριακο.
        toDate = chkIsHoliday(toDate);        

    }
    
    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
        
        //Νέος υπολογισμός ημερομηνιών ΑΠΟ-ΕΩΣ
        calcFromToDate();
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
    
   
    //Εαν η ημέρα εισόδου είναι Σάββατο ή κυρική, επιστρέφεται 
    //η επόμενη εργάσιμη
    public Date chkIsWeekend(Date insDate) {
        Calendar time = new GregorianCalendar();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        time.setTime(insDate);
        if (time.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) time.add(Calendar.DATE, 1);
        if (time.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY) time.add(Calendar.DATE, 2);
        return time.getTime();
    }
    
    //Κάνει έλεγχο εάν η ημερομηνία είναι ημερομηνία διακοπών 
    //και εάν είναι τότε προσθέτει μια ημέρα,
    //κάνει τον ίδιο έλεγχο για την επόμενη ημέρα.
    public Date chkIsHoliday(Date inDate){        
        boolean addAday=true;       
        while (addAday){
            addAday = false;
            for(Publicholidays p:PList){
                if (removeTime(inDate).equals(removeTime(p.getHolidaydate()))) addAday = true;
            }
            if (addAday) inDate = addDays(inDate,1);
            //Ελέγχος εάν είναι Σαββατοκύριακο η επόμενη ημερα
            inDate = chkIsWeekend(inDate);
        }        
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
    
    //Αφαιρεί την πληροφορία της ώρας απο την ημερομηνία 
    //για να μπορεί να γίνει σύγκριση ημερομηνιών.
    //http://stackoverflow.com/questions/3144387/compare-two-dates-in-java
    public Date removeTime(Date date) {
        
        Calendar cal = Calendar.getInstance();  
        
        cal.setTime(date);  
        cal.set(Calendar.HOUR_OF_DAY, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        
        return cal.getTime();       
    }
}