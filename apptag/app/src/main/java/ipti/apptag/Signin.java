package ipti.apptag;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

/**
 * Created by AdrianoDias on 10/03/2016.
 */
public class Signin extends AppCompatActivity {
    protected ProgressDialog progressBar;
    protected int progressBarStatus = 0;


    protected boolean validateEditText(EditText editText){
        String str = editText.getText().toString();
        if(isEmpty(str)) {
            editText.setError(alertingFail("Não pode ser vazio", editText));
            return  false;
        }
        editText.setError(null);
        return true;
    }

    protected void loadProgressBar(String msg){
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage(msg);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
    }

    protected boolean isEmpty(String str){
        if(str.length() <= 0){
            return true;
        }
        return false;
    }

    protected SpannableStringBuilder alertingFail(String estring, EditText editText){
        int ecolor = ContextCompat.getColor(this, R.color.steel_blue);;
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
        SpannableStringBuilder builder = new SpannableStringBuilder(estring);
        builder.setSpan(fgcspan, 0, estring.length(), 0);
        editText.setError(builder);
        return builder;
    }


}
