package br.ipti.org.apptag.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private Typeface mTypeface;

    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_report);

        //TYPEFACE
        mTypeface = Typeface.createFromAsset(getResources().getAssets(), "font/Lato-Bold.ttf");

        //TOOLBAR
        mToolbar = (Toolbar) findViewById(R.id.tbSchoolReport);
        mToolbar.setTitle(R.string.school_report);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorIcons));
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RECYCLER VIEW
        mRecyclerView = (RecyclerView) findViewById(R.id.rvSchoolReport);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //SCHOOL REPORT
        mSchoolReports = new ArrayList<>();

        //SAVED SHARED PREFERENCES
        final String username = SavedSharedPreferences.getUsername(this);

        //TEXT VIEW
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setTypeface(mTypeface);

        //PROGRESS DIALOG
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Obtendo dados...");
        progressDialog.show();

        //API
        final TAGAPI.TAGInterfaceAPI tagInterfaceAPI = TAGAPI.getClient();

        Call<ArrayList<SchoolReportReturn>> call = tagInterfaceAPI.getStudentParent(username);
        call.enqueue(new Callback<ArrayList<SchoolReportReturn>>() {
            @Override
            public void onResponse(Call<ArrayList<SchoolReportReturn>> call, Response<ArrayList<SchoolReportReturn>> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().get(0).isValid()) {
                            mSchoolReports = response.body().get(0).getSchoolReports();

                            Call<ArrayList<UserInfoReturn>> callUserInfo = tagInterfaceAPI.getUserInfo(username);
                            callUserInfo.enqueue(new Callback<ArrayList<UserInfoReturn>>() {
                                @Override
                                public void onResponse(Call<ArrayList<UserInfoReturn>> call, Response<ArrayList<UserInfoReturn>> response) {
                                    try {
                                        if (response.body() != null) {
                                            if (response.body().get(0).isValid()) {
                                                String aux = response.body().get(0).getUser().get(0).getName().toLowerCase();
                                                StringBuffer stringBuffer = new StringBuffer();
                                                String[] part = aux.split(" ");
                                                for (String str : part) {
                                                    char[] c = str.trim().toCharArray();
                                                    c[0] = Character.toUpperCase(c[0]);
                                                    str = new String(c);

                                                    stringBuffer.append(str).append(" ");
                                                }

                                                tvUsername.setText(stringBuffer);

                                                mSchoolReportAdapter = new SchoolReportAdapter(SchoolReportActivity.this, mSchoolReports);
                                                mRecyclerView.setAdapter(mSchoolReportAdapter);

                                                progressDialog.dismiss();
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
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SchoolReportActivity.this, "Esse usuário não possui filhos matriculados!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SchoolReportActivity.this, "Esse usuário não possui filhos matriculados!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(SchoolReportActivity.this, "Esse usuário não possui filhos matriculados!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SchoolReportReturn>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
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
                SavedSharedPreferences.logout(this);
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
