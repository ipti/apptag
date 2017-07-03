package br.ipti.org.apptag.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.ipti.org.apptag.activities.FrequencyActivity;
import br.ipti.org.apptag.activities.GradesActivity;
import br.ipti.org.apptag.models.StudentReport;

import br.ipti.org.apptag.R;

/**
 * Created by Filipi Andrade on 03-Jul-17.
 */

public class StudentReportAdapter extends RecyclerView.Adapter<StudentReportAdapter.StudentReportViewHolder> {

    private Context mContext;
    private ArrayList<StudentReport> mList;

    public StudentReportAdapter(Context c, ArrayList<StudentReport> l) {
        this.mContext = c;
        this.mList = l;
    }

    @Override
    public StudentReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_student_report, parent, false);
        return new StudentReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentReportViewHolder holder, int position) {
        holder.tvName.setText(mList.get(position).getName());
        holder.tvClassroom.setText("Turma: " + mList.get(position).getClassroom_name());
        holder.tvSituation.setText(mList.get(position).getSituation());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class StudentReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName, tvClassroom, tvSituation;
        private RelativeLayout rlFrequency, rlGrades;

        public StudentReportViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvClassroom = (TextView) itemView.findViewById(R.id.tvClassroom);
            tvSituation = (TextView) itemView.findViewById(R.id.tvSituation);

            rlFrequency = (RelativeLayout) itemView.findViewById(R.id.rlFrequency);
            rlGrades = (RelativeLayout) itemView.findViewById(R.id.rlGrades);

            rlFrequency.setOnClickListener(this);
            rlGrades.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlFrequency:
                    mContext.startActivity(new Intent(mContext, FrequencyActivity.class));
                    break;
                case R.id.rlGrades:
                    mContext.startActivity(new Intent(mContext, GradesActivity.class));
                    break;
            }
        }
    }
}
