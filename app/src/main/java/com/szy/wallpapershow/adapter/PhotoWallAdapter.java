package com.szy.wallpapershow.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.szy.wallpapershow.R;
import com.szy.wallpapershow.model.Gallery;

import java.util.List;

/**
 * Created by szjlc on 16/4/26.
 */
public class PhotoWallAdapter extends RecyclerView.Adapter {

    private List<Gallery > myDataSet;
    private RequestQueue requestQueue;
    public PhotoWallAdapter(List<Gallery> myDataSet,Context context){

        this.myDataSet = myDataSet;
        requestQueue = Volley.newRequestQueue(context);
    }

    public  void addData(List<Gallery> glist){
        myDataSet.addAll(glist);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });

        Gallery gallery = myDataSet.get(position);
        imageLoader.get("http://tnfs.tngou.net/image"+gallery.getImg()+"_540x360", ImageLoader.getImageListener(myViewHolder.ivPicture, R.drawable.default_img, R.drawable.failure));
        myViewHolder.tvPicTitle.setText(gallery.getTitle());
//        requestQueue.start();
    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivPicture;
        TextView tvPicTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivPicture = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvPicTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
        public ImageView getIvPicture()
        {
            return ivPicture;
        }
    }

}
