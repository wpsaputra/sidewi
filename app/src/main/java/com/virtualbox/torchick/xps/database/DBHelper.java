package com.virtualbox.torchick.xps.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Lenovo 17 on 4/21/2016.
 */
public class DBHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "dbsidewi.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}