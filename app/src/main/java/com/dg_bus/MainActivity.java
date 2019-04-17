package com.dg_bus;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dg_bus.dg_util.ItemObject;
import com.dg_bus.dg_util.ParsingAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ItemObject> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        //AsyncTask 작동시킴(파싱)
        new Description().execute();
    }


    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://movie.naver.com/movie/running/current.nhn").get();
                Elements mElementDataSize = doc.select("ul[class=lst_detail_t1]").select("li"); //필요한 것만 축출
               // int mElementSize = mElementDataSize.size(); //목록이 갯수

                for(Element elem : mElementDataSize){

                    String my_title = elem.select("li dt[class=tit] a").text();
                    String my_link = elem.select("li div[class=thumb] a").attr("href");
                    String my_imgUrl = elem.select("li div[class=thumb] a img").attr("src");
                    Element rElem = elem.select("dl[class=info_txt1] dt").next().first();
                    String my_release = rElem.select("dd").text();
                    Element dElem = elem.select("dt[class=tit_t2]").next().first();
                    String my_director = "감독: " + dElem.select("a").text();

                    //ArrayList에 계속 추가한다.
                    list.add(new ItemObject(my_title, my_imgUrl, my_link, my_release, my_director));
                }

                //추출한 전체 <li> 출력해 보자.
                Log.d("debug :", "List " + mElementDataSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            ParsingAdapter myAdapter = new ParsingAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }
}
