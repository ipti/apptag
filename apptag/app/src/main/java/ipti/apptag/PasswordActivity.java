package ipti.apptag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by AdrianoDias on 10/03/2016.
 */
public class PasswordActivity extends Signin{

    private  Button btn_login;
    private  EditText text_password;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity_login);

        //Tela de entrada de senha
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(btn_loginListener);
        text_password = (EditText) findViewById(R.id.input_password);

    }

    private View.OnClickListener btn_loginListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            if (validateEditText(text_password)) {

                loadProgressBar("Verificando Senha");
                new CountDownTimer(3000,1000){
                    @Override
                    public void onTick(long millisUntilFinished){}

                    @Override
                    public void onFinish(){
                        progressBar.dismiss();
                        Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                        PasswordActivity.this.startActivity(intent);
                    }
                }.start();
            }
        }
    };

}
