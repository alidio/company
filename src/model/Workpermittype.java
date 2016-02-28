/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dionysis
 */
@Entity
@Table(name = "WORKPERMITTYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workpermittype.findAll", query = "SELECT w FROM Workpermittype w"),
    @NamedQuery(name = "Workpermittype.findByWorkPermitTypeId", query = "SELECT w FROM Workpermittype w WHERE w.workPermitTypeId = :workPermitTypeId"),
    @NamedQuery(name = "Workpermittype.findByWorkPermitTypeText", query = "SELECT w FROM Workpermittype w WHERE w.workPermitTypeText = :workPermitTypeText")})
public class Workpermittype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "WORK_PERMIT_TYPE_ID")
    private Long workPermitTypeId;
    @Basic(optional = false)
    @Column(name = "WORK_PERMIT_TYPE_TEXT")
    private String workPermitTypeText;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workPermitTypeId")
    private List<Workpermit> workpermitList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workPermitTypeId")
    private List<Availableworkpermit> availableworkpermitList;

    public Workpermittype() {
    }

    public Workpermittype(Long workPermitTypeId) {
        this.workPermitTypeId = workPermitTypeId;
    }

    public Workpermittype(Long workPermitTypeId, String workPermitTypeText) {
        this.workPermitTypeId = workPermitTypeId;
        this.workPermitTypeText = workPermitTypeText;
    }

    public Long getWorkPermitTypeId() {
        return workPermitTypeId;
    }

    public void setWorkPermitTypeId(Long workPermitTypeId) {
        this.workPermitTypeId = workPermitTypeId;
    }

    public String getWorkPermitTypeText() {
        return workPermitTypeText;
    }

    public void setWorkPermitTypeText(String workPermitTypeText) {
        this.workPermitTypeText = workPermitTypeText;
    }

    @XmlTransient
    public List<Workpermit> getWorkpermitList() {
        return workpermitList;
    }

    public void setWorkpermitList(List<Workpermit> workpermitList) {
        this.workpermitList = workpermitList;
    }

    @XmlTransient
    public List<Availableworkpermit> getAvailableworkpermitList() {
        return availableworkpermitList;
    }

    public void setAvailableworkpermitList(List<Availableworkpermit> availableworkpermitList) {
        this.availableworkpermitList = availableworkpermitList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workPermitTypeId != null ? workPermitTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workpermittype)) {
            return false;
        }
        Workpermittype other = (Workpermittype) object;
        if ((this.workPermitTypeId == null && other.workPermitTypeId != null) || (this.workPermitTypeId != null && !this.workPermitTypeId.equals(other.workPermitTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Workpermittype[ workPermitTypeId=" + workPermitTypeId + " ]";
    }
    
}
