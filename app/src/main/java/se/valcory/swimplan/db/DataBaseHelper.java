package se.valcory.swimplan.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "exercisedb";
    private static final int DATABASE_VERSION = 1;

    public static final String EXERCISE_TABLE = "exercise";
    public static final String SWIMMINGSTYLE_TABLE = "swimmingstyle";

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String EXERCISE_DISTANCE = "distance";
    public static final String EXERCISE_REPETITION = "repetition";
    public static final String EXERCISE_SWIMMINGSTYLE_ID = "dept_id";

    public static final String CREATE_EXERCISE_TABLE = "CREATE TABLE "
            + EXERCISE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + EXERCISE_DISTANCE + " DOUBLE, "
            + EXERCISE_REPETITION + " INT, "
            + EXERCISE_SWIMMINGSTYLE_ID + " INT, "
            + "FOREIGN KEY(" + EXERCISE_SWIMMINGSTYLE_ID + ") REFERENCES "
            + SWIMMINGSTYLE_TABLE + "(id) " + ")";

    public static final String CREATE_SWIMMINGSTYLE_TABLE = "CREATE TABLE "
            + SWIMMINGSTYLE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY,"
            + NAME_COLUMN + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SWIMMINGSTYLE_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}