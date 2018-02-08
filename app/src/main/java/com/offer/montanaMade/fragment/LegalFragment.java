package com.offer.montanaMade.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.offer.montanaMade.R;
import com.offer.montanaMade.adapter.HolidayThirdAdapter;
import com.offer.montanaMade.model.HolidayThirdModel;

import java.util.ArrayList;
import java.util.List;

public class LegalFragment extends Fragment {
    HolidayThirdAdapter holidayThirdAdapter;
    private List<HolidayThirdModel> holidayThirdList = new ArrayList<>();
    RecyclerView horizontal_recycler_view_third;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Legal");
        View mRootView = inflater.inflate(R.layout.fragment_legal, container, false);
        horizontal_recycler_view_third = (RecyclerView) mRootView.findViewById(R.id.horizontal_recycler_view_first);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
//
        horizontal_recycler_view_third.setLayoutManager(linearLayoutManager);
        holidayThirdAdapter = new HolidayThirdAdapter(getActivity(), holidayThirdList);
        horizontal_recycler_view_third.setAdapter(holidayThirdAdapter);
        holidayThirdAdapter.notifyDataSetChanged();

        return mRootView;
    }

}
