package com.example.spendingmanagement.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.spendingmanagement.model.Category;
import com.example.spendingmanagement.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "spending_management";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CATEGORY = "category";
    private static final String CATEGORY_ID = "id";
    private static final String CATEGORY_NAME = "name";
    private static final String CATEGORY_TYPE = "type";
    private static final String CATEGORY_COLOR = "color";
    private static final String CATEGORY_ICON = "icon";

    private static final String TABLE_TRANSACTION = "transaction";
    private static final String TRANSACTION_ID = "id";
    private static final String TRANSACTION_FROM_ID = "from_category_id";
    private static final String TRANSACTION_TO_ID = "to_category_id";
    private static final String TRANSACTION_FROM_TYPE = "from_type";
    private static final String TRANSACTION_TO_TYPE = "to_type";
    private static final String TRANSACTION_DATE = "date";
    private static final String TRANSACTION_AMOUNT = "amount";


    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.valueOf(
                "CREATE TABLE " + TABLE_CATEGORY + " ( " +
                        CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CATEGORY_NAME + " TEXT, " +
                        CATEGORY_TYPE + " TEXT, " +
                        CATEGORY_COLOR + " INTEGER, " +
                        CATEGORY_ICON + " INTEGER )")
        );
        sqLiteDatabase.execSQL(String.valueOf(
                "CREATE TABLE " + TABLE_TRANSACTION + " ( " +
                        TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TRANSACTION_FROM_ID + " INTEGER," +
                        TRANSACTION_TO_ID + " INTEGER, " +
                        TRANSACTION_FROM_TYPE + " TEXT, " +
                        TRANSACTION_TO_TYPE + " TEXT, " +
                        TRANSACTION_DATE + " TEXT, " +
                        TRANSACTION_AMOUNT + " INTEGER, " +
                        "FOREIGN KEY (" + TRANSACTION_FROM_ID + " ) REFERENCES " + TABLE_CATEGORY + "( " + CATEGORY_ID + " ), " +
                        "FOREIGN KEY (" + TRANSACTION_TO_ID + " ) REFERENCES " + TABLE_CATEGORY + "( " + CATEGORY_ID + "))")
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_CATEGORY));
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_TRANSACTION));
        onCreate(sqLiteDatabase);
    }

    public boolean addCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, category.getName());
        contentValues.put(CATEGORY_TYPE, category.getType());
        contentValues.put(CATEGORY_COLOR, category.getColor());
        contentValues.put(CATEGORY_ICON, category.getIcon());
        long result = db.insert(TABLE_CATEGORY, null, contentValues);

        return result != -1;
    }

    public ArrayList<Category> getCategoryByType(String type) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE " + CATEGORY_TYPE + " = ?", new String[]{type});

        ArrayList<Category> listCategory = new ArrayList<>();
        if (cursor.getCount() == 0) return listCategory;

        while (cursor.moveToNext()) {
            listCategory.add(new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4)));
        }
        return listCategory;
    }

    public boolean addTransaction(Transaction transaction) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(TRANSACTION_FROM_ID,transaction.getFromCategory().getId());
//        contentValues.put(TRANSACTION_TO_ID, transaction.getToCategory().getId());

        LocalDate localDate = LocalDate.now();
        contentValues.put(TRANSACTION_DATE, localDate.toString());
        long result = db.insert(TABLE_TRANSACTION, null, contentValues);

        return result != -1;
    }
}
