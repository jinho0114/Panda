package com.example.firebasetest.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Post {
    private String documentId;
    private String title;
    private String name;
    private String disease;
    private String content;
    @ServerTimestamp
    private Date date;

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Post() {
    }

    public Post(String documentId, String name,String disease, String title, String content) {
        this.documentId = documentId;
        this.name = name;
        this.disease = disease;
        this.title = title;
        this.content = content;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", disease='" + disease + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
