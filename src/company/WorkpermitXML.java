package company;

import java.io.File;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Workpermit;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class WorkpermitXML {
    
    private final DateFormat df;
    private File xmlFile;
    private boolean isNewDocument = true;
    
    public WorkpermitXML(File xmlFile){
        this.xmlFile = xmlFile;        
        df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    private Document readXmlDocument() {
        Document document;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
            isNewDocument = false;
            return document;
        }catch (Exception e){
            // File does not exist
            isNewDocument = true;
            return null;
        }
    }
    
    public void writeXML(List<Workpermit> workpermits){
        Document document = readXmlDocument();
  
  // στη συνέχεια πάω να δημιουργήσω ένα αρχείο όπου θα αποθηκεύσω όλα τα Workpermits που είναι στη βάση.
     try {
         if (isNewDocument){ // Create New file
  // Δημιουργώ ένα καινούργιο αρχείο
     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
  // Δημιουργία parser που παράγει το DOM XML αρχείων
     DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
  // Δυνατότητα δημιουργίας των στιγμιότυπων του XML αρχείου από το DOM
     Document doc = docBuilder.newDocument();
  // δημιουργώ την ρίζα workpermits
     Element rootElement = doc.createElement("workpermits");
     doc.appendChild(rootElement);
         }
     Element root = document.getDocumentElement();
  
     for (Workpermit so:workpermits)
  // System.out.println(so.getName());
       {
  // δημιουργώ ένα στοιχείο με το όνομα workpermit
        Element workpermit = document.createElement("Workpermit"); 

  // τον συνδέω με τον τρέχοντα κόμβο
        root.appendChild(workpermit); 
  // ο κόμβος αυτός έχει ένα χαρακτηριστικό το id (είναι η ιδιότητα id του Workpermit)
  // σαν τιμή θα πάρει id του Workpermit, για αυτό καλώ τη συνάρτηση getId()
        Attr attr = document.createAttribute("id"); 
        attr.setValue(Long.toString(so.getWorkPermitId())); 
        workpermit.setAttributeNode(attr);
        
  // κόμβος: Από Ημερομηνία        
        Element fromdate = document.createElement("fromdate");
        fromdate.appendChild(document.createTextNode(df.format(so.getFromdate())));
        workpermit.appendChild(fromdate);
        
  // κόμβος: Έως Ημερομηνία
        Element todate = document.createElement("todate");
        todate.appendChild(document.createTextNode(df.format(so.getTodate())));
        workpermit.appendChild(todate);

   // κόμβος: Ημέρες άδειας
        Element numdays = document.createElement("numdays");
        numdays.appendChild(document.createTextNode(Integer.toString(so.getNumdays())));
        workpermit.appendChild(numdays);
       }
 
       Transformer transformer = TransformerFactory.newInstance().newTransformer();
       DOMSource source = new DOMSource(document);        
       StreamResult result = new StreamResult(xmlFile); // δημιουργεία xml αρχείου
       transformer.transform(source, result);
       System.out.println("File saved!");

       } catch (Exception e) {
            e.printStackTrace();
      
               }
    }
}