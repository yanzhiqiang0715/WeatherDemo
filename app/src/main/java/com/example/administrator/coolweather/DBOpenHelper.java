package com.example.administrator.coolweather;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public DBOpenHelper(Context context){//打开数据库
        super(context,"db_test",null,1);//1：上下文，2：数据库名，3：允许我们查询数据时返回一个Cursor，4：当前数据库的版本号
        db = getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db){//建表（user）语句
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +//PRIMARY key 将id设为主键 ，AUTOINCREMENT 设置id列自为增长
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +                       //text 文本类型
                "password TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){//重写升级
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
    public void add(String name,String password){//重写添加
        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
    }
    public void delete(String name,String password){//重写删除
        db.execSQL("DELETE FROM user WHERE name = AND password ="+name+password);
    }
    public void updata(String password){//重写更新
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }
    public ArrayList<User> getAllData(){//将表内信息返回成一个list

        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("user",null,null,null,null,null,"name DESC");//1表名，2列，3行，4行，5指定列进行过滤，6进一步过滤。7得到的信息进行排序（desc逆序）
        while(cursor.moveToNext()){//一行一行遍历
            String name = cursor.getString(cursor.getColumnIndex("name"));//移动到name列，读取出来
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(name,password));//添加到user 的list中
        }
        return list;//把list返回
    }
}

