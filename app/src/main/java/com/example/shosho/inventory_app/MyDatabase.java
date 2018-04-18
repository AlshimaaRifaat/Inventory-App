package com.example.shosho.inventory_app;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class MyDatabase extends SQLiteOpenHelper {
    private Context context = null;

    public MyDatabase(Context context) {
        super(context, poductForContract.DB_NAME, null, poductForContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(poductForContract.Table.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL(poductForContract.Table.DELETE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public void add_product(MyProduct product) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(poductForContract.Table.COLUMN_NAME, product.getName());
        values.put(poductForContract.Table.COLUMN_QUANTITY, product.getQuantity());
        values.put(poductForContract.Table.COLUMN_PRICE, product.getPrice());
        values.put(poductForContract.Table.COLUMN_IMAGE, product.getImage());
        values.put(poductForContract.Table.COLUMN_SUPPLIER_MAIL, product.getSupplierMail());

        sqLiteDatabase.insert(poductForContract.Table.TABLE_PRODUCTS, null, values);
        sqLiteDatabase.close();
    }

    private Cursor read() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {poductForContract.Table.COLUMN_ID, poductForContract.Table.COLUMN_NAME, poductForContract.Table.COLUMN_QUANTITY,
                poductForContract.Table.COLUMN_PRICE, poductForContract.Table.COLUMN_IMAGE, poductForContract.Table.COLUMN_SUPPLIER_MAIL};

        Cursor cursor = sqLiteDatabase.query(poductForContract.Table.TABLE_PRODUCTS, columns, null, null, null, null, null);

        return cursor;
    }

    public ArrayList<MyProduct> readAllProducts() {
        ArrayList<MyProduct> productsList = new ArrayList<MyProduct>();

        Cursor cursor = read();
        if (cursor.moveToFirst()) {
            do {
                MyProduct product = new MyProduct();
                product.setMy_id(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setQuantity(cursor.getInt(2));
                product.setPrice(cursor.getInt(3));
                product.setImage(cursor.getString(4));
                product.setSupplierMail(cursor.getString(5));
                productsList.add(product);
            } while (cursor.moveToNext());
        }

        return productsList;
    }

    public void modify_product(int id, int quantity) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(poductForContract.Table.COLUMN_QUANTITY, quantity);
        sqLiteDatabase.update(poductForContract.Table.TABLE_PRODUCTS, contentValues, poductForContract.Table.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void deleteProduct(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(poductForContract.Table.TABLE_PRODUCTS, poductForContract.Table.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

    }

}
