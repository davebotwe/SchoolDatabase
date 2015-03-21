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
public class AssessmentFragment extends Fragment implements LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = AssessmentFragment.class.getSimpleName();
    private String mAssessmentStr;
    public static final int ASSESSMENT_LOADER =0;
    Uri uri = SchoolDBProvider.CONTENT_URI_4;
    String[] assessmentData = {SchoolDBProvider._ID, SchoolDBProvider.INDEX_NO, SchoolDBProvider.ACADEMIC_YEAR, SchoolDBProvider.SEMESTER, SchoolDBProvider.COURSE_CODE, SchoolDBProvider.ATTENDANCE, SchoolDBProvider.MID_SEMESTER, SchoolDBProvider.FINAL_EXAM};
    SimpleCursorAdapter mAdapter;
    LoaderManager loadermanager;
    ListView mListView;

    public AssessmentFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assessments, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mAssessmentStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            //((TextView) rootView.findViewById(R.id.txtIndexNo)).setText(mAssessmentStr);
        }

        loadermanager=getLoaderManager();
        mListView = (ListView) rootView.findViewById(R.id.assessmentListView);

        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_assessment_records,
                null,
                new String[] {SchoolDBProvider._ID,SchoolDBProvider.INDEX_NO, SchoolDBProvider.ACADEMIC_YEAR, SchoolDBProvider.SEMESTER, SchoolDBProvider.COURSE_CODE, SchoolDBProvider.ATTENDANCE, SchoolDBProvider.MID_SEMESTER, SchoolDBProvider.FINAL_EXAM},
                new int[] { R.id.txtId, R.id.txtIndexNo , R.id.txtAcYear, R.id.txtSemester, R.id.txtCourseCode, R.id.txtAttendance, R.id.txtMidSemester, R.id.txtFinalExam}, 0);

        mListView.setAdapter(mAdapter);

        loadermanager.initLoader(1, null, this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ASSESSMENT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    /** A callback method invoked by the loader when initLoader() is called */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        return new CursorLoader(getActivity(), uri, assessmentData, null, null, null);
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