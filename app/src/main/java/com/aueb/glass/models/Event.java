package com.aueb.glass.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Event {

    //βασικες πληροφοριες
    private String id;
    private String organizerId;
    private String name;
    private String description;
    private String category;
    private String url;
    private Date startDate;
    private int remainingTickets;

    //ρυθμισεις
    private int maxTickets;
    private boolean isPublished;

    public Event() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public int getMaxTickets() {
        return maxTickets;
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }


    public void resetMaxAndRemainingTickets(int maxTickets) {
        if (this.maxTickets <= maxTickets) {
            this.remainingTickets += (maxTickets - this.maxTickets);
        } else {
            this.remainingTickets -= (this.maxTickets - maxTickets);
        }
        this.maxTickets = maxTickets;
    }

    public String getStartDateToDisplay() {
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy h:mm a");
        return dateFormat.format(this.startDate.getTime());
    }

    public boolean hasStarted() { return this.startDate.after(new Date()); }
}