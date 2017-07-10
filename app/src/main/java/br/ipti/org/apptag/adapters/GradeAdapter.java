package br.ipti.org.apptag.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.ipti.org.apptag.R;
import br.ipti.org.apptag.models.Grade;

/**
 * Created by Filipi Andrade on 01-Jul-17.
 */

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {

    private Context mContext;
    private ArrayList<Grade> mList;
    private Typeface mTypeface;

    public GradeAdapter(Context c, ArrayList<Grade> l) {
        this.mContext = c;
        this.mList = l;
        this.mTypeface = Typeface.createFromAsset(mContext.getResources().getAssets(), "font/Lato-Regular.ttf");
    }

    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_grade, parent, false);
        return new GradeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position) {
        if (mList.get(position).getDiscipline_name().equals("Arte (Educação Artística, Teatro, Dança, Música, Artes Plásticas e outras)")) {
            holder.tvDisciplineName.setText("Artes");
        } else {
            holder.tvDisciplineName.setText(mList.get(position).getDiscipline_name());
        }

        String grade1 = mContext.getResources().getString(R.string.grade1) + " - " + mList.get(position).getGrade1();
        holder.tvGrade1.setText(Html.fromHtml(grade1));
        if (mList.get(position).getRecovery_grade1() == null) {
            String grade1Rec = mContext.getResources().getString(R.string.grade1Rec) + " - " + "";
            holder.tvGrade1Rec.setText(Html.fromHtml(grade1Rec));
        } else {
            String grade1Rec = mContext.getResources().getString(R.string.grade1Rec) + " - " + mList.get(position).getRecovery_grade1();
            holder.tvGrade1Rec.setText(Html.fromHtml(grade1Rec));
        }


        String grade2 = mContext.getResources().getString(R.string.grade2) + " - " + mList.get(position).getGrade2();
        holder.tvGrade2.setText(Html.fromHtml(grade2));
        if (mList.get(position).getRecovery_grade2() == null) {
            String grade2Rec = mContext.getResources().getString(R.string.grade2Rec) + " - " + "";
            holder.tvGrade2Rec.setText(Html.fromHtml(grade2Rec));
        } else {
            String grade2Rec = mContext.getResources().getString(R.string.grade2Rec) + " - " + mList.get(position).getRecovery_grade2();
            holder.tvGrade2Rec.setText(Html.fromHtml(grade2Rec));
        }

        String grade3 = mContext.getResources().getString(R.string.grade3) + " - " + mList.get(position).getGrade3();
        holder.tvGrade3.setText(Html.fromHtml(grade3));
        if (mList.get(position).getRecovery_grade3() == null) {
            String grade3Rec = mContext.getResources().getString(R.string.grade3Rec) + " - " + "";
            holder.tvGrade3Rec.setText(Html.fromHtml(grade3Rec));
        } else {
            String grade3Rec = mContext.getResources().getString(R.string.grade3Rec) + " - " + mList.get(position).getRecovery_grade3();
            holder.tvGrade3Rec.setText(Html.fromHtml(grade3Rec));
        }

        String grade4 = mContext.getResources().getString(R.string.grade4) + " - " + mList.get(position).getGrade4();
        holder.tvGrade4.setText(Html.fromHtml(grade4));
        if (mList.get(position).getRecovery_grade4() == null) {
            String grade4Rec = mContext.getResources().getString(R.string.grade4Rec) + " - " + "";
            holder.tvGrade4Rec.setText(Html.fromHtml(grade4Rec));
        } else {
            String grade4Rec = mContext.getResources().getString(R.string.grade4Rec) + " - " + mList.get(position).getRecovery_grade4();
            holder.tvGrade4Rec.setText(Html.fromHtml(grade4Rec));
        }

        if (mList.get(position).getRecovery_final_grade() == null) {
            String gradeFinalRec = mContext.getResources().getString(R.string.gradeFinalRec) + " - " + "";
            holder.tvGradeFinalRec.setText(Html.fromHtml(gradeFinalRec));
        } else {
            String gradeFinalRec = mContext.getResources().getString(R.string.gradeFinalRec) + " - " + mList.get(position).getRecovery_final_grade();
            holder.tvGradeFinalRec.setText(Html.fromHtml(gradeFinalRec));
        }

        double g1 = Double.valueOf(mList.get(position).getGrade1());
        double g2 = Double.valueOf(mList.get(position).getGrade2());
        double g3 = Double.valueOf(mList.get(position).getGrade3());
        double g4 = Double.valueOf(mList.get(position).getGrade4());
        double final_media = (g1 + g2 + g3 + g4) / 4;

        if (final_media >= 5.0) {
            String situation = String.valueOf(final_media) + " - " + mContext.getResources().getText(R.string.approved);
            holder.tvSituation.setText(situation);
        } else {
            String situation = String.valueOf(final_media) + " - " + mContext.getResources().getText(R.string.disapproved);
            holder.tvSituation.setText(situation);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class GradeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDisciplineName, tvSituation, tvGrade1, tvGrade1Rec, tvGrade2, tvGrade2Rec, tvGrade3, tvGrade3Rec, tvGrade4, tvGrade4Rec, tvGradeFinalRec;

        public GradeViewHolder(View itemView) {
            super(itemView);

            tvDisciplineName = (TextView) itemView.findViewById(R.id.tvDisciplineName);
            tvDisciplineName.setTypeface(mTypeface);
            tvSituation = (TextView) itemView.findViewById(R.id.tvSituation);
            tvSituation.setTypeface(mTypeface);
            tvGrade1 = (TextView) itemView.findViewById(R.id.tvGrade1);
            tvGrade1.setTypeface(mTypeface);
            tvGrade1Rec = (TextView) itemView.findViewById(R.id.tvGrade1Rec);
            tvGrade1Rec.setTypeface(mTypeface);
            tvGrade2 = (TextView) itemView.findViewById(R.id.tvGrade2);
            tvGrade2.setTypeface(mTypeface);
            tvGrade2Rec = (TextView) itemView.findViewById(R.id.tvGrade2Rec);
            tvGrade2Rec.setTypeface(mTypeface);
            tvGrade3 = (TextView) itemView.findViewById(R.id.tvGrade3);
            tvGrade3.setTypeface(mTypeface);
            tvGrade3Rec = (TextView) itemView.findViewById(R.id.tvGrade3Rec);
            tvGrade3Rec.setTypeface(mTypeface);
            tvGrade4 = (TextView) itemView.findViewById(R.id.tvGrade4);
            tvGrade4.setTypeface(mTypeface);
            tvGrade4Rec = (TextView) itemView.findViewById(R.id.tvGrade4Rec);
            tvGrade4Rec.setTypeface(mTypeface);
            tvGradeFinalRec = (TextView) itemView.findViewById(R.id.tvGradeFinalRec);
            tvGradeFinalRec.setTypeface(mTypeface);

//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}