package com.example.todoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp.Model.Utils.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    public  static final String TAG="AddNewTask";
    private EditText mEditText;
    private Button mSaveButton;

    private DatabaseHelper myDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_new_task,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText=view.findViewById(R.id.editText);
        mSaveButton=view.findViewById(R.id.addbtn);

        myDB=new DatabaseHelper(getActivity());
        boolean isUpdate=false;

        Bundle bundle=getArguments();
        if(bundle!=null){
            isUpdate=true;
            String task=bundle.getString("task");
            mEditText.setText(task);

            if(task.length()>0){
                mSaveButton.setEnabled(false );
            }
        }
    }
}
