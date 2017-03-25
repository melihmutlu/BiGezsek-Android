package com.hackathon.getir.bigezsek;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.app.DialogFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String placeID =data.getStringExtra("placeID");

                Bundle bundle = new Bundle();
                bundle.putString("placeID", placeID);
                DialogFragment dFragment = new DatePickerFragment();
                dFragment.setArguments(bundle);
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent i = new Intent(this, GooglePlacesActivity.class);
            startActivityForResult(i, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        static String placeID;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            placeID = getArguments().getString("placeID");
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            /*
                Initialize a new DatePickerDialog

                DatePickerDialog(Context context, DatePickerDialog.OnDateSetListener callBack,
                    int year, int monthOfYear, int dayOfMonth)
             */
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),this,year,month,day);
            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            Intent getCards = new Intent(getActivity(),CardUIActivity.class);
            getCards.putExtra("location",placeID);
            getCards.putExtra("date",""+day+"."+month+"."+year);
            startActivity(getCards);

        }
    }


}
