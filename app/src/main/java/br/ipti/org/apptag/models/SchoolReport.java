package br.ipti.org.apptag.models;

import java.util.ArrayList;

/**
 * Created by Filipi Andrade on 01-Jul-17.
 */

public class SchoolReport {

    private String id;
    private String year;
    private ArrayList<StudentReport> students;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ArrayList<StudentReport> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentReport> students) {
        this.students = students;
    }
}
