package com.example.pictza.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME="PictzaDB";
    //private static final int DATABASE_VERSION = 4;

    private static final String TABLE_CUSTOMER = "customer";
    private static final String KEY_CUSTOMERID = "id";
    private static final String KEY_CUSTOMERUSERNAME = "username";
    private static final String KEY_CUSTOMEREMAIL = "email";
    private static final String KEY_CUSTOMERPASSWORD = "password";


    private static final String TABLE_SHOW = "show";
    private static final String KEY_SHOWID = "sid";
    private static final String KEY_SHOWLOCATION = "location";
    private static final String KEY_SHOWDATE = "date";
    private static final String KEY_SHOWTIME = "time";
    private static final String KEY_SHOWDESCRIPTION = "description";
    private static final String KEY_SHOWIMAGE = "image";


    private static final String TABLE_PAINTING = "painting";
    private static final String KEY_PAINTINGID = "pid";
    private static final String KEY_PAINTINGTITLE = "title";
    private static final String KEY_PAINTINGCATEGORY = "category";
    private static final String KEY_PAINTINGDESCRIPTION = "description";
    private static final String KEY_PAINTINGPRICE = "price";
    private static final String KEY_PAINTINGIMAGE = "painting";


    private static final String TABLE_CART = "cart";
    private static final String KEY_ITEMID = "cid";
    private static final String KEY_ITEMTITLE = "itemTitle";
    private static final String KEY_ITEMCATEGORY = "itemCategory";
    private static final String KEY_ITEMDESCRIPTION = "itemDescription";
    private static final String KEY_ITEMPRICE = "itemPrice";
    private static final String KEY_ITEMQUANTITY = "itemQuantity";


    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE " + TABLE_CUSTOMER + "(" + KEY_CUSTOMERID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CUSTOMERUSERNAME + " TEXT, "+ KEY_CUSTOMEREMAIL + " TEXT, "+ KEY_CUSTOMERPASSWORD + " TEXT);";

    private static final String CREATE_TABLE_SHOW = "CREATE TABLE " + TABLE_SHOW + "(" + KEY_SHOWID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SHOWLOCATION + " TEXT, "+ KEY_SHOWDATE + " TEXT, "+ KEY_SHOWTIME + " TEXT, "+ KEY_SHOWDESCRIPTION + " TEXT, "+ KEY_SHOWIMAGE + " BLOB);";

    private static final String CREATE_TABLE_PAINTING = "CREATE TABLE " + TABLE_PAINTING + "(" + KEY_PAINTINGID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PAINTINGTITLE + " TEXT, "+ KEY_PAINTINGCATEGORY + " TEXT, "+ KEY_PAINTINGDESCRIPTION + " TEXT, "+ KEY_PAINTINGPRICE + " TEXT, "+ KEY_PAINTINGIMAGE + " BLOB);";

    private static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + "(" + KEY_ITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ITEMTITLE + " TEXT, "+ KEY_ITEMCATEGORY + " TEXT, "+ KEY_ITEMDESCRIPTION + " TEXT, "+ KEY_ITEMPRICE + " TEXT, "+ KEY_ITEMQUANTITY + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUSTOMER);
        db.execSQL(CREATE_TABLE_SHOW);
        db.execSQL(CREATE_TABLE_PAINTING);
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int i1) {

    }


    //Customer Section Start

    public Boolean addCustomer(String name, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_CUSTOMERUSERNAME, name);
        values.put(KEY_CUSTOMEREMAIL, email);
        values.put(KEY_CUSTOMERPASSWORD, password);

        // insert row in customer table
        long insert = db.insert(TABLE_CUSTOMER, null, values);

        if(insert>=1){
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<CustomerModel> getAllCustomers() {
        // ArrayList<CustomerModel> customerModelArrayList = new ArrayList<CustomerModel>();
        ArrayList<CustomerModel> customerModelArrayList = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_CUSTOMER,null);


//        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            customerModelArrayList = new ArrayList<CustomerModel>();
            do {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(c.getInt(c.getColumnIndex(KEY_CUSTOMERID)));
                customerModel.setUsername(c.getString(c.getColumnIndex(KEY_CUSTOMERUSERNAME)));
                customerModel.setEmail(c.getString(c.getColumnIndex(KEY_CUSTOMEREMAIL)));
                customerModel.setPassword(c.getString(c.getColumnIndex(KEY_CUSTOMERPASSWORD)));

                // adding to customer list
                customerModelArrayList.add(customerModel);
            } while (c.moveToNext());
        }
        return customerModelArrayList;

    }
    public ArrayList<CustomerModel> getCustomer(String id){

        ArrayList<CustomerModel> customerArray = null;
        SQLiteDatabase db=getReadableDatabase();
        String[] args={id};
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_CUSTOMER+" WHERE "+KEY_CUSTOMERID+" = ?",args);
        if(c.moveToFirst()){
            customerArray=new ArrayList<CustomerModel>();
            do{
                CustomerModel customerMod=new CustomerModel();
                customerMod.setId(c.getInt(c.getColumnIndex(KEY_CUSTOMERID)));
                customerMod.setUsername(c.getString(c.getColumnIndex(KEY_CUSTOMERUSERNAME)));
                customerMod.setEmail(c.getString(c.getColumnIndex(KEY_CUSTOMEREMAIL)));
                customerMod.setPassword(c.getString(c.getColumnIndex(KEY_CUSTOMERPASSWORD)));

                customerArray.add(customerMod);

            }while (c.moveToNext());
        }
        return customerArray;

    }

    public Boolean updateCustomer(int id,String username, String email,String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(this.KEY_CUSTOMERUSERNAME, username);
        values.put(KEY_CUSTOMEREMAIL,email);
        values.put(KEY_CUSTOMERPASSWORD,password);

        // update row in customer table base on customer.is value
        //String args=Integer.toString(id);
        int row=db.update(TABLE_CUSTOMER, values,KEY_CUSTOMERID + " = ?",new String[]{String.valueOf(id)});
        if(row>=1){
            return true;
        }else {
            return false;
        }


    }

    public Boolean deleteCustomer(int id) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_CUSTOMER, KEY_CUSTOMERID + " = ?",
                new String[]{String.valueOf(id)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<CustomerModel> searchCustomer(String customer) {
        // ArrayList<CustomerModel> customerModelArrayList = new ArrayList<CustomerModel>();
        ArrayList<CustomerModel> customerModels = null;

        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + KEY_CUSTOMERUSERNAME + " LIKE ?", new String[] { "%" + customer + "%" });


//        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                customerModels = new ArrayList<CustomerModel>();
                do {
                    CustomerModel customerModel = new CustomerModel();
                    customerModel.setId(c.getInt(c.getColumnIndex(KEY_CUSTOMERID)));
                    customerModel.setUsername(c.getString(c.getColumnIndex(KEY_CUSTOMERUSERNAME)));
                    customerModel.setEmail(c.getString(c.getColumnIndex(KEY_CUSTOMEREMAIL)));
                    customerModel.setPassword(c.getString(c.getColumnIndex(KEY_CUSTOMERPASSWORD)));
                   // adding to customer list
                    customerModels.add(customerModel);
                } while (c.moveToNext());
            }
        }catch(Exception e) {
            customerModels = null;
        }
        return customerModels;
    }

    public boolean readInfo(String eml, String pswd)
    {
        SQLiteDatabase db = getReadableDatabase();

        // define a projection that specifies which columns from the database
        // you will actually use after this query
        String[] projection = {
                CustomerMaster.Users.COLUMN_NAME_EMAIL,
                CustomerMaster.Users.COLUMN_NAME_PASSWORD
        };

        //Filter results
        String selection = CustomerMaster.Users.COLUMN_NAME_EMAIL + " = ?" + " AND " + CustomerMaster.Users.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {eml, pswd};

        // How you want the results sorted in the resulting cursor
        String sortOrder = CustomerMaster.Users.COLUMN_NAME_EMAIL + " DESC";

        Cursor cursor = db.query(
                CustomerMaster.Users.TABLE_NAME,           // the table to query
                projection,                 // the columns to return
                selection,               // the columns for the WHERE clause
                selectionArgs,            // the values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                sortOrder                  // the sort order
        );

        List emails = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString( cursor.getColumnIndexOrThrow(CustomerMaster.Users.COLUMN_NAME_EMAIL));
            String password = cursor.getString( cursor.getColumnIndexOrThrow(CustomerMaster.Users.COLUMN_NAME_PASSWORD));
            emails.add(username);
            passwords.add(password);
        }
        if (cursor.getCount() == 0)
            return false;
        else
            return true;
        // cursor.close();

    }

