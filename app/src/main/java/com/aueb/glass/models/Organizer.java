package com.aueb.glass.models;

import java.util.ArrayList;
import java.util.List;

public class Organizer {

    private String id;
    private String email;
    private String fullName;
    private String phone;
    private String companyName;
    private String companyDescription;

    private List<Event> myEvents;

    public Organizer() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public void addNewEvent(Event myEvent) {
        if (this.myEvents == null) {
            this.myEvents = new ArrayList<>();
        }
        this.myEvents.add(myEvent);
    }

}
