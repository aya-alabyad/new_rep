package com.example.e_wejob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_wejob.models.Job;

import java.util.List;

public class JobItemAdapter extends RecyclerView.Adapter<JobItemAdapter.ViewHolder> {

    private List<Job> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    JobItemAdapter(Context context, List<Job> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.job_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Job j = mData.get(position);
        holder.jobTitle.setText(j.title);
        holder.companyName.setText(j.companyName);
        holder.requiredEducationLevel.setText(j.requiredEducationLevel);
        holder.requiredExperienceYears.setText(j.requiredExperienceYears + "");
        holder.salary.setText(j.salary + " S.P");
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, companyName, requiredEducationLevel, requiredExperienceYears, salary;

        ViewHolder(View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            companyName = itemView.findViewById(R.id.companyName);
            requiredEducationLevel = itemView.findViewById(R.id.requiredEducationLevel);
            requiredExperienceYears = itemView.findViewById(R.id.requiredExperienceYears);
            salary = itemView.findViewById(R.id.salary);
//            itemView.setOnClickListener(this);
        }

    }

}
