package com.aueb.glass.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Event implements Parcelable {

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

    //βοηθητικές
    private long startDateTime;

    public Event() { }

    protected Event(Parcel in) {
        id = in.readString();
        organizerId = in.readString();
        name = in.readString();
        description = in.readString();
        category = in.readString();
        url = in.readString();
        remainingTickets = in.readInt();
        maxTickets = in.readInt();
        isPublished = in.readByte() != 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    public void initStartDate() {
        this.startDate = new Date(this.startDateTime);
    }

    public String getStartDateToDisplay() {
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy h:mm a");
        return dateFormat.format(this.startDate.getTime());
    }

    public boolean hasStarted() { return this.startDate.after(new Date()); }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.organizerId);
        parcel.writeString(this.name);
        parcel.writeString(this.description);
        parcel.writeString(this.category);
        parcel.writeString(this.url);

        this.startDateTime = this.startDate.getTime();
        parcel.writeLong(this.startDateTime);

        parcel.writeInt(this.remainingTickets);
        parcel.writeInt(this.maxTickets);
    }
}