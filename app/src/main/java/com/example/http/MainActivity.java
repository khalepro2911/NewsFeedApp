package com.example.http;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class MainActivity extends AppCompatActivity {
    //private TextView mTextViewResult;
    private final String url = "https://vnexpress.net/";

    private ArrayList<String> titles;
    private ArrayList<String> links;
    private ArrayList<Website> websites;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        websites = new ArrayList<>();
        titles = new ArrayList<>();
        links = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titles = getTitles(myResponse);
                            links = getURL(myResponse);
                            for(int i = 0; i < titles.size();++i){
                                Website ele = new Website(titles.get(i),links.get(i));
                                websites.add(ele);
                            }
                            adapter = new WebAdapter(websites,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        }
                    });
                }
            }
        });




    }
    public ArrayList<String> getTitles(String html){
        Document doc = Jsoup.parse(html,"utf-8");

        ArrayList<String>titles = new ArrayList<>();
        Elements link = doc.getElementsByClass("title-news");
        for(Element e : link){
            titles.add(e.getElementsByTag("a").attr("title"));
        }

        return titles;
    }
    public ArrayList<String> getURL(String html){
        Document doc = Jsoup.parse(html,"utf-8");
        ArrayList<String>links = new ArrayList<>();
        Elements link = doc.getElementsByClass("title-news");
        for(Element e : link){
            links.add(e.getElementsByTag("a").attr("href"));
        }

        return links;
    }

    public void init(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }



}