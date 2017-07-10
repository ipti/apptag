package br.ipti.org.apptag.activities;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import br.ipti.org.apptag.R;

public class FrequencyActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private BetterSpinner bsMonths;
    private Typeface mTypeface;

    private String TAG = "TAG";
    private static final String[] MONTHS = new String[]{
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency);

        //TYPEFACE
        mTypeface = Typeface.createFromAsset(getResources().getAssets(), "font/Lato-Regular.ttf");

        //TOOLBAR
        mToolbar = (Toolbar) findViewById(R.id.tbFrequency);
        mToolbar.setTitle(R.string.frequency);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //BETTER SPINNER
        bsMonths = (BetterSpinner) findViewById(R.id.bsMonths);
        bsMonths.setTypeface(mTypeface);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, MONTHS) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTypeface(mTypeface);
                return v;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setTypeface(mTypeface);
                return v;
            }
        };

        bsMonths.setText(arrayAdapter.getItem(0));
        bsMonths.setAdapter(arrayAdapter);
        bsMonths.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
