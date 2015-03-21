package com.example.david.schooldatabase;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.david.schooldatabase.data.SchoolDBProvider;

/**
 * Created by david on 3/14/15.
 */
public class StudentsFragment extends Fragment implements LoaderCallbacks<Cursor> {
    private String mStudentStr;
    public static final int STUDENT_LOADER =0;
    Uri uri = SchoolDBProvider.CONTENT_URI_1;
    String[] studentData = {SchoolDBProvider._ID, SchoolDBProvider.INDEX_NO, SchoolDBProvider.FIRST_NAME, SchoolDBProvider.LAST_NAME, SchoolDBProvider.DEPARTMENT, SchoolDBProvider.LEVEL};
    SimpleCursorAdapter mAdapter;
    LoaderManager loadermanager;
    ListView mListView;

    public StudentsFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_students, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mStudentStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            //((TextView) rootView.findViewById(R.id.txtIndexNo)).setText(mStudentStr);
        }

        loadermanager=getLoaderManager();
        mListView = (ListView) rootView.findViewById(R.id.studentListView);

        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_student_records,
                null,
                new String[] {SchoolDBProvider._ID,SchoolDBProvider.INDEX_NO, SchoolDBProvider.FIRST_NAME, SchoolDBProvider.LAST_NAME, SchoolDBProvider.DEPARTMENT, SchoolDBProvider.LEVEL},
                new int[] { R.id.txtId, R.id.txtIndexNo , R.id.txtFirstName, R.id.txtLastName, R.id.txtProgramme, R.id.txtLevel}, 0);

        mListView.setAdapter(mAdapter);

        loadermanager.initLoader(1, null, this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(STUDENT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    /** A callback method invoked by the loader when initLoader() is called */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        return new CursorLoader(getActivity(), uri, studentData, null, null, null);
    }

    /** A callback method, invoked after the requested content provider returned all the data */
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        mAdapter.swapCursor(null);
    }


}