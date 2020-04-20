package com.example.admission;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ShowData extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    public List<Deptdata> myparent;
    public HashMap<String, List<String>> expandableListDetail;

    public static DBHelper dbHelper;
    public Deptdata deptdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);


        ActionBar actionBar = getSupportActionBar();

        SharedPreferences sharedPreferences = getSharedPreferences("namePref", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        actionBar.setTitle("Welcome " + username);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        dbHelper = new DBHelper(this);
        deptdata = new Deptdata();
        myparent = new ArrayList<>();
        expandableListDetail = new HashMap<>();

        Cursor cursor = dbHelper.get_data("Select * from Department ORDER BY Dept_Name ASC");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String dept_name = cursor.getString(1);

            myparent.add(new Deptdata(id, dept_name));
        }
        cursor.close();
        int counter = myparent.size();
        //getData();
        int x = 1;
        for (int i = 1; i <= counter; i++) {
            Cursor cursor1 = dbHelper.get_data("Select Dept_Name from Department Where Dept_ID = " + x + ";");
            String name = null;
            if (cursor1.moveToNext()) {
                name = cursor1.getString(0);
                cursor1.close();
            } else {
                counter++;
                x++;
                continue;
            }

            List<String> data_list = new ArrayList<>();
            Cursor cursor2 = dbHelper.get_data("SELECT DISTINCT  Prog_Name FROM [Relation ]" +
                    " JOIN Program ON \"Relation \".Prog_ID = Program.Prog_ID WHERE Dept_ID=" + x + ";");
            while (cursor2.moveToNext()) {
                String a = cursor2.getString(0);
                data_list.add(a);
            }
            cursor2.close();
            x++;
            expandableListDetail.put(name, data_list);
            expandableListAdapter = new CustomExpandableListAdapter(this, myparent, expandableListDetail);

        }


        expandableListView.setAdapter(expandableListAdapter);



        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String Parent_Click = myparent.get(groupPosition).getDepartment();
                String Child_Click = expandableListDetail.get(myparent.get(groupPosition).getDepartment()).get(childPosition);

                Intent i = new Intent(ShowData.this, ShowUniData.class);



                i.putExtra("Parent", Parent_Click);
                i.putExtra("Child", Child_Click);
                startActivity(i);
                return false;
            }
        });
    }

}