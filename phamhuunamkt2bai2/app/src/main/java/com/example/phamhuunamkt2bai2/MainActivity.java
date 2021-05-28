package com.example.phamhuunamkt2bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Lich> arrayList = new ArrayList<>();
    ListviewAdapter listviewAdapter;
    ListView listView;
    EditText name , time , dates;
    CheckBox checkBox;
    TextView tongsomon;
    Button addbtn,updatebtn,deletebtn,searchbtn;
    int h1,m1;
    int pos;
    int idd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        DatePickerDialog.OnDateSetListener setListener;
        time = findViewById(R.id.time);
        tongsomon = findViewById(R.id.tongsomon);
        dates = findViewById(R.id.dates);
        checkBox = findViewById(R.id.checkboxtv);
        addbtn= findViewById(R.id.addbtn);
        updatebtn = findViewById(R.id.updatebtn);
        deletebtn = findViewById(R.id.deletebtn);
        searchbtn = findViewById(R.id.searchbtn);
        listView = findViewById(R.id.listview);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        h1 = hourOfDay;
                        m1 = minute;
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(0,0,0,h1,m1);
                        time.setText(DateFormat.format("hh:mm aa",calendar1));
                    }
                },12,0,false
                );
                timePickerDialog.updateTime(h1,m1);
                timePickerDialog.show();
            }
        });



        dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"-"+month+"-"+year;
                        dates.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        final DatabaseSQL database = new DatabaseSQL(this,"LichThi.sqlite",null,1);
        database.queryData("CREATE TABLE IF NOT EXISTS " +
                "Thi(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name varchar(200) , Times varchar(200) , Dates date , ischecked float) ");
        //  database.queryData("INSERT INTO Thi VALUES(null,'Le Thi Thuy Dung','13:30','5-28-2021',1)");
        final List<Lich> array = database.search("");
        int sum =0;
        for(Lich c: array){
            arrayList.add(c);
            if(c.getIschecked().equals("1")){
                sum++;
            }
        }
        tongsomon.setText(sum+"");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lich c= arrayList.get(position);
                name.setText(c.getName());
                dates.setText(c.getDates());
                idd = Integer.parseInt(c.getId());
                Toast.makeText(MainActivity.this, c.getId(),Toast.LENGTH_LONG).show();
                pos = position;
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = name.getText().toString();
                String amounts = time.getText().toString();
                String ischecked = 0+"";
                if(checkBox.isChecked()){
                    ischecked = 1+"";
                }
                String date = dates.getText().toString();
                Lich c = new Lich(String.valueOf(idd),ten,date,amounts,ischecked);
                int result = database.update(c);
                if(result >0){
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                    arrayList.set(pos,c);

                }


                listviewAdapter.notifyDataSetChanged();
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar2 = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String curentDate = simpleDateFormat.format(calendar2.getTime());
                //Toast.makeText(MainActivity.this,curentDate, Toast.LENGTH_LONG).show();
                if(curentDate.compareTo(dates.getText().toString()) > 0) {


                    int result = database.delete(idd + "");
                    if (result > 0) {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                        arrayList.remove(pos);

                    }
                    listviewAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(MainActivity.this, "Date Thi < Date current", Toast.LENGTH_LONG).show();
                }



            }
        });
        listviewAdapter = new ListviewAdapter(arrayList);
        listView.setAdapter(listviewAdapter);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = name.getText().toString();
                String amounts = time.getText().toString();
                String ischecked = 0 +"";
                if(checkBox.isChecked()){
                    ischecked = 1+"";
                }
                String date = dates.getText().toString();
                Lich c = new Lich("",ten,amounts,date,ischecked);
                Long result = database.add(c);
                if(result > 0){
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                    if(arrayList != null)
                    {arrayList.clear();}
                    List<Lich> array = database.search("");
                    for(Lich cs: array){
                        arrayList.add(cs);
                    }
                }


                listviewAdapter.notifyDataSetChanged();
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = name.getText().toString();
                if(arrayList != null)
                {arrayList.clear();}
                List<Lich> array = database.search(ten);
                for(Lich c: array){
                    arrayList.add(c);
                }
                listviewAdapter.notifyDataSetChanged();
            }
        });
    }

}