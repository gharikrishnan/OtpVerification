package com.angler.otpverification.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.angler.otpverification.response.ImageList2;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    List<ImageList2.DataList> imageList2 = new ArrayList<>();
    Context context;

    public static final String DB_NAME = "course.db";
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mycourses";

    // below variable is for our id column.
    //private static final String NAME_COL10 = "__v";
    // private static final String NAME_COL1 = "is_active";

    private static final String ID_COL1 = "id";
    private static final String NAME_COL2 = "_id";
    private static final String NAME_COL3 = "brand_id";
    private static final String NAME_COL4 = "created_on";
    private static final String NAME_COL5 = "description";
    private static final String NAME_COL6 = "image_dir";
    private static final String NAME_COL7 = "image_name";
    private static final String NAME_COL8 = "modified_on";
    private static final String NAME_COL9 = "service_id";
    private static final String NAME_COL10 = "service_name";



    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;


    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + "("
                + ID_COL1 + "   INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME_COL2 + " TEXT, " + NAME_COL3 + " TEXT, " + NAME_COL4 + " TEXT,"
                + NAME_COL5 + " TEXT, " + NAME_COL6 + " TEXT, " + NAME_COL7 + " TEXT, "
                + NAME_COL8 + " TEXT, " + NAME_COL9 + " TEXT, " + NAME_COL10 + " TEXT )";

      db.execSQL(query);

    }

    public void addNewCourse(List<ImageList2.DataList> name)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        for(int i = 0; i < name.size();i++)
        {

                Log.d("DATA_BASE_INDEX_VALUE", "" + name.get(i));

                values.put(NAME_COL2, name.get(i).get_id());
                values.put(NAME_COL3, name.get(i).getBrand_id());
                values.put(NAME_COL4, name.get(i).getCreated_on());
                values.put(NAME_COL5, name.get(i).getDescription());
                values.put(NAME_COL6, name.get(i).getImage_dir());
                values.put(NAME_COL7, name.get(i).getImage_name());
                values.put(NAME_COL8, name.get(i).getModified_on());
                values.put(NAME_COL9, name.get(i).getService_id());
                values.put(NAME_COL10, name.get(i).getService_name());

                db.insert(TABLE_NAME, null, values);



            }
            db.close();

    }
        public List<ImageList2.DataList> listData()
        {

          String sql = " select * from " +TABLE_NAME;
          SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(sql,null);
            if(cursor.moveToFirst())
            {
                do{

                    imageList2.add(new ImageList2.DataList(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9)));

                  }while (cursor.moveToNext());

            }
            cursor.close();
            return imageList2;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COL1	+ "	= ?", new String[] { String.valueOf(id)});

    }

    public void updateData(String id, String service_name) {

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(NAME_COL10, service_name);

        db.update(TABLE_NAME, values, ID_COL1	+ "	= ?", new String[] { String.valueOf(id)});

    }
}
