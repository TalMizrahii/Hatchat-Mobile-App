package com.example.hatchatmobile1.DaoRelated;

import androidx.room.Entity;

/**
 * The message class to contain a message between two users.
 */
public class Message {
    private String content; // The content of the message.
    private String timeAndDate; // the time and date that the message was sent.
    private String sender; // The sender of the message.

    /**
     * A full constructor for all fields.
     * @param content The content of the message.
     * @param timeAndDate The date and time the message was sent.
     * @param sender Who sent the message.
     */
    public Message(String content, String timeAndDate, String sender) {
        this.content = content;
        this.timeAndDate = timeAndDate;
        this.sender = sender;
    }

    /**
     * A getter for the content of the message.
     * @return The content of the message.
     */
    public String getContent() {
        return content;
    }

    /**
     * A setter for the content of the message.
     * @param content The content of the message.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * A getter for the time and date the message was sent.
     * @return The time and date of the message.
     */
    public String getTimeAndDate() {
        return timeAndDate;
    }

    /**
     * A setter for the time and date the message was sent.
     * @param timeAndDate The time and date of the message.
     */
    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    /**
     * A getter for the sender of the message.
     * @return The sender of the message.
     */
    public String getSender() {
        return sender;
    }

    /**
     * A setter for the sender of the message.
     * @param sender The sender of the message.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
}
