package com.quizzgameapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

public class User {

    @JsonIgnore
    @Id
    private ObjectId id = ObjectId.get();
    private String idUser;
    private String name;
    private String email;
    private String username;
    private String password;
    private String type;
    private String idLevel;
    private List<String> questionsAnswered;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(String idLevel) {
        this.idLevel = idLevel;
    }

    public List<String> getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuestionsAnswered(List<String> questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }
}
