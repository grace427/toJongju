package com.example.iamas.travelfragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// 맛집 코드 : 39
public class FourFragment extends Fragment {
    private View view;
    ProgressDialog pDialog;

    RecyclerView recyclerView;
    MyAdapter adapter;
    LinearLayoutManager layoutManager;

    RequestQueue queue;
    View dialogView;
    AlertDialog.Builder dialog;
    // 메인 화면 출력용
    ArrayList<Data> list = new ArrayList<>();
    // 상세 다이얼로그 출력용
    Data detailData = new Data();
    TextView txt_Detail_Info ;
    ImageView img_Detail_Info ;

    final static String TAG = "MainActivity";
    static final String KEY = "GN2mE8m8pbEpOyKZDhiRdDOZjg%2FR%2FUEIgo7z26k3HEefz8M0DvSZZwn0ekpLJmg%2F42jihzBbKf57CP79m12CrA%3D%3D";
    static final String appName = "Zella";

    ArrayList<Integer> contentIdList = new ArrayList<>();

    public FourFragment() {
        // Required empty public constructor
    }

    //뷰페이저로 프레그먼트가 변화되는 상태를 저장하는 변수가 필요하다.
    public static FourFragment newInstance(){
        FourFragment fragmentFour = new FourFragment();
        return fragmentFour;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_four,container,false);
        ((MainActivity)getActivity()).refresh();

        recyclerView = view.findViewById(R.id.grid_recyclerview);
        adapter = new MyAdapter(getActivity(), list, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    int position = (int) v.getTag();

                    FourFragment.AsyncTaskClassSub asyncSub = new FourFragment.AsyncTaskClassSub();
                    asyncSub.execute(position);
                }
            }
        });

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        FourFragment.AsyncTaskClassMain async = new FourFragment.AsyncTaskClassMain();
        async.execute();

        return view;
    }

    class AsyncTaskClassMain extends android.os.AsyncTask<Integer, Long, String> {

        // 일반쓰레드 돌리기 전 메인쓰레드에서 보여줄 화면처리
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            displayLoader();
        }

        // 일반쓰레드에서 돌릴 네트워크 작업
        @Override
        protected String doInBackground(Integer... integers) {
            getAreaBasedList();
            // publishProgress()를 호출하면 onProgressUpdate가 실행되고 메인쓰레드에서 UI 처리를 한다
            //publishProgress();
            return "작업 종료";
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            // 일반 쓰레드가 도는 도중에 메인 쓰레드에서 처리할 UI작업
            super.onProgressUpdate(values);
        }

        // doInBackground 메서드가 완료되면 메인 쓰레드가 얘를 호출한다(doInBackground가 반환한 값을 매개변수로 받음)
        @Override
        protected void onPostExecute(String s) {
            // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    } // end of AsyncTaskClassMain

    class AsyncTaskClassSub extends android.os.AsyncTask<Integer, Data, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int position = integers[0];
            adapter.TourData(position);
            Log.d(TAG, "클릭 이벤트");
            Log.d(TAG, "포지션 값 : " + position);

            Data data = getData(contentIdList.get(position));

            publishProgress(data);
            return "작업 종료";
        }

        @Override
        protected void onProgressUpdate(Data... values) {
            Data data = values[0];
            // Log.d(TAG, "asyncTask에서 : "+detailData);
            Log.d(TAG, "asyncTask에서 : "+data.toString());
            dialogView = View.inflate(getActivity(), R.layout.detail_info, null);
            dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("  -- 상세 정보 --");

            txt_Detail_Info = dialogView.findViewById(R.id.txt_Detail_Info);
            img_Detail_Info = dialogView.findViewById(R.id.img_Detail_Info);

            txt_Detail_Info.setText(data.getTitle()+"\n\n");
            txt_Detail_Info.append(data.getAddr()+"\n\n");
            txt_Detail_Info.append(data.getOverView()+"\n\n");
            // img_Detail_Info.setImageURI(Uri.parse(data.getFirstImage()));

            dialog.setView(dialogView);
            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    } // end of AsyncTaskClass


    // contentid를 위한 함수(contentId는 detailCommon에서 쓰기 위해 구한다)
    private void getAreaBasedList() {
        queue = Volley.newRequestQueue(getActivity());
        // 맛집 코드 39
        String url = "http://api.visitkorea.or.kr/openapi/service/"
                + "rest/KorService/areaBasedList?ServiceKey=" + KEY
                + "&areaCode=1&contentTypeId=39&listYN=Y&arrange=P"
                + "&numOfRows=14&pageNo=1&MobileOS=AND&MobileApp="
                + appName + "&_type=json";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            JSONObject parse_response = (JSONObject) response.get("response");
                            JSONObject parse_body = (JSONObject) parse_response.get("body");
                            JSONObject parse_items = (JSONObject) parse_body.get("items");
                            JSONArray parse_itemlist = (JSONArray) parse_items.get("item");

                            for (int i = 0; i < parse_itemlist.length(); i++) {
                                JSONObject imsi = (JSONObject) parse_itemlist.get(i);

                                Data data = new Data();
                                data.setFirstImage(imsi.getString("firstimage"));
                                data.setTitle(imsi.getString("title"));

                                list.add(data);

                                contentIdList.add(Integer.valueOf(imsi.getString("contentid")));
                            }

                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, error.getMessage() + "에러");
                    }
                });
        queue.add(jsObjRequest);
    } // end of getAreaBasedList

    private void displayLoader() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private Data getData(int contentID) {
        queue = Volley.newRequestQueue(getActivity());

        String url = "http://api.visitkorea.or.kr/openapi/service/"
                + "rest/KorService/detailCommon?ServiceKey=" + KEY
                + "&contentId=" + contentID
                + "&firstImageYN=Y&mapinfoYN=Y&addrinfoYN=Y&defaultYN=Y&overviewYN=Y"
                + "&pageNo=1&MobileOS=AND&MobileApp="
                + appName + "&_type=json";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject parse_response = (JSONObject) response.get("response");
                            JSONObject parse_body = (JSONObject) parse_response.get("body");
                            JSONObject parse_items = (JSONObject) parse_body.get("items");
                            JSONObject parse_itemlist = (JSONObject) parse_items.get("item");

                            detailData.setFirstImage(parse_itemlist.getString("firstimage"));
                            detailData.setTitle(parse_itemlist.getString("title"));
                            detailData.setAddr(parse_itemlist.getString("addr1"));
                            detailData.setOverView(parse_itemlist.getString("overview"));

                            Log.d(TAG, detailData.getTitle());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(jsObjRequest);
        Log.d(TAG, "getDATA에서 : "+detailData);
        return detailData;
    }


}
