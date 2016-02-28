/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dionysis
 */
@Entity
@Table(name = "PUBLICHOLIDAYS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Publicholidays.findAll", query = "SELECT p FROM Publicholidays p"),
    @NamedQuery(name = "Publicholidays.findById", query = "SELECT p FROM Publicholidays p WHERE p.id = :id"),
    @NamedQuery(name = "Publicholidays.findByName", query = "SELECT p FROM Publicholidays p WHERE p.name = :name"),
    @NamedQuery(name = "Publicholidays.findByHolidaydate", query = "SELECT p FROM Publicholidays p WHERE p.holidaydate = :holidaydate")})
public class Publicholidays implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @Column(name = "HOLIDAYDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holidaydate;

    public Publicholidays() {
    }

    public Publicholidays(Integer id) {
        this.id = id;
    }

    public Publicholidays(Integer id, String name, Date holidaydate) {
        this.id = id;
        this.name = name;
        this.holidaydate = holidaydate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getHolidaydate() {
        return holidaydate;
    }

    public void setHolidaydate(Date holidaydate) {
        this.holidaydate = holidaydate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Publicholidays)) {
            return false;
        }
        Publicholidays other = (Publicholidays) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Publicholidays[ id=" + id + " ]";
    }
    
}
