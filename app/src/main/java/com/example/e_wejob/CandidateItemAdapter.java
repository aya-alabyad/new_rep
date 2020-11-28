package com.example.e_wejob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_wejob.models.Candidate;

import java.util.List;

public class CandidateItemAdapter extends RecyclerView.Adapter<CandidateItemAdapter.ViewHolder> {

    private List<Candidate> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    CandidateItemAdapter(Context context, List<Candidate> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.candidate_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Candidate c = mData.get(position);
        holder.fullName.setText(c.full_name);
        String diplomas = c.diplomaType;
       /* for (int i = 0; i < c.diplomaList.size(); i++) {
            Diploma d = c.diplomaList.get(i);
            diplomas += d.diplomaType + " " + d.diplomaTitle + "\n";
        }*/

        holder.diploma.setText(diplomas);
        holder.experienceYears.setText(c.experienceYears + "");
        holder.phone.setText(c.phone);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, diploma, experienceYears, phone;

        ViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName);
            diploma = itemView.findViewById(R.id.diploma);
            experienceYears = itemView.findViewById(R.id.experienceYears);
            phone = itemView.findViewById(R.id.phone);
        }

    }

}
