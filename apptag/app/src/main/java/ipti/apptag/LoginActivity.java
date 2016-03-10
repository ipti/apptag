package ipti.apptag;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;

import java.util.Timer;

/**
 * Created by AdrianoDias on 04/03/2016.
 */
public class LoginActivity extends Signin
{
    private  Button btn_proximo;
    private  EditText text_user;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_login);

        //Tela de entrada de usuário
        btn_proximo = (Button) findViewById(R.id.btn_proximo);
        btn_proximo.setOnClickListener(btn_proximoListener);
        text_user = (EditText) findViewById(R.id.input_user);
    }

    private OnClickListener btn_proximoListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            if (validateEditText(text_user)) {

                loadProgressBar("Verificando Usuário");
                new CountDownTimer(3000,1000){
                    @Override
                    public void onTick(long millisUntilFinished){}

                    @Override
                    public void onFinish(){
                        progressBar.dismiss();
//                        setContentView(R.layout.password_activity_login);
                        Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                        LoginActivity.this.startActivity(intent);

                    }
                }.start();
            }
        }
    };

}
