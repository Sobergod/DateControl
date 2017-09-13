package com.chen.schedule.myschedule.addpicture;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.schedule.myschedule.R;

/**
 * Created by chenxin on 2017/5/13.
 */

public class PicAdapter extends BaseAdapter{
    private Context mContext;
    private Cursor mCursor;
    private LinearLayout mLinearLayout;
    public PicAdapter(Context mContext,Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;

    }
    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mCursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mLinearLayout =(LinearLayout) inflater.inflate(R.layout.activity_cell,null);
        TextView contentTv =(TextView) mLinearLayout.findViewById(R.id.list_content);
        TextView timeTv = (TextView) mLinearLayout.findViewById(R.id.list_time);
        ImageView imgIv =(ImageView) mLinearLayout.findViewById(R.id.list_img);
        mCursor.moveToPosition(position);
        String content = mCursor.getString(mCursor.getColumnIndex("content"));
        String time = mCursor.getString(mCursor.getColumnIndex("time"));
        String url = mCursor.getString(mCursor.getColumnIndex("path"));
        contentTv.setText(content);
        timeTv.setText(time);
        imgIv.setImageBitmap(getImageThumbnail(url,200,200));

        return mLinearLayout;
    }
    /**
     * 处理缩略图
     * */
    public Bitmap getImageThumbnail(String uri,int width,int heigh) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri,options);
        options.inJustDecodeBounds = false;
        int beWidth = options.outWidth/width;
        int beHeigh = options.outWidth/heigh;
        int be = 1;
        if(beWidth<beHeigh) {
            be = beWidth;
        }else {
            be = beHeigh;
        }
        if(be <= 0) {
            be=1;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(uri,options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,width,heigh,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;

    }
}
