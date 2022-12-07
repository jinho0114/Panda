package com.example.firebasetest.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Comment {
    public String documentId;
    private String name;

    @ServerTimestamp
    private Date date;
    private String content;

    public Comment(String documentId, String name, String content) {
        this.documentId = documentId;
        this.name = name;
        this.content = content;
    }

    public Comment() {
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "documentId='" + documentId + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                '}';
    }
}
