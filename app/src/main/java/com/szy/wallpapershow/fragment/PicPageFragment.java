package com.szy.wallpapershow.fragment;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.szy.wallpapershow.R;
import com.szy.wallpapershow.adapter.PhotoWallAdapter;
import com.szy.wallpapershow.model.Gallery;
import com.szy.wallpapershow.net.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szjlc on 16/6/15.
 */
public class PicPageFragment extends Fragment {

    private String title;
    private RecyclerView recyclerView;
    private int categoryId;
    private PhotoWallAdapter adapter;
    public PicPageFragment(){}
    private List<Gallery> galleryList;
    public PicPageFragment(int categoryId,String title){

        this.categoryId  = categoryId;

        this.title = title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.picture_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_photo);
        galleryList = new ArrayList<Gallery>();
        adapter = new PhotoWallAdapter(galleryList,PicPageFragment.this.getActivity());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8));
        this.initData();
        return view;
    }

    public String getTitle(){
        return  title;
    }

    private void initData(){
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        Map<String ,String > params = new HashMap<String ,String >();
        params.put("page","1");
        params.put("rows","10");
        params.put("id",String.valueOf(categoryId));
        CustomRequest request = new CustomRequest(Request.Method.POST,"http://www.tngou.net/tnfs/api/list",params,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("tngou");

                    List<Gallery> gList = new ArrayList<Gallery>();
                    Gson gson = new GsonBuilder().create();
                    for(int i = 0;i<array.length();i++){

                        String galleryJsonStr = array.getString(i);

                        Gallery gallery = gson.fromJson(galleryJsonStr,Gallery.class);

                        gList.add(gallery);
                    }

                    adapter.addData(gList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    static class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space=space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }
}
