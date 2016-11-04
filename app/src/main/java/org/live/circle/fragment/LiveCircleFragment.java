/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.live.circle.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.live.circle.LiveCircleApi;
import org.live.circle.R;
import org.live.circle.component.ProgramListAdapter;
import org.live.circle.entity.DouyuEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LiveCircleFragment extends LiveCircleBaseFragment {

    private final static String ARG_NAME = "ARG_NAME";
    private final static String ARG_PAGE = "ARG_PAGE";
    private String mName;
    private int mPage;
    private ProgramListAdapter mMeizhiListAdapter;
    private List<DouyuEntity> mMeizhiList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static LiveCircleFragment newInstance(String name, int page) {
        LiveCircleFragment fragment = new LiveCircleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    public LiveCircleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArguments();
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    private void parseArguments() {
        Bundle bundle = getArguments();
        mName = bundle.getString(ARG_NAME);
        mPage = bundle.getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_live_circle, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.refreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3, R.color.refresh_progress_2, R.color.refresh_progress_1);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestDataRefresh();
            }
        });
        setupRecyclerView();
        if (mPage == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRequestDataRefresh(true);
                    requestDataRefresh();
                }
            }, 2000);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //进行网络请求
    private void getMovie() {
//        String baseUrl = "http://capi.douyucdn.cn/api/";
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        LiveCircleApi movieService = retrofit.create(LiveCircleApi.class);
//        Call<ResponseBody> str = movieService.getDouyuLushi(20, 0, 0, "");
//        str.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    mContent.setText(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                mContent.setText(t.getMessage());
//            }
//        });

        String baseUrl = "http://capi.douyucdn.cn/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        LiveCircleApi movieService = retrofit.create(LiveCircleApi.class);
        movieService.getDouyuLushi(20, 0, 0, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.string());
                            List<DouyuEntity> entities = new ArrayList<DouyuEntity>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                DouyuEntity entity = new DouyuEntity();
                                entity.setCover(jsonObject1.getString("room_src"));
                                entity.setId(jsonObject1.getLong("room_id"));
                                entity.setTitle(jsonObject1.getString("room_name"));
                                entities.add(entity);
//                                "room_id": "302946",
//                                        "room_src": "http://rpic.douyucdn.cn/z1606/22/22/302946_160622220644.jpg",
//                                        "vertical_src": "http://rpic.douyucdn.cn/z1606/22/22/302946_160622220644.jpg",
//                                        "isVertical": 0,
//                                        "cate_id": "2",
//                                        "room_name": "虎头哥：OMG颜值担当争夺战征服9",
//                                        "show_status": "1",
//                                        "subject": "",
//                                        "show_time": "1466601060",
//                                        "owner_uid": "1317920",
//                                        "specific_catalog": "hutouge",
//                                        "specific_status": "1",
//                                        "vod_quality": "0",
//                                        "nickname": "虎头哥",
//                                        "online": 12614,
//                                        "url": "/hutouge",
//                                        "game_url": "/directory/game/How",
//                                        "game_name": "炉石传说",
//                                        "child_id": "0",
//                                        "avatar": "http://uc.douyutv.com/upload/avatar/face/201604/15/2bf76a100f3423b4d6622968cae9c775_big.jpg",
//                                        "fans": "60784",
//                                        "ranktype": 2
                            }
                            for (DouyuEntity entity : entities) {
                                System.out.println(">>>>:entity:" + entity.getTitle());
                            }
                            if (mMeizhiList == null) {
                                mMeizhiList = entities;
                            } else {
                                mMeizhiList.addAll(entities);
                            }
                            mMeizhiListAdapter.notifyDataSetChanged();
                            setRequestDataRefresh(false);
                        } catch (JSONException e) {
                        } catch (IOException e) {
                        }

                    }
                });
    }

    public void setRequestDataRefresh(boolean requestDataRefresh) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            // 防止刷新消失太快，让子弹飞一会儿.
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override public void run() {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    public void requestDataRefresh() {
        mPage = 1;
        loadData();
    }

    private void setupRecyclerView() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mMeizhiListAdapter = new ProgramListAdapter(getActivity(), mMeizhiList);
        mRecyclerView.setAdapter(mMeizhiListAdapter);
//        mRecyclerView.addOnScrollListener(getOnBottomListener(layoutManager));
//        mMeizhiListAdapter.setOnMeizhiTouchListener(getOnMeizhiTouchListener());
    }

//    RecyclerView.OnScrollListener getOnBottomListener(StaggeredGridLayoutManager layoutManager) {
//        return new RecyclerView.OnScrollListener() {
//            @Override public void onScrolled(RecyclerView rv, int dx, int dy) {
//                boolean isBottom =
//                        layoutManager.findLastCompletelyVisibleItemPositions(
//                                new int[2])[1] >=
//                                mMeizhiListAdapter.getItemCount() -
//                                        PRELOAD_SIZE;
//                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
//                    if (!mIsFirstTimeTouchBottom) {
//                        mSwipeRefreshLayout.setRefreshing(true);
//                        mPage += 1;
//                        loadData();
//                    }
//                    else {
//                        mIsFirstTimeTouchBottom = false;
//                    }
//                }
//            }
//        };
//    }

    protected void loadData() {
        getMovie();
    }
}
