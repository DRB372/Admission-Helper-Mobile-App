package com.example.admission;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowUniData extends AppCompatActivity {

    public ExpandableListView expandableListView;
    UniCustomAdapter expandableListAdapter;
    public UniData UniData;
    public List<UniData> Uni_Parent;
    public HashMap<String, List<String>> Uni_Child;
    public DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_uni_data);

        String Parent = getIntent().getStringExtra("Parent");
        String Child = getIntent().getStringExtra("Child");

        expandableListView = findViewById(R.id.expandableListViewUni);

        dbHelper = new DBHelper(this);
        Uni_Parent = new ArrayList<>();
        UniData = new UniData();
        Uni_Child = new HashMap<>();

        Cursor cursor = dbHelper.get_data("SELECT Uni_Name From [Relation ] JOIN University ON \"Relation \".Uni_ID=University.Uni_ID" +
                "    WHERE Dept_ID = (SELECT Dept_ID FROM Department WHERE Dept_Name = '" + Parent + "')" +
                "    AND Prog_ID = (SELECT Prog_ID FROM Program WHERE Prog_Name='" + Child + "') ORDER BY Uni_Name ASC;");
        while (cursor.moveToNext()) {
            String  uni_name = cursor.getString(0);


            Uni_Parent.add(new UniData(uni_name));
        }
        cursor.close();

        Cursor cursor1 = dbHelper.get_data(
                "SELECT Count(Uni_Name) From [Relation ] JOIN University ON \"Relation \".Uni_ID=University.Uni_ID" +
                        "    WHERE Dept_ID = (SELECT Dept_ID FROM Department WHERE Dept_Name = '" + Parent + "')" +
                        "    AND Prog_ID = (SELECT Prog_ID FROM Program WHERE Prog_Name='" + Child + "');"
        );

        int size = 0;
        while (cursor1.moveToNext()) {
            size = cursor1.getInt(0);
        }
        cursor1.close();


        for (int i = 0; i < size; i++) {
            Cursor cursor2 = dbHelper.get_data("SELECT Uni_Name From [Relation ] JOIN University ON \"Relation \".Uni_ID=University.Uni_ID" +
                    "    WHERE Dept_ID = (SELECT Dept_ID FROM Department WHERE Dept_Name = '" + Parent + "')" +
                    "    AND Prog_ID = (SELECT Prog_ID FROM Program WHERE Prog_Name='" + Child + "');");


            String name = null;

            if (cursor2.moveToPosition(i)) {
                name = cursor2.getString(0);
              //  Uni_Parent.add(new UniData(name));
                cursor2.close();
            } else {
                size++;
                continue;
            }
            List<String> data_list = new ArrayList<>();
            Cursor cursor3 = dbHelper.get_data("SELECT University.\"Admission Date\"," +
                    "University.\"Campus\",University.\"Website\" " +
                    "FROM University WHERE University.Uni_Name='" + name + "';");
            while (cursor3.moveToNext()) {
                String a = cursor3.getString(0);
                String b = cursor3.getString(1);
                String c = cursor3.getString(2);
                data_list.add(a);
                data_list.add(b);
                data_list.add(c);
            }
            cursor3.close();
            Uni_Child.put(name, data_list);
            expandableListAdapter = new UniCustomAdapter(this, Uni_Parent, Uni_Child);
        }
        expandableListView.setAdapter(expandableListAdapter);

    }
}
