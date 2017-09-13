package com.chen.schedule.myschedule.addpicture;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.schedule.myschedule.R;
import com.chen.schedule.myschedule.database.NotesDB;

/**
 * Created by chenxin on 2017/5/13.
 */

public class SelectPicList extends Activity implements View.OnClickListener{
    private Button s_delete;
    private Button s_back;
    private ImageView s_img;
    private TextView s_tv;
    private NotesDB mNotesDB;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        s_delete = (Button) findViewById(R.id.s_delete);
        s_back = (Button)findViewById(R.id.s_back);
        s_img = (ImageView)findViewById(R.id.s_img);
        s_tv = (TextView)findViewById(R.id.s_tv);

        s_delete.setOnClickListener(this);
        s_back.setOnClickListener(this);

        mNotesDB = new NotesDB(this);
        dbWriter = mNotesDB.getWritableDatabase();
        if(getIntent().getStringExtra(NotesDB.PATH).equals("null")) {
            s_img.setVisibility(View.GONE);
        }else {
            s_img.setVisibility(View.VISIBLE);
        }

        s_tv.setText(getIntent().getStringExtra(NotesDB.CONTENT));
        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(
                NotesDB.PATH));
        s_img.setImageBitmap(bitmap);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_delete:
                deleteDate();
                finish();

                break;
            case R.id.s_back:
                finish();

                break;
        }

    }
    public void deleteDate() {
        dbWriter.delete(NotesDB.TABLE_NAME,"_id="+getIntent()
                .getIntExtra(NotesDB.ID,0),null);
    }
}
