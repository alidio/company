package Forms;

import company.DBManager;
import company.ThreadMonitor;
import company.WorkPermitSimulation;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Employee;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FRM_Workpermit extends javax.swing.JFrame {
    
    private JFrame thisframe;  //Αυτό το παράθυρο (Χρήση στον listener)
    private JFrame prevwin; //Προηγούμενο παράθυρο για επιστροφή στο menu
    private EntityManager em;
    private Employee selectedEmpId; //Ο Εργαζόμενος που επιλέγει ο χρήστης
    
    //Τα περιεχόμενα του πίνακα με τα συγκεντρωτικά στοιχεία των εργαζόμενων
    private List<Object[]> resultsSyg;
    
    //Τα περιεχόμενα του πίνακα με τα αναλυτικά στοιχεία των εργαζόμενων
    private List<Object[]> resultsAnal;
    
    //Για κάθε εργαζόμενο θα δημιουργηθεί ένα αντικείμενο τύπου
    //WorkPermitSimulation η οποία κάνει extend τον τύπο Thread.
    //To WPSimulationList κρατάει σε μια λίστα όλα αυτά τα αντικείμενα.
    private List<WorkPermitSimulation> WPSimulationList;
    
    public FRM_Workpermit(JFrame prevwin) {
        this.prevwin = prevwin;
        thisframe = this;
        this.em = DBManager.em;       
    
        this.prevwin.setEnabled(false);
        
        //Αρχικοποίηση στοιχείων φόρμας
        initComponents();
        
        //Γέμισμα του συγκεντρωτικού πίνακα
        fillTBSyg();
        
        //Ενεργοποίηση actionListener για την επιλογή 
        //γραμμής στον συγκεντρωτικό πίνακα
        crtTBSygActionListener();
        
    }

    //Καθαρίζει τα δεδομένα του πίνακα
    private void delTBLines(JTable tbl) {
        DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
        int rows = dtm.getRowCount();
        for (int i=0;i<rows;i++) {
            dtm.removeRow(0);
        }
    }
    
    //Καθαρίζει τα δεδομένα του πίνακα
    public void updTables() {
        fillTBSyg();
        fillTBAnal();        
    }
   
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBSyg = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TBAnal = new javax.swing.JTable();
        PBStartSim = new javax.swing.JButton();
        PBExtractXML = new javax.swing.JButton();
        PBExit = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Συγκεντρωτικός πίνακας αιτημάτων άδειας");

        TBSyg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Επώνυμο", "Όνομα", "E-mail", "Προϊστάμενος", "Αιτήματα Συνολικά", "Αιτήματα Εγκεκριμένα"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TBSyg.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TBSyg.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(TBSyg);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Αιτήματα Άδειας");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Αναλυτικός πίνακας αιτημάτων εργαζομένου");

        TBAnal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Είδος", "Από", "Έως", "Ημέρες", "Έγκριση"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TBAnal.setRowSelectionAllowed(false);
        jScrollPane2.setViewportView(TBAnal);

        PBStartSim.setText("Έναρξη Προσομοίωσης");
        PBStartSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PBStartSimActionPerformed(evt);
            }
        });

        PBExtractXML.setText("Εξαγωγή αιτημάτων σε XML");
        PBExtractXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PBExtractXMLActionPerformed(evt);
            }
        });

        PBExit.setText("Έξοδος");
        PBExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PBExitActionPerformed(evt);
            }
        });

        jTable1.setModel(TBSyg.getModel());
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue("Επώνυμο");
            jTable1.getColumnModel().getColumn(1).setHeaderValue("Όνομα");
            jTable1.getColumnModel().getColumn(2).setHeaderValue("E-mail");
            jTable1.getColumnModel().getColumn(3).setHeaderValue("Προϊστάμενος");
            jTable1.getColumnModel().getColumn(4).setHeaderValue("Αιτήματα Συνολικά");
            jTable1.getColumnModel().getColumn(5).setHeaderValue("Αιτήματα Εγκεκριμένα");
        }

        jTable2.setModel(TBAnal.getModel());
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setHeaderValue("Είδος");
            jTable2.getColumnModel().getColumn(1).setHeaderValue("Από");
            jTable2.getColumnModel().getColumn(2).setHeaderValue("Έως");
            jTable2.getColumnModel().getColumn(3).setHeaderValue("Ημέρες");
            jTable2.getColumnModel().getColumn(4).setHeaderValue("Έγκριση");
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PBExit)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(PBStartSim)
                                .addGap(31, 31, 31)
                                .addComponent(PBExtractXML)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PBStartSim)
                    .addComponent(PBExtractXML))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(PBExit)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PBExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PBExitActionPerformed
        //Κλείσιμο παραθύρου
        //Ενεργοποίηση προηγούμενου παραθύρου
        prevwin.setEnabled(true);
        dispose();
    }//GEN-LAST:event_PBExitActionPerformed
    
    
    //Εκκίνηση των threads.
    //Για κάθε εργαζόμενο φτάχνεται ένα thread το οποίο αναλαμβάνει να εκτελέσει
    //τις λειτουργίες οι οποίες περιγράφνται στις απαιτήσεις της εφαρμογής.
    //Επίσης δημιουργείται ακόμα ένα thread το οποίο έχει σαν σκοπό να
    //ενημερώνει την βασική οθόνη με τα αποτελέσματα των πεπραγμένων των
    //άλλων threads. To thread αυτό τερματίζει όταν και όλα τα άλλα έχουν τερματίσει.
    private void PBStartSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PBStartSimActionPerformed
        //Aρχικοποίηση λίστας με τα Thread, ένα για κάθε employee
        WPSimulationList = new ArrayList<>();
        
        //Για κάθε υπάλληλο δημιουργείται ένα Thread και όλα μαζί μπαινουν 
        //σε μία λίστα.
        for (Object[] result : resultsSyg) {
            // Στοιχεία του υπαλλήλου
            WPSimulationList.add(new WorkPermitSimulation((Employee)result[0]));
        }
        //Εκκίνηση των Thread
        for (WorkPermitSimulation result : WPSimulationList) {
            result.start();
        }
        
        //Δημιουργία Thread το οποίο ελέγχει τα threads που εκτελούνται 
        //και ενημερώνει την οθόνη.
        ThreadMonitor tmon = new ThreadMonitor(this,WPSimulationList);
        tmon.start();
        
    }//GEN-LAST:event_PBStartSimActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //Κλείσιμο παραθύρου
        //Ενεργοποίηση προηγούμενου παραθύρου
        prevwin.setEnabled(true);
        dispose();
    }//GEN-LAST:event_formWindowClosing

    synchronized public void fillTBSyg(){      

                int row = TBSyg.getSelectedRow();
System.out.println("row="+row);


        // Ανακτούμε τα στοιχεία αδειών των υπαλλήλων της ΒΔ
        TypedQuery<Object[]> query = em.createQuery(
            "SELECT  e, " +
                    "e.lname, \n" +
                    "e.fname, \n" +
                    "e.email, \n" +
                    "COALESCE((select e1.managerId.fname from Employee e1 where e1 = e),' '), \n" +
                    "COALESCE((select e2.managerId.fname from Employee e2 where e2 = e),' '), \n" +
                    "COALESCE((select count(w1) from Workpermit w1 where w1.employeeId = e),0), \n" +
                    "COALESCE((select count(w2) from Workpermit w2 where w2.employeeId = e and w2.approved = 1),0) \n" +
                    "FROM Employee e", Object[].class);
        
        resultsSyg = query.getResultList();
        
        //Καθαρισμός του πίνακα
        delTBLines(TBSyg);
        
        //TableModel του TBSyg
        DefaultTableModel Mdl = (DefaultTableModel) TBSyg.getModel();
        //Ορισμός γραμμών TBSyg = γραμμές query
        Mdl.setRowCount(resultsSyg.size());
        // Για κάθε υπάλληλο που υπάρχει στο ArrayList
        
        int i=0;
        for (Object[] result : resultsSyg) {
            // Στοιχεία του υπαλλήλου

            String lname = (String) result[1];
            String fname = (String) result[2];
            String email = (String) result[3];
            String manager = (String) result[4] +" "+ (String) result[5];
            Integer numdays = (int) result[6];
            Integer approved = (int) result[7];

            Mdl.setValueAt(lname, i, 0);
            Mdl.setValueAt(fname, i, 1);
            Mdl.setValueAt(email, i, 2);
            Mdl.setValueAt(manager, i, 3);
            Mdl.setValueAt(numdays, i, 4);
            Mdl.setValueAt(approved, i, 5);
            i++;
        }

    }
    
    public void crtTBSygActionListener(){
        TBSyg.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent event){
                int row = TBSyg.getSelectedRow();
                if (!(row<0)){
                    selectedEmpId = (Employee) resultsSyg.get(row)[0];
                    fillTBAnal();
                }
            }
        });
    }
    
    synchronized public void fillTBAnal(){       
        
        int row = TBSyg.getSelectedRow();
        if (!(row<0)){
            selectedEmpId = (Employee) resultsSyg.get(row)[0];            
        } else return;
        
        // Ανακτούμε τα στοιχεία αδειών του υπαλλήλου που επιλέχθηκε
        TypedQuery<Object[]> query = em.createQuery(
              "SELECT   wpt.workPermitTypeText,\n" +
                       "w.numdays, \n" +
                       "w.approved, \n" +
                       "w.fromdate, \n" +
                       "w.todate \n" +
                       " from Workpermit w, Workpermittype wpt \n" +
                       " where w.workPermitTypeId = wpt" +
                       " and w.employeeId = :emp", Object[].class);
        
        resultsAnal = query.setParameter("emp",selectedEmpId).getResultList();
        
        //Καθαρισμός του πίνακα
        delTBLines(TBAnal);
        
        //TableModel του TBSyg
        DefaultTableModel Mdl = (DefaultTableModel) TBAnal.getModel();
        //Ορισμός γραμμών TBAnal = γραμμές query
        Mdl.setRowCount(resultsAnal.size());
        // Για κάθε άδεια που υπάρχει στο ArrayList
        
        int i=0;
        for (Object[] result : resultsAnal) {
            // Στοιχεία του υπαλλήλου
            String eidos = (String) result[0];
            int hmeres = (int) result[1];
            String egrisi;
            if (result[2] != null){
                if ((int)result[2] == 1)egrisi = "Ναί"; else egrisi = "Όχι";
            } else egrisi = "Πρός Έγκριση";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String apo = sdf.format((Date) result[3]);
            String ews = sdf.format((Date) result[4]);
            
            Mdl.setValueAt(eidos, i, 0);
            Mdl.setValueAt(apo, i, 1);
            Mdl.setValueAt(ews, i, 2);
            Mdl.setValueAt(hmeres, i, 3);
            Mdl.setValueAt(egrisi, i, 4);
            i++;
        }       
        
    }
    
    
    private void PBExtractXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PBExtractXMLActionPerformed
