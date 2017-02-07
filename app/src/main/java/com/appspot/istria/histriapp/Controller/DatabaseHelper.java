package com.appspot.istria.histriapp.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appspot.istria.histriapp.Model.AdventureModel;
import com.appspot.istria.histriapp.Model.InstitutionModel;

import java.util.ArrayList;

/**
 * Created by bozidarkokot on 19/06/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * SQLite Databases, categories data and businesses data
     * */

    public static final String databaseName = "users";
    public static final int databaseVersion = 13;




    public enum databaseTable{
        QUESTIONS("questions"),

        RANKING("ranking"),
        INSTITUTIONS("institutions"),
        ADVENTURES("adventures"),
        TREASURES("treasures"),
        DISCOUNTS("discounts");


        public String table;
        databaseTable(String table) {
            this.table = table;
        }
    }


    public enum treasureColumn{
        ID("id"),
        LAT("lat"),
        LONG("long"),
        DESCRIPTION("description"),
        IMAGE("image");
        public  String column;
        treasureColumn(String column){
            this.column = column;
        }
    }

    public enum  adventureColumn{
        ID("id"),
        RIDDLE("riddle"),
        DESC("desc");
        public  String column;
        adventureColumn(String column){
            this.column = column;
        }
    }

    public enum questionColumn{
        ID("id"),
        QUESTION("question"),
        ANSWER1("answer1"),
        ANSWER2("answer2"),
        INSTITUTION_ID("inst_id"),
        CORRECT("correct"),
        ANSWER3("answer3");
        public String column;
        questionColumn(String column){
            this.column=column;
        }

    }

    public  enum  institutionColumn{
        ID("id"),
        INSTITUION_NAME("inst_name"),
        PASSED_STATE("passed_state"),
        LATITUDE("lat"),
        ADDRESS("address"),
        LONGITUDE("longitude"),
        INSTITUTION_IDENTIFIER("inst_identifier");
        public String column;
        institutionColumn(String column){
            this.column=column;
        }
    }

   public  enum discountColumn{
       ID("id"),
       DISCOUNT("discount"),
       ITEM("item"),
       ADDRESS("address"),
       LOGO("logo"),
       COMPANY("company");
       public String column;
       discountColumn(String column){
           this.column=column;
       }

   }

    public  enum rankingColumn{
        ID("id"),
        USERNAME("username"),
        SCORE("score"),
        TIME("time");
        public String column;
        rankingColumn(String column){
            this.column=column;
        }

    }





    /**
     * SQLite businesses table columns
     * */

    /**
     * SQLite business comments table columns
     * */


    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String createQuestionsTable = "CREATE TABLE "+databaseTable.QUESTIONS.table+
                "("+questionColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                questionColumn.QUESTION.column+" VARCHAR, "+
                questionColumn.ANSWER1.column+" VARCHAR, "+

                questionColumn.ANSWER2.column+" VARCHAR," +
                questionColumn.ANSWER3.column+" VARCHAR," +
                questionColumn.CORRECT.column+" VARCHAR," +

                questionColumn.INSTITUTION_ID.column+" INTEGER);";
        db.execSQL(createQuestionsTable);



        String createTreasureTable = "CREATE TABLE "+databaseTable.TREASURES.table+
                "("+treasureColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                treasureColumn.LAT.column+" VARCHAR, "+
                treasureColumn.LONG.column+" VARCHAR, "+

                treasureColumn.DESCRIPTION.column+" VARCHAR," +
                treasureColumn.IMAGE.column+" BLOB);";
        db.execSQL(createTreasureTable);

        String createAdventureTable = "CREATE TABLE "+databaseTable.ADVENTURES.table+
                "("+adventureColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                adventureColumn.RIDDLE.column+" VARCHAR, "+
                adventureColumn.DESC.column+" VARCHAR);";
        db.execSQL(createAdventureTable);



        String createInstitutionsTable = "CREATE TABLE "+databaseTable.INSTITUTIONS.table+
                "("+institutionColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                institutionColumn.INSTITUION_NAME.column+" VARCHAR, "+
                institutionColumn.INSTITUTION_IDENTIFIER.column+" VARCHAR, "+
                institutionColumn.ADDRESS.column+" VARCHAR, "+
                institutionColumn.PASSED_STATE.column+" INTEGER," +

                institutionColumn.LONGITUDE.column+" DOUBLE, "+
                institutionColumn.LATITUDE.column+" DOUBLE );";
        db.execSQL(createInstitutionsTable);


        String createDiscountTable = "CREATE TABLE "+databaseTable.DISCOUNTS.table+
                "("+discountColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                discountColumn.DISCOUNT.column+" VARCHAR, "+
                discountColumn.ADDRESS.column+" VARCHAR, "+
                discountColumn.LOGO.column+" BLOB, "+

                discountColumn.ITEM.column+" VARCHAR, "+
                discountColumn.COMPANY.column+" VARCHAR );";
        db.execSQL(createDiscountTable);

        String createRankingTable = "CREATE TABLE "+databaseTable.RANKING.table+
                "("+rankingColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                rankingColumn.SCORE.column+" VARCHAR, "+
                rankingColumn.TIME.column+" VARCHAR, "+
                rankingColumn.USERNAME.column+" VARCHAR );";
        db.execSQL(createRankingTable);


    }

    public boolean addQuestions(String question,String answer1,String answer2,String answer3,String correct,String inst_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(questionColumn.INSTITUTION_ID.column, inst_id);
        contentValues.put(questionColumn.QUESTION.column, question);
        contentValues.put(questionColumn.ANSWER3.column, answer3);
        contentValues.put(questionColumn.ANSWER2.column, answer2);
        contentValues.put(questionColumn.ANSWER1.column, answer1);
        contentValues.put(questionColumn.CORRECT.column, correct);

        db.replace(databaseTable.QUESTIONS.table, null, contentValues);
        db.close();
        return true;
    }

    public  boolean addTreasure(String lat,String longitude,String description,byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(treasureColumn.LAT.column, lat);
        contentValues.put(treasureColumn.LONG.column, longitude);
        contentValues.put(treasureColumn.DESCRIPTION.column, description);
        contentValues.put(treasureColumn.IMAGE.column, image);


        db.replace(databaseTable.TREASURES.table, null, contentValues);
        db.close();
        return true;
    }

    public  boolean addAdventure(String riddle,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(adventureColumn.RIDDLE.column, riddle);
        contentValues.put(adventureColumn.DESC.column, description);


        db.replace(databaseTable.ADVENTURES.table, null, contentValues);
        db.close();
        return true;
    }


    public boolean addDiscount(String discount,String item,String company,String address,byte[]  logo ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(discountColumn.DISCOUNT.column, discount);
        contentValues.put(discountColumn.ITEM.column, item);
        contentValues.put(discountColumn.COMPANY.column, company);
        contentValues.put(discountColumn.ADDRESS.column, address);

        contentValues.put(discountColumn.LOGO.column,logo);


        db.replace(databaseTable.DISCOUNTS.table, null, contentValues);
        db.close();
        return true;
    }
