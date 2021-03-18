package com.aueb.glass.models;

import java.util.Date;

public class Event {

    //βασικες πληροφοριες
    private String name;
    private String description;
    private String url;
    private Date startDate;
    private Date endDate;
    private int remainingTickets;
    //ρυθμισεις
    private int maxTickets;
    private boolean enableLiveChat;

    public Event() { }

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

    public boolean isEnableLiveChat() {
        return enableLiveChat;
    }

    public void setEnableLiveChat(boolean enableLiveChat) {
        this.enableLiveChat = enableLiveChat;
    }

    public void decreaseRemainingTickets() {
        this.remainingTickets -= 1;
    }

}
