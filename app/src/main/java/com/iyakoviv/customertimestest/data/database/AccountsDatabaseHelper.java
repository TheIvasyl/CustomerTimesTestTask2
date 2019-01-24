package com.iyakoviv.customertimestest.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iyakoviv.customertimestest.domain.model.AccountModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AccountsDatabaseHelper extends SQLiteOpenHelper {

  private static AccountsDatabaseHelper mIntance;

  private static final String DATABASE_NAME = "accountsDatabase";
  private static final int DATABASE_VERSION = 1;
  private static final String TABLE_NAME = "Account";

  public static synchronized AccountsDatabaseHelper getInstance(Context context){

    if(mIntance == null){
      mIntance = new AccountsDatabaseHelper(context.getApplicationContext());
    }
    return mIntance;
  }


  public AccountsDatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {

  }

  public void createTable(JsonArray fields){
    String CREATE_ACCOUNT_TABLE = buildCreateTableQueryString(fields);
    SQLiteDatabase database = getWritableDatabase();

    try{
      database.execSQL(CREATE_ACCOUNT_TABLE);
    }
    catch (SQLiteException e){
      Log.d("DATABASE_HELPER", "no need to create a table, it already exists");
    }

  }

  public boolean isTableExists(){
    SQLiteDatabase database = getReadableDatabase();
    String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='Account'";
    Cursor mCursor = database.rawQuery(sql, null);
    if (mCursor.getCount() > 0) {
      return true;
    }
    mCursor.close();
    return false;
  }

  public void insertAll(JsonArray records){
    for (int i = 0; i < records.size(); i++){
      JsonObject record = records.get(i).getAsJsonObject();
      insert(record);
    }
  }

  private void insert(JsonObject record){

    ContentValues values = new ContentValues();
    Set<String> keys = record.keySet();

    for (String key: keys
            ) {
      String value = record.get(key).toString();
      // this if statement is a crutch to avoid "no such column" error
      if (!key.contains("__c") && !key.contains("attributes")) {
        values.put(key, value);
      }
    }

    SQLiteDatabase database = getWritableDatabase();
    database.beginTransaction();
    try {
      database.insertOrThrow(TABLE_NAME, null, values);
      database.setTransactionSuccessful();
    }
    catch (Exception e){

    }
    finally {
      database.endTransaction();
    }
  }

  public List<AccountModel> getPage(int pageNumber, int limit){
    SQLiteDatabase database = getReadableDatabase();

    int offset = limit * (pageNumber-1);

    Log.d("DB", "OFFSET IS " + offset);

    String sql = "SELECT * FROM Account LIMIT " + offset + ", " + limit;

    Log.d("DB", "INB4 QUERRY");
    Cursor mCursor = database.rawQuery(sql, null);

    List<AccountModel> page = new ArrayList<>();
    if (mCursor.moveToFirst()){
      int i = 0;
      while(!mCursor.isAfterLast()){
        page.add(cursorToModel(mCursor));
        Log.d("DB", "ADDED ONE ELEMENT " + page.get(i).getValue().get("Id"));
        mCursor.moveToNext();
        i++;
      }
    }
    mCursor.close();
    //Log.d("DB", "INB4 CURSOR ITERATION");
    //for (int i = 0; i < limit; i++){
    //  page.add(cursorToModel(mCursor));
    //  Log.d("DB", "ADDED ONE ELEMENT " + page.get(i).getValue().get("Id"));
    //  mCursor.moveToNext();
    //}
    //mCursor.close();
    Log.d("DB", "PAGE LOADED");
    return page;
  }

  private AccountModel cursorToModel(Cursor cursor){
    Map<String, String> map = new HashMap<>();

    String[] columnNames = cursor.getColumnNames();
    int columnsCount = cursor.getColumnCount();
    Log.d("DB", "GOT FIELDS " + columnNames.length);
    for (int i = 0; i<columnsCount; i++){
      String key = columnNames[i];
      String value = cursor.getString(cursor.getColumnIndex(key));
      map.put(key, value);
    }
    return new AccountModel(map);
  }

  public boolean isTableEmpty(){
    if (isTableExists()) {
      SQLiteDatabase database = getWritableDatabase();
      String count = "SELECT count(*) FROM Account";
      Cursor mcursor = database.rawQuery(count, null);
      mcursor.moveToFirst();
      int icount = mcursor.getInt(0);
      if (icount > 0)
        return false;
      else
        return true;
    }
    return true;
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }


  public String buildCreateTableQueryString(JsonArray fields){

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("CREATE TABLE ");
    stringBuilder.append(TABLE_NAME);
    stringBuilder.append("(");

    for (int i = 0; i < fields.size(); i++){
      String fieldName = fields.get(i).getAsJsonObject().get("name").getAsString();

      stringBuilder.append(fieldName);
      stringBuilder.append(" TEXT,");
    }
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    stringBuilder.append(")");

    return stringBuilder.toString();
  }



}
