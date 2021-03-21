package com.aueb.glass.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {

    //βασικες πληροφοριες
    private String id;
    private String organizerId;
    private String name;
    private String description;
    private String url;
    private Date startDate;
    private Date endDate;
    private int remainingTickets;
    //ρυθμισεις
    private int maxTickets;
    private boolean showLiveParticipants;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public boolean isShowLiveParticipants() {
        return showLiveParticipants;
    }

    public void setShowLiveParticipants(boolean showLiveParticipants) {
        this.showLiveParticipants = showLiveParticipants;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public void increaseRemainingTickets() {
        this.remainingTickets += 1;
    }

    public void decreaseRemainingTickets() {
        this.remainingTickets -= 1;
    }

    public String getStartDateToDisplay() {
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy h:mm a");
        return dateFormat.format(this.startDate.getTime());
    }

    public String getEndDateToDisplay() {
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy h:mm a");
        return dateFormat.format(this.endDate.getTime());
    }

    public boolean isExpired() {
        return this.startDate.after(new Date());
    }

}
