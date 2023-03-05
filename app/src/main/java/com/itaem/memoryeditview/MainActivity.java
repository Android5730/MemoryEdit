package com.itaem.memoryeditview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

public class MainActivity extends AppCompatActivity {
    private  EditMemory editMemory;
    private Button back;
    private Button nextBack;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back = findViewById(R.id.backBtn);
        nextBack = findViewById(R.id.nextBtn);
        save = findViewById(R.id.saveBtn);
        AppCompatEditText editText = findViewById(R.id.editView);
        editMemory = new EditMemory(editText);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMemory.save();
            }
        });
        nextBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMemory.rollNext();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMemory.rollBack();
            }
        });
    }
    public void iniSetCheckable(){
        if (editMemory.getLastStack().size()==0){
            back.setClickable(false);
        }else {
            back.setClickable(true);
        }
        if (editMemory.getNextStack().size()==0){
            nextBack.setClickable(false);
        }else {
            nextBack.setClickable(true);
        }
    }
}