package com.example.todoapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.AddNewTask;
import com.example.todoapp.MainActivity;
import com.example.todoapp.Model.TodoModel;
import com.example.todoapp.Model.Utils.DatabaseHelper;
import com.example.todoapp.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MYViewHolder> {

    private List<TodoModel> mlist;
    private MainActivity activity;
    private DatabaseHelper mydb;

    public TodoAdapter(DatabaseHelper mydb, MainActivity activity) {
        this.activity = activity;
        this.mydb = mydb;
    }

    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new MYViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        final TodoModel item = mlist.get(position);

        // Show the actual task text
        holder.checkBox.setText(item.getTask());

        // Set checkbox state
        holder.checkBox.setChecked(toBoolean(item.getStatus()));

        // Update task status in database
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mydb.updateStatus(item.getId(), 1);
            } else {
                mydb.updateStatus(item.getId(), 0);
            }
        });
    }

    public boolean toBoolean(int num) {
        return num != 0;
    }

    public void setTask(List<TodoModel> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    public void deleteTask(int position) {
        TodoModel item = mlist.get(position);
        mydb.deleteTask(item.getId());
        mlist.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position) {
        TodoModel item = mlist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("Id", item.getId());
        bundle.putString("task", item.getTask());

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag());
    }

    public Context getContext() {
        return activity;
    }

    @Override
    public int getItemCount() {
        return mlist != null ? mlist.size() : 0;
    }

    public static class MYViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}