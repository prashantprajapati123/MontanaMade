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
import android.widget.Toast;

import com.offer.montanaMade.CheckNetworkState;
import com.offer.montanaMade.R;
import com.offer.montanaMade.WebService;
import com.offer.montanaMade.adapter.SearchCategoriAdapter;
import com.offer.montanaMade.adapter.SubCategoriNameAdapter;
import com.offer.montanaMade.model.CategoriNameModel;
import com.offer.montanaMade.model.ProductDetailsModel;
import com.offer.montanaMade.vollyrequest.Helper;
import com.offer.montanaMade.vollyrequest.JSONParser;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubCategoriNameFragment extends Fragment {
    RecyclerView recyclerViewSearch;
    SubCategoriNameAdapter subCategoriNameAdapter;
    private ArrayList<CategoriNameModel> subCategoriName = new ArrayList<>();
    AVLoadingIndicatorView avi;
    String CategoriId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_sub_categori_name, container, false);
        CategoriId = getArguments().getString("productId");
        recyclerViewSearch = (RecyclerView) mRootView.findViewById(R.id.recyclerViewSearch);
        avi = (AVLoadingIndicatorView) mRootView.findViewById(R.id.avi);
        getCategoriName();
        subCategoriName.clear();
        return mRootView;
    }

    private void getCategoriName() {
        if (CheckNetworkState.isOnline(getActivity())) {
            avi.setVisibility(View.VISIBLE);
            new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.CategoriName, 1, getParams(), new Helper() {
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

                                JSONArray jsonArray1 = jsonObject.getJSONArray("sub_categories");
                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                    CategoriNameModel categoriNameModel = new CategoriNameModel();
                                    categoriNameModel.setID(jsonObject1.getString("ID"));
                                    categoriNameModel.setTitle(jsonObject1.getString("title"));
                                    subCategoriName.add(categoriNameModel);
                                }

                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            subCategoriNameAdapter = new SubCategoriNameAdapter(getActivity(), subCategoriName);
                            recyclerViewSearch.setLayoutManager(layoutManager);
                            recyclerViewSearch.setAdapter(subCategoriNameAdapter);
                            subCategoriNameAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getContext(), "error..", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Not Getting Response", Toast.LENGTH_SHORT).show();
                        Log.e("Not Getting Responce", "");

                    }

                }
            });
        } else {
            showNetDisabledAlertToUser(getActivity());
        }
    }

    private Map<String, String> getParams() {
        HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("id", CategoriId);
        return stringHashMap;
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
