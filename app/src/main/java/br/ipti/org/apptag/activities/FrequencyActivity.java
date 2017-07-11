package br.ipti.org.apptag.activities;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;

import br.ipti.org.apptag.R;
import br.ipti.org.apptag.adapters.FrequencyAdapter;
import br.ipti.org.apptag.api.TAGAPI;
import br.ipti.org.apptag.models.FrequencyClassStudentReturn;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrequencyActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private BetterSpinner bsMonths;
    private Typeface mTypeface;
    private FrequencyAdapter mFrequencyAdapter;
    private RecyclerView mRecyclerView;

    private String TAG = "TAG", student_fk, month, classroom_fk;
    private static final String[] MONTHS = new String[]{
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency);

        //BUNDLE
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            student_fk = bundle.getString("student_fk");
            classroom_fk = bundle.getString("classroom_fk");
            month = "1";
        }

        //TYPEFACE
        mTypeface = Typeface.createFromAsset(getResources().getAssets(), "font/Lato-Regular.ttf");

        //TOOLBAR
        mToolbar = (Toolbar) findViewById(R.id.tbFrequency);
        mToolbar.setTitle(R.string.frequency);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RECYCLER VIEW
        mRecyclerView = (RecyclerView) findViewById(R.id.rvFrequency);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //API
        TAGAPI.TAGInterfaceAPI tagInterfaceAPI = TAGAPI.getClient();
        Call<ArrayList<FrequencyClassStudentReturn>> call = tagInterfaceAPI.getFrequency(student_fk, classroom_fk, month);
        call.enqueue(new Callback<ArrayList<FrequencyClassStudentReturn>>() {
            @Override
            public void onResponse(Call<ArrayList<FrequencyClassStudentReturn>> call, Response<ArrayList<FrequencyClassStudentReturn>> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().get(0).isValid()) {
                            mFrequencyAdapter = new FrequencyAdapter(FrequencyActivity.this, response.body().get(0).getFrequency_class(), response.body().get(0).getFrequency_student());
                            mRecyclerView.setAdapter(mFrequencyAdapter);
                        } else {
                            Toast.makeText(FrequencyActivity.this, "Esse aluno não possui frequência cadastrada!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FrequencyActivity.this, "Esse aluno não possui frequência cadastrada!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(FrequencyActivity.this, "Esse aluno não possui frequência cadastrada!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<FrequencyClassStudentReturn>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(FrequencyActivity.this, "Esse aluno não possui frequência cadastrada!", Toast.LENGTH_SHORT).show();
            }
        });

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
