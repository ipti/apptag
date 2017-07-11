package br.ipti.org.apptag.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ipti.org.apptag.activities.FrequencyActivity;
import br.ipti.org.apptag.activities.GradesActivity;
import br.ipti.org.apptag.api.TAGAPI;
import br.ipti.org.apptag.models.Grade;
import br.ipti.org.apptag.models.GradeReturn;
import br.ipti.org.apptag.models.StudentReport;

import br.ipti.org.apptag.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Filipi Andrade on 03-Jul-17.
 */

public class StudentReportAdapter extends RecyclerView.Adapter<StudentReportAdapter.StudentReportViewHolder> {

    private Context mContext;
    private ArrayList<StudentReport> mList;
    private Typeface mTypeface, mTypefaceBold;

    public StudentReportAdapter(Context c, ArrayList<StudentReport> l) {
        this.mContext = c;
        this.mList = l;
        this.mTypeface = Typeface.createFromAsset(mContext.getResources().getAssets(), "font/Lato-Regular.ttf");
        this.mTypefaceBold = Typeface.createFromAsset(mContext.getResources().getAssets(), "font/Lato-Bold.ttf");
    }

    @Override
    public StudentReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_student_report, parent, false);
        return new StudentReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentReportViewHolder holder, int position) {
        String aux = mList.get(position).getName().toLowerCase();
        StringBuffer stringBuffer = new StringBuffer();
        String[] part = aux.split(" ");
        for (String str : part) {
            char[] c = str.trim().toCharArray();
            c[0] = Character.toUpperCase(c[0]);
            str = new String(c);

            stringBuffer.append(str).append(" ");
        }

        holder.tvName.setText(stringBuffer);
        holder.tvClassroom.setText("Turma: " + mList.get(position).getClassroom_name());
        holder.tvSituation.setText("Situação: " + mList.get(position).getSituation());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class StudentReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName, tvClassroom, tvSituation, tvFrequency, tvGrades;
        private RelativeLayout rlFrequency, rlGrades;

        public StudentReportViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvName.setTypeface(mTypefaceBold);
            tvClassroom = (TextView) itemView.findViewById(R.id.tvClassroom);
            tvClassroom.setTypeface(mTypeface);
            tvSituation = (TextView) itemView.findViewById(R.id.tvSituation);
            tvSituation.setTypeface(mTypeface);
            tvFrequency = (TextView) itemView.findViewById(R.id.tvFrequency);
            tvFrequency.setTypeface(mTypeface);
            tvGrades = (TextView) itemView.findViewById(R.id.tvGrades);
            tvGrades.setTypeface(mTypeface);

            rlFrequency = (RelativeLayout) itemView.findViewById(R.id.rlFrequency);
            rlGrades = (RelativeLayout) itemView.findViewById(R.id.rlGrades);

            rlFrequency.setOnClickListener(this);
            rlGrades.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlFrequency:
                    mContext.startActivity(new Intent(mContext, FrequencyActivity.class)
                            .putExtra("student_fk", mList.get(getAdapterPosition()).getId())
                            .putExtra("classroom_fk", mList.get(getAdapterPosition()).getClassroom_id()));
                    break;
                case R.id.rlGrades:
                    //API
                    TAGAPI.TAGInterfaceAPI tagInterfaceAPI = TAGAPI.getClient();
                    Call<ArrayList<GradeReturn>> call = tagInterfaceAPI.getGrade(mList.get(getAdapterPosition()).getEnrollment_fk(), mList.get(getAdapterPosition()).getClassroom_id());
                    call.enqueue(new Callback<ArrayList<GradeReturn>>() {
                        @Override
                        public void onResponse(Call<ArrayList<GradeReturn>> call, Response<ArrayList<GradeReturn>> response) {
                            try {
                                if (response.body() != null) {
                                    if (response.body().get(0).isValid()) {
                                        ArrayList<Grade> grade = response.body().get(0).getGrade();
                                        if (grade.get(0).getGrade1() == null && grade.get(0).getGrade2() == null && grade.get(0).getGrade3() == null && grade.get(0).getGrade4() == null) {
                                            Toast.makeText(mContext, "Esse aluno não possui notas cadastradas!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mContext.startActivity(new Intent(mContext, GradesActivity.class)
                                                    .putExtra("enrollment_fk", mList.get(getAdapterPosition()).getEnrollment_fk())
                                                    .putExtra("classroom_id", mList.get(getAdapterPosition()).getClassroom_id()));
                                        }
                                    } else {
                                        Toast.makeText(mContext, "Esse aluno não possui notas cadastradas!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(mContext, "Esse aluno não possui notas cadastradas!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "Esse aluno não possui notas cadastradas!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<GradeReturn>> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(mContext, "Esse aluno não possui notas cadastradas!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    }
}
