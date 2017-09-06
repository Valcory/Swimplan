package se.valcory.swimplan.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
//import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import se.valcory.swimplan.to.SwimmingStyle;
import se.valcory.swimplan.to.Exercise;

public class ExerciseDAO extends ExerciseDBDAO {

    public static final String EXERCISE_ID_WITH_PREFIX = "exer.id";
    public static final String EXERCISE_NAME_WITH_PREFIX = "exer.name";
    public static final String SWST_NAME_WITH_PREFIX = "swst.name";

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN + " =?";

    public ExerciseDAO(Context context) {
        super(context);
    }

    public long save(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_COLUMN, exercise.getName());
        values.put(DataBaseHelper.EXERCISE_DISTANCE, exercise.getDistance());
        values.put(DataBaseHelper.EXERCISE_REPETITION, exercise.getRepetition());
        values.put(DataBaseHelper.EXERCISE_SWIMMINGSTYLE_ID, exercise.getSwimmingStyle().getId());

        return database.insert(DataBaseHelper.EXERCISE_TABLE, null, values);
    }

    public long update(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_COLUMN, exercise.getName());
        values.put(DataBaseHelper.EXERCISE_DISTANCE, exercise.getDistance());
        values.put(DataBaseHelper.EXERCISE_REPETITION, exercise.getRepetition());
        values.put(DataBaseHelper.EXERCISE_SWIMMINGSTYLE_ID, exercise.getSwimmingStyle().getId());

        long result = database.update(DataBaseHelper.EXERCISE_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(exercise.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;
    }

    public int deleteExercise(Exercise exercise) {
        return database.delete(DataBaseHelper.EXERCISE_TABLE, WHERE_ID_EQUALS,
                new String[] { exercise.getId() + "" });
    }


    public ArrayList<Exercise> getExercises() {
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        String query = "SELECT " + EXERCISE_ID_WITH_PREFIX + ","
                + EXERCISE_NAME_WITH_PREFIX + ","
                + DataBaseHelper.EXERCISE_DISTANCE + ","
                + DataBaseHelper.EXERCISE_REPETITION + ","
                + DataBaseHelper.EXERCISE_SWIMMINGSTYLE_ID + ","
                + SWST_NAME_WITH_PREFIX + " FROM "
                + DataBaseHelper.EXERCISE_TABLE + " exer, "
                + DataBaseHelper.SWIMMINGSTYLE_TABLE + " swst WHERE exer."
                + DataBaseHelper.EXERCISE_SWIMMINGSTYLE_ID + " = swst."
                + DataBaseHelper.ID_COLUMN;


        Log.d("query", query);
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Exercise exercise = new Exercise();
            exercise.setId(cursor.getInt(0));
            exercise.setName(cursor.getString(1));
            exercise.setDistance(cursor.getDouble(2));
            exercise.setRepetition(cursor.getInt(3));

            SwimmingStyle swimmingStyle = new SwimmingStyle();
            swimmingStyle.setId(cursor.getInt(4));
            swimmingStyle.setName(cursor.getString(5));

            exercise.setSwimmingStyle(swimmingStyle);

            exercises.add(exercise);
        }
        return exercises;
    }
}