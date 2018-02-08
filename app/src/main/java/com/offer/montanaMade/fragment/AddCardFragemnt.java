package com.offer.montanaMade.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.offer.montanaMade.CheckNetworkState;
import com.offer.montanaMade.R;
import com.offer.montanaMade.SavedData;
import com.offer.montanaMade.WebService;
import com.offer.montanaMade.adapter.ProductDeatilsAdapter;
import com.offer.montanaMade.model.ProductDetailsModel;
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

public class AddCardFragemnt extends Fragment implements AdapterView.OnItemSelectedListener {
    ExpandableRelativeLayout expendablelayoutshop, expendablereview, expendableovervire, expendaleDetails;
    TextView expendableshop, review, itemoverview, itemdetails;
    ImageView ImageItemId, ProductImageId;
    TextView txtDescriptionId, txtAmountId, txtAskId, txtTittleId;
    Button btnAddCardId;
    Spinner spinerQuty;
    AVLoadingIndicatorView avi;
    String product;
    Button btnDoneAddCard;
    ProductDeatilsAdapter deatilsAdapter;
    private List<ProductDetailsModel> productlist = new ArrayList<>();
    String[] number = {"1", "2", "3", "4", "5", "6", "7"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.raw_add_card, container, false);
        String User_Id = SavedData.getUserId();
        Log.e("User_Id", "User_Id" + User_Id);
        product = getArguments().getString("productId");
        Log.e("product", "product" + product);
        txtTittleId = (TextView) mRootView.findViewById(R.id.txtTittleId);
        btnDoneAddCard = (Button) mRootView.findViewById(R.id.btnDoneAddCard);
        ProductImageId = (ImageView) mRootView.findViewById(R.id.ProductImageId);
        btnAddCardId = (Button) mRootView.findViewById(R.id.btnAddCardId);
        txtAskId = (TextView) mRootView.findViewById(R.id.txtAskId);
        txtAmountId = (TextView) mRootView.findViewById(R.id.txtAmountId);
        spinerQuty = (Spinner) mRootView.findViewById(R.id.spinerQuty);
        spinerQuty.setOnItemSelectedListener(this);
        ImageItemId = (ImageView) mRootView.findViewById(R.id.ImageItemId);
        expendableshop = (TextView) mRootView.findViewById(R.id.expendableshop);
        review = (TextView) mRootView.findViewById(R.id.review);
        avi = (AVLoadingIndicatorView) mRootView.findViewById(R.id.avi);
        itemoverview = (TextView) mRootView.findViewById(R.id.itemoverview);
        itemdetails = (TextView) mRootView.findViewById(R.id.itemdetails);
        txtDescriptionId = (TextView) mRootView.findViewById(R.id.txtDescriptionId);
        expendablereview = (ExpandableRelativeLayout) mRootView.findViewById(R.id.expendablereview);
        expendablelayoutshop = (ExpandableRelativeLayout) mRootView.findViewById(R.id.expendablelayoutshop);
        expendableovervire = (ExpandableRelativeLayout) mRootView.findViewById(R.id.expendableovervire);
        expendaleDetails = (ExpandableRelativeLayout) mRootView.findViewById(R.id.expendaleDetails);
//            expandableLayout1.toggle(); // toggle expand and collapse
        expendableshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expendablelayoutshop.toggle();

            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expendablereview.toggle(); //
            }
        });
        itemoverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expendableovervire.toggle(); //
            }
        });
        itemdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expendaleDetails.toggle(); //
            }
        });


        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, number);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinerQuty.setAdapter(aa);
        getProductList();
        btnAddCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCard();
            }
        });
        return mRootView;
    }

    private void addToCard() {
        avi.setVisibility(View.VISIBLE);
        new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.Add_To_Cart, 1, getParam(), new Helper() {
            @Override
            public void backResponse(String response) {
                Log.e("response", "response" + response);
                try {
                    JSONObject mainobject = new JSONObject(response);
                    int status = mainobject.getInt("status");
                    String info = mainobject.getString("info");
                    if (status == 1) {
                        avi.setVisibility(View.GONE);
                        btnAddCardId.setVisibility(View.GONE);
                        btnDoneAddCard.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Add Card Done", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                }
            }
        });
    }

    private Map<String, String> getParam() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userID", SavedData.getUserId());
        hashMap.put("productID", product);
        hashMap.put("qty", "1");
        return hashMap;
    }


    private void getProductList() {
        if (CheckNetworkState.isOnline(getActivity())) {
            avi.setVisibility(View.VISIBLE);
            new JSONParser(getActivity()).parseVollyStringRequest(WebService.URL.Single_Product, 1, getParams(), new Helper() {
                @Override
                public void backResponse(String response) {
                    try {
                        Log.e("responseaddcard", "response" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        String info = jsonObject.getString("info");
                        if (status == 1) {
                            avi.setVisibility(View.GONE);
                            JSONObject object = jsonObject.getJSONObject("data");
                            Picasso.with(getActivity())
                                    .load(object.getString("product_image"))
                                    .into(ProductImageId);
                            Picasso.with(getActivity())
                                    .load(object.getString("product_image"))
                                    .into(ImageItemId);
                            txtTittleId.setText(object.getString("title"));
                            txtDescriptionId.setText(object.getString("description"));
                            txtAmountId.setText(object.getString("price"));
                            review.setText(object.getString("review_count"));

                            JSONArray jsonArray = object.getJSONArray("related_products");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                ProductDetailsModel productDetailsModel1 = new ProductDetailsModel();
                                productDetailsModel1.setProduct_image(jsonObject1.getString("product_image"));
                                productDetailsModel1.setSlug(jsonObject1.getString("slug"));
                                productDetailsModel1.setTitle(jsonObject1.getString("title"));
                                productDetailsModel1.setPrice(jsonObject1.getString("price"));
                                productlist.add(productDetailsModel1);
                            }


                        } else {
                            Toast.makeText(getActivity(), "Error Info..", Toast.LENGTH_SHORT).show();
                        }


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
        hashMap.put("id", product);
        Log.e("single id", "" + getArguments().getString("bundleShop"));
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}