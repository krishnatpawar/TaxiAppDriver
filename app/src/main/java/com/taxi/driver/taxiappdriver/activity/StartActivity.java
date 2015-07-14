package com.taxi.driver.taxiappdriver.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.taxi.driver.taxiappdriver.R;
import com.taxi.driver.taxiappdriver.application.AppConfig;
import com.taxi.driver.taxiappdriver.utils.AppUtils;
import com.taxi.driver.taxiappdriver.utils.CustomLog;
import com.taxi.driver.taxiappdriver.utils.Preferences;
import com.taxi.driver.taxiappdriver.utils.Webservices;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by koti on 7/11/2015.
 */
public class StartActivity extends AbstractTaxiDriverActivity implements View.OnClickListener {



    public final String TAG_RESPONSEINFO="responseinfo";

    public final String TAG_USERID="userid";

    private Button signUpBtn;
    private Button loginBtn;
    private RelativeLayout loginLlyt;
    private RelativeLayout signupLlt;
    //for login
    private EditText loginEmailEdt;
    private EditText loginPwdEdt;
    private Button loginAppBtn;
    //for nregister
    private EditText registPwdEdt;
    private EditText fullNameEdt;
    private EditText phoneEdt;
    private EditText carNoEdt;
    private EditText mobileNoEdt;
    private EditText carType;
    private EditText registerEmailEdt;
    private TextView forgetPwdTv;
    private Button registerBt;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        AppConfig.setCurentContext(this);
        init();
        signUpBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    private void init() {
        signUpBtn = (Button) findViewById(R.id.signup_btn);
        loginBtn = (Button) findViewById(R.id.login_btn);
        forgetPwdTv = (TextView)findViewById(R.id.app_forgot);
        forgetPwdTv.setOnClickListener(this);
        loginLlyt = (RelativeLayout) findViewById(R.id.app_login_layout);
        signupLlt = (RelativeLayout) findViewById(R.id.app_register_layout);
        loginEmailEdt = (EditText) findViewById(R.id.login_email);
        loginPwdEdt = (EditText) findViewById(R.id.login_pwd);
        loginAppBtn = (Button) findViewById(R.id.app_login_btn);
        loginAppBtn.setOnClickListener(this);

        registPwdEdt = (EditText) findViewById(R.id.register_pwd);
        fullNameEdt = (EditText) findViewById(R.id.register_fname);
        phoneEdt = (EditText) findViewById(R.id.register_phone);
        carNoEdt = (EditText) findViewById(R.id.register_carno);
        mobileNoEdt = (EditText) findViewById(R.id.register_mobileno);
        carType = (EditText) findViewById(R.id.register_cartype);
        registerEmailEdt = (EditText) findViewById(R.id.register_email);
        registerBt = (Button) findViewById(R.id.app_register_btn);
        registerBt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_btn:
                signupLlt.setVisibility(View.VISIBLE);
                loginLlyt.setVisibility(View.GONE);
                signUpBtn.setBackgroundResource(R.drawable.btn_bg_hover);
                loginBtn.setBackgroundResource(R.drawable.btn_bg);
                break;
            case R.id.login_btn:
                signupLlt.setVisibility(View.GONE);
                loginLlyt.setVisibility(View.VISIBLE);
                signUpBtn.setBackgroundResource(R.drawable.btn_bg);
                loginBtn.setBackgroundResource(R.drawable.btn_bg_hover);
                break;
            case R.id.app_register_btn:
                registerUser();
                break;
            case R.id.app_login_btn:
                driverLogin();
                break;

            case R.id.app_forgot: forgetPwd();
                break;
            default:
                break;
        }
    }

    private void forgetPwd(){
        String loginEmailStr = loginEmailEdt.getText().toString().trim();

        if (!AppUtils.chkStatus(StartActivity.this)) {
            showMessage("check internet connection");
            return;
        }

        if (loginEmailStr.isEmpty() || !AppUtils.isValidEmail(loginEmailStr)) {
            showMessage("Enter valid mail");
            return;
        }
        serviceToForget(loginEmailStr);
    }

    private void serviceToForget(String loginEmailStr){


        String url = Webservices.BASE_URL + Webservices.FORGET_URL
                + "email=" + loginEmailStr;

        final ProgressDialog pd = new ProgressDialog(StartActivity.this);
        pd.setTitle("Requesting...");
        pd.setMessage("Forget to InstantTaxi..Wait");
        pd.setCancelable(false);
        pd.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObj) {
                        pd.dismiss();
                        forgotResponse(jsonObj);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                pd.dismiss();
                CustomLog.v("", "Volley Error: " + arg0);
                showMessage("Volley error: " + arg0);
                AppConfig.getInstance().cancelPendingRequests("driver_forget");
            }

        });

        AppConfig.getInstance().addToRequestque(jsonObjectRequest, "driver_forget");
    }


    private void forgotResponse(JSONObject jsonObject) {
        CustomLog.v("TAXI_FORGET", "forget: " + jsonObject);
        try {
            String responseInfo = jsonObject.getString(TAG_RESPONSEINFO);
            if (responseInfo.isEmpty()) {
                return;
            }
            if (responseInfo.equalsIgnoreCase("success")) {
                showMessage("Password sent your mail, please check");
            }else if(responseInfo.equalsIgnoreCase("failure")){
                showMessage("Incorrect email please try again");
            }else{
                showMessage("Something went wrong please try again");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void driverLogin(){
        String loginPwdStr = loginPwdEdt.getText().toString().trim();
        String loginEmailStr = loginEmailEdt.getText().toString().trim();

        if (!AppUtils.chkStatus(StartActivity.this)) {
            showMessage("check internet connection");
            return;
        }

        if (loginEmailStr.isEmpty() || !AppUtils.isValidEmail(loginEmailStr)) {
            showMessage("Enter valid mail");
            return;
        }

        if (loginPwdStr.isEmpty()) {
            showMessage("Enter Password");
            return;
        }

        serviceToLoginDriver(loginEmailStr, loginPwdStr);

    }

    //login
    private void serviceToLoginDriver(String loginEmailStr,String loginpwStrin){

        /*http://wowads.asia/taxidriver/login.php?email=ashishssss@dkslakds.com&password=sdhjlashdh*/
        String url = Webservices.BASE_URL + Webservices.LOGIN_URL
                + "?email=" + loginEmailStr + "&password=" + loginpwStrin;

        final ProgressDialog pd = new ProgressDialog(StartActivity.this);
        pd.setTitle("Requesting...");
        pd.setMessage("Login to InstantTaxi..Wait");
        pd.setCancelable(false);
        pd.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObj) {
                        pd.dismiss();
                        loginResponse(jsonObj);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                pd.dismiss();
//                CustomLog.v("", "Volley Error: " + arg0);
                showMessage("Volley error: " + arg0);
                AppConfig.getInstance().cancelPendingRequests("driver_login");
            }

        });

        AppConfig.getInstance().addToRequestque(jsonObjectRequest, "driver_login");
    }

    private void loginResponse(JSONObject jsonObject) {
        CustomLog.v("TAXI_LOGIN", "login: " + jsonObject);
        try {
            String responseInfo = jsonObject.getString(TAG_RESPONSEINFO);

            if (responseInfo.isEmpty()) {
                return;
            }
            if (responseInfo.equalsIgnoreCase("success")) {
                startScreen(MainActivity.class);
                String userId = jsonObject.getString(TAG_USERID);
                Preferences.setUserId(getApplicationContext(), userId);
                finish();
            }else if(responseInfo.equalsIgnoreCase("Incorrect email or password please try again")){
               showMessage("Incorrect email or password please try again");
            }else{
                showMessage("Something went wrong please try again");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //register
    private void registerUser() {

        String registPwdStr = registPwdEdt.getText().toString().trim();
        String registFullNameStr = fullNameEdt.getText().toString().trim();
        String registPhoneStr = phoneEdt.getText().toString().trim();
        String registcarNoStr = carNoEdt.getText().toString().trim();
        String registMobileStr = mobileNoEdt.getText().toString().trim();
        String registCartypeStr = carType.getText().toString().trim();
        String registEmailStr = registerEmailEdt.getText().toString().trim();

        if (!AppUtils.chkStatus(StartActivity.this)) {
            showMessage("check internet connection");
            return;
        }

        if (registPwdStr.isEmpty()) {
            showMessage("Enter Password");
            return;
        }
        if (registFullNameStr.isEmpty()) {
            showMessage("Enter Full name");
            return;
        }

        if (registPhoneStr.isEmpty()) {
            showMessage("Enter your Phone number");
            return;
        }
        if (registcarNoStr.isEmpty()) {
            showMessage("Enter Car number");
            return;
        }

        if (registMobileStr.isEmpty()) {
            showMessage("Enter Mobile number");
            return;
        }
        if (registMobileStr.length() != 10) {
            showMessage("mobile number must have 10 digits");
            return;
        }

        if (registCartypeStr.isEmpty()) {
            showMessage("Enter Car type");
            return;
        }

        if (registEmailStr.isEmpty() || !AppUtils.isValidEmail(registEmailStr)) {
            showMessage("Enter valid mail");
            return;
        }

        serviceToRegisterUser(registPwdStr,registFullNameStr,registPhoneStr,registcarNoStr,registMobileStr,registCartypeStr,registEmailStr);

    }

    private void serviceToRegisterUser(String registPwdStr, String registFullNameStr, String registPhoneStr, String registcarNoStr,String registMobileStr,String registCartypeStr,String registEmailStr) {

        /*http://wowads.asia/taxidriver/register.php?newemail=ashishs@dkslakds.com
        &newfullname=dsfsdsaf&newpassword=sdhjlashdh&newphonenumber=324234324*/
        String url = Webservices.BASE_URL + Webservices.REGISTER_URL
                + "?newemail=" + registEmailStr + "&newfullname=" + registFullNameStr + "&newpassword=" + registPwdStr
                + "&newphonenumber=" + registPhoneStr;

        final ProgressDialog pd = new ProgressDialog(StartActivity.this);
        pd.setTitle("Requesting...");
        pd.setMessage("Register to InstantTaxi..Wait");
        pd.setCancelable(false);
        pd.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObj) {
                        pd.dismiss();
                        registerResponse(jsonObj);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                pd.dismiss();
                CustomLog.v("", "Volley Error: " + arg0);
                showMessage("Volley error: " + arg0);
                AppConfig.getInstance().cancelPendingRequests("driver_register");
            }

        });

        AppConfig.getInstance().addToRequestque(jsonObjectRequest, "driver_register");
    }

    private void registerResponse(JSONObject jsonObject) {
        CustomLog.v("TAXI_REGISTER", "register: " + jsonObject);
        try {
            String responseInfo = jsonObject.getString(TAG_RESPONSEINFO);

            if (responseInfo.isEmpty()) {
                return;
            }
            if (responseInfo.equalsIgnoreCase("Success")) {
                startScreen(MainActivity.class);
                String userId = jsonObject.getString("id");
                Preferences.setUserId(getApplicationContext(), userId);
                finish();
            }else  if (responseInfo.equalsIgnoreCase("Email Exists")) {
               showMessage("Driver Email already existed");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfig.setCurentContext(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppConfig.setCurentContext(this);
    }
}


