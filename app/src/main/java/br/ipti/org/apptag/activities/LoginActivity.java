package br.ipti.org.apptag.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import br.ipti.org.apptag.R;
import br.ipti.org.apptag.api.TAGAPI;
import br.ipti.org.apptag.extras.SavedSharedPreferences;
import br.ipti.org.apptag.models.LoginReturn;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Autenticando...");
                progressDialog.show();

                String username = edtCPF.getText().toString();
                String password = edtPassword.getText().toString();

                if (username.length() == 0 && password.length() == 0) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Informe o usuário e a senha!", Toast.LENGTH_SHORT).show();
                } else if (username.length() == 0) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Informe o usuário!", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "informe o senha!", Toast.LENGTH_SHORT).show();
                } else {
                    TAGAPI.TAGInterfaceAPI tagInterfaceAPI = TAGAPI.getClient();
                    Call<ArrayList<LoginReturn>> call = tagInterfaceAPI.getLogin(username, password);
                    call.enqueue(new Callback<ArrayList<LoginReturn>>() {
                        @Override
                        public void onResponse(Call<ArrayList<LoginReturn>> call, Response<ArrayList<LoginReturn>> response) {
                            try {
                                progressDialog.dismiss();
                                if (response.body() != null) {
                                    if (response.body().get(0).isValid()) {
                                        SavedSharedPreferences.setUsername(LoginActivity.this, response.body().get(0).getLogin().get(0).getUsername());
                                        startActivity(new Intent(LoginActivity.this, SchoolReportActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Erro ao fazer login. Tente novamente!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Erro ao fazer login. Tente novamente!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(LoginActivity.this, "Erro ao fazer login. Tente novamente!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<LoginReturn>> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Erro ao fazer login. Tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }
}
