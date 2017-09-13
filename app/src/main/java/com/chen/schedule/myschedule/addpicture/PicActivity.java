package com.chen.schedule.myschedule.addpicture;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.chen.schedule.myschedule.R;
import com.chen.schedule.myschedule.database.NotesDB;

/**
 * Created by chenxin on 2017/5/13.
 */

public class PicActivity extends Activity implements View.OnClickListener{
    private Button mButton;
    private ListView lv;
    private PicAdapter mPicAdapter;
    private NotesDB mNotesDB;
    private SQLiteDatabase dbReader;
    private Cursor cursor;
    Intent i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpic_main);
        initPicView();
    }

    @Override
    public void onClick(View v) {
        i = new Intent(this,AddContent.class);
        i.putExtra("flag",1);
        startActivity(i);

    }
    public void initPicView() {
        lv = (ListView)findViewById(R.id.pic_list);
        mButton = (Button)findViewById(R.id.add_pic);
        mButton.setOnClickListener(this);

        mNotesDB = new NotesDB(this);
        dbReader = mNotesDB.getReadableDatabase();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent i =new Intent(PicActivity.this,SelectPicList.class);
                i.putExtra(NotesDB.ID,cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.CONTENT,cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TIME,cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                i.putExtra(NotesDB.PATH,cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
                startActivity(i);
            }
        });
    }
    public void selectDB() {
        cursor = dbReader.query(NotesDB.TABLE_NAME,null,null,null
        ,null,null,null);
        mPicAdapter = new PicAdapter(this,cursor);
        lv.setAdapter(mPicAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }
}
