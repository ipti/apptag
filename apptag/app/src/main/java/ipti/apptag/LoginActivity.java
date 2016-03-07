package ipti.apptag;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;

import java.util.Timer;

/**
 * Created by AdrianoDias on 04/03/2016.
 */
public class LoginActivity extends AppCompatActivity
{
    private  Button btn_proximo;
    private  EditText text_user;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_login);
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
        btn_proximo = (Button) findViewById(R.id.btn_proximo);
        btn_proximo.setOnClickListener(btnListener);
        text_user = (EditText) findViewById(R.id.input_user);

//         progress = new ProgressDialog(this);
//        Timer timer = new Timer();
    }

    private OnClickListener btnListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            if (validateTextUser()) {

                loadProgressBar("Verificando Usuário");
                new CountDownTimer(3000,1000){
                    @Override
                    public void onTick(long millisUntilFinished){}

                    @Override
                    public void onFinish(){
                        progressBar.dismiss();
                        setContentView(R.layout.password_activity_login);
                    }
                }.start();



            }

        }
    };

    private boolean validateTextUser(){
        String user = text_user.getText().toString();
        if(isEmpty(user)) {
            String estring = "Não pode ser vazio";
            int ecolor = ContextCompat.getColor(this, R.color.steel_blue);;
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
            SpannableStringBuilder builder = new SpannableStringBuilder(estring);
            builder.setSpan(fgcspan, 0, estring.length(), 0);
            text_user.setError(builder);
            return  false;
        }




        text_user.setError(null);
        return true;
    }

    private void loadProgressBar(String msg){
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage(msg);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
    }

    private boolean isEmpty(String str){
        if(str.length() <= 0){
            return true;
        }
        return false;
    }


}
