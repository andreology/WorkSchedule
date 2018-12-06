package com.schedulesmadeeasy.schedular;

/**
 * A class to store information of a group.
 * This class is used for the cardview and to push to a database.
 * @author Andre Barajas
 */

public class Group {
    String title;
    String members;
    String status;
    String id;

    public Group(){};

    /**
     * Constructor
     * @param title Name of the group.
     * @param members The number of members that belong to the group including creator.
     * @param status The current status of the group.
     * @param id The unique ID representing the group. Given by database.
     */
    public Group(String title, String members, String status, String id) {
        this.title = title;
        this.members = members;
        this.status = status;
        this.id = id;
    }

    /**
     * String representation of a group.
     * @return the string representation of a group.
     */
    @Override
    public String toString() {
        return String.format("Title: %s\nMembers: %s, Status: %s, ID: %s", title, members, status, id);
    }

    /////////GETTERS AND SETTERS///////////////////////
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}


