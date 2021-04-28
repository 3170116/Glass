package com.aueb.glass.models;

public class VotingOption {

    public static final int LIKE_OPTION = 1;
    public static final int DISLIKE_OPTION = 2;
    public static final int BREAK_OPTION = 3;
    public static final int CONTINUE_OPTION = 4;
    public static final int AGREE_OPTION = 5;
    public static final int DISAGREE_OPTION = 6;

    private String id;
    private String eventId;
    private String text;
    private int typeId;
    private boolean selected;
    private int votes;

    public VotingOption() {}

    public VotingOption(String text, int typeId, boolean selected) {
        this.text = text;
        this.typeId = typeId;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
