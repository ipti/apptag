package br.ipti.org.apptag.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import br.ipti.org.apptag.extras.SavedSharedPreferences;
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

    private String TAG = "TAG", student_fk, month, classroom_fk, student_name;
    private boolean grades;
    private static final String[] MONTHS = new String[]{
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency);

        //BUNDLE
        Bundle bundle = getIntent().getExtras();
        StringBuffer stringBuffer = new StringBuffer();
        if (bundle != null) {
            student_name = bundle.getString("student_name");
            String name = student_name.toLowerCase();
            String[] part = name.split(" ");
            for (String str : part) {
                char[] c = str.trim().toCharArray();
                c[0] = Character.toUpperCase(c[0]);
                str = new String(c);

                stringBuffer.append(str).append(" ");
            }

            student_fk = bundle.getString("student_fk");
            classroom_fk = bundle.getString("classroom_fk");
            grades = bundle.getBoolean("grades");
            month = "1";
        }

        //TYPEFACE
        mTypeface = Typeface.createFromAsset(getResources().getAssets(), "font/Lato-Regular.ttf");

        //TOOLBAR
        mToolbar = (Toolbar) findViewById(R.id.tbFrequency);
        mToolbar.setTitle(getResources().getString(R.string.frequency) + " - " + stringBuffer);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorIcons));
        setSupportActionBar(mToolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorIcons), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RECYCLER VIEW
        mRecyclerView = (RecyclerView) findViewById(R.id.rvFrequency);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //PROGRESS DIALOG
        final ProgressDialog progressDialog = new ProgressDialog(FrequencyActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Espero um momento...");
        progressDialog.show();

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
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(FrequencyActivity.this, "O aluno não possui frequência cadastrada no mês escolhido!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(FrequencyActivity.this, "O aluno não possui frequência cadastrada no mês escolhido!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(FrequencyActivity.this, "O aluno não possui frequência cadastrada no mês escolhido!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<FrequencyClassStudentReturn>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(FrequencyActivity.this, "O aluno não possui frequência cadastrada no mês escolhido!", Toast.LENGTH_SHORT).show();
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
                mRecyclerView.setAdapter(null);
                month = String.valueOf(position + 1);
                arrayAdapter.notifyDataSetChanged();

                final ProgressDialog progressDialog = new ProgressDialog(FrequencyActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Espero um momento...");
                progressDialog.show();

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
                                    progressDialog.dismiss();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(FrequencyActivity.this, "O aluno não possui frequência cadastrada no mês escolhido!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(FrequencyActivity.this, "O aluno não possui frequência cadastrada no mês escolhido!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(FrequencyActivity.this, "O aluno não possui frequência cadastrada no mês escolhido!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<FrequencyClassStudentReturn>> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(FrequencyActivity.this, "O aluno não possui frequência cadastrada no mês escolhido!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_frequency, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_grade:
                if (grades) {
                    finish();
                } else {
                    startActivity(new Intent(this, GradesActivity.class)
                            .putExtra("student_name", student_name)
                            .putExtra("student_fk", student_fk)
                            .putExtra("classroom_fk", classroom_fk)
                            .putExtra("frequency", true));
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
