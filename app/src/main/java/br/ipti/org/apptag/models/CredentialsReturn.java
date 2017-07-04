package br.ipti.org.apptag.models;

import java.util.ArrayList;

/**
 * Created by Filipi Andrade on 04-Jul-17.
 */

public class CredentialsReturn {

    private boolean valid;
    private ArrayList<String> error;
    private ArrayList<Credentials> credentials;

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

    public ArrayList<Credentials> getCredentials() {
        return credentials;
    }

    public void setCredentials(ArrayList<Credentials> credentials) {
        this.credentials = credentials;
    }
}
