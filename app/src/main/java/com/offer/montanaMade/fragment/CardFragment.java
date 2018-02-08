package com.offer.montanaMade.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.offer.montanaMade.CheckNetworkState;
import com.offer.montanaMade.R;
import com.offer.montanaMade.WebService;
import com.offer.montanaMade.vollyrequest.Helper;
import com.offer.montanaMade.vollyrequest.JSONParser;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CardFragment extends Fragment {
    ImageView ImageProfileId, productImageId;
    TextView titleId, contactshopId, productNameId, ApplyCouponId, txtAmountId, txtItemTotalId;
    LinearLayout linerGiftOrderId;
    Spinner spinnerId;
    AVLoadingIndicatorView avi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_card, container, false);
        ImageProfileId = (ImageView) mRootView.findViewById(R.id.ImageProfileId);
        productImageId = (ImageView) mRootView.findViewById(R.id.productImageId);
        titleId = (TextView) mRootView.findViewById(R.id.titleId);
        contactshopId = (TextView) mRootView.findViewById(R.id.contactshopId);
        productNameId = (TextView) mRootView.findViewById(R.id.productNameId);
        ApplyCouponId = (TextView) mRootView.findViewById(R.id.ApplyCouponId);
        txtAmountId = (TextView) mRootView.findViewById(R.id.txtAmountId);
        linerGiftOrderId = (LinearLayout) mRootView.findViewById(R.id.linerGiftOrderId);
        txtItemTotalId = (TextView) mRootView.findViewById(R.id.txtItemTotalId);
        avi = (AVLoadingIndicatorView) mRootView.findViewById(R.id.avi);
        spinnerId = (Spinner) mRootView.findViewById(R.id.spinnerId);
        getAlert();
        getCardDetails();
        return mRootView;
    }

    private void getCardDetails() {
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
                        JSONObject object = jsonObject.getJSONObject("data");
//                        titleId.setText(object.getString("Display_name"));
//                        Picasso.with(getActivity())
//                                .load(object.getString("profile_image"))
//                                .into(productImageId);
                        JSONArray array = object.getJSONArray("cart");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject1 = array.getJSONObject(i);
                            String Key = jsonObject1.getString("key");
                            String ProductId = jsonObject1.getString("product_id");
                            int quantity = jsonObject1.getInt("quantity");
                            ArrayAdapter serviceAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, quantity);
                            spinnerId.setAdapter(serviceAdapter);
                            String line_subtotal = jsonObject1.getString("line_subtotal");
                            String line_subtotal_tax = jsonObject1.getString("line_subtotal_tax");
                            String line_total = jsonObject1.getString("line_total");
                            String line_tax = jsonObject1.getString("line_tax");
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("productDetails");
                            titleId.setText(jsonObject2.getString("title"));
                            txtAmountId.setText(jsonObject2.getString("price"));

                            Picasso.with(getActivity())
                                    .load(object.getString("product_image"))
                                    .into(ImageProfileId);
                        }


                    } else {
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }


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

    public void getAlert() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()
        ).create();
        alertDialog.setTitle("Alert Dialog");
        alertDialog.setMessage("Working On..");
//        alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}