//Customer Section Over


// Shows Section Start

    public Boolean addShow(String location, String date, String time, String description , String imgPath) {
        SQLiteDatabase db = getWritableDatabase();

        try{

            FileInputStream fs = new FileInputStream(imgPath);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);

            // Creating content values
            ContentValues values = new ContentValues();
            values.put(KEY_SHOWLOCATION, location);
            values.put(KEY_SHOWDATE, date);
            values.put(KEY_SHOWTIME, time);
            values.put(KEY_SHOWDESCRIPTION, description);
            values.put(KEY_SHOWIMAGE,imgbyte);

            // insert row in customer table
            long insertShow = db.insert(TABLE_SHOW, null, values);

            fs.close();

            if(insertShow>=1){
                return true;
            }else {
                return false;
            }


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public ArrayList<ShowModel> getAllShows() {
        ArrayList<ShowModel> showModelArrayList = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_SHOW,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            showModelArrayList = new ArrayList<ShowModel>();
            do {
                ShowModel showModel = new ShowModel();

                showModel.setSid(c.getInt(c.getColumnIndex(KEY_SHOWID)));
                showModel.setLocation(c.getString(c.getColumnIndex(KEY_SHOWLOCATION)));
                showModel.setDate(c.getString(c.getColumnIndex(KEY_SHOWDATE)));
                showModel.setTime(c.getString(c.getColumnIndex(KEY_SHOWTIME)));
                showModel.setDescription(c.getString(c.getColumnIndex(KEY_SHOWDESCRIPTION)));

                // adding to customer list
                showModelArrayList.add(showModel);
            } while (c.moveToNext());
        }
        return showModelArrayList;


    }

    public ArrayList<ShowModel> getShow(String sid){

        ArrayList<ShowModel> showArray = null;
        SQLiteDatabase db=getReadableDatabase();
        String[] args={sid};
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_SHOW+" WHERE "+KEY_SHOWID+" = ?",args);
        if(c.moveToFirst()){
            showArray=new ArrayList<ShowModel>();
            do{
                ShowModel showMod=new ShowModel();
                showMod.setSid(c.getInt(c.getColumnIndex(KEY_SHOWID)));
                showMod.setLocation(c.getString(c.getColumnIndex(KEY_SHOWLOCATION)));
                showMod.setDate(c.getString(c.getColumnIndex(KEY_SHOWDATE)));
                showMod.setTime(c.getString(c.getColumnIndex(KEY_SHOWTIME)));
                showMod.setDescription(c.getString(c.getColumnIndex(KEY_SHOWDESCRIPTION)));

                showArray.add(showMod);

            }while (c.moveToNext());
        }
        return showArray;

    }

    public Boolean updateShow(int sid, String location, String date, String time, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(this.KEY_SHOWLOCATION, location);
        values.put(KEY_SHOWDATE,date);
        values.put(KEY_SHOWTIME,time);
        values.put(KEY_SHOWDESCRIPTION,description);

        // update row in customer table base on customer.is value
        //String args=Integer.toString(id);
        int row=db.update(TABLE_SHOW, values,KEY_SHOWID + " = ?",new String[]{String.valueOf(sid)});
        if(row>=1){
            return true;
        }else {
            return false;
        }


    }


    public Boolean deleteShow(int sid) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_SHOW, KEY_SHOWID + " = ?",
                new String[]{String.valueOf(sid)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }


    public ArrayList<ShowModel> searchShow(String show) {

        ArrayList<ShowModel> showModels = null;

        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_SHOW + " WHERE " + KEY_SHOWLOCATION + " LIKE ?", new String[] { "%" + show + "%" });

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                showModels = new ArrayList<ShowModel>();
                do {
                    ShowModel showModel = new ShowModel();
                    showModel.setSid(c.getInt(c.getColumnIndex(KEY_SHOWID)));
                    showModel.setLocation(c.getString(c.getColumnIndex(KEY_SHOWLOCATION)));
                    showModel.setDate(c.getString(c.getColumnIndex(KEY_SHOWDATE)));
                    showModel.setTime(c.getString(c.getColumnIndex(KEY_SHOWTIME)));
                    showModel.setDescription(c.getString(c.getColumnIndex(KEY_SHOWDESCRIPTION)));
                    // adding to customer list
                    showModels.add(showModel);
                } while (c.moveToNext());
            }
        }catch(Exception e) {
            showModels = null;
        }
        return showModels;
    }


    public Cursor getShowData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

