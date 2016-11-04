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

package org.live.circle.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import org.live.circle.R;
import org.live.circle.entity.DouyuEntity;

import java.util.List;

/**
 * Created by drakeet on 6/20/15.
 */
public class ProgramListAdapter extends RecyclerView.Adapter<ProgramListAdapter.ViewHolder> {

    public static final String TAG = "MeizhiListAdapter";

    private List<DouyuEntity> mList;
    private Context mContext;

    public ProgramListAdapter(Context context, List<DouyuEntity> meizhiList) {
        mList = meizhiList;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizhi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        DouyuEntity meizhi = mList.get(position);
        viewHolder.meizhi = meizhi;
        viewHolder.titleView.setText(meizhi.getTitle());
        viewHolder.card.setTag(meizhi.getTitle());
        Glide.with(mContext)
                .load(meizhi.getCover())
                .centerCrop()
                .into(viewHolder.meizhiView)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!viewHolder.card.isShown()) {
                            viewHolder.card.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RatioImageView meizhiView;
        TextView titleView;
        View card;
        DouyuEntity meizhi;


        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            meizhiView = (RatioImageView) itemView.findViewById(R.id.iv_meizhi);
            titleView = (TextView) itemView.findViewById(R.id.tv_title);
            meizhiView.setOnClickListener(this);
            card.setOnClickListener(this);
            meizhiView.setOriginalSize(50, 50);
        }
        @Override
        public void onClick(View v) {
            ToastUtil.showToast(meizhi.getTitle());
        }
    }
}