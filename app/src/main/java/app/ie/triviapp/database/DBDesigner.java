package app.ie.triviapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import static app.ie.triviapp.Base.userName;

/**
 * This Class defines the design and implementation of the Triviapp database User table.
 *
 * Created by Peter on 02/05/2018.
 */

public class DBDesigner extends SQLiteOpenHelper {


    //the name of the database file, the version number, and the table to be created
    private  static  final String Database_Name = "triviapp.db";
    private static final int Database_Version = 1;
    private static final String Table_Name = "Users";

    //column names
    private static final String COL_ID = "user_id";
    private static final String COL_NAME = "user_name";
    private static final String COL_EMAIL = "user_email";
    private static final String COL_PASSWORD = "user_password";

    //Construction by concatenation of text and field variables, of the SQL instruction creating the table
    private String CREATE_USER_TABLE = "CREATE TABLE " + Table_Name + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " TEXT,"
            + COL_EMAIL + " TEXT," + COL_PASSWORD + " TEXT" + ")";



    public DBDesigner(Context context){
        super(context, Database_Name, null, Database_Version);
    }


    //code to create the table
    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_USER_TABLE);
    }


    //boilerplate code to handle upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        Log.w(DBDesigner.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS "+ Table_Name);
        onCreate(db);
    }


    /**
     * Method to add a user to the database table.
     *
     * @param user An object of type User.
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(Table_Name, null, values);
        db.close();
    }


    /**
     * Method to list all users. Unused in current version.
     *
     * @return Returns a list of all recorder users.
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COL_ID,
                COL_EMAIL,
                COL_NAME,
                COL_PASSWORD
        };
        // sorting orders
        String sortOrder = COL_NAME + " ASC";

        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /*
          Here query function is used to fetch records from user table this function works like we use sql query.
          SQL query equivalent to this query function is
          SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(Table_Name, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COL_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }


    /**
     * Method to get username matching a given email. Unused.
     *
     * @param email Takes one String to compare with email.
     */
    public void getUserName(String email){
       // String user;
       // String query = "Select "+COL_NAME+" from "+Table_Name+" where "+COL_EMAIL+ " = "+email;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "Select " +COL_NAME+ " from " +Table_Name+ " where "+COL_EMAIL+" = " +email+";", null);
        userName = cursor.getString(0);
        cursor.close();
        db.close();

    }


    /**
     * Method to update user info. Unused due to time constraints.
     *
     * @param user Takes one object of type user.
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());

        // updating row
        db.update(Table_Name, values, COL_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


    /**
     * Method to delete user. Same functionality is implemented elsewhere.
     *
     * @param user Takes one object of type user.
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(Table_Name, COL_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


    /**
     * Method to delete a user based on their userName, which in this case is actually their email.
     *
     * @param userName Takes one string username, in this case actually the email.
     */
    public void deleteUser(String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Name, COL_EMAIL + " = ?", new String[]{userName});
        db.close();
    }


    /**
     * Method to check if a user exists, based on a given email.
     *
     * @param email Takes one parameter of type String. The email to be checked against.
     * @return Returns true or false.
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COL_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COL_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /*
          Here query function is used to fetch records from user table this function works like we use sql query.
          SQL query equivalent to this query function is
          SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(Table_Name, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * Method to check if a user exists based on email and password.
     *
     * @param email Takes a parameter of type String. The email to be checked.
     * @param password Takes a parameter of type String. The assword to be checked.
     * @return Returns true or false.
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COL_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COL_EMAIL + " = ?" + " AND " + COL_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /*
          Here query function is used to fetch records from user table this function works like we use sql query.
          SQL query equivalent to this query function is
          SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(Table_Name, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * This Method is intended for testing purposes only, and is not intended for grading.
     * The code for this Activity was taken in its entirety from the following link,
     * https://github.com/sanathp/DatabaseManager_For_Android
     * The purpose is to allow database viewing from within the app.
     * This was needed as API 26 does not allow viewing of databases through Android Device Manager.
     * Rebuilding the app for API 23 threw up insoluble errors, and time constraints meant I had to find an alternative method.
     *
     * @param Query
     * @return
     */
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
