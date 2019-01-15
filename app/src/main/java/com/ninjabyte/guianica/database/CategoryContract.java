package com.ninjabyte.guianica.database;

import android.provider.BaseColumns;

public class CategoryContract {

    public static abstract class categoryEntry implements BaseColumns{
        public static final String TABLE_NAME = "category";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String COLOR = "color";
        public static final String TYPE = "type";
    }
}
