package com.example.spendingmanagement.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.spendingmanagement.model.Category;
import com.example.spendingmanagement.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "spending_management";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CATEGORY = "`category`";
    private static final String CATEGORY_ID = "id";
    private static final String CATEGORY_NAME = "name";
    private static final String CATEGORY_TYPE = "type";
    private static final String CATEGORY_COLOR = "color";
    private static final String CATEGORY_COLOR_CODE = "color_code";
    private static final String CATEGORY_ICON = "icon";

    private static final String TABLE_TRANSACTION = "`transaction`";
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
                        CATEGORY_COLOR_CODE + " INTEGER, " +
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
        contentValues.put(CATEGORY_COLOR_CODE, category.getColorCode());
        contentValues.put(CATEGORY_ICON, category.getIcon());
        long result = db.insert(TABLE_CATEGORY, null, contentValues);

        return result != -1;
    }

    public boolean updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, category.getName());
        contentValues.put(CATEGORY_TYPE, category.getType());
        contentValues.put(CATEGORY_COLOR, category.getColor());
        contentValues.put(CATEGORY_COLOR_CODE, category.getColorCode());
        contentValues.put(CATEGORY_ICON, category.getIcon());
        long result = db.update(TABLE_CATEGORY, contentValues, CATEGORY_ID + " = ?", new String[]{String.valueOf(category.getId())});
        return result != -1;
    }

    public ArrayList<Category> getCategoryByType(String type) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE " + CATEGORY_TYPE + " = ?", new String[]{type});

        ArrayList<Category> listCategory = new ArrayList<>();
        if (cursor.getCount() == 0) return listCategory;

        while (cursor.moveToNext()) {
            listCategory.add(new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
        }
        return listCategory;
    }

    public ArrayList<Category> getCategoryWithAmountByType(String type, String startDate, String endDate, int accountId) {
        ArrayList<Category> listCategory = getCategoryByType(type);
        for (Category category : listCategory) {
            category.setAmount(getCategoryAmount(category.getId(), type, startDate, endDate, accountId));
        }
        return listCategory;
    }

    public int getAccountAmount(Boolean isAllAccount, int accountId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT SUM(" + TRANSACTION_AMOUNT + ") amount FROM " + TABLE_TRANSACTION;
        String sqlExpense, sqlIncome;
        int expensesAmount, incomeAmount;
        sqlExpense = sql + " WHERE " + TRANSACTION_TO_TYPE + " = ?";
        sqlIncome = sql + " WHERE " + TRANSACTION_FROM_TYPE + " = ?";

        if (!isAllAccount) {
            sqlExpense += " AND " + TRANSACTION_FROM_ID + " = " + accountId;
            sqlIncome += " AND " + TRANSACTION_TO_ID + " = " + accountId;
        }

        Cursor cursor1 = db.rawQuery(sqlExpense, new String[]{"EXPENSES"});
        if (cursor1.getCount() == 0) expensesAmount = 0;
        else {
            cursor1.moveToNext();
            expensesAmount = cursor1.getInt(0);
        }
        Cursor cursor2 = db.rawQuery(sqlIncome, new String[]{"INCOME"});
        if (cursor2.getCount() == 0) incomeAmount = 0;
        else {
            cursor2.moveToNext();
            incomeAmount = cursor2.getInt(0);
        }

        return incomeAmount - expensesAmount;
    }

    public ArrayList<Category> getListAccountWithAmount(){
        ArrayList<Category> listAccount = getCategoryByType("ACCOUNT");
        for(Category account: listAccount){
            account.setAmount(getAccountAmount(false, account.getId()));
        }
        return listAccount;
    }

    public Category getCategoryById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE " + CATEGORY_ID + " = ?", new String[]{String.valueOf(id)});

        if (cursor.getCount() == 0) return null;

        Category category;
        cursor.moveToNext();
        category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));

        return category;
    }

    public Category getCategoryByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE " + CATEGORY_NAME + " = ?", new String[]{name});

        if (cursor.getCount() == 0) return null;

        Category category;
        cursor.moveToNext();
        category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));

        return category;
    }

    public void addTransaction(Transaction transaction) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSACTION_FROM_ID, transaction.getFromCategory().getId());
        contentValues.put(TRANSACTION_TO_ID, transaction.getToCategory().getId());
        contentValues.put(TRANSACTION_FROM_TYPE, transaction.getFromType());
        contentValues.put(TRANSACTION_TO_TYPE, transaction.getToType());
        contentValues.put(TRANSACTION_DATE, transaction.getDate());
        contentValues.put(TRANSACTION_AMOUNT, transaction.getAmount());

        db.insert(TABLE_TRANSACTION, null, contentValues);

    }

    public ArrayList<Transaction> getTransaction(String startDate, String endDate, int accountId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_TRANSACTION
                + " WHERE " + TRANSACTION_DATE + " >= ? AND "
                + TRANSACTION_DATE + " <= ? ";

        if(accountId != -1) {
            sql += " AND ( " + TRANSACTION_FROM_ID + " = " + accountId +
                    " OR " + TRANSACTION_TO_ID + " = " + accountId + " ) ";
        }

        Cursor cursor = db.rawQuery(sql, new String[]{startDate, endDate});

        ArrayList<Transaction> listTransaction = new ArrayList<>();
        if (cursor.getCount() == 0) return listTransaction;

        while (cursor.moveToNext()) {
            Transaction transaction = new Transaction();
            transaction.setId(cursor.getInt(0));
            transaction.setFromCategory(getCategoryById(cursor.getInt(1)));
            transaction.setToCategory(getCategoryById(cursor.getInt(2)));
            transaction.setFromType(cursor.getString(3));
            transaction.setToType(cursor.getString(4));
            transaction.setDate(cursor.getString(5));
            transaction.setAmount(cursor.getInt(6));

            listTransaction.add(transaction);
        }
        return listTransaction;
    }

    public void deleteTransaction(int transactionId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TRANSACTION, TRANSACTION_ID + " = " + transactionId, null);
    }
    public void deleteCategory(int categoryId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CATEGORY, CATEGORY_ID + " = " + categoryId, null);
    }
    public int getCategoryAmountOfType(String categoryType, String startDate, String endDate, int accountId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT SUM(" + TRANSACTION_AMOUNT + ") amount FROM " + TABLE_TRANSACTION;

        if (categoryType.equals("EXPENSES")) {
            sql += " WHERE " + TRANSACTION_TO_TYPE + " = ? ";
            if(accountId != -1) sql += " AND " + TRANSACTION_FROM_ID + " = " + accountId;
        }

        if (categoryType.equals("INCOME")) {
            sql += " WHERE " + TRANSACTION_FROM_TYPE + " = ? ";
            if(accountId != -1) sql += " AND " + TRANSACTION_TO_ID + " = " + accountId;
        }

        sql += " AND " + TRANSACTION_DATE + " >= ? AND "
                + TRANSACTION_DATE + " <= ?";

        Cursor cursor = db.rawQuery(sql, new String[]{categoryType, startDate, endDate});

        if (cursor.getCount() == 0) return 0;
        cursor.moveToNext();
        return cursor.getInt(0);
    }

    protected int getCategoryAmount(int categoryId, String categoryType, String startDate, String endDate, int accountId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT SUM(" + TRANSACTION_AMOUNT + ") amount FROM " + TABLE_TRANSACTION;

        if (categoryType.equals("EXPENSES")) {
            sql += " WHERE " + TRANSACTION_TO_ID + " = " + categoryId;
            if(accountId != -1) sql += " AND " + TRANSACTION_FROM_ID + " = " + accountId;
        }

        if (categoryType.equals("INCOME")) {
            sql += " WHERE " + TRANSACTION_FROM_ID + " = " + categoryId;
            if(accountId != -1) sql += " AND " + TRANSACTION_TO_ID + " = " + accountId;
        }

        sql += " AND " + TRANSACTION_DATE + " >= ? AND "
                + TRANSACTION_DATE + " <= ?";

        Cursor cursor = db.rawQuery(sql, new String[]{startDate, endDate});

        if (cursor.getCount() == 0) return 0;
        cursor.moveToNext();
        return cursor.getInt(0);
    }
}
