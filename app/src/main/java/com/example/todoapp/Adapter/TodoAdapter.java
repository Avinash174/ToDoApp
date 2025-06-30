package com.example.todoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.MainActivity;
import com.example.todoapp.Model.TodoModel;
import com.example.todoapp.Model.Utils.DatabaseHelper;
import com.example.todoapp.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MYViewHolder> {

    private List<TodoModel>mlist;
    private MainActivity activity;
    private DatabaseHelper mydb;

    public  TodoAdapter(DatabaseHelper mydb,MainActivity activity){
        this.activity=activity;
        this.mydb=mydb;
    }

    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);

        return new MYViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        final TodoModel item=mlist.get(position);
        holder.checkBox.setText(item.getId());
        holder.checkBox.setChecked(toBollen(item.getStatus()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    mydb.updateStatus(item.getId(),1 );
                }else{
                    mydb.updateStatus(item.getId(),0);
                }
            }
        });

    }
    public  boolean toBollen(int num){
        return  num !=0;
    }

    public void setTask(List<TodoModel> mlist){
        this.mlist=mlist;
        notifyDataSetChanged();
    }
   public Context getContext(){
        return  activity;
   }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static  class MYViewHolder extends  RecyclerView.ViewHolder{
        CheckBox checkBox;


        public MYViewHolder(@NonNull View itemView) {

            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox);


        }
    }
}
