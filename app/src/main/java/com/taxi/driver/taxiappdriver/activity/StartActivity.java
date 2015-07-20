package com.taxi.driver.taxiappdriver.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.taxi.driver.taxiappdriver.R;
import com.taxi.driver.taxiappdriver.application.AppConfig;
import com.taxi.driver.taxiappdriver.utils.AppUtils;
import com.taxi.driver.taxiappdriver.utils.Constants;
import com.taxi.driver.taxiappdriver.utils.CustomLog;
import com.taxi.driver.taxiappdriver.utils.Preferences;
import com.taxi.driver.taxiappdriver.utils.Webservices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by koti on 7/11/2015.
 */
public class StartActivity extends AbstractTaxiDriverActivity implements View.OnClickListener {

    public final String TAG_RESPONSEINFO ="responseinfo";
    public final String TAG_RESPONSE ="response";

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
    private ImageView driverPicImg;
    private JsonObjectRequest jsonObjectRequest;
    //profile pic values
    private String selectedImagePath="";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;
    private int desiredImageWidth = 300, desiredImageHeight = 300;
    private Bitmap rotatedBitmap;
    private boolean isFileImageUploaded;
    private ProgressDialog dialog;

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
        driverPicImg = (ImageView)findViewById(R.id.register_profile_pic);
        driverPicImg.setOnClickListener(this);


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

