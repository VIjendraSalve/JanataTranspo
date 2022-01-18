package com.wht.janatatraspo.Constant;

import com.wht.janatatraspo.Helpers.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 2/15/2018.
 */

public class IUrls {


    //Third party api
    public static final String THIRD_PARTY_BASE_URL = "https://kyc-api.aadhaarkyc.io/api/v1/";
    // public static final String THIRD_PARTY_BASE_URL = "https://sandbox.aadhaarkyc.io/api/v1/";
    public static final String THIRD_PARTY_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY0MTAxNDQwNiwianRpIjoiMjk0Y2IzODAtMGZiYy00ODJiLWE2YTYtNjAwNmViMWE5ZjdjIiwidHlwZSI6ImFjY2VzcyIsImlkZW50aXR5IjoiZGV2LnRlY2t0ZWFtamFuYXRhQGFhZGhhYXJhcGkuaW8iLCJuYmYiOjE2NDEwMTQ0MDYsImV4cCI6MTk1NjM3NDQwNiwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.a6sRTrDHMrCOlyVQVYO0rMc5dlwL75Rl-zyXMnOtwps";
    public static final String SALES_THIRD_PARTY_PASSWORD = "sales!JT@12Transpo3";

    public static final String URL_GST_VERIFICATION = "corporate/gstin";
    public static final String URL_RC_VERIFICATION = "rc/rc";
    public static final String URL_LICENSE_VERIFICATION = "driving-license/driving-license";

    //Aadhar Version 1 api
    public static final String URL_AADHAR_CARD_V1_VERIFICATION = "aadhaar-validation/aadhaar-validation";

    //Aadhar Version 2 Api
    public static final String URL_AADHAR_CARD_V2_GENRATE_OTP = "aadhaar-v2/generate-otp";
    public static final String URL_AADHAR_CARD_V2_SUBMIT_OTP = "aadhaar-v2/submit-otp";

    //Sales Person Verification
    public static final String SALES_PERSON_BASE_URL = "http://salesjt.com/GetAuthorise_Sponsor.asmx/";
    public static final String URL_SALES_PERSON_VERIFICATION = "get_auth_sponsor";
    public static final String URL_ACCEPT_LOAD_BID = "app_cust_accept_add";
    public static final String URL_CUSTOMER_PAYMENT_TRANSO = "app_cust_payment_transpo";
    public static final String URL_ACCEPT_EARTHMOVER_BID = "app_cust_accept_earth_mover_add";


    public static Dispatcher dispatcher;
    public static final String BASE_URL = "http://wetap.in/janata_transport/";

    public static final String APP_URL = "https://play.google.com/store/apps/details?id=com.wht.janatatraspo";

    public static final String URL_USER_REGISTRATION = "register_user";
    public static final String URL_LOGIN = "login_customer";
    public static final String URL_OTP_CHECK = "otp_verification_customer";

