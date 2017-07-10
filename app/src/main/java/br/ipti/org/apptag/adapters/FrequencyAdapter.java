package br.ipti.org.apptag.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.ipti.org.apptag.R;
import br.ipti.org.apptag.models.Frequency;
import br.ipti.org.apptag.models.SchoolReport;

/**
 * Created by Filipi Andrade on 01-Jul-17.
 */

public class FrequencyAdapter extends RecyclerView.Adapter<FrequencyAdapter.FrequencyViewHolder> {

    private Context mContext;
    private ArrayList<Frequency> mList;

    public FrequencyAdapter(Context c, ArrayList<Frequency> l) {
        this.mContext = c;
        this.mList = l;
    }

    @Override
    public FrequencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_frequency, parent, false);
        return new FrequencyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FrequencyViewHolder holder, int position) {
//        holder.tvYear.setText(mList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FrequencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public FrequencyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}