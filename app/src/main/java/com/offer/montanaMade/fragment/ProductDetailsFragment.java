package com.offer.montanaMade.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.offer.montanaMade.adapter.ProductDeatilsAdapter;
import com.offer.montanaMade.adapter.SearchCategoriAdapter;
import com.offer.montanaMade.model.CategoriNameModel;
import com.offer.montanaMade.model.ProductDetailsModel;
import com.offer.montanaMade.vollyrequest.Helper;
import com.offer.montanaMade.vollyrequest.JSONParser;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDetailsFragment extends Fragment {
    ProductDeatilsAdapter deatilsAdapter;
    private List<ProductDetailsModel> productlist = new ArrayList<>();
    RecyclerView recyclerproductDetalisId;
    AVLoadingIndicatorView avi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mRootView = inflater.inflate(R.layout.fragment_subcat, container, false);

        String b = getArguments().getString("productId");
        Log.e("sohil ", "jain" + b);
        recyclerproductDetalisId = (RecyclerView) mRootView.findViewById(R.id.recyclerproductDetalisId);
        avi = (AVLoadingIndicatorView) mRootView.findViewById(R.id.avi);
        getProductList();
        productlist.clear();

        return mRootView;
    }

    private void getProductList() {
        if (CheckNetworkState.isOnline(getActivity())) {
            avi.setVisibility(View.VISIBLE);
            new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.Single_Product, 1, getParams(), new Helper() {
                @Override
                public void backResponse(String response) {
                    try {
                        Log.e("response", "response" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        String info = jsonObject.getString("info");
                        if (status == 1) {
                            avi.setVisibility(View.GONE);
                            JSONObject object = jsonObject.getJSONObject("data");
                            ProductDetailsModel productDetailsModel = new ProductDetailsModel();
                            JSONArray jsonArray = object.getJSONArray("related_products");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                ProductDetailsModel productDetailsModel1 = new ProductDetailsModel();
                                productDetailsModel1.setId(jsonObject1.getString("id"));
                                productDetailsModel1.setProduct_image(jsonObject1.getString("product_image"));
                                productDetailsModel1.setSlug(jsonObject1.getString("slug"));
                                productDetailsModel1.setTitle(jsonObject1.getString("title"));
                                productDetailsModel1.setPrice(jsonObject1.getString("price"));
                                productlist.add(productDetailsModel1);
                            }


                        } else {
                            Toast.makeText(getActivity(), "error info..", Toast.LENGTH_SHORT).show();
                        }
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
                        deatilsAdapter = new ProductDeatilsAdapter(getActivity(), productlist);
                        recyclerproductDetalisId.setLayoutManager(linearLayoutManager);
                        recyclerproductDetalisId.setAdapter(deatilsAdapter);
                        deatilsAdapter.notifyDataSetChanged();


                    } catch (JSONException e1) {
                        Toast.makeText(getActivity(), "Not Getting Response", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        } else {
            showNetDisabledAlertToUser(getActivity());
        }
    }

    private Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", getArguments().getString("productId"));
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
                getProductList();

            }
        });
        android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}