    public static final String URL_RESEND_OTP = "resend_otp";
    public static final String URL_VEHICLE_LIST = "app_vehicle_my_list";
    public static final String URL_DRIVER_LIST = "app_driver_my_list";
    public static final String URL_DASHBOARD_COUNT = "app_get_dashboard_owner";
    public static final String URL_ADD_DRIVER = "app_driver_add";
    public static final String URL_UPDATE_DRIVER = "driver_update_profile";
    public static final String URL_UPDATE_OWNER = "owner_update_profile";
    public static final String URL_UPDATE_CUSTOMER = "app_cust_updateProfile";
    public static final String URL_ADD_VEHICLE = "app_vehicle_owner_add";
    public static final String URL_ALL_VEHICLE_LIST = "app_vehicle_master_list";
    public static final String URL_UPDATE_PROFILE_IMAGE = "update_profile_pic";
    public static final String URL_STATE_LIST = "state_list";
    public static final String URL_CITY_LIST = "city_list";
    public static final String URL_LOAD_LIST = "app_cust_loader_list";
    public static final String URL_EARTHMOVER_LIST = "app_cust_earth_mover_list";
    public static final String URL_BID_LIST = "app_cust_bid_list";
    public static final String URL_BID_LIST_EARTHMOVER = "app_cust_earth_mover_bid_list";
    public static final String URL_VEHICLE_TYPE_LIST = "app_vehicle_master_type_list";
    public static final String URL_VEHICLE_MAKE_LIST = "app_vehicle_master_make_list";
    public static final String URL_aadhar_verification = "aadhar_verification";
    public static final String URL_GST_verification = "gst_verification";
    public static final String URL_LICENSE_verification = "driving_verification";
    public static final String URL_GET_PROFILE = "get_custom_data";
    public static final String URL_RC_verification = "rc_verification";
    public static final String URL_ADD_LOAD = "app_cust_loader_add_request";
    public static final String URL_ADD_EARTH_MOVER = "app_cust_earth_mover_add_request";
    /*
    public static final String URL_FORGOT_PASSWORD = "app_forgot_password";
    public static final String URL_CATEGORY_LIST = "app_category_list";
    public static final String URL_ADD_CATEGORY = "app_buss_add_category";
    public static final String URL_ADD_SUB_CATEGORY = "app_buss_add_subCategory";

    public static final String URL_STATE_LIST = "app_states_list";
    public static final String URL_DISTRICT_LIST = "app_city_list";
    public static final String URL_MARIATAL_STATUS_LIST = "app_mariatal_status_list";
    public static final String URL_UPDATE_PROFILE = "app_update_profile";
    public static final String URL_UPDATE_COVER_IMAGE = "app_update_cover_image";
    public static final String URL_UPDATE_PROFILE_IMAGE = "app_update_profile_image";
    public static final String URL_BUSINESS_LIST = "app_userbussiness_list";
    public static final String URL_RECENT_VIEWED_BUSINESS_LIST = "app_recent_business_view_list";
    public static final String URL_SERVICE_LIST = "app_userservices_list";
    public static final String URL_ADD_N_UPDATE_BUSINESS = "app_bussiness_add";
    public static final String URL_ADD_N_UPDATE_SERVICE = "app_service_addupdate";
    public static final String URL_BUSINESS_SERVICE_LIST = "app_business_services_list";
    public static final String URL_ADD_BUSINESS_HOURS = "app_bussiness_hrs_add_up_list";
    public static final String URL_ADD_BUSINESS_HAPPY_HOURS = "app_buss_happy_hrs_add_upd_list";
    public static final String URL_MY_BUSINESS_SERVICES_LIST = "app_buss_services_list";
    public static final String URL_NEWS_LIST = "app_news_list";
    public static final String URL_BUSINESS_VACANCY_LIST = "app_buss_my_vacancy_list";
    public static final String URL_MY_BUSINESS_VACANCY_STATUS = "app_buss_status_vacancy";
    public static final String URL_RESUME_LIST = "app_buss_vacancy_resume_list";
    public static final String URL_APPLY_ON_VACANCY = "app_buss_apply_vacancy";
    public static final String URL_DELETE_VACANCY = "app_buss_delete_vacancy";
    public static final String URL_ADD_VACANCY = "app_buss_add_vacancy";
    public static final String URL_BUSINESS_PAGE_ROLE = "app_buss_page_user";
    public static final String URL_BUSINESS_PRODUCTS = "app_buss_product_list";
    public static final String URL_BUSINESS_EVENTS = "app_buss_event_list";
    public static final String URL_EVENT_REPORT = "app_reports_add";
    public static final String URL_OFFER_LIST = "app_buss_offer_list";
    public static final String URL_EVENT_INTEREST_GOING = "app_buss_event_interest_going_apply";
    public static final String URL_INTERESTED_MEMBER_LIST = "app_buss_event_interest_going_list";
    public static final String URL_ADD_USER_JOB = "app_job_add";
    public static final String URL_ADD_USER_EDUCATION = "app_education_add";
    public static final String URL_DELETE_USER_JOB = "app_job_delete";
    public static final String URL_DELETE_USER_EDUCATION = "app_education_delete";
    public static final String URL_BUSINESS_HIGHTLIGHTS = "app_buss_highlights_list";
    public static final String URL_BUSINESS_REVIEWS = "app_buss_review_list";
    public static final String URL_ADD_BUSINESS_REVIEWS = "app_buss_review_add";
    public static final String URL_ADD_PRODUCT_ENQUIRY = "app_buss_product_enquiry_add";
    public static final String URL_ADD_PRODUCT = "app_buss_product_addupdate";
    public static final String URL_DELETE_PRODUCT = "app_buss_product_delete";
    public static final String URL_ADD_OFFER = "app_buss_offer_addupdate";
    public static final String URL_DELETE_OFFER = "app_buss_offer_delete";
    public static final String URL_ADD_HIGHTLIGHTS = "app_buss_highlights_add";
    public static final String URL_ADD_SERVICE_HIGHTLIGHTS = "app_service_highlights_add";
    public static final String URL_ADD_EVENT = "app_buss_event_add";
    public static final String URL_CHECK_USERNAME = "app_check_username";
    public static final String URL_PRODUCT_ENQUIRY_LIST = "app_buss_product_enquiry_list";
    public static final String URL_DELETE_EVENT = "app_buss_event_delete";
    public static final String URL_SUGGESTION_LIST = "app_buss_suggestList";
    public static final String URL_ADD_BUSSINESS_SUGGESTION = "app_buss_addsuggest";

    public static final String URL_DELETE_REASON = "app_reason_list";
    public static final String URL_BUSINESS_DELETE = "app_bussiness_delete";
    public static final String URL_GENRAL_PAGE_DETAILS = "app_buss_page_general_visiblity";
    public static final String URL_GENRAL_PAGE_UPDATE = "app_buss_add_page_visiblity";
    public static final String URL_BUSINESS_LIKE_UNLIKE = "app_buss_likeUnlike";
    public static final String URL_SERVICE_LIKE_UNLIKE = "app_service_likeUnlike";
    public static final String URL_BUSINESS_BLOCK = "app_buss_block";
    public static final String URL_GET_DOCUMENT_LIST = "app_document_master_list";
    public static final String URL_BUSINESS_VERIFICATION_REQ = "app_buss_add_verificationRequest";
    public static final String URL_BUSINESS_COUPONS_AND_OFFERS = "app_buss_addCoupons_codes";
    public static final String URL_BUSINESS_COUPONS_AND_OFFERS_LIST = "app_buss_couponsList";
    public static final String URL_DELETE_BUSINESS_COUPONS = "app_buss_deleteCoupons_codes";
    public static final String URL_SERVICE_PACKAGE = "app_service_servicesExtraList";
    public static final String URL_BUSINESS_PAGE_MANAGEMENT = "app_buss_page_user_mangementList";
    public static final String URL_ADD_SERVICE_PACKAGE = "app_service_addServicesExtra";
    public static final String URL_DELETE_SERVICE_PACKAGE = "app_service_packagedelete";
    public static final String URL_INTERESTED_DOMAIN_LIST = "app_master_interest_list";
    public static final String URL_ADD_INTEREST = "app_user_interest_module";
    public static final String URL_BLOCK_BUSINESS_LIST = "app_buss_wise_block_list";
    public static final String URL_SERVICE_HIGHTLIGHTS = "app_service_highlights_list";
    public static final String URL_BLOCK_SERVICE = "app_service_block";
    public static final String URL_ADD_SERVICE_REVIEWS = "app_service_review_add";
    public static final String URL_SERVICE_REVIEWS_LIST = "app_service_review_list";
    public static final String URL_SERVICE_GENRAL_PAGE_VISIBILITY = "app_service_page_general_visiblity";
    public static final String URL_SERVICE_GENRAL_PAGE_UPDATE_VISIBILITY = "app_service_add_page_visiblity";
    public static final String URL_SERVICE_PAGE_MANAGEMENT_HISTORY = "app_service_page_user_mangementList";
    public static final String URL_DELETE_SERVICE = "app_service_delete";
    public static final String URL_SERVICE_VERIFICATION = "app_service_add_verificationRequest";
    public static final String URL_BUSINESS_NEWS_LIST = "app_buss_list_news";
    public static final String URL_HOME_WEBCALL = "app_user_dashboard";
    public static final String URL_DASHBOARD_WEBCALL = "app_user_dashboard_count";
    public static final String URL_SUBSCRIBE_NEW_AND_MAGZINE = "app_user_subscribe_news_magazine";
    public static final String URL_MAGZINE_LIST = "app_buss_list_magazine";
    public static final String URL_ADD_MAGZINE = "app_buss_add_magazine";
    public static final String URL_ADD_BUSINESS_NEWS = "app_buss_add_news";
    public static final String URL_DELETE_BUSINESS_NEWS = "app_buss_delete_news";
    public static final String URL_DELETE_BUSINESS_MAGAZINE = "app_buss_delete_magazine";
    public static final String URL_ADD_REQ_FOR_AMBASSADOR = "app_request_dulocal_ambassador";
    public static final String URL_CONCIERGE_CITY_LIST = "app_concierge_city_list";
    public static final String URL_AMBASSDOR_LIST = "app_concierge_list";
    public static final String URL_AMBASSDOR_SUBSCRIBERS_LIST = "app_concierge_my_subscribe_list";
    public static final String URL_AMBASSDOR_BUY_REQ = "app_concierge_buy_request";
    public static final String URL_ARTICLE = "app_articles_list";
    public static final String URL_DASHBOARD_EVENT = "app_event_list";
    public static final String URL_NGO_LIST = "app_ngo_list";
    public static final String URL_COOL_THINGS_CITY_LIST = "app_cool_things_city_list";
    public static final String URL_COOL_THINGS_LIST = "app_cool_thing";
    public static final String URL_LIKE_UNLIKE = "app_add_post_like";
    public static final String URL_POST_SHARE = "app_post_share_request";
    public static final String URL_DELETE_COMMENT = "app_delete_post_agnist_comment";

    //User Profile Webcalls
    public static final String URL_USER_EDUCATION_LIST = "app_education_list";
    public static final String URL_USER_JOB_LIST = "app_job_list";
    public static final String URL_USER_PROFILE_INFO = "app_user_profile";
    public static final String URL_USER_ACCOUNT_VERIFICATION = "app_user_verification_request";

    //Friends Module
    public static final String URL_PAGE_ROLE_TYPE = "app_buss_page_user_typeList";
    public static final String URL_PAGE_ROLE_SUGGESTION_FRINDS = "app_buss_page_user_suggestFriendsList";
    public static final String URL_REMOVE_FRINDS_FROM_SUGGESTION_LIST = "app_buss_page_user_suggest_removed";
    public static final String URL_SEND_REQUEST_FOR_PAGE_ROLE = "app_buss_page_user_request_send";
    public static final String URL_ALL_FRIENDS_LIST = "app_friends_list";
    public static final String URL_BLOCK_FRIENDS_LIST = "app_friends_block_list";
    public static final String URL_SUGGESTION_FRIENDS_LIST = "app_friends_suggest_list";
    public static final String URL_SEND_FRIENDS_REQUEST = "app_send_friend_request";
    public static final String URL_SEND_AND_RECEIVED_REQUEST_LIST = "app_send_received_request_list";
    public static final String URL_ACCEPT_REJECT_BLOCK = "app_accept_reject_block";
    public static final String URL_REMOVE_FROM_SUGGESTION_LIST = "app_friends_suggest_remove_list";
    public static final String URL_UNFRIEND = "app_send_unfriend_request";
    public static final String URL_PAGE_ROLE_REQ_ACCEPT_AND_REJECT = "app_buss_page_user_accept_cancel_request";
    public static final String URL_ROLE_REQ_LIST = "app_buss_page_user_requestList";
    public static final String URL_CHAT_USER_LIST = "app_chart_user_list";

    //Chating APi
    public static final String URL_GET_CHATTING_DATA = "app_ambassadoradd_getchart";
    public static final String URL_SEND_CHATTING_DATA = "app_ambassadoradd_chart";
    public static final String URL_GET_COMMON_CHATTING_DATA = "app_user_chart";
    public static final String URL_SEND_COMMON_CHATTING = "app_add_chart";

    //Post Timeline Webcall
    public static final String URL_POST_EXPLORE = "app_explore_post_list";
    public static final String URL_POST_TIMELINE = "app_post_list";
    public static final String URL_POST_DETAILS = "app_post_detail_list";
    public static final String URL_ADD_POST = "app_add_post";
    public static final String URL_POST_AGAINS_COMMENTS = "app_get_post_agnist_comment";
    public static final String URL_ADD_COMMENTS_AGAINS_POST = "app_add_post_comment";
    public static final String URL_DELETE_POST = "app_post_delete";
    public static final String URL_DELETE_POSTED_IMAGE = "app_post_image_delete";


    //Shout Out
    public static final String URL_SHOUTOUT_LIST = "app_buss_shoutList";
    public static final String URL_ADD_SHOUTOUT = "app_buss_addShoutout";
    public static final String URL_ADD_ANSWER_OF_SHOUTOUT = "app_buss_addanswerShoutout";
    public static final String URL_SHOUTOUT_ANSWER_LIST = "app_buss_shoutAnswerList";
    public static final String URL_DELETE_SHOUTOUT_QUESTION = "app_buss_deleteShoutout";


    public static final String URL_SEND_CONTACT_US = "app_add_contactUs";

    public static final String URL_POST_IMAGES_AND_VIDEOS = "app_post_image_video_list";
    // public static final String URL_INSTRUCTIONS = "app_imp_notes";

    //Wallet
    public static final String URL_GET_WALLET_TANSACTION = "app_get_wallet";
    public static final String URL_RECHARGE_WALLET = "app_rechange_wallet";

    public static final String URL_POST_BOOST = "app_post_boost_request";
    public static final String URL_USER_LOGS = "app_activityLog_list";

    //Hangout
    public static final String URL_HANGOUT_LIST = "app_hangout_list";


    //Acount Privacy
    public static final String URL_PRIVACY_MASTER = "app_privacy_master_list";
    public static final String URL_UPDATE_SETTINGS = "app_update_profileSetting";
    public static final String URL_DEATIVATE_ACC = "app_user_deactived_request";
    public static final String URL_GET_USER_SETTINGS = "app_get_userSetting";

    //Betting
    public static final String URL_BETTING_RULES = "app_bitting_master_list";
    public static final String URL_BETTING_CITY = "app_city_bitting";
    public static final String URL_BETTING_LIST = "app_check_bitting_list";
    public static final String URL_APPLY_FOR_POSTION = "app_apply_bitting";

    //Heat Map
    public static final String URL_HEAT_MAP_CATEGORY_LIST = "app_heatMap_category_list";
    public static final String URL_HEAT_MAP_MARKER_LIST = "app_heatMapBussiness_list";


    //Comunity Forum
    public static final String URL_POST_COMUNITY_FORUM_BANNERS = "app_ngo_banner_list";

    //Did YOu Know
    public static final String URL_POST_DID_YOU_KNOW_CITY_LIST = "app_cool_things_city_list";

    //Universal Search
    public static final String URL_UNIVERSAL_SEARCH = "app_universal_serach";

    //Page Inslight
    public static final String URL_PAGE_INSIGHTS = "app_business_service_page_insights_list";

    //All Clicks
    public static final String URL_SPECIAL_OFFERS = "app_business_offer_list";
    public static final String URL_BUSINESS_HIGHLIGHTS = "app_business_hotspot_list";
    public static final String URL_DULOCAL_HOTSPOTS= "app_dulocal_hotspot_list";*/


/*
    public static final String URL_POST_CANCEL_ORDER = "app_cancel_order";
    public static final String URL_POST_PRODUCT_DETAILS = "app_product_details";
    public static final String URL_CART_COUNT = "app_cart_count";
    public static final String URL_BLOG_LIST = "app_blog_list";
    public static final String URL_RECEIPE_LIST = "app_recipe_list";
    public static final String URL_OFFER_LIST = "app_offer_list";
    public static final String URL_APPLY_OFFERS = "app_apply_offer";
    public static final String URL_GET_VERSION = "app_version_check";
    // public static final String URL_GET_VERSION = "app_version_demo";

    //Static Pages
    public static final String URL_PRIVACY_POLICY = BASE_URL + "app_privacy_policy";
    public static final String URL_ABOUT_US = BASE_URL + "app_about_us";
    public static final String URL_TERMS_AND_CONDITIONS = BASE_URL + "app_term_condition";
    public static final String URL_RETURN_POLICY = BASE_URL + "app_return_policy";
    public static final String URL_CONTACT_US = BASE_URL + "app_contact_us";
    public static final String URL_OPEN_BILL = BASE_URL + "download-bill/";*/


    public static Retrofit getRetrofit(String BASE_URL) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(180, TimeUnit.SECONDS);
        httpClient.readTimeout(180, TimeUnit.SECONDS);
        httpClient.writeTimeout(180, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);


        dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(2);
        httpClient.dispatcher(dispatcher);

        Retrofit retrofit = builder.client(httpClient.build()).build();
        Interface service = retrofit.create(Interface.class);
        return retrofit;
    }


    public static Interface getApiService() {
        return getRetroClient().create(Interface.class);
    }

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Interface getApiThirdPartyService() {
        return RetrofitClient.getClient(THIRD_PARTY_BASE_URL, THIRD_PARTY_TOKEN).create(Interface.class);
    }



}
