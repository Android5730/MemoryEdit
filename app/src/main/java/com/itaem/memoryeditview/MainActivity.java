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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back = findViewById(R.id.backBtn);
        nextBack = findViewById(R.id.nextBtn);
        AppCompatEditText editText = findViewById(R.id.editView);
        editMemory = new EditMemory(editText);
        nextBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMemory.rollNext();
                iniSetCheckable();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMemory.rollBack();
                iniSetCheckable();
            }
        });
    }
    public void iniSetCheckable(){
        if (editMemory.getLastStack().size()==0){
            back.setClickable(false);
        }
        if (editMemory.getNextStack().size()==0){
            nextBack.setClickable(false);
        }
    }
}