// Shows Section Over


// Painting Section Start

        public Boolean addPainting(String title, String category, String description, String price, String imgPath) {
        SQLiteDatabase db = getWritableDatabase();

            try{

                FileInputStream fs = new FileInputStream(imgPath);
                byte[] imgbyte = new byte[fs.available()];
                fs.read(imgbyte);

                // Creating content values
                ContentValues values = new ContentValues();
                values.put(KEY_PAINTINGTITLE, title);
                values.put(KEY_PAINTINGCATEGORY, category);
                values.put(KEY_PAINTINGDESCRIPTION, description);
                values.put(KEY_PAINTINGPRICE, price);
                values.put(KEY_PAINTINGIMAGE,imgbyte);

                // insert row in customer table
                long insertPainting = db.insert(TABLE_PAINTING, null, values);

                fs.close();

                if(insertPainting>=1){
                    return true;
                }else {
                    return false;
                }


            }catch (Exception e){
                e.printStackTrace();
                return false;
            }


    }

    public ArrayList<PaintingModel> getAllPaintings() {
        ArrayList<PaintingModel> paintingModelArrayList = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_PAINTING,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            paintingModelArrayList = new ArrayList<PaintingModel>();
            do {
                PaintingModel paintingModel = new PaintingModel();

                paintingModel.setPid(c.getInt(c.getColumnIndex(KEY_PAINTINGID)));
                paintingModel.setTitle(c.getString(c.getColumnIndex(KEY_PAINTINGTITLE)));
                paintingModel.setCategory(c.getString(c.getColumnIndex(KEY_PAINTINGCATEGORY)));
                paintingModel.setDescription(c.getString(c.getColumnIndex(KEY_PAINTINGDESCRIPTION)));
                paintingModel.setPrice(c.getString(c.getColumnIndex(KEY_PAINTINGPRICE)));

                // adding to customer list
                paintingModelArrayList.add(paintingModel);
            } while (c.moveToNext());
        }
        return paintingModelArrayList;


    }

    public ArrayList<PaintingModel> getPainting(String pid){

        ArrayList<PaintingModel> paintingArray = null;
        SQLiteDatabase db=getReadableDatabase();
        String[] args={pid};
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_PAINTING+" WHERE "+KEY_PAINTINGID+" = ?",args);
        if(c.moveToFirst()){
            paintingArray=new ArrayList<PaintingModel>();
            do{
                PaintingModel paintingMod=new PaintingModel();
                paintingMod.setPid(c.getInt(c.getColumnIndex(KEY_PAINTINGID)));
                paintingMod.setTitle(c.getString(c.getColumnIndex(KEY_PAINTINGTITLE)));
                paintingMod.setCategory(c.getString(c.getColumnIndex(KEY_PAINTINGCATEGORY)));
                paintingMod.setDescription(c.getString(c.getColumnIndex(KEY_PAINTINGDESCRIPTION)));
                paintingMod.setPrice(c.getString(c.getColumnIndex(KEY_PAINTINGPRICE)));

                paintingArray.add(paintingMod);

            }while (c.moveToNext());
        }
        return paintingArray;

    }

    public Boolean updatePainting(int pid, String title, String category, String description, String price) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(this.KEY_PAINTINGTITLE, title);
        values.put(KEY_PAINTINGCATEGORY,category);
        values.put(KEY_PAINTINGDESCRIPTION,description);
        values.put(KEY_PAINTINGPRICE,price);

        // update row in customer table base on customer.is value
        //String args=Integer.toString(id);
        int row=db.update(TABLE_PAINTING, values,KEY_PAINTINGID + " = ?",new String[]{String.valueOf(pid)});
        if(row>=1){
            return true;
        }else {
            return false;
        }


    }


    public Boolean deletePainting(int pid) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_PAINTING, KEY_PAINTINGID + " = ?",
                new String[]{String.valueOf(pid)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }


    public ArrayList<PaintingModel> searchPainting(String painting) {

        ArrayList<PaintingModel> paintingModels = null;

        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PAINTING + " WHERE " + KEY_PAINTINGTITLE + " LIKE ?", new String[] { "%" + painting + "%" });

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                paintingModels = new ArrayList<PaintingModel>();
                do {
                    PaintingModel paintingModel = new PaintingModel();
                    paintingModel.setPid(c.getInt(c.getColumnIndex(KEY_PAINTINGID)));
                    paintingModel.setTitle(c.getString(c.getColumnIndex(KEY_PAINTINGTITLE)));
                    paintingModel.setCategory(c.getString(c.getColumnIndex(KEY_PAINTINGCATEGORY)));
                    paintingModel.setDescription(c.getString(c.getColumnIndex(KEY_PAINTINGDESCRIPTION)));
                    paintingModel.setPrice(c.getString(c.getColumnIndex(KEY_PAINTINGPRICE)));
                    // adding to customer list
                    paintingModels.add(paintingModel);
                } while (c.moveToNext());
            }
        }catch(Exception e) {
            paintingModels = null;
        }
        return paintingModels;
    }

    public Cursor getPaintingData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

