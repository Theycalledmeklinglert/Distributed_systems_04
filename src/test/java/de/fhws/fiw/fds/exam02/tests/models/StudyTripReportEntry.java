package de.fhws.fiw.fds.exam02.tests.models;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.io.Serializable;

public class StudyTripReportEntry extends AbstractModel implements Serializable, Cloneable {
    String city;
    String country;
    int numberOfStudents;
    int numberOfDays;


    public StudyTripReportEntry(String city, String country, int numberOfStudents, int numberOfDays) {
        this.city = city;
        this.country = country;
        this.numberOfStudents = numberOfStudents;
        this.numberOfDays = numberOfDays;
    }

    public StudyTripReportEntry()
    {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
