package com.offer.montanaMade.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.offer.montanaMade.CheckNetworkState;
import com.offer.montanaMade.R;
import com.offer.montanaMade.WebService;
import com.offer.montanaMade.adapter.SearchCategoriAdapter;
import com.offer.montanaMade.model.ProductDetailsModel;
import com.offer.montanaMade.model.CategoriNameModel;
import com.offer.montanaMade.vollyrequest.Helper;
import com.offer.montanaMade.vollyrequest.JSONParser;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriNameFragment extends Fragment {
    RecyclerView recyclerViewSearch;
    SearchCategoriAdapter searchCategoriAdapter;
    private ArrayList<CategoriNameModel> categorilist = new ArrayList<>();
    private ArrayList<CategoriNameModel> subcategoriList = new ArrayList<>();
    AVLoadingIndicatorView avi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_categori_name, container, false);
        recyclerViewSearch = (RecyclerView) mRootView.findViewById(R.id.recyclerViewSearch);
        avi = (AVLoadingIndicatorView) mRootView.findViewById(R.id.avi);
        getCategoriName();
        categorilist.clear();
        return mRootView;
    }

    private void getCategoriName() {
        if (CheckNetworkState.isOnline(getActivity())) {
            avi.setVisibility(View.VISIBLE);
            new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.CategoriName, 0, null, new Helper() {
                @Override
                public void backResponse(String response) {
                    Log.e("response", "response" + response);
                    try {
                        JSONObject mainobject = new JSONObject(response);
                        int status = mainobject.getInt("status");
                        String info = mainobject.getString("info");
                        if (status == 1) {
                            avi.setVisibility(View.GONE);
                            JSONArray jsonArray = mainobject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                CategoriNameModel nameModel = new CategoriNameModel();
                                nameModel.setID(jsonObject.getString("ID"));
                                nameModel.setPost_title(jsonObject.getString("post_title"));
                                categorilist.add(nameModel);

                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            searchCategoriAdapter = new SearchCategoriAdapter(getActivity(), categorilist);
                            recyclerViewSearch.setLayoutManager(layoutManager);
                            recyclerViewSearch.setAdapter(searchCategoriAdapter);
                            searchCategoriAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getContext(), "error development site", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "not getting Responce", Toast.LENGTH_SHORT).show();
                        Log.e("Not Getting Responce", "");

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
                getCategoriName();

            }
        });
        android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}



