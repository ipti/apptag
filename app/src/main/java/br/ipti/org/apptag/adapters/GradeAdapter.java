package br.ipti.org.apptag.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.ipti.org.apptag.R;
import br.ipti.org.apptag.models.Grade;

/**
 * Created by Filipi Andrade on 01-Jul-17.
 */

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.SchoolReportViewHolder> {

    private Context mContext;
    private ArrayList<Grade> mList;

    public GradeAdapter(Context c, ArrayList<Grade> l) {
        this.mContext = c;
        this.mList = l;
    }

    @Override
    public SchoolReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_grade, parent, false);
        return new SchoolReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SchoolReportViewHolder holder, int position) {
//        holder.tvYear.setText(mList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SchoolReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public SchoolReportViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}