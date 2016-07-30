package com.szy.wallpapershow;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.szy.wallpapershow.adapter.PicPageFragmentPagerAdapter;
import com.szy.wallpapershow.fragment.PicPageFragment;
import com.szy.wallpapershow.model.PicCatogery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout   cateTabLayout;
    private ViewPager   picFragPager;
    private List<PicCatogery> picCatogeryList;
    private List<PicPageFragment> fragmentList;
    private PicPageFragmentPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cateTabLayout = (TabLayout) findViewById(R.id.tab_title);
        picFragPager = (ViewPager) findViewById(R.id.vp_main);
        initData();
    }

    private void initData(){

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request  = new JsonObjectRequest("http://www.tngou.net/tnfs/api/classify", null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("tngou");
                    Gson gson = new GsonBuilder().create();
                    picCatogeryList = new ArrayList<PicCatogery>();
                    fragmentList = new ArrayList<PicPageFragment>();
                    for(int i = 0;i<array.length();i++){
                        String catogeryJson = array.getString(i);
                        PicCatogery picCatogery = gson.fromJson(catogeryJson, PicCatogery.class);
                        picCatogeryList.add(picCatogery);
                        PicPageFragment frag = new PicPageFragment(picCatogery.getId(),picCatogery.getTitle());
                        fragmentList.add(frag);
                    }
                    adapter = new PicPageFragmentPagerAdapter(getFragmentManager(),MainActivity.this,fragmentList);
                    picFragPager.setAdapter(adapter);
                    cateTabLayout.setupWithViewPager(picFragPager);
                    cateTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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

}
