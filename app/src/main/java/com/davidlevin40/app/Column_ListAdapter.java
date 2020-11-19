package com.davidlevin40.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class Column_ListAdapter extends ArrayAdapter<RegiStudent> {

    private LayoutInflater mInflater;
    private ArrayList<RegiStudent> students;
    private int viewResourceId;


    public Column_ListAdapter(Context context, int textViewResourceId, ArrayList<RegiStudent> students) {
        super(context, textViewResourceId, students);
        this.students = students;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = textViewResourceId;
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(viewResourceId, null);

        RegiStudent user = students.get(position);

        if (user != null) {
            TextView firstName = (TextView) convertView.findViewById(R.id.first_name_edit_text);
            TextView lastName = (TextView) convertView.findViewById(R.id.last_name_edit_text);
            TextView healthSign = (TextView) convertView.findViewById(R.id.health_signature_edit_text);
            if (healthSign != null) {
                healthSign.setText((user.getSignatureStat()));
            }
            if (lastName != null) {
                lastName.setText((user.getLastName()));
            }
            if (firstName != null) {
                firstName.setText(user.getFirstName());
            }
        }
        return convertView;
    }
}
