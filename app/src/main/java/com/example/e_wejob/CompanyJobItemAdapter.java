package com.example.e_wejob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_wejob.models.Job;

import java.util.List;

public class CompanyJobItemAdapter extends RecyclerView.Adapter<CompanyJobItemAdapter.ViewHolder> {

    private List<Job> mData;
    private LayoutInflater mInflater;

    private MyOnClick onClickListener;

    // data is passed into the constructor
    CompanyJobItemAdapter(Context context, List<Job> data, MyOnClick onClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.onClickListener = onClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.company_job_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Job j = mData.get(position);
        holder.jobTitle.setText(j.title);
        holder.companyName.setText(j.companyId + "");
        holder.requiredEducationLevel.setText(j.requiredEducationLevel);
        holder.requiredExperienceYears.setText(j.requiredExperienceYears + "");
        holder.salary.setText(j.salary + " S.P");

        holder.btnShowCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(j.id);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, companyName, requiredEducationLevel, requiredExperienceYears, salary;

        Button btnShowCandidates;

        ViewHolder(View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            companyName = itemView.findViewById(R.id.companyName);
            requiredEducationLevel = itemView.findViewById(R.id.requiredEducationLevel);
            requiredExperienceYears = itemView.findViewById(R.id.requiredExperienceYears);
            salary = itemView.findViewById(R.id.salary);
            btnShowCandidates = itemView.findViewById(R.id.btnShowCandidates);
//            itemView.setOnClickListener(this);
        }

    }

}
