package com.chen.schedule.myschedule.addpicture;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chen.schedule.myschedule.R;
import com.chen.schedule.myschedule.database.NotesDB;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenxin on 2017/5/13.
 */

public class AddContent extends Activity implements View.OnClickListener{
    String val;
    private Button saveBtn;
    private Button unSaveBtn;
    private EditText mEditText;
    private ImageView mImageView;
    private NotesDB mNotesDB;
    private SQLiteDatabase dbWriter;
    private File phonefile;
    private Button voice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_content);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "= 591d7995");
        val = getIntent().getStringExtra("flag");
        saveBtn = (Button)findViewById(R.id.save_pic);
        unSaveBtn = (Button) findViewById(R.id.unsave_pic);
        mEditText = (EditText) findViewById(R.id.et_text);
        mImageView = (ImageView) findViewById(R.id.c_img);
        voice = (Button) findViewById(R.id.voice);

        saveBtn.setOnClickListener(this);
        unSaveBtn.setOnClickListener(this);
        voice.setOnClickListener(this);

        mNotesDB = new NotesDB(this);
        dbWriter = mNotesDB.getWritableDatabase();
        initPicView();


    }
    public void initPicView() {
        Intent iImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        phonefile = new File(Environment.getExternalStorageDirectory()
                .getAbsoluteFile()+"/"+getTime()+".jpg");
        iImg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phonefile));
        startActivityForResult(iImg,1);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save_pic:
                addDB();
                finish();
                break;
            case R.id.unsave_pic:
                finish();
                break;
            case R.id.voice:
                btnVoice();
                break;

        }
    }
    public void addDB() {
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT,mEditText.getText().toString());
        cv.put(NotesDB.TIME,getTime());
        cv.put(NotesDB.PATH,phonefile+"");
        dbWriter.insert(NotesDB.TABLE_NAME,null,cv);
    }
    private String getTime() {
        SimpleDateFormat format =new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String str = format.format(date);
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1){
            Bitmap bitmap = BitmapFactory.decodeFile(phonefile
                    .getAbsolutePath());
            mImageView.setImageBitmap(bitmap);
        }
    }
    //TODO 开始说话：
    private void btnVoice() {
        RecognizerDialog dialog = new RecognizerDialog(this,null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        dialog.show();
        Toast.makeText(this, "请开始说话", Toast.LENGTH_SHORT).show();
    }

    //回调结果：
    private void printResult(RecognizerResult results) {
        String text = parseIatResult(results.getResultString());
        // 自动填写地址
        mEditText.append(text);
    }

    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
}
