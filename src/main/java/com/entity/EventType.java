package com.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idType;
    private String name;

    @OneToMany(mappedBy = "eventType",orphanRemoval = true)
    Set<Event> events;

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public EventType() {
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
