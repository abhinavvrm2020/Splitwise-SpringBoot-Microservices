package com.example.MainProject.Service;

import java.util.List;

public class RegisterRequest {
    private String name;
    private String password;
    private String email;
    private List<Integer> paySettlement;
    private List<Integer> receiveSettlement;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getPaySettlement() {
        return paySettlement;
    }

    public void setPaySettlement(List<Integer> paySettlement) {
        this.paySettlement = paySettlement;
    }

    public List<Integer> getReceiveSettlement() {
        return receiveSettlement;
    }

    public void setReceiveSettlement(List<Integer> receiveSettlement) {
        this.receiveSettlement = receiveSettlement;
    }
}
