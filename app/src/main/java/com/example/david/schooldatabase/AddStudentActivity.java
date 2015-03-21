package com.example.david.schooldatabase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.content.ContentValues;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.david.schooldatabase.data.SchoolDBProvider;


public class AddStudentActivity extends ActionBarActivity{
    private String[] levels;
    private Spinner levelSpinner;
    private String[] programmes;
    private Spinner programmeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        levels = getResources().getStringArray(R.array.level_values);
        levelSpinner = (Spinner) findViewById(R.id.txtLevel);
        programmes = getResources().getStringArray(R.array.programme_options);
        programmeSpinner = (Spinner) findViewById(R.id.txtProgramme);

        ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, levels);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(levelAdapter);

        ArrayAdapter<String> programmeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, programmes);
        programmeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programmeSpinner.setAdapter(programmeAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_student, menu);
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

    public void onClickAddName(View view) {
// Add a new student record
        ContentValues values = new ContentValues();
        values.put(SchoolDBProvider.INDEX_NO,
                ((EditText)findViewById(R.id.txtIndexNo)).getText().toString());
        values.put(SchoolDBProvider.FIRST_NAME,
                ((EditText)findViewById(R.id.txtFirstName)).getText().toString());
        values.put(SchoolDBProvider.LAST_NAME,
                ((EditText)findViewById(R.id.txtLastName)).getText().toString());
        values.put(SchoolDBProvider.DEPARTMENT,
                programmeSpinner.getSelectedItem().toString());
        values.put(SchoolDBProvider.LEVEL,
                levelSpinner.getSelectedItem().toString());
        Uri uri = getContentResolver().insert(SchoolDBProvider.CONTENT_URI_1, values);
        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }
    public void onClickRetrieveStudents(View view) {
        startActivity(new Intent(this, StudentsActivity.class));
    }


}