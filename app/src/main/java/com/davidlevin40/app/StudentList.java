package com.davidlevin40.app;

import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class StudentList extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<RegiStudent> studentList;
    ListView listView;
    RegiStudent student;
    String schoolName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents);

        studentList = new ArrayList<>();
        // get the school name
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {}
            else {
                schoolName = extras.getString("School_Name");
            }
        }
        // creating the format of the list
        RegiStudent headLine = new RegiStudent("שם פרטי", "שם משפחה", "הצהרת בריאות");
        studentList.add(headLine);
        RegiStudent emptyRowAfterHeadLine = new RegiStudent("", "", "");
        studentList.add(emptyRowAfterHeadLine);

        // getting the data
        databaseReference = FirebaseDatabase.getInstance().getReference("Institution").child(schoolName);
        listView = (ListView) findViewById(R.id.listView);
        final Column_ListAdapter adapter =  new Column_ListAdapter(this, R.layout.list_adapter_view, studentList);
        listView.setAdapter(adapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RegiStudent student = snapshot.getValue(RegiStudent.class);
                studentList.add(student);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
    }


