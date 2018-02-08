package com.offer.montanaMade.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.offer.montanaMade.CheckNetworkState;
import com.offer.montanaMade.R;
import com.offer.montanaMade.WebService;
import com.offer.montanaMade.adapter.HolidayFirstAdapter;
import com.offer.montanaMade.adapter.HolidaySecondAdapter;
import com.offer.montanaMade.adapter.HolidaySellerAdapter;
import com.offer.montanaMade.adapter.HolidayThirdAdapter;
import com.offer.montanaMade.adapter.MontanaMadeGiftsSecondAdapter;
import com.offer.montanaMade.model.HolidayFirstModel;
import com.offer.montanaMade.model.HolidaySecondModel;
import com.offer.montanaMade.model.HolidaySellerModel;
import com.offer.montanaMade.model.HolidayThirdModel;
import com.offer.montanaMade.model.MontanaMadeGiftsSecondModel;
import com.offer.montanaMade.vollyrequest.Helper;
import com.offer.montanaMade.vollyrequest.JSONParser;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayFragment extends Fragment {
    RecyclerView horizontal_recycler_view_first;
    HolidayFirstAdapter holidayFirstAdapter;
    private List<HolidayFirstModel> holidayfirstlist = new ArrayList<>();
    RecyclerView horizontal_recycle_SellerSpotlight;
    HolidaySellerAdapter holidaySellerAdapter;
    private List<HolidaySellerModel> holidaySellerlist = new ArrayList<>();
    HolidayThirdAdapter holidayThirdAdapter;
    private List<HolidayThirdModel> holidayThirdList = new ArrayList<>();
    RecyclerView horizontal_recycler_view_third;
    AVLoadingIndicatorView avi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_holiday, container, false);
        horizontal_recycler_view_first = (RecyclerView) mRootView.findViewById(R.id.horizontal_recycler_view_first);
        horizontal_recycle_SellerSpotlight = (RecyclerView) mRootView.findViewById(R.id.horizontal_recycle_SellerSpotlight);
        horizontal_recycler_view_third = (RecyclerView) mRootView.findViewById(R.id.horizontal_recycler_view_third);
        avi = (AVLoadingIndicatorView) mRootView.findViewById(R.id.avi);
        getCategoriTittle();
        getCategoriItem();
        getSellerLight();
        return mRootView;
    }

    private void getCategoriItem() {
        avi.setVisibility(View.VISIBLE);
        new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.Categori_Details, 1, getParam(), new Helper() {
            @Override
            public void backResponse(String response) {
                try {
                    Log.e("response", "response" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String info = jsonObject.getString("info");
                    if (status == 1) {
                        avi.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e("jsonArray.length();", "" + jsonArray.length());
                            JSONObject object = jsonArray.getJSONObject(i);
                            HolidayThirdModel holidaythird = new HolidayThirdModel();
                            holidaythird.setId((object.getString("id")));
                            holidaythird.setTitle(object.getString("title"));
                            holidaythird.setSlug(object.getString("slug"));
                            holidaythird.setPrice(object.getString("price"));
                            holidaythird.setProduct_image(object.getString("product_image"));
                            holidayThirdList.add(holidaythird);
                        }
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
                        horizontal_recycler_view_third.setLayoutManager(linearLayoutManager);
                        holidayThirdAdapter = new HolidayThirdAdapter(getActivity(), holidayThirdList);
                        horizontal_recycler_view_third.setAdapter(holidayThirdAdapter);
                        holidayThirdAdapter.notifyDataSetChanged();


                    } else {
                        Toast.makeText(getActivity(), "maintance problem", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Not Getting Response", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private Map<String, String> getParam() {
        HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("", "");
        return stringHashMap;
    }


    private void getCategoriTittle() {
        avi.setVisibility(View.VISIBLE);
        new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.Categori_Holiday, 1, null, new Helper() {
            @Override
            public void backResponse(String response) {
                try {
                    Log.e("response", "response" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String info = jsonObject.getString("info");
                    if (status == 1) {
                        avi.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e("jsonArray.length();", "" + jsonArray.length());
                            JSONObject object = jsonArray.getJSONObject(i);
                            HolidayFirstModel holidayfirst = new HolidayFirstModel();
                            holidayfirst.setTitle(object.getString("title"));

                            holidayfirstlist.add(holidayfirst);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        holidayFirstAdapter = new HolidayFirstAdapter(getActivity(), holidayfirstlist);
                        horizontal_recycler_view_first.setLayoutManager(layoutManager);
                        horizontal_recycler_view_first.setAdapter(holidayFirstAdapter);
                        holidayFirstAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getActivity(), "maintance problem", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Not Getting Response", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getSellerLight() {
        if (CheckNetworkState.isOnline(getActivity())) {
            avi.setVisibility(View.VISIBLE);
            new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.SellerSportLight, 0, null, new Helper() {
                @Override
                public void backResponse(String response) {
                    try {
                        Log.e("responseseller", "response" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        String info = jsonObject.getString("info");
                        if (status == 1) {
                            avi.setVisibility(View.GONE);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.e("jsonArray.length();", "" + jsonArray.length());
                                JSONObject object = jsonArray.getJSONObject(i);
                                HolidaySellerModel holidaySeller = new HolidaySellerModel();
                                holidaySeller.setID(object.getString("ID"));
                                holidaySeller.setDisplay_name(object.getString("display_name"));
                                holidaySeller.setProfile_image(object.getString("profile_image"));


                                holidaySellerlist.add(holidaySeller);
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            holidaySellerAdapter = new HolidaySellerAdapter(getActivity(), holidaySellerlist);
                            horizontal_recycle_SellerSpotlight.setLayoutManager(layoutManager);
                            horizontal_recycle_SellerSpotlight.setAdapter(holidaySellerAdapter);
                            holidaySellerAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), "error info..", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {

                        Toast.makeText(getActivity(), "Not Getting Response.. ", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        } else {
            showNetDisabledAlertToUser(getActivity());
        }
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
                getSellerLight();

            }
        });
        android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}


