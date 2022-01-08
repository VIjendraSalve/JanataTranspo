package com.wht.janatatraspo.Constant;

public interface IConstant {



    String OWNER = "2";
    String DRIVER = "3";
    String OWNER_DRIVER = "4";

    String BUSINESS_TYPE_ID = "1";
    String SERVICE_TYPE_ID = "2";
    String USER_TYPE_ID = "3";
    String COMUNITY_TYPE_ID = "4";
    String DID_YOU_KNOW_TYPE_ID = "5";


    int SPLASH_TIME = 3000;

    int BACK_PRESSED = 1000;

    int EXIT = 500;

    String ANDROID_ID = "2";
    String REMEMBER_ID = "0";
    // String DEVICE_ID ="A" ;
    String ADD = "1";
    String UPDATE = "2";
    String UPDATE_FLAG = "UPDATE_FLAG";

    String CHAT_TYPE = "CHAT_TYPE";
    String CATEGORY_TYPE = "CATEGORY_TYPE";
    String BUSINESS_CATEGORY_TYPE = "1";
    String SERVICE_CATEGORY_TYPE = "2";
    String FRIENDS_CATEGORY_TYPE = "3";

    //Sender Receiver ID
    String SENDER_TYPE_ID_FOR_USER = "0";
    String RECEIVER_TYPE_ID_FOR_USER = "0";
    String SENDER_TYPE_ID_FOR_BUSINESS_AND_SERVICE = "1";
    String RECEIVER_TYPE_ID_FOR_BUSINESS_AND_SERVICE = "2";


    /**
     * SHOW TO USER
     */
    String RESPONSE_MESSAGE = "reason";


    /**
     * FOR PERFORMING CONDITIONS
     */
    String RESPONSE_CODE = "result";
    String RESPONSE_OTP_VERIFIED= "is_verified";
    /**
     * for getting array of inserted id
     */
    String RESPONSE_KEY = "key";
    String RESPONSE_KEY1 = "Data";

    /**
     * KEY FOR ID'S RETURN BY RESPONSE_KEY
     */
    String RESPONSE_ID = "ID";

    String RESPONSE_SUCCESS = "true";
    String RESPONSE_VERIFIED = "1";
    String RESPONSE_ERROR = "0";
    String RESPONSE_ALREADY_EXISTS = "2";
    String RESPONSE_NULL_PARAMETER = "3";
    String RESPONSE_METHOD_NOT_ALLOWED = "4";
    String RESPONSE_NOT_EXIST = "5";
    String RESPONSE_UPDATE = "6";
    String RESPONSE_DELETE = "7";

    public static final int RC_IMAGE = 1;


    String USER_API_TOKEN = "USER_API_TOKEN";
    String USER_ID_DRIVER = "USER_ID_DRIVER";
    String AMBASSADOR_ID = "AMBASSADOR_ID";
    String USER_ID = "USER_ID";
    String USER_FIRST_NAME = "USER_FIRST_NAME";
    String CUSTOMER_TYPE = "CUSTOMER_TYPE";
    String USER_LAST_NAME = "USER_LAST_NAME";
    String USER_MOBILE = "USER_MOBILE";
    String USER_ADDRESS = "USER_ADDRESS";
    String USER_BLOODGROUP = "USER_BLOODGROUP";
    String USER_BILLINGADDRESS = "USER_BILLINGADDRESS";
    String USER_EMAIL = "USER_EMAIL";
    String USER_PHOTO = "USER_PHOTO";
    String USER_COVER_PHOTO = "USER_COVER_PHOTO";
    String USER_ROLE_ID = "USER_ROLE_ID";
    String USER_IS_LOGIN = "USER_IS_LOGIN";
    String CITY_ID = "CITY_ID";
    String CITY_NAME = "CITY_NAME";
    String USER_NUMER = "USER_NUMER";
    String USER_PRIVACY = "USER_PRIVACY";
    String USER_STATUS = "USER_STATUS";
    String USER_FROM = "USER_FROM";
    String STATE_ID = "STATE_ID";
    String LIVES_IN = "LIVES_IN";
    String MARITAL_STATUS_ID = "MARITAL_STATUS_ID";
    String WORK_EXPERIENCE = "WORK_EXPERIENCE";
    String USER_ABOUT_US = "USER_ABOUT_US";
    String OWNER_BUSINESS_NAME = "OWNER_BUSINESS_NAME";
    String STATE_NAME = "STATE_NAME";
    String USER_GENDER = "USER_GENDER";
    String USER_DOB = "USER_DOB";
    String USER_COUNTRY_ID = "USER_COUNTRY_ID";
    String USER_COUNTRY_NAME = "USER_COUNTRY_NAME";

    String USER_VERIFICATION_ID = "USER_VERIFICATION_ID";
    String USER_REJECT_REASON = "USER_REJECT_REASON";
    String USER_WALLET_AMT = "USER_WALLET_AMT";


