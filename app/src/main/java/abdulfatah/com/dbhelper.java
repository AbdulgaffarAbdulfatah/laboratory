package abdulfatah.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class dbhelper extends SQLiteOpenHelper {

    public static final String TBL_USERS = "users",
            TBL_USER_ID="id",
            TBL_USER_USERNAME="Username",
            TBL_USER_PASSWORD="Password",
            TBL_USER_FULLNAME="FullName";

    SQLiteDatabase dbreadable = getReadableDatabase();
    SQLiteDatabase dbwritable = getWritableDatabase();




    public dbhelper(Context context) {
        super(context, "db_databasing", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create_users_table = String.format("CREATE TABLE %s"+
                "(%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "%s TEXT, " +
        "%s TEXT, " +
        "%s TEXT)",
        TBL_USERS,
        TBL_USER_ID,
        TBL_USER_USERNAME,
        TBL_USER_PASSWORD,
        TBL_USER_FULLNAME);

       db.execSQL(sql_create_users_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int addUser(HashMap<String, String> map_user) {

        ContentValues val = new ContentValues();

        String sql = "SELECT  * FROM " +  TBL_USERS +
                " WHERE " + TBL_USER_FULLNAME + " = '" + map_user.get(TBL_USER_USERNAME) +  "'";
        Cursor cur  = dbreadable.rawQuery(sql, null);
        int userID = 0;

        if(cur.moveToNext()) {
            userID =  cur.getInt(cur.getColumnIndex(TBL_USER_ID));
        }
        else {
            val.put(TBL_USER_USERNAME,map_user.get(TBL_USER_USERNAME));
            val.put(TBL_USER_PASSWORD, map_user.get(TBL_USER_PASSWORD));
            val.put(TBL_USER_FULLNAME,map_user.get(TBL_USER_FULLNAME));


            dbwritable.insert(TBL_USERS, null,val);
        }


        return userID;
    }

    public int  checkUser(HashMap<String, String> map_user) {

        String sql = "SELECT * FROM " + TBL_USERS +
                " WHERE " + TBL_USER_USERNAME + " +'" + map_user.get(TBL_USER_USERNAME) + "' AND " +
                TBL_USER_PASSWORD + " = '" + map_user.get(TBL_USER_PASSWORD) + "'";
        Cursor cur = dbreadable.rawQuery(sql, null);
        int userid = 0;

        if(cur.moveToNext()) {
            userid = cur.getInt(cur.getColumnIndex(TBL_USER_ID));

        }

        return userid;
    }

    public ArrayList<HashMap<String, String>> getAllUsers() {

        String sql = "SELECT * FROM " + TBL_USERS + " ORDER BY " + TBL_USER_USERNAME + " ASC " ;
        Cursor cur = dbreadable.rawQuery(sql, null);

        ArrayList<HashMap<String, String>> all_users = new ArrayList();

        while(cur.moveToNext()) {
            HashMap<String, String> map_user = new HashMap();
            map_user.put(TBL_USER_ID, cur.getString(cur.getColumnIndex(TBL_USER_ID)));
            map_user.put(TBL_USER_USERNAME, cur.getString(cur.getColumnIndex(TBL_USER_USERNAME)));
            map_user.put(TBL_USER_PASSWORD, cur.getString(cur.getColumnIndex(TBL_USER_PASSWORD)));
            map_user.put(TBL_USER_FULLNAME, cur.getString(cur.getColumnIndex(TBL_USER_FULLNAME)));
            all_users.add(map_user);
        }

        cur.close();


        return all_users;
    }

    public void deleteUser(int userID) {
        dbwritable.delete(TBL_USERS, TBL_USER_ID + " = " + userID, null);
    }

    public ArrayList<HashMap<String, String>> getSelectedUserData(int userID) {

        String sql = " SELECT * FROM " + TBL_USERS + " WHERE " +TBL_USER_ID + " = " + userID;
        Cursor cur = dbreadable.rawQuery(sql, null);

        ArrayList<HashMap<String, String>> selected_user = new ArrayList();

        while (cur.moveToNext())  {
            HashMap<String, String> map_user = new HashMap();
            map_user.put(TBL_USER_USERNAME, cur.getString(cur.getColumnIndex(TBL_USER_USERNAME)));
            map_user.put(TBL_USER_PASSWORD, cur.getString(cur.getColumnIndex(TBL_USER_PASSWORD)));
            selected_user.add(map_user);
        }

        cur.close();

        return  selected_user;

    }

    public void updateUser(HashMap<String, String> map_user) {
        ContentValues val = new ContentValues();
        val.put(TBL_USER_USERNAME, map_user.get(TBL_USER_USERNAME));
        val.put(TBL_USER_PASSWORD, map_user.get(TBL_USER_PASSWORD));
        dbwritable.update(TBL_USERS, val, TBL_USER_ID + " = " + map_user.get(TBL_USER_ID),null);

    }
}
