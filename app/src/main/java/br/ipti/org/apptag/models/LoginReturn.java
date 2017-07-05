package br.ipti.org.apptag.models;

import java.util.ArrayList;

/**
 * Created by Filipi Andrade on 04-Jul-17.
 */

public class LoginReturn {

    private boolean valid;
    private ArrayList<String> error;
    private ArrayList<Login> login;

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

    public ArrayList<Login> getLogin() {
        return login;
    }

    public void setLogin(ArrayList<Login> login) {
        this.login = login;
    }
}
