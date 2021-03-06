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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mild-TN
 */
@Entity
@Table(name = "ACCOUNT")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
  , @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM Account a WHERE a.accountId = :accountId")
  , @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
  , @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password")})
public class Account implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ACCOUNT_ID")
  private Integer accountId;
  // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "EMAIL")
  private String email;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PASSWORD")
  private String password;
  @JoinColumn(name = "REGISTER_ID", referencedColumnName = "REGISTER_ID")
  @ManyToOne(optional = false)
  private Register registerId;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
  private List<Customer> customerList;

  public Account() {
  }

  public Account(Integer accountId) {
    this.accountId = accountId;
  }

  public Account(Integer accountId, String email, String password) {
    this.accountId = accountId;
    this.email = email;
    this.password = password;
  }

  public Account(String email, String password, Register registerId) {
    this.email = email;
    this.password = password;
    this.registerId = registerId;
  }

  public String checkPass(String newPass) {
    if (!newPass.equals(this.getPassword())) {
      this.password = newPass;
    }
    return newPass;

  }

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Register getRegisterId() {
    return registerId;
  }

  public void setRegisterId(Register registerId) {
    this.registerId = registerId;
  }

  @XmlTransient
  public List<Customer> getCustomerList() {
    return customerList;
  }

  public void setCustomerList(List<Customer> customerList) {
    this.customerList = customerList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (accountId != null ? accountId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Account)) {
      return false;
    }
    Account other = (Account) object;
    if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "models.Account[ accountId=" + accountId + " ]";
  }

}
