package com.tiy;

import javax.persistence.*;

@Entity
@Table(name = "userevents")
public class UserEvent {
    @Id
    @GeneratedValue
    int id;

    @OneToOne
    Event event;

    @OneToOne
    User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEvent() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserEvent(User user, Event event) {
        this.user = user;
        this.event = event;
    }

}
