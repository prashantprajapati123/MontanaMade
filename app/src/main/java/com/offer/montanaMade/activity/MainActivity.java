//package com.offer.montanaMade.activity;
package com.offer.montanaMade.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.SharedPreferences;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appinvite.PreviewActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.juanpabloprado.countrypicker.Country;
import com.offer.montanaMade.R;
import com.offer.montanaMade.SavedData;
import com.offer.montanaMade.UserAccount;
import com.offer.montanaMade.Utils;
import com.offer.montanaMade.WebService;
import com.offer.montanaMade.fragment.CardFragment;
import com.offer.montanaMade.fragment.CategoriNameFragment;
import com.offer.montanaMade.fragment.HolidayFragment;
import com.offer.montanaMade.fragment.ForYouFragment;
import com.offer.montanaMade.fragment.LegalFragment;
import com.offer.montanaMade.fragment.MontanaMadeFragment;
import com.offer.montanaMade.fragment.LocalFragment;

import com.offer.montanaMade.fragment.ProductDetailsFragment;
import com.offer.montanaMade.fragment.ProfileFragment;
import com.offer.montanaMade.model.LoginModel;
import com.offer.montanaMade.vollyrequest.Helper;
import com.offer.montanaMade.vollyrequest.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;
    private ProfileTracker mProfileTracker;
    //google api client
    private GoogleApiClient mGoogleApiClient;
    ViewPagerAdapter pagerAdapter;
    ViewPager ViewPagerContentId;
    TabLayout TabsContentId;
    EditText EtUserNameLoginId;
    EditText EtPasswordLoginId;
    Button BtnLogin;
    EditText etFirstNameCustomRegisterId;
    EditText etLastNameCustomRegisterId;
    EditText etYouMailCustomRegisterId;
    EditText etUserNameCustomRegisterId;
    EditText etPasswordCustomRegisterId;
    EditText etConfirmPasswordCustomRegisterId;
    Button btnRegisterCustomRegisterId;
    TextView txtSignInCustomRegisterId;
    Button BtnRegisterRawId;
    Button BtnLoginRawId;
    TextView txtRegisterRawCustomLoginId;
    Dialog dialoglogin;
    Dialog dialogReg;
    Dialog dialog;
    Dialog dialogSettings;
    final Context context = this;
    String UserName;
    Button btnSignOutId;
    ImageView imgCrossDialogSettingId;
    NavigationView navigationView;
    LinearLayout linearLoginVisisableLayout;
    TextView loginUserNameId;
    LinearLayout currencySettingId;
    Dialog dialogcurrency;
    LinearLayout legalLinearLayoutId;
    TextView txtForgotRawCustomLoginId;
    Dialog dialogforget;
    EditText editEnterMailId;
    Button forget_submit;
    ImageView imgCrossDialogForgetId;
    String UserId;
    String date;
    Profile mProfile;
    AccessToken accessToken;
    Boolean cancelLogin = false;
    Button ImgFacebookPreviewId;
    Button BtnGooglePlusPreviewId;
    TextView TxtSignInUserNamePreviewId;
    TextView TxtUserPreViewId;
    private int RC_SIGN_IN = 100;
    public static int sIMAGESHARE_CODE = 1001;
    LoginButton login_button;
    SignInButton btn_sign_in_google;
    //facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FacebookSdk.sdkInitialize(this);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        ViewPagerContentId = (ViewPager) findViewById(R.id.ViewPagerContentId);
        setupViewPager(ViewPagerContentId);
        TabsContentId = (TabLayout) findViewById(R.id.TabsContentId);
        TabsContentId.setupWithViewPager(ViewPagerContentId);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        MenuItem nav_user = menu.findItem(R.id.nav_user);
        String username = SavedData.getUserTpye();
        nav_user.setTitle(username);
        Log.e("UserName", "UserName" + username);


        try {
            if (SavedData.getUserTpye().equals("")) {
                Toast.makeText(MainActivity.this, "login not sucessfull!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this, "login!", Toast.LENGTH_SHORT).show();
                navigationView.getMenu().findItem(R.id.nav_conversation).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_home).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_favorites).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_purchase).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_user).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_sign).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_setting).setVisible(true);

            }
        } catch (Exception r) {

        }
    }

    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void FacebookLogin() {
        mCallbackManager = CallbackManager.Factory.create();
        //register access token to check whether ic_user logged in before
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                accessToken = newToken;
            }
        };
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
            }
        };

        mAccessTokenTracker.startTracking();
        mProfileTracker.startTracking();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setVisibility(View.GONE);

        ImgFacebookPreviewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cancelLogin) {
                    LoginButton btn = new LoginButton(context);
                    btn.performClick();
                } else {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    alertDialogBuilder.setMessage("Are you sure you want to login")
                            .setTitle("Confirmation")
                            .setPositiveButton(" Yes ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    LoginButton btn = new LoginButton(context);
                                    btn.performClick();
                       /* Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(dialogIntent);*/
                                }
                            });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    android.app.AlertDialog alert = alertDialogBuilder.create();
                    alert.show();
                }

            }
        });
        // Callback registration
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                cancelLogin = false;
                // Toast.makeText(PreviewActivity.this, R.string.Success, Toast.LENGTH_SHORT).show();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject jsonObject,
                                    GraphResponse response) {
                                final LoginModel loginItem = new LoginModel();
                                // Application code
                                try {
                                    String socialId = jsonObject.has("id") ? jsonObject.getString("id") : "";
                                    String profilePicture = "https://graph.ic_facebook.com/" + socialId + "/picture?width=500&height=500";
                                    String firstName = jsonObject.has("first_name") ? jsonObject.getString("first_name") : "";
                                    String lastName = jsonObject.has("last_name") ? jsonObject.getString("last_name") : "";
                                    String email = jsonObject.has("email") ? jsonObject.getString("email") : "";
                                    String link = "";
                                    String name = firstName + " " + lastName;
                                    SavedData.writePrefs("name", name);
                                    SavedData.writePrefs("image", profilePicture);

                                    loginItem.setmFirstName(firstName);
                                    loginItem.setmLastName(lastName);
                                    loginItem.setId(socialId);
                                    loginItem.setmImage(profilePicture);
                                    loginItem.setMlink("");

                                    Bundle b = new Bundle();
                                    b.putString("Name", loginItem.getmFirstName());
                                    b.putString("Flag", "1");
                                    b.putString("Image", loginItem.getmImage());


                                    TxtUserPreViewId.setText("Credited to [" + loginItem.getmFirstName() + " " + loginItem.getmLastName() + "]");
                                    TxtSignInUserNamePreviewId.setText("by " + loginItem.getmFirstName() + " " + loginItem.getmLastName());
                                    updateUI(true);
                                    Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();

                                } catch (JSONException e) {
                                    updateUI(false);
                                    Utils.dismissDialog();
                                    e.printStackTrace();
                                }
                                Utils.dismissDialog();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,birthday,gender,cover,friends,picture.type(large),first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                cancelLogin = true;
                Toast.makeText(context, R.string.Login_Failed, Toast.LENGTH_LONG).show();

                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                cancelLogin = true;
//                GLog.i("Error:", "" + exception);
                Toast.makeText(context, R.string.Login_Failed, Toast.LENGTH_LONG).show();

                // App code
            }
        });

        //Set permission to use in this app
        List<String> permissionNeeds = Arrays.asList("user_friends", "email", "user_birthday");
        loginButton.setReadPermissions(permissionNeeds);

        mAccessTokenTracker.startTracking();


    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            //Calling a new function to handle signin
            handleSignInResult(result);
        } else //noinspection StatementWithEmptyBody
            if (requestCode == sIMAGESHARE_CODE) {
            } else if (requestCode == 64206) {
                try {
                    mCallbackManager.onActivityResult(requestCode, responseCode, intent);
                    //  facebookLogin.onActivityResult(requestCode, responseCode, intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            cancelLogin = false;
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Bundle b = new Bundle();
            b.putString("Name", acct.getDisplayName());
            b.putString("Flag", "0");
            b.putString("Image", String.valueOf(acct.getPhotoUrl()));
            SavedData.writePrefs("name", acct.getDisplayName());

            SavedData.writePrefs("image", String.valueOf(acct.getPhotoUrl()));

            TxtUserPreViewId.setText("Credited to [" + acct.getDisplayName() + "]");
            TxtSignInUserNamePreviewId.setText("by " + acct.getDisplayName());
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show();

            updateUI(true);


        } else {
            cancelLogin = true;
            updateUI(false);
            //If login fails
            Toast.makeText(this, R.string.Login_Failed, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mAccessTokenTracker.stopTracking();
//        mProfileTracker.stopTracking();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {

            TxtSignInUserNamePreviewId.setVisibility(View.VISIBLE);
//            LinearUserNotLogPreviewId.setVisibility(View.GONE);
//            LinearUserLoggedPreviewId.setVisibility(View.VISIBLE);

        } else {

            TxtSignInUserNamePreviewId.setVisibility(View.VISIBLE);
//            TxtSignInUserNamePreviewId.setText("by Anonymous");
//            LinearUserNotLogPreviewId.setVisibility(View.VISIBLE);
//            LinearUserLoggedPreviewId.setVisibility(View.GONE);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            GoogleSignInOptions mgso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            //Initializing google api client

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, mgso)
                    .build();


            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

            if (opr.isDone()) {
//                GLog.d(sTAG, "Got cached sign-in");
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            }
            if (Profile.getCurrentProfile() != null && AccessToken.getCurrentAccessToken() != null) {
                mProfile = Profile.getCurrentProfile();
                TxtUserPreViewId.setText("Credited to [" + mProfile.getName() + "]");
                TxtSignInUserNamePreviewId.setText("by " + mProfile.getName());
                updateUI(true);
//                GLog.d(sTAG, "Got fb sign-in");
                //You are logged
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        pagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        pagerAdapter.addFragment(new HolidayFragment(), "Holiday");
        pagerAdapter.addFragment(new ForYouFragment(), "For You");
        pagerAdapter.addFragment(new MontanaMadeFragment(), "MontanaMade Picks");
        pagerAdapter.addFragment(new LocalFragment(), "Local");
        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            CardFragment card = new CardFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frame, card);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }
        if (id == R.id.action_serach) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            // Handle the camera action
        } else if (id == R.id.nav_sign) {
            dialogSign();
//            login_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    FacebookLogin();
//                }
//            });
            btn_sign_in_google.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });
            BtnLoginRawId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogLogin();
                    txtForgotRawCustomLoginId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogForget();
                            imgCrossDialogForgetId.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogforget.dismiss();
                                }
                            });
                            forget_submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (UserAccount.isEmpty(editEnterMailId)) {
                                        ForgetPasword();
                                    } else {
                                        UserAccount.EditTextPointer.setError("Enter Vaild EMail!");
                                        UserAccount.EditTextPointer.requestFocus();
                                    }


                                }
                            });
                            dialogforget.show();
                        }
                    });
                    txtRegisterRawCustomLoginId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialoglogin.dismiss();
                            dialogRegister();
                            dialogReg.show();

                        }
                    });

                    BtnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (UserAccount.isEmpty(EtUserNameLoginId, EtPasswordLoginId)) {
                                if (UserAccount.isEmpty(EtUserNameLoginId)) {
                                    login();
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
                    dialoglogin.show();
                }
            });

            BtnRegisterRawId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogRegister();
                    txtSignInCustomRegisterId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogReg.dismiss();
                            dialogLogin();
                            dialoglogin.show();
                        }
                    });
                    btnRegisterCustomRegisterId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (UserAccount.isEmpty(etFirstNameCustomRegisterId, etLastNameCustomRegisterId, etYouMailCustomRegisterId,
                                    etUserNameCustomRegisterId, etPasswordCustomRegisterId, etConfirmPasswordCustomRegisterId)) {
                                if (UserAccount.isEmailValid(etYouMailCustomRegisterId)) {
                                    if (etPasswordCustomRegisterId.getText().toString().equals(etConfirmPasswordCustomRegisterId.getText().toString())) {
                                        regisrtation();
                                    } else {
                                        UserAccount.EditTextPointer.setError("Error Password not Match!");
                                        UserAccount.EditTextPointer.requestFocus();
                                    }
                                } else {
                                    UserAccount.EditTextPointer.setError("Email not Valid !");
                                    UserAccount.EditTextPointer.requestFocus();
                                }
                            } else {
                                UserAccount.EditTextPointer.setError("Fields Can't be Empty!");
                                UserAccount.EditTextPointer.requestFocus();
                            }

                        }
                    });
                    dialogReg.show();
                }
            });
            dialog.show();
        } else if (id == R.id.nav_conversation) {
            Toast.makeText(context, "Conversation", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        } else if (id == R.id.nav_favorites) {
            Toast.makeText(context, "Favorites", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_purchase) {
            Toast.makeText(context, "puchase", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_user) {
            ProfileFragment fr = new ProfileFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle b = new Bundle();
            b.putString("User", UserId);
            fr.setArguments(b);
            fragmentManager.beginTransaction().replace(R.id.frame, fr).addToBackStack(null).commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_setting) {

            if (SavedData.getUserTpye() == null) {
                dialogSetting();
                imgCrossDialogSettingId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogSettings.dismiss();
                    }
                });
                currencySettingId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

                legalLinearLayoutId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LegalFragment fr = new LegalFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fr);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                });

            } else {
                dialogSetting();
                linearLoginVisisableLayout.setVisibility(View.VISIBLE);
                loginUserNameId.setText(SavedData.getUserTpye());
                btnSignOutId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SavedData.logout();
                        dialogSettings.dismiss();
                        Toast.makeText(context, "Logout SucessFully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);


                    }
                });

            }

            dialogSettings.show();


        } else if (id == R.id.nav_get_sell) {


        }
        return true;
    }

    private void ForgetPasword() {
        new JSONParser(this).parseVollyStringRequest(WebService.URL.Forgot_Password, 1, getParames(), new Helper() {
            @Override
            public void backResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    Log.e("response", "response" + response);
                    int status = mainObject.getInt("status");
                    String info = mainObject.getString("info");
                    if (status == 1) {
                        Toast.makeText(context, "Send Request On Your Email", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(MainActivity.this, MainActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "UserName already exist!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Response", "Not getting response");
                }
            }
        });
    }

    private Map<String, String> getParames() {
        HashMap<String, String> postParameters = new HashMap<>();
        postParameters.put("username", editEnterMailId.getText().toString());

        return postParameters;
    }


    private void regisrtation() {
        new JSONParser(this).parseVollyStringRequest(WebService.URL.Register_Url, 1, getParams(), new Helper() {
            @Override
            public void backResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    Log.e("response", "response" + response);
                    int status = mainObject.getInt("status");
                    String info = mainObject.getString("info");
                    if (status == 1) {
                        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(MainActivity.this, MainActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "UserName already exist!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Response", "Not getting response");
                }
            }
        });
    }

    private Map<String, String> getParams() {
        HashMap<String, String> postParameters = new HashMap<>();
        postParameters.put("username", etUserNameCustomRegisterId.getText().toString());
        postParameters.put("password", etPasswordCustomRegisterId.getText().toString());
        postParameters.put("email", etYouMailCustomRegisterId.getText().toString());
        postParameters.put("first_name", etFirstNameCustomRegisterId.getText().toString());
        postParameters.put("last_name", etLastNameCustomRegisterId.getText().toString());
        postParameters.put("role", "vendor");
        return postParameters;
    }


    private void login() {
        new JSONParser(this).parseVollyStringRequest(WebService.URL.Login_Url, 1, getParms(), new Helper() {
            @Override
            public void backResponse(String response) {
                try {
                    Log.e("response", "response" + response);
                    JSONObject mainObject = new JSONObject(response);
                    int status = mainObject.getInt("status");
                    String info = mainObject.getString("info");
                    if (status == 1) {
                        JSONObject jsonObject = mainObject.getJSONObject("data");
                        UserId = jsonObject.getString("ID");
                        Log.e("Id", "Id" + UserId);
                        UserName = jsonObject.getString("user_login");
                        Log.e("UserNamelogin", "UserName" + UserName);

                        SavedData.saveUserType(UserName);
                        SavedData.saveUserId(UserId);
                        Toast.makeText(MainActivity.this, "WelCome User", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "UserName already exist!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Response", "Not getting response");
                }
            }
        });
    }

    private Map<String, String> getParms() {

        HashMap<String, String> params = new HashMap<>();

        params.put("username", EtUserNameLoginId.getText().toString());
        params.put("password", EtPasswordLoginId.getText().toString());
        params.put("regID", "das5d65s");


        return params;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HolidayFragment();
                case 1:
                    return new ForYouFragment();
                case 2:
                    return new MontanaMadeFragment();
                case 3:
                    return new LocalFragment();
                default:
            }
            return null;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void dialogLogin() {
        dialoglogin = new Dialog(context);
        dialoglogin.setContentView(R.layout.raw_custom_login);
        dialoglogin.setTitle("Sign In");
        EtUserNameLoginId = (EditText) dialoglogin.findViewById(R.id.etUserNameRawCustomLoginId);
        EtPasswordLoginId = (EditText) dialoglogin.findViewById(R.id.etPasswordRawCustomLoginId);
        BtnLogin = (Button) dialoglogin.findViewById(R.id.btnSignInRawCustomLoginId);
        txtForgotRawCustomLoginId = (TextView) dialoglogin.findViewById(R.id.txtForgotRawCustomLoginId);
        txtRegisterRawCustomLoginId = (TextView) dialoglogin.findViewById(R.id.txtRegisterRawCustomLoginId);

    }

    public void dialogSetting() {
        dialogSettings = new Dialog(context);
        dialogSettings.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSettings.setContentView(R.layout.dialog_setting);
        imgCrossDialogSettingId = (ImageView) dialogSettings.findViewById(R.id.imgCrossDialogSettingId);
        btnSignOutId = (Button) dialogSettings.findViewById(R.id.btnSignOutId);
        linearLoginVisisableLayout = (LinearLayout) dialogSettings.findViewById(R.id.linearLoginVisisableLayout);
        loginUserNameId = (TextView) dialogSettings.findViewById(R.id.loginUserNameId);
        currencySettingId = (LinearLayout) dialogSettings.findViewById(R.id.currencySettingId);
        legalLinearLayoutId = (LinearLayout) dialogSettings.findViewById(R.id.legalLinearLayoutId);


    }

    public void dialogBoxCurrency() {
        dialogcurrency = new Dialog(context);
        dialogcurrency.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogcurrency.setContentView(R.layout.dialog_currency);


    }


    private void dialogRegister() {
        dialogReg = new Dialog(context);
        dialogReg.setContentView(R.layout.raw_custom_registers);
        dialogReg.setTitle("Register");
        etFirstNameCustomRegisterId = (EditText) dialogReg.findViewById(R.id.etFirstNameCustomRegisterId);
        etLastNameCustomRegisterId = (EditText) dialogReg.findViewById(R.id.etLastNameCustomRegisterId);
        etYouMailCustomRegisterId = (EditText) dialogReg.findViewById(R.id.etYouMailCustomRegisterId);
        etUserNameCustomRegisterId = (EditText) dialogReg.findViewById(R.id.etUserNameCustomRegisterId);
        etPasswordCustomRegisterId = (EditText) dialogReg.findViewById(R.id.etPasswordCustomRegisterId);
        etConfirmPasswordCustomRegisterId = (EditText) dialogReg.findViewById(R.id.etConfirmPasswordCustomRegisterId);
        btnRegisterCustomRegisterId = (Button) dialogReg.findViewById(R.id.btnRegisterCustomRegisterId);
        txtSignInCustomRegisterId = (TextView) dialogReg.findViewById(R.id.txtSignInCustomRegisterId);

    }

    public void dialogSign() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.raw_sign);
        dialog.setTitle("Sign in to MontanaMade");
        btn_sign_in_google = (SignInButton) dialog.findViewById(R.id.btn_sign_in_google);
        ImgFacebookPreviewId = (Button) dialog.findViewById(R.id.ImgFacebookPreviewId);
        BtnGooglePlusPreviewId = (Button) dialog.findViewById(R.id.BtnGooglePlusPreviewId);
        BtnRegisterRawId = (Button) dialog.findViewById(R.id.BtnRegisterRawSignId);
        BtnLoginRawId = (Button) dialog.findViewById(R.id.BtnLoginRawSignId);

    }

    public void dialogForget() {
        dialogforget = new Dialog(context);
        dialogforget.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogforget.setContentView(R.layout.dialog_forgot);
        editEnterMailId = (EditText) dialogforget.findViewById(R.id.editEnterMailId);
        imgCrossDialogForgetId = (ImageView) dialogforget.findViewById(R.id.imgCrossDialogForgetId);
        forget_submit = (Button) dialogforget.findViewById(R.id.forget_submit);


    }
}


