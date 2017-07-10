package br.ipti.org.apptag.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import br.ipti.org.apptag.R;
import br.ipti.org.apptag.adapters.GradeAdapter;
import br.ipti.org.apptag.api.TAGAPI;
import br.ipti.org.apptag.models.GradeReturn;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradesActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private GradeAdapter mGradeAdapter;
    private RecyclerView mRecyclerView;

    private String enrollment_fk, classroom_id, TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        //BUNDLE
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            enrollment_fk = bundle.getString("enrollment_fk");
            classroom_id = bundle.getString("classroom_id");
        }

        //TOOLBAR
        mToolbar = (Toolbar) findViewById(R.id.tbGrades);
        mToolbar.setTitle(R.string.grades);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RECYCLER VIEW
        mRecyclerView = (RecyclerView) findViewById(R.id.rvGrades);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //API
        TAGAPI.TAGInterfaceAPI tagInterfaceAPI = TAGAPI.getClient();
        Call<ArrayList<GradeReturn>> call = tagInterfaceAPI.getGrade(enrollment_fk, classroom_id);
        call.enqueue(new Callback<ArrayList<GradeReturn>>() {
            @Override
            public void onResponse(Call<ArrayList<GradeReturn>> call, Response<ArrayList<GradeReturn>> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().get(0).isValid()) {
                            mGradeAdapter = new GradeAdapter(GradesActivity.this, response.body().get(0).getGrade());
                            mRecyclerView.setAdapter(mGradeAdapter);
                        } else {
                            Toast.makeText(GradesActivity.this, "Esse aluno não possui notas cadastradas!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(GradesActivity.this, "Esse aluno não possui notas cadastradas!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(GradesActivity.this, "Esse aluno não possui notas cadastradas!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GradeReturn>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(GradesActivity.this, "Esse aluno não possui notas cadastradas!", Toast.LENGTH_SHORT).show();
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
