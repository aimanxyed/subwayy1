package com.traped.subwayy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class requirements extends AppCompatActivity {


    EditText addChildrenEditText;
    Button addChildrenButton;
    DatabaseReference databaseChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);

        databaseChildren= FirebaseDatabase.getInstance().getReference("children");

        addChildrenEditText = findViewById(R.id.add_childreneditText);
        addChildrenButton = findViewById(R.id.add_childrenbutton);

        addChildrenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addchildren();
            }
        });








    }
    private  void addchildren()
    {
        String name = addChildrenEditText.getText().toString().trim();
        String button = addChildrenButton.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
           String id=  databaseChildren.push().getKey();
           children childrens = new children(id , name);
           databaseChildren.child(id).setValue(childrens);
           Toast.makeText(this, "artist added ", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "enter name ", Toast.LENGTH_SHORT).show();
        }
    }

}
