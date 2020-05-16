package com.cmk.mailbox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddLetter extends AppCompatActivity {
    private EditText edtEvent, edtDate, edtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button btnDone;
    private AlarmManager am;
    private String eventEntered;
    private String timeEntered;
    private String dateEntered;
    private String formattedTime;
    private Long tsCurrent, tsSet;
    private Calendar c1, c2;
    private String ts;
    private String toParse;
    private SimpleDateFormat formatter;
    private Date date;
    private RelativeLayout rlSpeak;
    private String timeToNotify;
    public static final String EXTRA_REPLY = "com.example.android.roomwordssample.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_letter);

        edtEvent = (EditText)findViewById(R.id.event);
        edtTime = (EditText)findViewById(R.id.time);
        edtDate = (EditText)findViewById(R.id.date);
        //btnDone = (Button)findViewById(R.id.button);

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c1 = Calendar.getInstance();
                mHour = c1.get(Calendar.HOUR_OF_DAY);
                mMinute = c1.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddLetter.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                timeToNotify = hourOfDay+":"+minute;
                                formattedTime = FormatTime(hourOfDay, minute);
                                edtTime.setText(formattedTime);
                            }
                        }, mHour, mMinute, false);

                timePickerDialog.show();
            }

        });

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddLetter.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                edtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(edtEvent.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = edtEvent.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }

    public String FormatTime(int hour, int minute){

        String time;
        time = "";
        String formattedMinute;

        if(minute/10 == 0){
            formattedMinute = "0"+minute;
        }
        else{
            formattedMinute = ""+minute;
        }


        if(hour == 0){
            time = "12" + ":" + formattedMinute + " AM";
        }
        else if(hour < 12){
            time = hour + ":" + formattedMinute + " AM";
        }
        else if(hour == 12){
            time = "12" + ":" + formattedMinute + " PM";
        }
        else{
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }



        return time;
    }


}
