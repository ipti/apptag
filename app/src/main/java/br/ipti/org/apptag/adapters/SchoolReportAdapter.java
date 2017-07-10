package br.ipti.org.apptag.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ipti.org.apptag.models.SchoolReport;

import br.ipti.org.apptag.R;

/**
 * Created by Filipi Andrade on 01-Jul-17.
 */

public class SchoolReportAdapter extends RecyclerView.Adapter<SchoolReportAdapter.SchoolReportViewHolder> {

    private Context mContext;
    private ArrayList<SchoolReport> mList;
    private Typeface mTypeface;

    public SchoolReportAdapter(Context c, ArrayList<SchoolReport> l) {
        this.mContext = c;
        this.mList = l;
        this.mTypeface = Typeface.createFromAsset(mContext.getResources().getAssets(), "font/Lato-Regular.ttf");
    }

    @Override
    public SchoolReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_school_report, parent, false);
        return new SchoolReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SchoolReportViewHolder holder, int position) {
        holder.tvYear.setText(mList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SchoolReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View viewYear;
        private ImageView ivRightDown;
        private TextView tvYear;
        private RecyclerView rvStudentReport;

        public SchoolReportViewHolder(View itemView) {
            super(itemView);

            viewYear = (View) itemView.findViewById(R.id.viewYear);
            ivRightDown = (ImageView) itemView.findViewById(R.id.ivRightDown);
            tvYear = (TextView) itemView.findViewById(R.id.tvYear);
            tvYear.setTypeface(mTypeface);
            rvStudentReport = (RecyclerView) itemView.findViewById(R.id.rvStudentReport);
            rvStudentReport.setHasFixedSize(true);
            rvStudentReport.setLayoutManager(new LinearLayoutManager(mContext));
            rvStudentReport.setVisibility(View.GONE);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (mList.get(getAdapterPosition()).getStudents() != null) {
                    if (rvStudentReport.getVisibility() == View.GONE) {
                        ivRightDown.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_menu_down));
                        rvStudentReport.setAdapter(new StudentReportAdapter(mContext, mList.get(getAdapterPosition()).getStudents()));
                        rvStudentReport.setVisibility(View.VISIBLE);
                    } else {
                        ivRightDown.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_menu_right));
                        rvStudentReport.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}