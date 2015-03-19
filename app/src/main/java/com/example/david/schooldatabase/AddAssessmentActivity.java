package com.example.david.schooldatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.david.schooldatabase.data.SchoolDBProvider;


public class AddAssessmentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAddAssessment(View view) {
// Add a new enrollment record
        ContentValues values = new ContentValues();
        values.put(SchoolDBProvider.INDEX_NO,
                ((EditText)findViewById(R.id.txtIndexNo)).getText().toString());
        values.put(SchoolDBProvider.ACADEMIC_YEAR,
                ((EditText)findViewById(R.id.txtAcYear)).getText().toString());
        values.put(SchoolDBProvider.SEMESTER,
                ((EditText)findViewById(R.id.txtSemester)).getText().toString());
        values.put(SchoolDBProvider.COURSE_CODE,
                ((EditText)findViewById(R.id.txtCourseCode)).getText().toString());
        values.put(SchoolDBProvider.ATTENDANCE,
                ((EditText)findViewById(R.id.txtAttendance)).getText().toString());
        values.put(SchoolDBProvider.MID_SEMESTER,
                ((EditText)findViewById(R.id.txtMidSemester)).getText().toString());
        values.put(SchoolDBProvider.FINAL_EXAM,
                ((EditText)findViewById(R.id.txtCourseCode)).getText().toString());
        Uri uri = getContentResolver().insert(SchoolDBProvider.CONTENT_URI_4, values);
        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }
    public void onClickRetrieveAssessments(View view) {
// Retrieve enrollment records
        String URL = "content://com.example.david.schooldatabase/assessments";
        Uri enrollments = Uri.parse(URL);
        Cursor c = managedQuery(enrollments, null, null, null, "index_no");
        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(SchoolDBProvider._ID)) +
                                ", " + c.getString(c.getColumnIndex( SchoolDBProvider.INDEX_NO)) +
                                ", " + c.getString(c.getColumnIndex( SchoolDBProvider.ACADEMIC_YEAR)) +
                                ", " + c.getString(c.getColumnIndex( SchoolDBProvider.SEMESTER)) +
                                ", " + c.getString(c.getColumnIndex( SchoolDBProvider.COURSE_CODE)) +
                                ", " + c.getString(c.getColumnIndex( SchoolDBProvider.ATTENDANCE)) +
                                ", " + c.getString(c.getColumnIndex( SchoolDBProvider.MID_SEMESTER)) +
                                ", " + c.getString(c.getColumnIndex( SchoolDBProvider.FINAL_EXAM)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}
