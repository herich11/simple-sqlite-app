package com.simplesqliteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<Person> peopleList;
    private Context context;
    private PersonClickListener listener;

    public PersonAdapter(Context context, List<Person> peopleList, PersonClickListener listener) {
        this.context = context;
        this.peopleList = peopleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = peopleList.get(position);
        holder.textViewName.setText(person.getName());

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEditButtonClick(person);
                }
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteButtonClick(person);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        Button buttonEdit;
        Button buttonDelete;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public interface PersonClickListener {
        void onEditButtonClick(Person person);
        void onDeleteButtonClick(Person person);
    }




}

