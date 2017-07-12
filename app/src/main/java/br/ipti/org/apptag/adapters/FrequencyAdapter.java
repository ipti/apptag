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

import java.text.DecimalFormat;
import java.util.ArrayList;

import br.ipti.org.apptag.R;
import br.ipti.org.apptag.models.Frequency;
import br.ipti.org.apptag.models.FrequencyClass;
import br.ipti.org.apptag.models.FrequencyStudent;
import br.ipti.org.apptag.models.SchoolReport;

/**
 * Created by Filipi Andrade on 01-Jul-17.
 */

public class FrequencyAdapter extends RecyclerView.Adapter<FrequencyAdapter.FrequencyViewHolder> {

    private Context mContext;
    private ArrayList<FrequencyClass> mListClass;
    private ArrayList<FrequencyStudent> mListStudent;
    private Typeface mTypeface;

    public FrequencyAdapter(Context c, ArrayList<FrequencyClass> cs, ArrayList<FrequencyStudent> s) {
        this.mContext = c;
        this.mListClass = cs;
        this.mListStudent = s;
        this.mTypeface = Typeface.createFromAsset(mContext.getResources().getAssets(), "font/Lato-Regular.ttf");
    }

    @Override
    public FrequencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_frequency, parent, false);
        return new FrequencyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FrequencyViewHolder holder, int position) {
        try {
            if (mListClass.get(position).getDiscipline_name() == null) {
                holder.tvDisciplineName.setText(mContext.getResources().getText(R.string.all_disciplines));
            } else {
                holder.tvDisciplineName.setText(mListClass.get(position).getDiscipline_name());
            }

            int presence = mListClass.get(position).getClasses() - mListStudent.get(position).getFaults();
            double percent = (Double.valueOf(presence) / Double.valueOf(mListClass.get(position).getClasses()) * 1000) / 10;
            DecimalFormat formatter = new DecimalFormat("###.##");
            String final_percent = formatter.format(percent) + "%";
            holder.tvPercent.setText(final_percent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListClass.size();
    }

    public class FrequencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDisciplineName, tvPercent;

        public FrequencyViewHolder(View itemView) {
            super(itemView);

            tvDisciplineName = (TextView) itemView.findViewById(R.id.tvDisciplineName);
            tvDisciplineName.setTypeface(mTypeface);
            tvPercent = (TextView) itemView.findViewById(R.id.tvPercent);
            tvPercent.setTypeface(mTypeface);
        }

        @Override
        public void onClick(View v) {

        }
    }
}