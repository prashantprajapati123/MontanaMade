package com.offer.montanaMade.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.offer.montanaMade.CheckNetworkState;
import com.offer.montanaMade.R;
import com.offer.montanaMade.WebService;
import com.offer.montanaMade.adapter.HolidaySellerAdapter;
import com.offer.montanaMade.adapter.ProfileItemAdapter;
import com.offer.montanaMade.model.HolidaySellerModel;
import com.offer.montanaMade.model.ProfileModel;
import com.offer.montanaMade.vollyrequest.Helper;
import com.offer.montanaMade.vollyrequest.JSONParser;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {
    ImageView ImagepProfileId;
    TextView ProfileNameId;
    AVLoadingIndicatorView avi;
    RecyclerView profile_recyclerViewId;
    ProfileItemAdapter profileItemAdapter;
    List<ProfileModel> profileModelList = new ArrayList<>();
    String UserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_profile, container, false);
        UserId = getArguments().getString("User");
        Log.e("UserId", "UserId" + UserId);
        profile_recyclerViewId = (RecyclerView) mRootView.findViewById(R.id.profile_recyclerViewId);
        ImagepProfileId = (ImageView) mRootView.findViewById(R.id.ImagepProfileId);
        ProfileNameId = (TextView) mRootView.findViewById(R.id.ProfileNameId);
        avi = (AVLoadingIndicatorView) mRootView.findViewById(R.id.avi);
        getUserProfile();
        return mRootView;
    }

    private void getUserProfile() {
        avi.setVisibility(View.VISIBLE);
        new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.User_SingleUser, 1, getprams(), new Helper() {
            @Override
            public void backResponse(String response) {
                try {
                    Log.e("responseprofile", "response" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String info = jsonObject.getString("info");
                    if (status == 1) {
                        avi.setVisibility(View.GONE);
                        JSONObject object = jsonObject.getJSONObject("data");
                        ProfileNameId.setText(object.getString("display_name"));
                        Log.e("display_name", "" + object.getString("display_name"));
                        Picasso.with(getActivity())
                                .load(object.getString("profile_image"))
                                .into(ImagepProfileId);
//                            JSONObject jsonObject1 = object.getJSONObject("shop_detail");
//                            ProfileModel profileModel = new ProfileModel();
//                            profileModel.setStore_city(jsonObject1.getString("store_city"));
//                            profileModel.setStore_name(jsonObject1.getString("store_name"));
//                            profileModel.setStore_country(jsonObject1.getString("store_country"));
//                            profileModelList.add(profileModel);
//
//
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//                        profileItemAdapter = new ProfileItemAdapter(getActivity(), profileModelList);
//                        profile_recyclerViewId.setLayoutManager(layoutManager);
//                        profile_recyclerViewId.setAdapter(profileItemAdapter);
//                        profileItemAdapter.notifyDataSetChanged();


                    } else {
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }


//                        }


                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Not Getting Response", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private Map<String, String> getprams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", "2");
        return hashMap;
    }


    public void showNetDisabledAlertToUser(final Context context) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialogBuilder.setMessage("Please Check Your Internet Connection")
                .setTitle("No Internet Connection")
                .setPositiveButton(" Ok ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });

        alertDialogBuilder.setNegativeButton(" Retry ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getUserProfile();

            }
        });
        android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}


