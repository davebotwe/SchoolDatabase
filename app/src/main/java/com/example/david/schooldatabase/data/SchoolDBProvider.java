package com.example.david.schooldatabase.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by david on 3/6/15.
 */

public class SchoolDBProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.david.schooldatabase";
    static final String URL_1 = "content://" + PROVIDER_NAME + "/students";
    public static final Uri CONTENT_URI_1 = Uri.parse(URL_1);
    static final String URL_2 = "content://" + PROVIDER_NAME + "/courses";
    public static final Uri CONTENT_URI_2 = Uri.parse(URL_2);
    static final String URL_3 = "content://" + PROVIDER_NAME + "/enrollments";
    public static final Uri CONTENT_URI_3 = Uri.parse(URL_3);
    static final String URL_4 = "content://" + PROVIDER_NAME + "/assessments";
    public static final Uri CONTENT_URI_4 = Uri.parse(URL_4);
    public static final String _ID = "_id";
    public static final String INDEX_NO = "index_no";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String DEPARTMENT = "department";
    public static final String LEVEL = "level";
    public static final String COURSE_CODE = "course_code";
    public static final String COURSE_TITLE = "course_title";
    public static final String CREDITS = "credits";
    public static final String ACADEMIC_YEAR = "academic_year";
    public static final String SEMESTER = "semester";
    public static final String ATTENDANCE = "attendance";
    public static final String MID_SEMESTER = "mid_semester";
    public static final String FINAL_EXAM = "final_exam";

    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;
    private static HashMap<String, String> COURSES_PROJECTION_MAP;
    private static HashMap<String, String> ENROLLMENTS_PROJECTION_MAP;
    private static HashMap<String, String> ASSESSMENTS_PROJECTION_MAP;

    static final int STUDENTS = 1;
    static final int STUDENT_ID = 2;
    static final int COURSES = 3;
    static final int COURSE_ID = 4;
    static final int ENROLLMENTS = 5;
    static final int ENROLLMENT_ID = 6;
    static final int ASSESSMENTS = 7;
    static final int ASSESSMENT_ID = 8;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);
        uriMatcher.addURI(PROVIDER_NAME, "courses", COURSES);
        uriMatcher.addURI(PROVIDER_NAME, "courses/#", COURSE_ID);
        uriMatcher.addURI(PROVIDER_NAME, "enrollments", ENROLLMENTS);
        uriMatcher.addURI(PROVIDER_NAME, "enrollments/#", ENROLLMENT_ID);
        uriMatcher.addURI(PROVIDER_NAME, "assessments", ASSESSMENTS);
        uriMatcher.addURI(PROVIDER_NAME, "assessments/#", ASSESSMENT_ID);
    }

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
/**
 * Create a write able database which will trigger its
 * creation if it doesn't already exist.
 */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri _uri = null;
        switch (uriMatcher.match(uri)){

            case STUDENTS:
                /**
                 * Add a new student record
                 */
                long rowID_1 = db.insert(
                        DatabaseHelper.STUDENTS_TABLE_NAME, "", values);
/**
 * If record is added successfully
 */
                if (rowID_1 > 0)
                {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_1, rowID_1);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("Failed to add a record into " + uri);
            case COURSES:
                /**
                 * Add a new course record
                 */
                long rowID_2 = db.insert(
                        DatabaseHelper.COURSES_TABLE_NAME, "", values);
/**
 * If record is added successfully
 */
                if (rowID_2 > 0)
                {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_2, rowID_2);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("Failed to add a record into " + uri);

            case ENROLLMENTS:
                /**
                 * Add a new enrollment record
                 */
                long rowID_3 = db.insert(
                        DatabaseHelper.ENROLLMENTS_TABLE_NAME, "", values);
/**
 * If record is added successfully
 */
                if (rowID_3 > 0)
                {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_3, rowID_3);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("Failed to add a record into " + uri);
            case ASSESSMENTS:
                /**
                 * Add a new assessment record
                 */
                long rowID_4 = db.insert(
                        DatabaseHelper.ASSESSMENTS_TABLE_NAME, "", values);
/**
 * If record is added successfully
 */
                if (rowID_4 > 0)
                {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_4, rowID_4);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("Failed to add a record into " + uri);
            default:
                return _uri;
        }


    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        Cursor c = null;
        switch (uriMatcher.match(uri)){
            case STUDENTS:
                qb.setTables(DatabaseHelper.STUDENTS_TABLE_NAME);
                switch (uriMatcher.match(uri)) {
                    case STUDENTS:
                        qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                        break;
                    case STUDENT_ID:
                        qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown URI " + uri);
                }
                if (sortOrder == null || sortOrder == ""){
/**
 * By default sort on last name
 */
                    sortOrder = LAST_NAME;
                }
                c = qb.query(db,
                        projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
/**
 * register to watch a content URI for changes
 */
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            case COURSES:

                qb.setTables(DatabaseHelper.COURSES_TABLE_NAME);
                switch (uriMatcher.match(uri)) {
                    case COURSES:
                        qb.setProjectionMap(COURSES_PROJECTION_MAP);
                        break;
                    case COURSE_ID:
                        qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown URI " + uri);
                }
                if (sortOrder == null || sortOrder == ""){
/**
 * By default sort on course title
 */
                    sortOrder = COURSE_TITLE;
                }
                c = qb.query(db,
                        projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
/**
 * register to watch a content URI for changes
 */
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            case ENROLLMENTS:

                qb.setTables(DatabaseHelper.ENROLLMENTS_TABLE_NAME);
                switch (uriMatcher.match(uri)) {
                    case ENROLLMENTS:
                        qb.setProjectionMap(ENROLLMENTS_PROJECTION_MAP);
                        break;
                    case ENROLLMENT_ID:
                        qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown URI " + uri);
                }
                if (sortOrder == null || sortOrder == ""){
/**
 * By default sort on index number
 */
                    sortOrder = INDEX_NO;
                }
                c = qb.query(db,
                        projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
/**
 * register to watch a content URI for changes
 */
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            case ASSESSMENTS:

                qb.setTables(DatabaseHelper.ASSESSMENTS_TABLE_NAME);
                switch (uriMatcher.match(uri)) {
                    case ASSESSMENTS:
                        qb.setProjectionMap(ASSESSMENTS_PROJECTION_MAP);
                        break;
                    case ASSESSMENT_ID:
                        qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown URI " + uri);
                }
                if (sortOrder == null || sortOrder == ""){
/**
 * By default sort on index number
 */
                    sortOrder = INDEX_NO;
                }
                c = qb.query(db,
                        projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
/**
 * register to watch a content URI for changes
 */
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            default:
                return c;
        }

    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case STUDENTS:
                count = db.delete(DatabaseHelper.STUDENTS_TABLE_NAME, selection, selectionArgs);
                break;
            case STUDENT_ID:
                String id1 = uri.getPathSegments().get(1);
                count = db.delete(DatabaseHelper.STUDENTS_TABLE_NAME, _ID + " = " + id1 +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case COURSES:
                count = db.delete(DatabaseHelper.COURSES_TABLE_NAME, selection, selectionArgs);
                break;
            case COURSE_ID:
                String id2 = uri.getPathSegments().get(1);
                count = db.delete(DatabaseHelper.COURSES_TABLE_NAME, _ID + " = " + id2 +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case ENROLLMENTS:
                count = db.delete(DatabaseHelper.ENROLLMENTS_TABLE_NAME, selection, selectionArgs);
                break;
            case ENROLLMENT_ID:
                String id3 = uri.getPathSegments().get(1);
                count = db.delete(DatabaseHelper.ENROLLMENTS_TABLE_NAME, _ID + " = " + id3 +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case ASSESSMENTS:
                count = db.delete(DatabaseHelper.ASSESSMENTS_TABLE_NAME, selection, selectionArgs);
                break;
            case ASSESSMENT_ID:
                String id4 = uri.getPathSegments().get(1);
                count = db.delete(DatabaseHelper.ASSESSMENTS_TABLE_NAME, _ID + " = " + id4 +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case STUDENTS:
                count = db.update(DatabaseHelper.STUDENTS_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case STUDENT_ID:
                count = db.update(DatabaseHelper.STUDENTS_TABLE_NAME, values, _ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case COURSES:
                count = db.update(DatabaseHelper.COURSES_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case COURSE_ID:
                count = db.update(DatabaseHelper.COURSES_TABLE_NAME, values, _ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case ENROLLMENTS:
                count = db.update(DatabaseHelper.ENROLLMENTS_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case ENROLLMENT_ID:
                count = db.update(DatabaseHelper.ENROLLMENTS_TABLE_NAME, values, _ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            case ASSESSMENTS:
                count = db.update(DatabaseHelper.ASSESSMENTS_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case ASSESSMENT_ID:
                count = db.update(DatabaseHelper.ASSESSMENTS_TABLE_NAME, values, _ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
/**
 * Get all student records
 */
            case STUDENTS:
                return "vnd.android.cursor.dir/vnd.example.students";
/**
 * Get a particular student
 */
            case STUDENT_ID:
                return "vnd.android.cursor.item/vnd.example.students";
/**
 * Get all course records
 */
            case COURSES:
                return "vnd.android.cursor.dir/vnd.example.courses";
/**
 * Get a particular course
 */
            case COURSE_ID:
                return "vnd.android.cursor.item/vnd.example.courses";
/**
 * Get all enrollment records
 */
            case ENROLLMENTS:
                return "vnd.android.cursor.dir/vnd.example.enrollments";
/**
 * Get a particular enrollment
 */
            case ENROLLMENT_ID:
                return "vnd.android.cursor.item/vnd.example.enrollments";
/**
 * Get all assessment records
 */
            case ASSESSMENTS:
                return "vnd.android.cursor.dir/vnd.example.assessments";
/**
 * Get a particular assessment
 */
            case ASSESSMENT_ID:
                return "vnd.android.cursor.item/vnd.example.assessments";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

}