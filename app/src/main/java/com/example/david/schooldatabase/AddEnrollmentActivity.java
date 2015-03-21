package com.example.david.schooldatabase;

import android.content.ContentValues;
import android.content.Intent;
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


public class AddEnrollmentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_enrollment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_enrollment, menu);
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

    public void onClickAddEnrollment(View view) {
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
        Uri uri = getContentResolver().insert(SchoolDBProvider.CONTENT_URI_3, values);
        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }
    public void onClickRetrieveEnrollments(View view) {
        startActivity(new Intent(this, EnrollmentsActivity.class));
    }
}
