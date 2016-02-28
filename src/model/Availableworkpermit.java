/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dionysis
 */
@Entity
@Table(name = "AVAILABLEWORKPERMIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Availableworkpermit.findAll", query = "SELECT a FROM Availableworkpermit a"),
    @NamedQuery(name = "Availableworkpermit.findByAvailableworkpermitId", query = "SELECT a FROM Availableworkpermit a WHERE a.availableworkpermitId = :availableworkpermitId"),
    @NamedQuery(name = "Availableworkpermit.findByAvailableDays", query = "SELECT a FROM Availableworkpermit a WHERE a.availableDays = :availableDays")})
public class Availableworkpermit implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AVAILABLEWORKPERMIT_ID")
    private Long availableworkpermitId;
    @Basic(optional = false)
    @Column(name = "AVAILABLE_DAYS")
    private int availableDays;
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne(optional = false)
    private Employee employeeId;
    @JoinColumn(name = "WORK_PERMIT_TYPE_ID", referencedColumnName = "WORK_PERMIT_TYPE_ID")
    @ManyToOne(optional = false)
    private Workpermittype workPermitTypeId;

    public Availableworkpermit() {
    }

    public Availableworkpermit(Long availableworkpermitId) {
        this.availableworkpermitId = availableworkpermitId;
    }

    public Availableworkpermit(Long availableworkpermitId, int availableDays) {
        this.availableworkpermitId = availableworkpermitId;
        this.availableDays = availableDays;
    }

    public Long getAvailableworkpermitId() {
        return availableworkpermitId;
    }

    public void setAvailableworkpermitId(Long availableworkpermitId) {
        Long oldAvailableworkpermitId = this.availableworkpermitId;
        this.availableworkpermitId = availableworkpermitId;
        changeSupport.firePropertyChange("availableworkpermitId", oldAvailableworkpermitId, availableworkpermitId);
    }

    public int getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(int availableDays) {
        int oldAvailableDays = this.availableDays;
        this.availableDays = availableDays;
        changeSupport.firePropertyChange("availableDays", oldAvailableDays, availableDays);
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
        hash += (availableworkpermitId != null ? availableworkpermitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Availableworkpermit)) {
            return false;
        }
        Availableworkpermit other = (Availableworkpermit) object;
        if ((this.availableworkpermitId == null && other.availableworkpermitId != null) || (this.availableworkpermitId != null && !this.availableworkpermitId.equals(other.availableworkpermitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Availableworkpermit[ availableworkpermitId=" + availableworkpermitId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
