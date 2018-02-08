package com.offer.montanaMade.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.offer.montanaMade.R;
import com.offer.montanaMade.UserAccount;
import com.offer.montanaMade.fragment.CategoriNameFragment;

import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText EtUserNameLoginId;
    EditText EtPasswordLoginId;
    ImageButton btnspeak;
    EditText searchbox;
    Button BtnLogin;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected int getContentResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnspeak = (ImageButton) findViewById(R.id.btnSpeak);
        searchbox = (EditText) findViewById(R.id.searchbox);
        btnspeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        CategoriNameFragment fr = new CategoriNameFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fr);

        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            // Handle the camera action
        } else if (id == R.id.nav_sign) {
            final Dialog dialog = new Dialog(SearchActivity.this);
            dialog.setContentView(R.layout.raw_sign);
            dialog.setTitle("Sign in to MontanaMade");
            dialog.show();
            Button BtnRegisterRawId = (Button) dialog.findViewById(R.id.BtnRegisterRawSignId);
            Button BtnLoginRawId = (Button) dialog.findViewById(R.id.BtnLoginRawSignId);
            BtnLoginRawId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final Dialog dialog1 = new Dialog(SearchActivity.this);
                    dialog1.setContentView(R.layout.raw_custom_login);
                    dialog1.setTitle("Sign In");
                    dialog1.show();
                    EtUserNameLoginId = (EditText) dialog1.findViewById(R.id.etUserNameRawCustomLoginId);
                    EtPasswordLoginId = (EditText) dialog1.findViewById(R.id.etPasswordRawCustomLoginId);
                    BtnLogin = (Button) dialog1.findViewById(R.id.btnSignInRawCustomLoginId);
                    BtnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (UserAccount.isEmpty(EtUserNameLoginId, EtPasswordLoginId)) {
                                if (UserAccount.isEmpty(EtUserNameLoginId)) {
//                                        login();
                                } else {
                                    UserAccount.EditTextPointer.setError("Enter UserName!");
                                    UserAccount.EditTextPointer.requestFocus();
                                }
                            } else {
                                UserAccount.EditTextPointer.setError("Fields Can't be Empty!");
                                UserAccount.EditTextPointer.requestFocus();
                            }
                        }


                    });
                }
            });
            BtnRegisterRawId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    final Dialog dialog2 = new Dialog(SearchActivity.this);
                    dialog2.setContentView(R.layout.raw_custom_registers);
                    dialog2.setTitle("Register");
                    dialog2.show();


                }
            });

        } else if (id == R.id.nav_setting) {
            final Dialog dialog = new Dialog(SearchActivity.this);
            // Include dialog.xml file
            dialog.setContentView(R.layout.raw_custom_registers);
            // Set dialog title
            dialog.setTitle("Custom Dialog");


            dialog.show();

            TextView declineButton = (TextView) dialog.findViewById(R.id.txtRegisterRawCustomLoginId);

            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    final Dialog dialog = new Dialog(SearchActivity.this);
                    // Include dialog.xml file
                    dialog.setContentView(R.layout.raw_custom_login);
                    // Set dialog title
                    dialog.setTitle("Custom Dialog");
                    dialog.show();

                }
            });

        } else if (id == R.id.nav_get_sell) {

        }
        return true;
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(this, "" + result.get(0), Toast.LENGTH_SHORT).show();
//                    searchbox.setTextAlignment(result.get(0));
                    searchbox.setText(result.get(0));
                }

                break;
            }

        }
    }

}

