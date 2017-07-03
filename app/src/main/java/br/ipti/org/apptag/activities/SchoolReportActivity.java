package br.ipti.org.apptag.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import br.ipti.org.apptag.adapters.SchoolReportAdapter;
import br.ipti.org.apptag.models.SchoolReport;
import br.ipti.org.apptag.models.StudentReport;

import br.ipti.org.apptag.R;

public class SchoolReportActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SchoolReportAdapter mSchoolReportAdapter;

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
        SchoolReport s1 = new SchoolReport();
        s1.setId("1");
        s1.setYear("2014");
        StudentReport sr1 = new StudentReport();
        sr1.setName("Jhonn Lennon");
        sr1.setClassroom_name("2º Ano");
        sr1.setSituation("Concluído");
        ArrayList<StudentReport> studentReports1 = new ArrayList<>();
        studentReports1.add(sr1);
        s1.setStudents(studentReports1);

        SchoolReport s2 = new SchoolReport();
        s2.setId("2");
        s2.setYear("2015");
        StudentReport sr2 = new StudentReport();
        sr2.setName("Adriano Michael Jackson");
        sr2.setClassroom_name("1º Ano");
        sr2.setSituation("Concluído");
        ArrayList<StudentReport> studentReports2 = new ArrayList<>();
        studentReports2.add(sr2);
        s2.setStudents(studentReports2);

        ArrayList<SchoolReport> schoolReports = new ArrayList<>();
        schoolReports.add(s1);
        schoolReports.add(s2);

        mSchoolReportAdapter = new SchoolReportAdapter(this, schoolReports);
        mRecyclerView.setAdapter(mSchoolReportAdapter);
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
