package com.example.healthcareapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.DoctorViewHolder> {

    private Context context;
    private List<DoctorClass> doctorList;

    public DoctorListAdapter(Context context, List<DoctorClass> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    public void setDoctorList(List<DoctorClass> doctorList) {
        this.doctorList = doctorList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        DoctorClass doctor = doctorList.get(position);
        holder.textViewName.setText(doctor.getName());
        holder.textViewSpecialization.setText(doctor.getDegree()); // Assuming degree represents specialization in DoctorClass
        // Set other doctor's details to respective views if available
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewSpecialization;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSpecialization = itemView.findViewById(R.id.textViewSpecialization);
        }
    }
}
