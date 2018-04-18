package com.example.shosho.inventory_app;

/**
 * Created by shosho on 2/2/2018.
 */

import android.provider.BaseColumns;

public final class poductForContract {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "products_DB.db";

    private poductForContract () {}

    public static abstract class Table implements BaseColumns {
        public static final String TABLE_PRODUCTS = "products";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_SUPPLIER_MAIL = "supplier_mail";

        public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_PRODUCTS +"("+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME + " TEXT NOT NULL, "+
                COLUMN_QUANTITY + " INTEGER NOT NULL, "+
                COLUMN_PRICE + " INTEGER NOT NULL, "+
                COLUMN_IMAGE + " TEXT NOT NULL, "+
                COLUMN_SUPPLIER_MAIL + " TEXT NOT NULL)";

        public static final String DELETE_TABLE = "DROP TABLE "+ TABLE_PRODUCTS +" IF EXISTS";
    }

}
