package com.tiy;

import javax.persistence.*;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String password) {
        this.location= location;
    }

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    String location;

    public Event() {
    }

    public Event(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
