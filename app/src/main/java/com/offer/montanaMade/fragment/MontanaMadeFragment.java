package com.offer.montanaMade.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.offer.montanaMade.CheckNetworkState;
import com.offer.montanaMade.R;
import com.offer.montanaMade.WebService;
import com.offer.montanaMade.adapter.MontanaMadeGiftsSecondAdapter;
import com.offer.montanaMade.adapter.MontanaMadePicksFirstAdapter;
import com.offer.montanaMade.adapter.ProductDeatilsAdapter;
import com.offer.montanaMade.model.MontanaMadeGiftsSecondModel;
import com.offer.montanaMade.model.MontanaMadePicksFirstModel;
import com.offer.montanaMade.model.MontanaMadeShopThirdModel;
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


public class MontanaMadeFragment extends Fragment {
    RecyclerView editorPickMonatanaId;
    MontanaMadePicksFirstAdapter montanaMadePicksFirstAdapter;
    private List<MontanaMadePicksFirstModel> montanafirstlist = new ArrayList<>();
    RecyclerView giltsrecyclerviewid;
    MontanaMadeGiftsSecondAdapter montanaMadeGiftsSecondAdapter;
    private List<MontanaMadeGiftsSecondModel> montanaSecondlist = new ArrayList<>();
    AVLoadingIndicatorView avi;
    ImageView giftImageId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_montanamade, container, false);
        editorPickMonatanaId = (RecyclerView) mRootView.findViewById(R.id.picksrecuclerId);
        giltsrecyclerviewid = (RecyclerView) mRootView.findViewById(R.id.giltsrecyclerviewid);
        giftImageId = (ImageView) mRootView.findViewById(R.id.giftImageId);
        avi = (AVLoadingIndicatorView) mRootView.findViewById(R.id.avi);
        giftImageId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                CreateGiftCardFragment creategift = new CreateGiftCardFragment();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                fragmentTransaction.replace(R.id.frame, creategift);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        getEditorPicks();
        montanafirstlist.clear();
        getShopForGift();
        montanaSecondlist.clear();

        return mRootView;
    }

    private void getEditorPicks() {
        avi.setVisibility(View.VISIBLE);
        new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.ProductDetails, 0, getParams(), new Helper() {
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
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            MontanaMadePicksFirstModel picksFirstModel = new MontanaMadePicksFirstModel();
                            picksFirstModel.setId(jsonObject1.getString("id"));
                            picksFirstModel.setProduct_image(jsonObject1.getString("product_image"));
                            picksFirstModel.setTitle(jsonObject1.getString("title"));
                            montanafirstlist.add(picksFirstModel);

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        montanaMadePicksFirstAdapter = new MontanaMadePicksFirstAdapter(getActivity(), montanafirstlist);
                        editorPickMonatanaId.setLayoutManager(layoutManager);
                        editorPickMonatanaId.setAdapter(montanaMadePicksFirstAdapter);
                        montanaMadePicksFirstAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getActivity(), "Error Info..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Not Getting Response", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("term", "editors pick");
        return hashMap;
    }

    private void getShopForGift() {
        if (CheckNetworkState.isOnline(getActivity())) {
            avi.setVisibility(View.VISIBLE);
            new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.ProductDetails, 0, null, new Helper() {
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
                                MontanaMadeGiftsSecondModel montanaMadeGiftsSecondModel = new MontanaMadeGiftsSecondModel();
                                montanaMadeGiftsSecondModel.setId(object.getString("id"));
                                montanaMadeGiftsSecondModel.setTitle(object.getString("title"));
                                montanaMadeGiftsSecondModel.setPrice(object.getString("price"));
                                montanaMadeGiftsSecondModel.setProduct_image(object.getString("product_image"));
                                montanaSecondlist.add(montanaMadeGiftsSecondModel);
                            }
                            GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
                            montanaMadeGiftsSecondAdapter = new MontanaMadeGiftsSecondAdapter(getActivity(), montanaSecondlist);
                            giltsrecyclerviewid.setLayoutManager(layoutManager1);
                            giltsrecyclerviewid.setAdapter(montanaMadeGiftsSecondAdapter);
                            montanaMadeGiftsSecondAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), "error info..", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Not Getting Response", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        } else {
            showNetDisabledAlertToUser(getActivity());
        }
    }


    public void showNetDisabledAlertToUser(final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialogBuilder.setMessage("Please Check Your Internet Connection")
                .setTitle("No Internet Connection")
                .setPositiveButton(" Ok ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });

        alertDialogBuilder.setNegativeButton(" Retry ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getShopForGift();

            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}


