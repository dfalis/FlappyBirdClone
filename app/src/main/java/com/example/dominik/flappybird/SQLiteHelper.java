package com.example.dominik.flappybird;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FlappyBird";
    private static final String TABLE_NAME = "stats";
    private static final String KEY_ID = "id";
    private static final String KEY_SCORE = "score";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE "+TABLE_NAME+" ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "score INTEGER )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        this.onCreate(db);
    }

    public ArrayList<DBScore> getAllScores() {
        ArrayList<DBScore> scores = new ArrayList<>();
        String query = "SELECT id, score FROM " + TABLE_NAME +" WHERE score > 0 ORDER BY score DESC LIMIT 10";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                scores.add(new DBScore(cursor.getInt(1)));
            } while (cursor.moveToNext());
        }

        return scores;
    }

    public void addScore(DBScore score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, score.getScore());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }
}
