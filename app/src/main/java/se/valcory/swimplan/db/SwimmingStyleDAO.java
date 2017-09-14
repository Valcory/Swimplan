package se.valcory.swimplan.db;

import java.util.ArrayList;
import java.util.List;

import se.valcory.swimplan.to.SwimmingStyle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class SwimmingStyleDAO extends ExerciseDBDAO{

        private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
                + " =?";

        public SwimmingStyleDAO(Context context) {
            super(context);
        }

        public long save(SwimmingStyle swimmingStyle) {
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.NAME_COLUMN, swimmingStyle.getName());

            return database.insert(DataBaseHelper.SWIMMINGSTYLE_TABLE, null, values);
        }

        public long update(SwimmingStyle swimmingStyle) {
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.NAME_COLUMN, swimmingStyle.getName());

            long result = database.update(DataBaseHelper.SWIMMINGSTYLE_TABLE, values,
                    WHERE_ID_EQUALS,
                    new String[] { String.valueOf(swimmingStyle.getId()) });
            Log.d("Update Result:", "=" + result);
            return result;

        }

        public int deleteSwst(SwimmingStyle swimmingStyle) {
            return database.delete(DataBaseHelper.SWIMMINGSTYLE_TABLE,
                    WHERE_ID_EQUALS, new String[] { swimmingStyle.getId() + "" });
        }

        public List<SwimmingStyle> getSwimmingStyles() {
            List<SwimmingStyle> swimmingStyles = new ArrayList<SwimmingStyle>();
            Cursor cursor = database.query(DataBaseHelper.SWIMMINGSTYLE_TABLE,
                    new String[] { DataBaseHelper.ID_COLUMN,
                            DataBaseHelper.NAME_COLUMN }, null, null, null, null,
                    null);

            while (cursor.moveToNext()) {
                SwimmingStyle swimmingStyle = new SwimmingStyle();
                swimmingStyle.setId(cursor.getInt(0));
                swimmingStyle.setName(cursor.getString(1));
                swimmingStyles.add(swimmingStyle);
            }
            return swimmingStyles;
        }

        public void loadSwimmingStyles() {
            SwimmingStyle swimmingStyle = new SwimmingStyle("Frisim");
            SwimmingStyle swimmingStyle1 = new SwimmingStyle("Bröstsim");
            SwimmingStyle swimmingStyle2 = new SwimmingStyle("Ryggsim");
            SwimmingStyle swimmingStyle3 = new SwimmingStyle("Fjäril");
            SwimmingStyle swimmingStyle4 = new SwimmingStyle("Medley");
            SwimmingStyle swimmingStyle5 = new SwimmingStyle("Spec");
            SwimmingStyle swimmingStyle6 = new SwimmingStyle("Insim");

            List<SwimmingStyle> swimmingStyles = new ArrayList<SwimmingStyle>();
            swimmingStyles.add(swimmingStyle);
            swimmingStyles.add(swimmingStyle1);
            swimmingStyles.add(swimmingStyle2);
            swimmingStyles.add(swimmingStyle3);
            swimmingStyles.add(swimmingStyle4);
            swimmingStyles.add(swimmingStyle5);
            swimmingStyles.add(swimmingStyle6);
            for (SwimmingStyle swst : swimmingStyles) {
                ContentValues values = new ContentValues();
                values.put(DataBaseHelper.NAME_COLUMN, swst.getName());
                database.insert(DataBaseHelper.SWIMMINGSTYLE_TABLE, null, values);
            }
        }

}
