package br.ipti.org.apptag.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ipti.org.apptag.adapters.SchoolReportAdapter;
import br.ipti.org.apptag.api.TAGAPI;
import br.ipti.org.apptag.extras.SavedSharedPreferences;
import br.ipti.org.apptag.models.SchoolReport;
import br.ipti.org.apptag.models.SchoolReportReturn;
import br.ipti.org.apptag.models.StudentReport;

import br.ipti.org.apptag.R;
import br.ipti.org.apptag.models.UserInfoReturn;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolReportActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SchoolReportAdapter mSchoolReportAdapter;
    private TextView tvUsername;
    private ArrayList<SchoolReport> mSchoolReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_report);

        //TOOLBAR
        mToolbar = (Toolbar) findViewById(R.id.tbSchoolReport);
        mToolbar.setTitle(R.string.school_report);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RECYCLER VIEW
        mRecyclerView = (RecyclerView) findViewById(R.id.rvSchoolReport);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //SCHOOL REPORT
        mSchoolReports = new ArrayList<>();

        //SAVED SHARED PREFERENCES
        String username = SavedSharedPreferences.getUsername(this);

        //TEXT VIEW
        tvUsername = (TextView) findViewById(R.id.tvUsername);

        //API
        TAGAPI.TAGInterfaceAPI tagInterfaceAPI = TAGAPI.getClient();

        Call<ArrayList<UserInfoReturn>> callUserInfo = tagInterfaceAPI.getUserInfo(username);
        callUserInfo.enqueue(new Callback<ArrayList<UserInfoReturn>>() {
            @Override
            public void onResponse(Call<ArrayList<UserInfoReturn>> call, Response<ArrayList<UserInfoReturn>> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().get(0).isValid()) {
                            tvUsername.setText(response.body().get(0).getUser().get(0).getName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserInfoReturn>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<ArrayList<SchoolReportReturn>> call = tagInterfaceAPI.getStudentParent(username);
        call.enqueue(new Callback<ArrayList<SchoolReportReturn>>() {
            @Override
            public void onResponse(Call<ArrayList<SchoolReportReturn>> call, Response<ArrayList<SchoolReportReturn>> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().get(0).isValid()) {
                            mSchoolReports = response.body().get(0).getSchoolReports();

                            mSchoolReportAdapter = new SchoolReportAdapter(SchoolReportActivity.this, mSchoolReports);
                            mRecyclerView.setAdapter(mSchoolReportAdapter);
                        } else {
                            Toast.makeText(SchoolReportActivity.this, "Esse usuário não possui filhos matriculados!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SchoolReportActivity.this, "Esse usuário não possui filhos matriculados!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SchoolReportActivity.this, "Esse usuário não possui filhos matriculados!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SchoolReportReturn>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SchoolReportActivity.this, "Esse usuário não possui filhos matriculados!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_school_report, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                startActivity(new Intent(this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
