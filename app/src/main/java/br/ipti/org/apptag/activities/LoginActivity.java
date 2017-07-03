package br.ipti.org.apptag.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ipti.org.apptag.R;

/**
 * Created by AdrianoDias on 04/03/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtCPF, edtPassword;
    private Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //INIT
        edtCPF = (EditText) findViewById(R.id.edtCPF);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                startActivity(new Intent(this, SchoolReportActivity.class));
                break;
        }
    }
}