//        
//        int selectedRow = TBSyg.getSelectedRow();
//        
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "ExportPlayListIsNotSelected", "NoRecordIsSelected",
//                    JOptionPane.WARNING_MESSAGE);
//        } else {
//            selectedRow = TBSyg.convertRowIndexToModel(selectedRow);
//            playList = playListList.get(selectedRow);
//
//            //Δημιουργούμε παράθυρο επιλογέα αρχείου
//            JFileChooser chooser = new JFileChooser();
//            //Φιλτράρουμε ώστε ο τύπος αρχείου να είναι μόνο xml
//            XMLFileFilter fileFilterXML = new XMLFileFilter();
//            chooser.setFileFilter(fileFilterXML);
//
//            //ο τύπος του παραθύρου να είναι αποθήκευσης
//            int selection = chooser.showSaveDialog(this);
//
//            //Εάν επιλέχθηκε Αποθήκευση
//            if (selection == JFileChooser.APPROVE_OPTION) {
//
//                //το όνομα που δόθηκε στο αρχείο
//                File selectedFile = chooser.getSelectedFile();
//
//                if (selectedFile != null) {
//                    //Το αρχείο να έχει .xml extension
//                    if (!selectedFile.getName().toLowerCase().endsWith(".xml")) {
//                        selectedFile = new File(selectedFile + ".xml");
//                    }
//                    //Εάν το όνομα του αρχείου υπάρχει ήδη στην επιλεγμένη τοποθεσία για αποθήκευση
//                    if (selectedFile.exists()) {
//                        int confirm = JOptionPane.showConfirmDialog(this,"FileAlreadyExists","Replace", 
//                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
//                        if (confirm != JOptionPane.YES_OPTION) {
//                            return;
//                        }
//                    }
//
//                    if (loggerUtil.isDebugEnabled()) {
//                        loggerUtil.debug("Exporting PlayList: " + playList.getIdPlayList()
//                                + " to XML with filename: " + selectedFile.getName());
//                    }
//                    //Κάνουμε παραγωγή και εξαγωγή του XML αρχείου
//                    controller.exportPlayListToXML(playList, selectedFile);
//
//                } else {
//                    JOptionPane.showMessageDialog(this, "FileDefExportError", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        }
    }//GEN-LAST:event_PBExtractXMLActionPerformed
        
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PBExit;
    private javax.swing.JButton PBExtractXML;
    private javax.swing.JButton PBStartSim;
    private javax.swing.JTable TBAnal;
    private javax.swing.JTable TBSyg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
