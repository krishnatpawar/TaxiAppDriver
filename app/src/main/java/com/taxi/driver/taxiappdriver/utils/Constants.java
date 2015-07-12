package com.taxi.driver.taxiappdriver.utils;

/**
 * Created by sharma on 13/6/15.
 */
public class Constants {
    //app preferences constants
    public static String KEY_APP_PREFERENCES = "taxi_preferences";
    public static String KEY_USER_ID = "taxi_user_id";
    public static String KEY_USER_NAME = "taxi_user_name";
    public static String KEY_USER_EMAIL = "taxi_user_email";
    public static String KEY_USER_PHNUM = "taxi_user_phnum";
    /**
     * tags used for capturing image from camera & gallery
     */
    public static final String PROFILEIMAGEPATH = "/taxi/profile_images";


    public static String BASE_URL = "http://fancynews.in/taxidriver/";


    //signin
    public static String sign_in = "http://fancynews.in/taxidriver/mobiledriverlogincheck.php?" +
            "driveremail=madirisalmanaashish@gmail.com&driverpassword=12345";

    public static String registration = "http://fancynews.in/taxidriver/mobiledriverregistration.php?" +
            "driverfullname=aashish&driverpassword=12345&drivercarnumber=1233838" +
            "&drivercartype=benzene&devicetoken=sdasd&devicetype=android" +
            "&driveremail=madirisalmanaashish@gmail.com&file=profilepic.jpg";

    public static String update = "http://fancynews.in/taxidriver/updatedriverdetails.php?" +
            "driverid=25&driverfullname=aashish&driverpassword=12345" +
            "&drivercarnumber=1233838&drivercartype=benzene" +
            "&devicetoken=sdasd&devicetype=android" +
            "&driveremail=madirisalmanaashish@gmail.com&file=profilepic.jpg";

    public static String forgot_pwd = "http://fancynews.in/taxidriver/driverforgotpasword.php?email=madirisalmanaashish@gmail.com";

}
