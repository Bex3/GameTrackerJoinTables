package com.tiy;

import javax.persistence.*;

@Entity
@Table(name = "contactrequests")
public class ContactRequest {
    @Id
    @GeneratedValue
    int id;

    @OneToOne
    User requestingUser;

    @OneToOne
    User targetUser;

    @Column(nullable = false)
    String status; //PENDING_APPROVAL, APPROVED, DENIED


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContactRequest() {
    }

    public User getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(User requestingUser) {
        this.requestingUser = requestingUser;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContactRequest(User requestingUser, User targetUser, String status) {
        this.requestingUser = requestingUser;
        this.targetUser = targetUser;
        this.status = status;
    }

}