// Painting Section Over


// Cart Section Start

    public Boolean addCart(String itemTitle, String itemCategory, String itemDescription, String itemPrice, String itemQuantity) {
        SQLiteDatabase db = getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_ITEMTITLE, itemTitle);
        values.put(KEY_ITEMCATEGORY, itemCategory);
        values.put(KEY_ITEMDESCRIPTION, itemDescription);
        values.put(KEY_ITEMPRICE, itemPrice);
        values.put(KEY_ITEMQUANTITY, itemQuantity);

        // insert row in customer table
        long insertItem = db.insert(TABLE_CART, null, values);

        if(insertItem>=1){
            return true;
        }else {
            return false;
        }


    }

    public ArrayList<CartModel> getAllItems() {
        ArrayList<CartModel> cartModelArrayList = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_CART,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            cartModelArrayList = new ArrayList<CartModel>();
            do {
                CartModel cartModel = new CartModel();

                cartModel.setCid(c.getInt(c.getColumnIndex(KEY_ITEMID)));
                cartModel.setItemTitle(c.getString(c.getColumnIndex(KEY_ITEMTITLE)));
                cartModel.setItemCategory(c.getString(c.getColumnIndex(KEY_ITEMCATEGORY)));
                cartModel.setItemDescription(c.getString(c.getColumnIndex(KEY_ITEMDESCRIPTION)));
                cartModel.setItemPrice(c.getString(c.getColumnIndex(KEY_ITEMPRICE)));
                cartModel.setItemQuantity(c.getString(c.getColumnIndex(KEY_ITEMQUANTITY)));

                // adding to customer list
                cartModelArrayList.add(cartModel);
            } while (c.moveToNext());
        }
        return cartModelArrayList;


    }

    public ArrayList<CartModel> getItem(String cid){

        ArrayList<CartModel> cartArray = null;
        SQLiteDatabase db=getReadableDatabase();
        String[] args={cid};
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_CART+" WHERE "+KEY_ITEMID+" = ?",args);
        if(c.moveToFirst()){
            cartArray=new ArrayList<CartModel>();
            do{
                CartModel cartMod=new CartModel();
                cartMod.setCid(c.getInt(c.getColumnIndex(KEY_ITEMID)));
                cartMod.setItemTitle(c.getString(c.getColumnIndex(KEY_ITEMTITLE)));
                cartMod.setItemCategory(c.getString(c.getColumnIndex(KEY_ITEMCATEGORY)));
                cartMod.setItemDescription(c.getString(c.getColumnIndex(KEY_ITEMDESCRIPTION)));
                cartMod.setItemPrice(c.getString(c.getColumnIndex(KEY_ITEMPRICE)));
                cartMod.setItemQuantity(c.getString(c.getColumnIndex(KEY_ITEMQUANTITY)));

                cartArray.add(cartMod);

            }while (c.moveToNext());
        }
        return cartArray;

    }

    public Boolean updateCart(int cid, String itemTitle, String itemCategory, String itemDescription, String itemPrice, String itemQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(this.KEY_ITEMTITLE, itemTitle);
        values.put(KEY_ITEMCATEGORY,itemCategory);
        values.put(KEY_ITEMDESCRIPTION,itemDescription);
        values.put(KEY_ITEMPRICE,itemPrice);
        values.put(KEY_ITEMQUANTITY,itemQuantity);

        // update row in customer table base on customer.is value
        //String args=Integer.toString(id);
        int row=db.update(TABLE_CART, values,KEY_ITEMID + " = ?",new String[]{String.valueOf(cid)});
        if(row>=1){
            return true;
        }else {
            return false;
        }


    }


    public Boolean deleteItem(int cid) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_CART, KEY_ITEMID + " = ?",
                new String[]{String.valueOf(cid)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }


    public ArrayList<CartModel> searchItem(String item) {

        ArrayList<CartModel> cartModels = null;

        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + KEY_ITEMTITLE + " LIKE ?", new String[] { "%" + item + "%" });

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                cartModels = new ArrayList<CartModel>();
                do {
                    CartModel cartModel = new CartModel();
                    cartModel.setCid(c.getInt(c.getColumnIndex(KEY_ITEMID)));
                    cartModel.setItemTitle(c.getString(c.getColumnIndex(KEY_ITEMTITLE)));
                    cartModel.setItemCategory(c.getString(c.getColumnIndex(KEY_ITEMCATEGORY)));
                    cartModel.setItemDescription(c.getString(c.getColumnIndex(KEY_ITEMDESCRIPTION)));
                    cartModel.setItemPrice(c.getString(c.getColumnIndex(KEY_ITEMPRICE)));
                    cartModel.setItemQuantity(c.getString(c.getColumnIndex(KEY_ITEMQUANTITY)));

                    cartModels.add(cartModel);
                } while (c.moveToNext());
            }
        }catch(Exception e) {
            cartModels = null;
        }
        return cartModels;
    }


// Cart Section Over

}
