package com.example.david.schooldatabase;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.david.schooldatabase.data.SchoolDBProvider;

/**
 * Created by david on 3/14/15.
 */
public class EnrollmentFragment extends Fragment implements LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = EnrollmentFragment.class.getSimpleName();
    private static final String ENROLLMENT_SHARE_HASHTAG = " #SchoolDatabaseApp";
    private String mEnrollmentStr;
    public static final int ENROLLMENT_LOADER =0;
    Uri uri = SchoolDBProvider.CONTENT_URI_3;
    String[] enrollmentData = {SchoolDBProvider._ID, SchoolDBProvider.INDEX_NO, SchoolDBProvider.ACADEMIC_YEAR, SchoolDBProvider.SEMESTER, SchoolDBProvider.COURSE_CODE};
    SimpleCursorAdapter mAdapter;
    LoaderManager loadermanager;
    ListView mListView;

    public EnrollmentFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enrollments, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mEnrollmentStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            //((TextView) rootView.findViewById(R.id.txtIndexNo)).setText(mEnrollmentStr);
        }

        loadermanager=getLoaderManager();
        mListView = (ListView) rootView.findViewById(R.id.enrollmentListView);

        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_enrollment_records,
                null,
                new String[] {SchoolDBProvider._ID,SchoolDBProvider.INDEX_NO, SchoolDBProvider.ACADEMIC_YEAR, SchoolDBProvider.SEMESTER, SchoolDBProvider.COURSE_CODE},
                new int[] { R.id.txtId, R.id.txtIndexNo , R.id.txtAcYear, R.id.txtSemester, R.id.txtCourseCode}, 0);

        mListView.setAdapter(mAdapter);

        loadermanager.initLoader(1, null, this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ENROLLMENT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    /** A callback method invoked by the loader when initLoader() is called */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        return new CursorLoader(getActivity(), uri, enrollmentData, null, null, null);
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