/*
    public boolean addRanking(String username,String score,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(rankingColumn.USERNAME.column, username);
        contentValues.put(rankingColumn.SCORE.column, score);
        contentValues.put(rankingColumn.TIME.column, time);


        db.replace(databaseTable.RANKING.table, null, contentValues);
        db.close();
        return true;
    }*/

    public boolean addInstitutions(String identifier,String name,double lat,double lont,String address,int passedState ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(institutionColumn.INSTITUION_NAME.column, name);
        contentValues.put(institutionColumn.INSTITUTION_IDENTIFIER.column,identifier);
        contentValues.put(institutionColumn.LATITUDE.column, lat);
        contentValues.put(institutionColumn.LONGITUDE.column, lont);
        contentValues.put(institutionColumn.ADDRESS.column, address);
        contentValues.put(institutionColumn.PASSED_STATE.column, passedState);




        db.replace(databaseTable.INSTITUTIONS.table, null, contentValues);
        db.close();
        return true;
    }

    /*
    public ArrayList<Companies> getAllDiscount(){
        ArrayList<Companies> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String readCategoriesSQL = "SELECT * FROM "+
                databaseTable.DISCOUNTS.table;
        Cursor cursor = db.rawQuery(readCategoriesSQL, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String id = cursor.getString(cursor.getColumnIndex(discountColumn.ID.column));
                String discount = cursor.getString(cursor.getColumnIndex(discountColumn.DISCOUNT.column));
                String item = cursor.getString(cursor.getColumnIndex(discountColumn.ITEM.column));
                String company = cursor.getString(cursor.getColumnIndex(discountColumn.COMPANY.column));
                String address = cursor.getString(cursor.getColumnIndex(discountColumn.ADDRESS.column));
                byte[] logo = cursor.getBlob(cursor.getColumnIndex(discountColumn.LOGO.column));


                categories.add(new Companies(id, discount,item,company,address,logo));
                cursor.moveToNext();
            }
        }

        cursor.close();


        return categories;
    }*/



    public ArrayList<AdventureModel> getAllAdventures(){
        ArrayList<AdventureModel> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String readCategoriesSQL = "SELECT * FROM "+
                databaseTable.ADVENTURES.table;
        Cursor cursor = db.rawQuery(readCategoriesSQL, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String riddle = cursor.getString(cursor.getColumnIndex(adventureColumn.RIDDLE.column));
                String description = cursor.getString(cursor.getColumnIndex(adventureColumn.DESC.column));



                categories.add(new AdventureModel(riddle,description));
                cursor.moveToNext();
            }
        }

        cursor.close();


        return categories;

    }



    public ArrayList<InstitutionModel> getAllInst(){
        ArrayList<InstitutionModel> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String readCategoriesSQL = "SELECT * FROM "+
                databaseTable.INSTITUTIONS.table;
        Cursor cursor = db.rawQuery(readCategoriesSQL, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String institution_name = cursor.getString(cursor.getColumnIndex(institutionColumn.INSTITUTION_IDENTIFIER.column));
                String institution_identifier = cursor.getString(cursor.getColumnIndex(institutionColumn.INSTITUION_NAME.column));
                String address = cursor.getString(cursor.getColumnIndex(institutionColumn.ADDRESS.column));

                double latitude = cursor.getDouble(cursor.getColumnIndex(institutionColumn.LATITUDE.column));
                double longitude = cursor.getDouble(cursor.getColumnIndex(institutionColumn.LONGITUDE.column));
                int passedState = cursor.getInt(cursor.getColumnIndex(institutionColumn.PASSED_STATE.column));
                categories.add(new InstitutionModel(institution_identifier, institution_name,latitude,longitude,address,passedState));
                cursor.moveToNext();
            }
        }

        cursor.close();


        return categories;
    }


    public  void updatePassedState(String inst_name,int value){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(institutionColumn.PASSED_STATE.column,value);
        db.update(databaseTable.INSTITUTIONS.table,cv,"inst_name= '"+inst_name +"'",null);

    }



    //A class which fills OrderData class instance which the result from sql query



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropCategoriesSQL = "DROP TABLE IF EXISTS "+databaseTable.QUESTIONS.table;
        String dropDiscountSql = "DROP TABLE IF EXISTS "+databaseTable.DISCOUNTS.table;
        String dropInstSql = "DROP TABLE IF EXISTS "+databaseTable.INSTITUTIONS.table;
        String dropTreasureSql = "DROP TABLE IF EXISTS "+databaseTable.TREASURES.table;



        db.execSQL(dropCategoriesSQL);
        db.execSQL(dropDiscountSql);
        db.execSQL(dropInstSql);
        db.execSQL(dropTreasureSql);

        onCreate(db);
    }
}
