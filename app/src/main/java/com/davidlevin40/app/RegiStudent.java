package com.davidlevin40.app;

public class RegiStudent {

    String firstName, lastName, id, signatureStat, SE_ID, date;


    public RegiStudent() {}

    public RegiStudent(String SE_ID, String firstName, String lastName, String ID, String signatureStat) {
        this.firstName = firstName;
        this.SE_ID = SE_ID;
        this.lastName = lastName;
        this.id = ID;
        this.signatureStat = signatureStat;
    }

    public RegiStudent(String firstName, String lastName, String signatureStat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.signatureStat = signatureStat;
    }

    public String getSignatureStat() {
        return signatureStat;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