            case R.id.register_profile_pic:
                clickProfilePic();
                break;
            default:
                break;
        }
    }

    /**
     * when profile imageview is clicked.
     */
    public void clickProfilePic() {
        final CharSequence[] options = { "Capture From Camera","Choose from Gallery", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setTitle("Upload Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Capture From Camera")) {
                    if (isSDCARDMounted()) {
                        Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						/*File tempfile = new File(Environment.getExternalStorageDirectory()+Utils.PROFILEIMAGEPATH, "sportsprofilepic.jpg");
						Uri uritemp = Uri.fromFile(tempfile);*/
                        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,getTempFile());
                        photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                        photoPickerIntent.putExtra("return-data", true);
                        startActivityForResult(photoPickerIntent,REQUEST_IMAGE_CAPTURE);
                    } else {
                        showMessage("You need to insert SD Card");
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, REQUEST_IMAGE_GALLERY);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * checking whether sd card exists or not
     * @return
     */
    private boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * creating temp file.
     * @return
     */
    private Uri getTempFile() {
        File root = new File(Environment.getExternalStorageDirectory()+ Constants.PROFILEIMAGEPATH,"profile_pic");
        if (!root.exists()) {
            root.mkdirs();
        }
        String filename = "" + System.currentTimeMillis();
        File file = new File(root, filename + ".jpeg");
        Uri muri = Uri.fromFile(file);
        selectedImagePath = muri.getPath();
        Log.v("Pic paht from Camera", selectedImagePath);
        return muri;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            //			CalenderModel model = new CalenderModel();
            if (REQUEST_IMAGE_GALLERY == requestCode) {
                Uri selectedImage = data.getData();
                Log.v("", "selected Image: " + selectedImage);
                selectedImagePath = getPath(selectedImage);
                rotatedBitmap = null;
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),selectedImage);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(0);
                    bitmap = Bitmap.createScaledBitmap(bitmap,desiredImageWidth, desiredImageHeight, true);
                    rotatedBitmap = Bitmap
                            .createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                    bitmap.getHeight(), matrix, true);
                    bitmap.recycle();
                    Log.v("", "Bitmap After Compression: " + rotatedBitmap.getWidth() + " : " + rotatedBitmap.getHeight() +"\n "+"Before " + selectedImagePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.registration_thumb);
                }

                Log.d("PATH", "PATH when choose from gallery: " + selectedImagePath);
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                cursor.close();
                //				savebitmap(selectedImage.getPath(), rotatedBitmap);
                //				setBitmap(rotatedBitmap);
                //				img_view.setImageBitmap(model.getBitmap());
                driverPicImg.setImageBitmap(rotatedBitmap);
                isFileImageUploaded = true;
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                int rotate = 0;
                Log.d(": ", "PATH when choosed from camera" + selectedImagePath);
                int index = selectedImagePath.lastIndexOf("/");
                String filename = selectedImagePath.substring(index + 1,selectedImagePath.length());
                String filepath = Environment.getExternalStorageDirectory().getAbsolutePath()+Constants.PROFILEIMAGEPATH + "/profile_pic/";
                File file = new File(filepath, filename);
                ExifInterface exif;
                try {
                    exif = new ExifInterface(file.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //				createTempFile(selectedImagePath);
                FileInputStream fs = null;
                try {
                    fs = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    showMessage("File not found");
                }
                BitmapFactory.Options bfOptions = new BitmapFactory.Options();
                bfOptions.inJustDecodeBounds = false;
                bfOptions.inTempStorage = new byte[32 * 1024];
                Bitmap bitmap = null;
                rotatedBitmap = null;
                try {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotate);
                    bitmap = Bitmap.createScaledBitmap(
                            BitmapFactory.decodeFile(selectedImagePath),
                            desiredImageWidth, desiredImageHeight, false);
                    rotatedBitmap = Bitmap
                            .createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                    bitmap.getHeight(), matrix, true);
                    bitmap.recycle();
                    Log.v("", "Bitmap After Compression: " + rotatedBitmap.getWidth() + " : " + rotatedBitmap.getHeight()+" : " + selectedImagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.registration_thumb);
                }
                //				savebitmap(selectedImagePath, rotatedBitmap);
                //				model.setBitmap(rotatedBitmap);
                //				img_view.setImageBitmap(model.getBitmap());
                driverPicImg.setImageBitmap(rotatedBitmap);
                isFileImageUploaded = true;
            }
        } else {
            Log.v("", "OnActivityResult: " + resultCode);
        }
    }


    // for getting path of image
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA,MediaStore.Images.ImageColumns.ORIENTATION };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    /**
     * This method call will update the user details
     */

    class ToRegisterDriverAsync extends AsyncTask<Void, Integer, String> {

        Map<String, String> params = new HashMap<String, String>();

        public ToRegisterDriverAsync(String registPwdStr, String registFullNameStr, String registPhoneStr,
                                     String registcarNoStr,String registMobileStr,String registCartypeStr,
                                     String registEmailStr) {

            params.put("driverfullname", registFullNameStr);
            params.put("driverpassword", registPwdStr);
            params.put("drivercarnumber", registcarNoStr);
            params.put("drivercartype", registCartypeStr);
            params.put("devicetoken", "sdasd");
            params.put("devicetype", "android");
            params.put("driveremail", registEmailStr);
//            params.put("file", "profilepic.jpg");
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(StartActivity.this);
            dialog.setTitle("Requesting");
            dialog.setMessage("Wait this may take few seconds..!");
            dialog.setCancelable(false);
            dialog.show();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.v("", "Responce onPostExecute: " + result);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                if (TextUtils.isEmpty(result)) {
                    ResponceAfterUpdateUser(result);
                } else {
                    showMessage("Something went wrong, Try again with valid credentails");
                }
            }
        }

        @Override
        protected String doInBackground(Void... params0) {
            String url = Webservices.BASE_URL + Webservices.REGISTER_URL;
            Log.v("", "ImagePath: " + selectedImagePath  + "::" + url);
            return uploadProfilePic(selectedImagePath, url, params);
        }
    }


    /**
     *
     * @param
     */
    public String uploadProfilePic(String sourceUri, String uploadServer, Map<String, String> params) {
        String responce = "";
        int serverResponceCode = 0;
        HttpURLConnection httpConnection = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String hyphens = "--";
        String boundary = "*****";
        int bytesRead;
        int bytesAvailable;
        int bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;

        File sourceFile = new File(sourceUri);
        if (!sourceFile.isFile()) {
            return String.valueOf(0);
        }

        File tempFile = createTempFile(sourceUri);
        try {
            //getting http connection
            FileInputStream fis = new FileInputStream(tempFile);
            URL url = new URL(uploadServer);
            httpConnection = (HttpURLConnection)url.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("connection", "Keep-Alive");
            httpConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            httpConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            httpConnection.setRequestProperty("file", sourceUri);

            //uploading user profile pic
            dos = new DataOutputStream(httpConnection.getOutputStream());
            dos.writeBytes(hyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + tempFile.getPath() +"\"" + lineEnd);
            dos.writeBytes(lineEnd);
            bytesAvailable = fis.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fis.read(buffer, 0, bufferSize);
            while(bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fis.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd);

            //uploading user description
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.get(key);
                dos.writeBytes(hyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(value);
                dos.writeBytes(lineEnd);
            }
            dos.writeBytes(hyphens + boundary + hyphens + lineEnd);
            serverResponceCode = httpConnection.getResponseCode();

            if (200 != serverResponceCode) {
                //				Utils.showToast("Error at server side in signup..!");
            } else {
                responce = convertStreamToString(httpConnection.getInputStream());
                Log.v("", "Responce Success : " + responce);
            }

            Log.d("", "Responce returned: " + httpConnection.getInputStream());
            if (responce== null || responce.equalsIgnoreCase("")) {
                responce = convertStreamToString(httpConnection.getInputStream());
            } else {
                fis.close();
                dos.flush();
                dos.close();
            }
        } catch(IOException e ) {
            e.printStackTrace();
            //			Utils.showToast("IO Exception");
        } catch (Exception e) {
            e.printStackTrace();
            //			Utils.showToast("Exception");
        }finally {
            tempFile.delete();
        }

        return responce;
    }


    /**
     * converting the inputstream after signup(username webservice)
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        try {
            Log.v("", "input stream: " + is.available());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i("", "Responce convert to stream: " + sb.toString());
        return sb.toString();
    }
    /**
     * creating temp file.
     * @param filePath
     * @return
     */
    public File createTempFile(String filePath) {
        File tempFile = null;
        try {
            Log.v("", "TempFile Bitmap Before: " + filePath);
            Bitmap bitmap = decodeSampledBitmapFromPath(filePath, desiredImageWidth, desiredImageHeight);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            tempFile = new File(getExternalCacheDir() + File.separator + new Date().getTime() + ".jpg");
            tempFile.createNewFile();
            FileOutputStream fo = new FileOutputStream(tempFile);
            fo.write(bytes.toByteArray());
            fo.close();
            Log.v("", "Tempfile Bitmap After: " + tempFile.getPath());
            return tempFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * decoding bitmap from its path
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        return bmp;
    }


    /**
     * Calculating inSample size
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }


    /**
     * responce after calling update user web service.
     * @param jsonResponse
     */
    public void ResponceAfterUpdateUser(String jsonResponse) {
        Log.v("", "json responce: " + jsonResponse);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonResponse);
            try {
                String responseInfo = jsonObject.getString(TAG_RESPONSEINFO);
                String response = jsonObject.getString(TAG_RESPONSE);
                Log.v(TAG_RESPONSE, "TAG: " + response);
                if (responseInfo.isEmpty()) {
                    return;
                }

                if(response.equalsIgnoreCase("success")){
                    if (responseInfo.equalsIgnoreCase("Email Exists")) {
                        showMessage("Driver Email already existed");
                        return;
                    }else if(responseInfo.equalsIgnoreCase("Verification Link sent to registered Email")){
                        showMessage("Verification Link sent to registered Email");
                        return;
                    }
                        startScreen(MainActivity.class);
                        String userId = jsonObject.getString("id");
                        Preferences.setUserId(getApplicationContext(), userId);
                        finish();
                }else{
                    showMessage("Something went wrong please try again");
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
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
        Log.v("FORGET_WS", "URL: " + url);
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
       // "http://fancynews.in/taxidriver/mobiledriverlogincheck.php?" +
       //         "driveremail=madirisalmanaashish@gmail.com&driverpassword=12345";
        String url = Webservices.BASE_URL + Webservices.LOGIN_URL
                + "driveremail=" + loginEmailStr + "&driverpassword=" + loginpwStrin;

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
            String response = jsonObject.getString(TAG_RESPONSE);
            if (responseInfo.isEmpty()) {
                return;
            }
            if (response.equalsIgnoreCase("success")) {
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

        if(!isFileImageUploaded){
            showMessage("Please take a picture");
            return;
        }
        new ToRegisterDriverAsync(registPwdStr,registFullNameStr,registPhoneStr,registcarNoStr,registMobileStr,registCartypeStr,registEmailStr).execute();

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


