package com.offer.montanaMade.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.offer.montanaMade.R;
import com.offer.montanaMade.activity.BaseActivity;

public class ForYouFragment extends Fragment {
//    private GoogleApiClient mGoogleApiClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mRootView = inflater.inflate(R.layout.fragment_for_you, container, false);

        return mRootView;
    }

}
