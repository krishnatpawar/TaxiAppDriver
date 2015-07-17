package com.taxi.driver.taxiappdriver.utils;

/**
 * Created by sharma on 19/6/15.
 */
public class Webservices {
    //public static String BASE_URL = "http://wowads.asia/taxidriver/";
   // public static String BASE_URL = "http://fancynews.in/taxidriver/";

    /*http://wowads.asia/taxidriver/forgotpassword.php?email=ashishssss@dkslakds.com*/
   // public static String FORGET_URL = "forgotpassword.php?";




    public static String BASE_URL = "http://fancynews.in/taxidriver/";

    public static String LOGIN_URL = "mobiledriverlogincheck.php?";

    public static String REGISTER_URL = "mobiledriverregistration.php?";

    public static String UPDATE_URL = "updatedriverdetails.php?";

    public static String FORGET_URL = "driverforgotpasword.php?";


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

    public static String forgot_pwd = "http://fancynews.in/taxidriver/driverforgotpasword.php" +
            "?email=madirisalmanaashish@gmail.com";
}
