package com.ninjabyte.guianica.database;

import android.provider.BaseColumns;

public class CategoryContract {

    public static abstract class categoryEntry implements BaseColumns{
        private static final String TABLE_NAME = "category";

        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String COLOR = "color";
        private static final String TYPE = "type";


        public static final String CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME +" (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                NAME + " TEXT NOT NULL," +
                COLOR + " TEXT NOT NULL," +
                TYPE + " TEXT NOT NULL);";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
