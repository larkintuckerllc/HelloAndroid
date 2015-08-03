package com.larkintuckerllc.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private List<String> things = new ArrayList<>(); // Stop warnings with implicit <String>

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        things.add("one");
        things.add("two");
        things.add("three");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView thingsRV = (RecyclerView)view.findViewById(R.id.things);
        thingsRV.setHasFixedSize(true);
        thingsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        thingsRV.setAdapter(new ThingAdapter(things));
        return view;
    }

    public class ThingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        String id;
        TextView name;

        ThingViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
        }
    }

    public class ThingAdapter extends RecyclerView.Adapter<ThingViewHolder> {

        List<String> things;

        ThingAdapter(List<String> things) {
            this.things = things;
        }

        public ThingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.thing, viewGroup, false);
            return new ThingViewHolder(view);
        }

        public int getItemCount() {
            return things.size();
        }

        public void onBindViewHolder(ThingViewHolder viewHolder, int i) {
            viewHolder.id = things.get(i);
            viewHolder.name.setText(things.get(i));
        }

    }
}