    String PAYMENT_ONLINE = "PAYMENT_ONLINE";
    String PAYMENT_COD = "PAYMENT_COD";


    String MINIMUM_ORDER_AMT = "MINIMUM_ORDER_AMT";
    String DELIVERY_CHARGE_LIMIT = "DELIVERY_CHARGE_LIMIT";

    public static final String SP_MIN_ORDER_VALUE = "min_order_value";
    public static final String SP_DELIVERY_CHARGES = "delivery_charges";
    public static final String SP_VERSION_CODE = "version_code";
    public static final String SP_VERSION_NAME = "version_name";
    public static final String DATA_VERSION = "DATA_VERSION";
    public static final String SubscriptionTitle = "SubscriptionTitle";
    public static final String SubscriptionDesc = "SubscriptionDesc";
    public static final String BannerPath = "BannerPath";


    String DELIVERY_ADDRESS_ID = "DELIVERY_ADDRESS_ID";
    String DELIVERY_FULL_ADDRESS = "DELIVERY_FULL_ADDRESS";
    String DELIVERY_PICKUP_PERSON_NAME = "DELIVERY_PICKUP_PERSON_NAME";
    String DELIVERY_PICKUP_PERSON_CONTACT = "DELIVERY_PICKUP_PERSON_CONTACT";


    String BUSINESS_SUB_CATEGORY_IDS_LIST = "BUSINESS_SUB_CATEGORY_IDS_LIST";
    String BUSINESS_SUB_CATEGORY_NAME_LIST = "BUSINESS_SUB_CATEGORY_NAME_LIST";

    String CITY_IDS_LIST = "CITY_IDS_LIST";
    String CITY_NAME_LIST = "CITY_NAME_LIST";
    String FRIENDS_ID_LIST = "FRIENDS_ID_LIST";
    String FRIENDS_NAME_LIST = "FRIENDS_NAME_LIST";


    String LOGIN_TYPE = "LOGIN_TYPE";
    String REMEMBER_USER_ID = "REMEMBER_USER_ID";
    String REMEMBER_PASSWORD = "REMEMBER_PASSWORD";
    String SKIP_FLAG = "SKIP_FLAG";
    String BUSINESS_ID = "BUSINESS_ID";
    String SERVICE_ID = "SERVICE_ID";

    String BUSINESS_VERIFICATION_ID = "BUSINESS_VERIFICATION_ID";
    String BUSINESS_VERIFICATION_REASON = "BUSINESS_VERIFICATION_REASON";

    String SERVICE_VERIFICATION_ID = "SERVICE_VERIFICATION_ID";
    String SERVICE_VERIFICATION_REASON = "SERVICE_VERIFICATION_REASON";

    //cHATTING pARMAETER
    String DriverIDForChat = "DriverIDForChat";
    String REG_ID = "REG_ID";
    String CHAT_COUNT = "CHAT_COUNT";
    String CHAT_SPEED = "CHAT_SPEED";


    String CURRENT_CITY_ID = "CURRENT_CITY_ID";
    String CURRENT_CITY_NAME = "CURRENT_CITY_NAME";
    String LANGUAGE = "LANGUAGE";
    String USER_BANK_NAME = "USER_BANK_NAME";
    String USER_ACCOUNT_NO = "USER_ACCOUNT_NO";
    String USER_IFSC_CODE = "USER_IFSC_CODE";
    String USER_BANK_BRANCH_CITY = "USER_BANK_BRANCH_CITY";
    String USER_PAN_CARD = "USER_PAN_CARD";
    String USER_GST_NO = "USER_GST_NO";
    String AADHAR_IMAGE_ONE = "AADHAR_IMAGE_ONE";
    String AADHAR_IMAGE_TWO = "AADHAR_IMAGE_TWO";
    String USER_AADHAR_NO = "USER_AADHAR_NO";
    String USER_IS_DRIVER = "USER_IS_DRIVER";
    String USER_PAN_CARD_NO = "USER_PAN_CARD_NO";
    String IS_PAN_VERIFIED = "IS_PAN_VERIFIED";
    String IS_GST_VERIFIED = "IS_GST_VERIFIED";
    String IS_AADHAR_VERIFIED = "IS_AADHAR_VERIFIED";
    String USER_GST_PHOTO = "USER_GST_PHOTO";
    String POLICE_VERIFICATION_IMAGE = "POLICE_VERIFICATION_IMAGE";
    String DRIVING_LICENSES_IMAGE = "DRIVING_LICENSES_IMAGE";
    String DRIVING_LICENSES_NO = "DRIVING_LICENSES_NO";
    String IS_DRIVER_VERIFIED = "IS_DRIVER_VERIFIED";
    String VehicleSelected = "VehicleSelected";
}
