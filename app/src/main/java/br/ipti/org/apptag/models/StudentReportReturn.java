package br.ipti.org.apptag.models;

import java.util.ArrayList;

/**
 * Created by Filipi Andrade on 01-Jul-17.
 */

public class StudentReportReturn {

    private boolean valid;
    private ArrayList<String> error;
    private ArrayList<StudentReport> students;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public ArrayList<String> getError() {
        return error;
    }

    public void setError(ArrayList<String> error) {
        this.error = error;
    }

    public ArrayList<StudentReport> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentReport> students) {
        this.students = students;
    }
}
