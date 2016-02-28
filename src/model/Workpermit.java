/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dionysis
 */
@Entity
@Table(name = "WORKPERMIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workpermit.findAll", query = "SELECT w FROM Workpermit w"),
    @NamedQuery(name = "Workpermit.findByWorkPermitId", query = "SELECT w FROM Workpermit w WHERE w.workPermitId = :workPermitId"),
    @NamedQuery(name = "Workpermit.findByFromdate", query = "SELECT w FROM Workpermit w WHERE w.fromdate = :fromdate"),
    @NamedQuery(name = "Workpermit.findByTodate", query = "SELECT w FROM Workpermit w WHERE w.todate = :todate"),
    @NamedQuery(name = "Workpermit.findByNumdays", query = "SELECT w FROM Workpermit w WHERE w.numdays = :numdays"),
    @NamedQuery(name = "Workpermit.findByApproved", query = "SELECT w FROM Workpermit w WHERE w.approved = :approved")})
public class Workpermit implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "WORK_PERMIT_ID")
    private Long workPermitId;
    @Basic(optional = false)
    @Column(name = "FROMDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromdate;
    @Basic(optional = false)
    @Column(name = "TODATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date todate;
    @Basic(optional = false)
    @Column(name = "NUMDAYS")
    private int numdays;
    @Column(name = "APPROVED")
    private Integer approved;
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne(optional = false)
    private Employee employeeId;
    @JoinColumn(name = "WORK_PERMIT_TYPE_ID", referencedColumnName = "WORK_PERMIT_TYPE_ID")
    @ManyToOne(optional = false)
    private Workpermittype workPermitTypeId;

    public Workpermit() {
    }

    public Workpermit(Long workPermitId) {
        this.workPermitId = workPermitId;
    }

    public Workpermit(Long workPermitId, Date fromdate, Date todate, int numdays) {
        this.workPermitId = workPermitId;
        this.fromdate = fromdate;
        this.todate = todate;
        this.numdays = numdays;
    }

    public Long getWorkPermitId() {
        return workPermitId;
    }

    public void setWorkPermitId(Long workPermitId) {
        Long oldWorkPermitId = this.workPermitId;
        this.workPermitId = workPermitId;
        changeSupport.firePropertyChange("workPermitId", oldWorkPermitId, workPermitId);
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        Date oldFromdate = this.fromdate;
        this.fromdate = fromdate;
        changeSupport.firePropertyChange("fromdate", oldFromdate, fromdate);
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        Date oldTodate = this.todate;
        this.todate = todate;
        changeSupport.firePropertyChange("todate", oldTodate, todate);
    }

    public int getNumdays() {
        return numdays;
    }

    public void setNumdays(int numdays) {
        int oldNumdays = this.numdays;
        this.numdays = numdays;
        changeSupport.firePropertyChange("numdays", oldNumdays, numdays);
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        Integer oldApproved = this.approved;
        this.approved = approved;
        changeSupport.firePropertyChange("approved", oldApproved, approved);
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        Employee oldEmployeeId = this.employeeId;
        this.employeeId = employeeId;
        changeSupport.firePropertyChange("employeeId", oldEmployeeId, employeeId);
    }

    public Workpermittype getWorkPermitTypeId() {
        return workPermitTypeId;
    }

    public void setWorkPermitTypeId(Workpermittype workPermitTypeId) {
        Workpermittype oldWorkPermitTypeId = this.workPermitTypeId;
        this.workPermitTypeId = workPermitTypeId;
        changeSupport.firePropertyChange("workPermitTypeId", oldWorkPermitTypeId, workPermitTypeId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workPermitId != null ? workPermitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workpermit)) {
            return false;
        }
        Workpermit other = (Workpermit) object;
        if ((this.workPermitId == null && other.workPermitId != null) || (this.workPermitId != null && !this.workPermitId.equals(other.workPermitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Workpermit[ workPermitId=" + workPermitId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
