package com.example.healthcareapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends FirebaseRecyclerAdapter<model,DoctorAdapter.myviewholder> {
    public DoctorAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    {

        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.clinicadd.setText(model.getClinictimings());

        // Glide.with(holder.img.getcontext()).load(model.getPurl()).into(holder.img);

        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle book button click event
                Context context = v.getContext();
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("doctorName", model.getName()); // Pass the doctor's name as an extra parameter
                context.startActivity(intent);
            }
        });




    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    //will hold reference of single row xml
    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name,email,clinicadd;
        CircleImageView img;
        Button bookButton;
        public myviewholder(@NonNull View itemView) {
            super(itemView);


            name=(TextView) itemView.findViewById(R.id.nametext);
            clinicadd=(TextView)itemView.findViewById(R.id.coursetext);
            email=(TextView) itemView.findViewById(R.id.emailtext);
            bookButton = itemView.findViewById(R.id.bookButton);
        }
    }
}
