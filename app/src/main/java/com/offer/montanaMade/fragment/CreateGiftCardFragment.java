package com.offer.montanaMade.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.offer.montanaMade.R;
import com.offer.montanaMade.activity.BaseActivity;
import com.offer.montanaMade.adapter.GiftImageAdapter;
import com.offer.montanaMade.adapter.HolidayFirstAdapter;
import com.offer.montanaMade.model.GiftImageModel;

import java.util.ArrayList;
import java.util.List;

public class CreateGiftCardFragment extends Fragment {
    RecyclerView giftRecyclerViewId;
    List<GiftImageModel> giftImageModelList = new ArrayList<>();
    GiftImageAdapter giftImageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_giftcard, container, false);
        giftRecyclerViewId = (RecyclerView) mRootView.findViewById(R.id.giftRecyclerViewId);
        giftImageModelList.add(new GiftImageModel(R.drawable.dct));
        giftImageModelList.add(new GiftImageModel(R.drawable.dot));
        giftImageModelList.add(new GiftImageModel(R.drawable.dct));
        giftImageModelList.add(new GiftImageModel(R.drawable.dot));
        giftImageModelList.add(new GiftImageModel(R.drawable.dct));
        giftImageModelList.add(new GiftImageModel(R.drawable.dot));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        giftImageAdapter = new GiftImageAdapter(getActivity(), giftImageModelList);
        giftRecyclerViewId.setLayoutManager(layoutManager);
        giftRecyclerViewId.setAdapter(giftImageAdapter);
        giftImageAdapter.notifyDataSetChanged();
        getAlert();
        return mRootView;
    }

    private void getAlert() {
        final AlertDialog alertDialog = new AlertDialog.Builder(
                getActivity()).create();
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