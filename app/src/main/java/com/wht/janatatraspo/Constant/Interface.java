package com.wht.janatatraspo.Constant;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by User on 2/15/2020.
 */

public interface Interface {

    @FormUrlEncoded
    @POST(IUrls.URL_LOGIN)
    public Call<ResponseBody> POSTLogin(
            @Field("mobile") String mobile_no,
            @Field("notification_token") String notification_token,
            @Field("device_details") String device_details);


    @FormUrlEncoded
    @POST(IUrls.URL_OTP_CHECK)
    Call<ResponseBody> getCheckOtp(
            @Field("mobile") String mobile,
            @Field("otp_number") String otp_number,
            @Field("notification_token") String notification_token);


    @FormUrlEncoded
    @POST(IUrls.URL_RESEND_OTP)
    public Call<ResponseBody> POSTResendOtp(
            @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST(IUrls.URL_USER_REGISTRATION)
    Call<ResponseBody> POSTAllUserRegistration(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password,
            @Field("notification_token") String notification_token,
            @Field("device_details") String device_details,
            @Field("is_driver") String is_driver,
            @Field("gender") String gender,
            @Field("referral_code") String referral_code);

    @FormUrlEncoded
    @POST(IUrls.URL_VEHICLE_LIST)
    Call<ResponseBody> POSTVehicleList(
            @Field("owner_vehicle_id") String owner_vehicle_id,
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("search") String search,
            @Field("page_no") String page_no,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST(IUrls.URL_DRIVER_LIST)
    Call<ResponseBody> POSTDriverList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("is_driver") String is_driver,
            @Field("driver_id") String driver_id,
            @Field("search") String search,
            @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST(IUrls.URL_DASHBOARD_COUNT)
    Call<ResponseBody> POSTDashboardCount(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_GET_PROFILE)
    Call<ResponseBody> POSTGetProfile(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token);


    @Multipart
    @POST(IUrls.URL_ADD_DRIVER)
    Call<ResponseBody> POSTAddDriver(@Part("user_id") RequestBody user_id,
                                     @Part("api_token") RequestBody api_token,
                                     @Part("first_name") RequestBody first_name,
                                     @Part("last_name") RequestBody last_name,
                                     @Part("email") RequestBody email,
                                     @Part("mobile_no") RequestBody mobile_no,
                                     @Part("birthdate") RequestBody birthdate,
                                     @Part("address") RequestBody address,
                                     @Part("city") RequestBody city,
                                     @Part("pan_number") RequestBody pan_number,
                                     @Part("aadhar_number") RequestBody aadhar_number,
                                     @Part("driver_id") RequestBody driver_id,
                                     @Part MultipartBody.Part fileUserImage,
                                     @Part MultipartBody.Part fileAddharFront,
                                     @Part MultipartBody.Part fileAddharBack,
                                     @Part MultipartBody.Part filePancardImg,
                                     @Part MultipartBody.Part filePoliceVerificationImage,
                                     @Part MultipartBody.Part fileDrivingLicence);

    @Multipart
    @POST(IUrls.URL_ADD_VEHICLE)
    Call<ResponseBody> POSTAddVehicle(@Part("user_id") RequestBody user_id,
                                      @Part("api_token") RequestBody api_token,
                                      @Part("vehicle_id") RequestBody vehicle_id,
                                      @Part("vehicle_no") RequestBody vehicle_no,
                                      @Part("weight") RequestBody weight,
                                      @Part("mfg_year") RequestBody mfg_year,
                                      @Part("model_no") RequestBody model_no,
                                      @Part("volume") RequestBody volume,
                                      @Part("capacity") RequestBody capacity,
                                      @Part("registration_no") RequestBody registration_no,
                                      @Part("passing_details") RequestBody passing_details,
                                      @Part("owner_vehicle_id") RequestBody owner_vehicle_id,
                                      @Part("is_valid_reference_code") RequestBody is_valid_reference_code,
                                      @Part("sponsor_id") RequestBody sponsor_id,
                                      @Part("sponsor_name") RequestBody sponsor_name,
                                      @Part("sponsor_first_name") RequestBody sponsor_first_name,
                                      @Part("sponsor_last_name") RequestBody sponsor_last_name,
                                      @Part("sponsor_email") RequestBody sponsor_email,
                                      @Part("sponsor_mobile") RequestBody sponsor_mobile,
                                      @Part("sponsor_dob") RequestBody sponsor_dob,
                                      @Part("sponsor_aadhar_number") RequestBody sponsor_aadhar_number,
                                      @Part("sponsor_designation") RequestBody sponsor_designation,
                                      @Part MultipartBody.Part fileChassis_no_Image,
                                      @Part MultipartBody.Part fileRCBookImage,
                                      @Part MultipartBody.Part fileRegistrationImage,
                                      @Part MultipartBody.Part fileFitnessImg,
                                      @Part MultipartBody.Part filePucImage,
                                      @Part MultipartBody.Part fileOwnerVehicleImage);


    @FormUrlEncoded
    @POST(IUrls.URL_VEHICLE_TYPE_LIST)
    Call<ResponseBody> POSTVehicleTypeList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST(IUrls.URL_VEHICLE_MAKE_LIST)
    Call<ResponseBody> POSTVehicleMakeList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("category_id") String category_id,
            @Field("type") String type);

    @FormUrlEncoded
    @POST(IUrls.URL_ALL_VEHICLE_LIST)
    public Call<ResponseBody> POSTVehicleType(
            @Field("user_id") String user_id,
            @Field("category_id") String category_id,
            @Field("api_token") String api_token,
            @Field("search") String search,
            @Field("type") String type,
            @Field("make") String make,
            @Field("page_no") String page_no);


    @Multipart
    @POST(IUrls.URL_UPDATE_PROFILE_IMAGE)
    Call<ResponseBody> POSTUpdateProfileImage(@Part("user_id") RequestBody user_id,
                                              @Part("api_token") RequestBody api_token,
                                              @Part MultipartBody.Part file);

    @Multipart
    @POST(IUrls.URL_UPDATE_OWNER)
    Call<ResponseBody> POSTUpdateProfile(@Part("user_id") RequestBody user_id,
                                         @Part("api_token") RequestBody api_token,
                                         @Part("first_name") RequestBody first_name,
                                         @Part("last_name") RequestBody last_name,
                                         @Part("email") RequestBody email,
                                         @Part("birthdate") RequestBody birthdate,
                                         @Part("address") RequestBody address,
                                         @Part("business_name") RequestBody business_name,
                                         @Part("bank_name") RequestBody bank_name,
                                         @Part("account_number") RequestBody account_number,
                                         @Part("ifsc_code") RequestBody ifsc_code,
                                         @Part("branch_name") RequestBody branch_name,
                                         @Part("pan_number") RequestBody pan_number,
                                         @Part("gst_number") RequestBody gst_number,
                                         @Part("aadhar_number") RequestBody aadhar_number,
                                         @Part MultipartBody.Part fileOwnerImage,
                                         @Part MultipartBody.Part fileAadharFrontImage,
                                         @Part MultipartBody.Part fileAadharBackImage,
                                         @Part MultipartBody.Part filePanImage,
                                         @Part MultipartBody.Part fileGSTImage);

    @Multipart
    @POST(IUrls.URL_UPDATE_CUSTOMER)
    Call<ResponseBody> POSTUpdateProfileCustomer(
                                         @Part("user_id") RequestBody user_id,
                                         @Part("api_token") RequestBody api_token,
                                         @Part("first_name") RequestBody first_name,
                                         @Part("last_name") RequestBody last_name,
                                         @Part("email") RequestBody email,
                                         @Part("birthdate") RequestBody birthdate,
                                         @Part("customer_type") RequestBody customer_type,
                                         @Part("address") RequestBody address,
                                         @Part("business_name") RequestBody business_name,
                                         @Part("bank_name") RequestBody bank_name,
                                         @Part("account_number") RequestBody account_number,
                                         @Part("ifsc_code") RequestBody ifsc_code,
                                         @Part("branch_name") RequestBody branch_name,
                                         @Part("pan_number") RequestBody pan_number,
                                         @Part("gst_number") RequestBody gst_number,
                                         @Part("aadhar_number") RequestBody aadhar_number,
                                         @Part("billing_name") RequestBody billing_name,
                                         @Part("billing_address") RequestBody billing_address,
                                         @Part("blood_group") RequestBody blood_group
                                         );


    @Multipart
    @POST(IUrls.URL_aadhar_verification)
    Call<ResponseBody> POSTAadharVerification(@Part("driver_id") RequestBody driver_id,
                                              @Part("user_id") RequestBody user_id,
                                              @Part("api_token") RequestBody api_token,
                                              @Part("role") RequestBody role,
                                              @Part("verified_document_status") RequestBody verified_document_status,
                                              @Part("client_id") RequestBody client_id,
                                              @Part("full_name") RequestBody full_name,
                                              @Part("gender") RequestBody gender,
                                              @Part("aadhaar_number") RequestBody aadhaar_number,
                                              @Part("dob") RequestBody dob,
                                              @Part("country") RequestBody country,
                                              @Part("dist") RequestBody dist,
                                              @Part("state") RequestBody state,
                                              @Part("po") RequestBody po,
                                              @Part("loc") RequestBody loc,
                                              @Part("vtc") RequestBody vtc,
                                              @Part("subdist") RequestBody subdist,
                                              @Part("street") RequestBody street,
                                              @Part("house") RequestBody house,
                                              @Part("landmark") RequestBody landmark,
                                              @Part("zip") RequestBody zip,
                                              @Part("profile_image") RequestBody profile_image,
                                              @Part("care_of") RequestBody care_of,
                                              @Part("reference_id") RequestBody reference_id,
                                              @Part("response_json_data") RequestBody jsonObject,
                                              @Part MultipartBody.Part fileAadharFrontImage,
                                              @Part MultipartBody.Part fileAadharBackImage);


    @Multipart
    @POST(IUrls.URL_GST_verification)
    Call<ResponseBody> POSTGSTVerification(@Part("user_id") RequestBody user_id,
                                           @Part("api_token") RequestBody api_token,
                                           @Part("role") RequestBody role,
                                           @Part("verified_document_status") RequestBody verified_document_status,
                                           @Part("gstin_status") RequestBody gstin_status,
                                           @Part("address") RequestBody address,
                                           @Part("date_of_registration") RequestBody date_of_registration,
                                           @Part("business_name") RequestBody business_name,
                                           @Part("gstin") RequestBody gstin,
                                           @Part("response_json_data") RequestBody response_json_data,
                                           @Part MultipartBody.Part fileGSTImage);


    @Multipart
    @POST(IUrls.URL_LICENSE_verification)
    Call<ResponseBody> POSTLicenseVerification(@Part("driver_id") RequestBody driver_id,
                                               @Part("user_id") RequestBody user_id,
                                               @Part("api_token") RequestBody api_token,
                                               @Part("role") RequestBody role,
                                               @Part("verified_document_status") RequestBody verified_document_status,
                                               @Part("temporary_address") RequestBody temporary_address,
                                               @Part("permanent_address") RequestBody permanent_address,
                                               @Part("name") RequestBody name,
                                               @Part("doi") RequestBody doi,
                                               @Part("doe") RequestBody doe,
                                               @Part("license_number") RequestBody license_number,
                                               @Part MultipartBody.Part fileGSTImage);


    @Multipart
    @POST(IUrls.URL_LICENSE_verification)
    Call<ResponseBody> POSTRCVerification(@Part("driver_id") RequestBody driver_id,
                                          @Part("user_id") RequestBody user_id,
                                          @Part("api_token") RequestBody api_token,
                                          @Part("role") RequestBody role,
                                          @Part("verified_document_status") RequestBody verified_document_status,
                                          @Part("owner_vehicle_id") RequestBody owner_vehicle_id,
                                          @Part("rc_number") RequestBody rc_number,
                                          @Part("registration_date") RequestBody registration_date,
                                          @Part("vehicle_category") RequestBody vehicle_category,
                                          @Part("vehicle_chasi_number") RequestBody vehicle_chasi_number,
                                          @Part("vehicle_engine_number") RequestBody vehicle_engine_number,
                                          @Part("maker_model") RequestBody maker_model,
                                          @Part("insurance_company") RequestBody insurance_company,
                                          @Part("insurance_policy_number") RequestBody insurance_policy_number,
                                          @Part("insurance_upto") RequestBody insurance_upto,
                                          @Part("manufacturing_date") RequestBody manufacturing_date,
                                          @Part("registered_at") RequestBody registered_at,
                                          @Part("vehicle_gross_weight") RequestBody vehicle_gross_weight,
                                          @Part("permit_number") RequestBody permit_number,
                                          @Part("permit_issue_date") RequestBody permit_issue_date,
                                          @Part("permit_valid_from") RequestBody permit_valid_from,
                                          @Part("permit_valid_upto") RequestBody permit_valid_upto,
                                          @Part("permit_type") RequestBody permit_type,
                                          @Part("national_permit_number") RequestBody national_permit_number,
                                          @Part("rc_status") RequestBody rc_status,
                                          @Part("national_permit_upto") RequestBody national_permit_upto,
                                          @Part("response_json_data") RequestBody response_json_data);

    @FormUrlEncoded
    @POST(IUrls.URL_STATE_LIST)
    Call<ResponseBody> POSTState(@Field("country_id") String country_id);


    @FormUrlEncoded
    @POST(IUrls.URL_CITY_LIST)
    Call<ResponseBody> POSTCity(@Field("state_id") String state_id);

    @FormUrlEncoded
    @POST(IUrls.URL_TALUKA_LIST)
    Call<ResponseBody> POSTTaluka(@Field("city_id") String city_id);

    @FormUrlEncoded
    @POST(IUrls.URL_VARIENT_LIST)
    Call<ResponseBody> POSTVarient(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("category_id") String category_id
    );

    @FormUrlEncoded
    @POST(IUrls.URL_MATERIAL_LIST)
    Call<ResponseBody> POSTMaterial(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("parent_id") String parent_id
    );

    @FormUrlEncoded
    @POST(IUrls.URL_LOAD_LIST)
    Call<ResponseBody> POSTLoadList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("loader_type") String loader_type
    );

    @FormUrlEncoded
    @POST(IUrls.URL_EARTHMOVER_LIST)
    Call<ResponseBody> POSTEarthMoverList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("page_no") int page_no
    );

    @FormUrlEncoded
    @POST(IUrls.URL_BID_LIST)
    Call<ResponseBody> POSTBidList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("loader_id") String loader_type
    );


    @FormUrlEncoded
    @POST(IUrls.URL_BID_LIST_EARTHMOVER)
    Call<ResponseBody> POSTBidListEarthMover(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("earth_mover_id") String loader_type
    );

    @Multipart
    @POST(IUrls.URL_ADD_LOAD)
    public Call<ResponseBody> POSTaddLoad(
            @Part("user_id") RequestBody user_id,
            @Part("api_token") RequestBody api_token,
            @Part("loader_type") RequestBody loader_type,
            @Part("pickup_lat") RequestBody pickup_lat,
            @Part("pickup_lng") RequestBody pickup_lng,
            @Part("pickup_loaction") RequestBody pickup_loaction,

            @Part("pickup_taluka") RequestBody pickup_taluka,
            @Part("pickup_village") RequestBody pickup_village,
            @Part("pickup_detail_address") RequestBody pickup_detail_address,


            @Part("drop_loaction") RequestBody drop_loaction,
            @Part("drop_lat") RequestBody drop_lat,
            @Part("drop_lng") RequestBody drop_lng,

            @Part("drop_taluka") RequestBody drop_taluka,
            @Part("drop_detail_address") RequestBody drop_detail_address,
            @Part("drop_village") RequestBody drop_village,



            @Part("transport_type") RequestBody transport_type,
            @Part("material") RequestBody material,

            @Part("category") RequestBody category,
            @Part("sub_category") RequestBody sub_category,
            @Part("variant_id") RequestBody variant_id,
            @Part("labour_type") RequestBody labour_type,
            @Part("no_of_count_variant") RequestBody no_of_count_variant,


            @Part("number_of_ton") RequestBody number_of_ton,
            @Part("expected_price") RequestBody expected_price,
            @Part("fixed_per_tone") RequestBody fixed_per_tone,
            @Part("payment_mode") RequestBody payment_mode,
            @Part("volumetric_weight") RequestBody volumetric_weight,
            @Part("length") RequestBody length,
            @Part("breadth") RequestBody breadth,
            @Part("height") RequestBody height,
            @Part("expected_weight") RequestBody expected_weight,
            @Part("remark") RequestBody remark,
            @Part("is_lablor_required") RequestBody is_lablor_required,
            @Part("no_of_labour") RequestBody no_of_labour,
            @Part("required_date") RequestBody required_date,
            @Part("loader_id") RequestBody loader_id,
            @Part("is_negotialable") RequestBody is_negotialable,
            @Part("required_time") RequestBody required_time,
            @Part List<MultipartBody.Part> rcPartList
    );


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_EARTH_MOVER)
    public Call<ResponseBody> POSTaddEarthMoverRequest(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("destination_lat") String destination_lat,
            @Field("destination_lng") String destination_lng,
            @Field("destination_loaction") String destination_loaction,
            @Field("transport_type") String transport_type,
            @Field("is_fixed_or_negotiable") String is_fixed_or_negotiable,
            @Field("expected_price") String expected_price,
            @Field("prices_timestamp") String prices_timestamp,
            @Field("no_of_days") String no_of_days,
            @Field("no_of_hours") String no_of_hours,
            @Field("remark") String remark,
            @Field("required_date") String required_date,
            @Field("required_time") String required_time
    );


    //POST AADHAR VERSION 1 THIRD PARTY API
    @FormUrlEncoded
    @POST(IUrls.URL_AADHAR_CARD_V1_VERIFICATION)
    Call<ResponseBody> POSTAadharVerification(@FieldMap Map<String, Object> param);

    //POST AADHAR VERSION 2 THIRD PARTY API
    @FormUrlEncoded
    @POST(IUrls.URL_AADHAR_CARD_V2_GENRATE_OTP)
    Call<ResponseBody> POSTAadharGenrateOtp(@FieldMap Map<String, Object> param);

    //POST AADHAR VERSION 2 THIRD PARTY API
    @FormUrlEncoded
    @POST(IUrls.URL_AADHAR_CARD_V2_SUBMIT_OTP)
    Call<ResponseBody> POSTAadharSubmitOtp(@FieldMap Map<String, Object> param);

    //POST THIRD PARTY API
    @FormUrlEncoded
    @POST(IUrls.URL_GST_VERIFICATION)
    Call<ResponseBody> POSTGSTVerification(@FieldMap Map<String, Object> param);

    //POST THIRD PARTY API
    @FormUrlEncoded
    @POST(IUrls.URL_RC_VERIFICATION)
    Call<ResponseBody> POSTRCVerification(@FieldMap Map<String, Object> param);

    //POST THIRD PARTY API
    @FormUrlEncoded
    @POST(IUrls.URL_LICENSE_VERIFICATION)
    Call<ResponseBody> POSTLICENSEVerification(@FieldMap Map<String, Object> param);


    //GET Third party api of sales person verifiction
    @GET(IUrls.URL_SALES_PERSON_VERIFICATION)
    Call<ResponseBody> GETVerifySalesPerson(
            @Query("sponsor_id") String sponsor_id,
            @Query("Password") String Password
    );

    @FormUrlEncoded
    @POST(IUrls.URL_ACCEPT_LOAD_BID)
    public Call<ResponseBody> POSTAcceptLoadBid(
            @Field("loader_id") String loader_id,
            @Field("bid_id") String bid_id,
            @Field("user_id") String user_id,
            @Field("api_token") String api_token
            );


    @FormUrlEncoded
    @POST(IUrls.URL_CUSTOMER_PAYMENT_TRANSO)
    public Call<ResponseBody> POSTAcceptPayment(
            @Field("transaction_id") String transaction_id,
            @Field("transaction_date") String transaction_date,
            @Field("amount") String amount,
            @Field("loader_id") String loader_id,
            @Field("payment_status") String payment_status,
            @Field("bid_id") String bid_id,
            @Field("user_id") String user_id,
            @Field("api_token") String api_token
    );

    @FormUrlEncoded
    @POST(IUrls.URL_ACCEPT_EARTHMOVER_BID)
    public Call<ResponseBody> POSTAcceptEarthMoverBid(
            @Field("earth_mover_id") String earth_mover_id,
            @Field("bid_id") String bid_id,
            @Field("user_id") String user_id,
            @Field("api_token") String api_token
    );

/*
    @Multipart
    @POST(IUrls.URL_UPDATE_DRIVER)
    Call<ResponseBody> POSTUpdatedDriver(@Part("user_id") RequestBody user_id,
                                         @Part("api_token") RequestBody api_token,
                                         @Part("first_name") RequestBody first_name,
                                         @Part("last_name") RequestBody last_name,
                                         @Part("email") RequestBody email,
                                         @Part("mobile_no") RequestBody mobile_no,
                                         @Part("birthdate") RequestBody birthdate,
                                         @Part("address") RequestBody address,
                                         @Part("city") RequestBody city,
                                         @Part("pan_number") RequestBody pan_number,
                                         @Part("aadhar_number") RequestBody aadhar_number,
                                         @Part("driver_id") RequestBody driver_id,
                                         @Part MultipartBody.Part fileDrivingLicence,
                                         @Part MultipartBody.Part fileUserImage,
                                         @Part MultipartBody.Part fileAddharFront,
                                         @Part MultipartBody.Part fileAddharBack,
                                         @Part MultipartBody.Part filePancardImg,
                                         @Part MultipartBody.Part filePoliceVerificationImage);
*/



 /*



    @FormUrlEncoded
    @POST(IUrls.URL_ADD_SUB_CATEGORY)
    Call<ResponseBody> POSTAddSubCategory(
            @Field("category_name") String category_name,
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("parent_id") String parent_id,
            @Field("type") String type);


    @FormUrlEncoded
    @POST(IUrls.URL_CATEGORY_LIST)
    Call<ResponseBody> POSTSubCategoryList(@Field("parent_id") String parent_id,
                                           @Field("type") String type);

    @GET(IUrls.URL_COUNTRY_LIST)
    Call<ResponseBody> GETCountry();

  *//*  @GET(IUrls.URL_STATE_LIST)
    Call<ResponseBody> POSTState(@Field("country_id") String state_id);*//*

    @FormUrlEncoded
    @POST(IUrls.URL_STATE_LIST)
    Call<ResponseBody> POSTState(@Field("country_id") String state_id);



    *//*@GET(IUrls.URL_DISTRICT_LIST)
    Call<ResponseBody> GETDistricts();*//*

    @FormUrlEncoded
    @POST(IUrls.URL_DISTRICT_LIST)
    Call<ResponseBody> POSTDistrict(@Field("state_id") String state_id);

    @GET(IUrls.URL_MARIATAL_STATUS_LIST)
    Call<ResponseBody> GETMaritalStatus();

    @Multipart
    @POST(IUrls.URL_UPDATE_PROFILE)
    Call<ResponseBody> POSTUpdateProfile(@Part("user_id") RequestBody user_id,
                                         @Part("first_name") RequestBody first_name,
                                         @Part("last_name") RequestBody last_name,
                                         @Part("gender") RequestBody gender,
                                         @Part("lives_in") RequestBody lives_in,
                                         @Part("maritial_status") RequestBody maritial_status,
                                         @Part("district") RequestBody district,
                                         @Part("state") RequestBody state,
                                         @Part("country") RequestBody country,
                                         @Part("work_experience") RequestBody work_experience,
                                         @Part("bio") RequestBody bio,
                                         @Part MultipartBody.Part file,
                                         @Part("api_token") RequestBody api_token,
                                         @Part("birthdate") RequestBody birthdate);

    @Multipart
    @POST(IUrls.URL_UPDATE_COVER_IMAGE)
    Call<ResponseBody> POSTUpdateCoverImage(@Part("user_id") RequestBody user_id,
                                            @Part MultipartBody.Part file,
                                            @Part("api_token") RequestBody api_token);

    @Multipart
    @POST(IUrls.URL_UPDATE_PROFILE_IMAGE)
    Call<ResponseBody> POSTUpdateProfileImage(@Part("user_id") RequestBody user_id,
                                              @Part MultipartBody.Part file,
                                              @Part("api_token") RequestBody api_token);


    @Multipart
    @POST(IUrls.URL_ADD_N_UPDATE_BUSINESS)
    Call<ResponseBody> POSTAddBusiness(@Part("user_id") RequestBody user_id,
                                       @Part("business_name") RequestBody business_name,
                                       @Part("category") RequestBody category,
                                       @Part("sub_categories") RequestBody sub_categories,
                                       @Part("address") RequestBody address,
                                       @Part("city") RequestBody city,
                                       @Part("state") RequestBody state,
                                       @Part("country") RequestBody country,
                                       @Part("webssite") RequestBody webssite,
                                       @Part("phone") RequestBody phone,
                                       @Part("mobile") RequestBody mobile,
                                       @Part("email") RequestBody email,
                                       @Part("about_us") RequestBody about_us,
                                       @Part("lat") RequestBody lat,
                                       @Part("lng") RequestBody lng,
                                       @Part("date_of_establish") RequestBody date_of_establish,
                                       @Part("business_hrs") RequestBody business_hrs,
                                       @Part MultipartBody.Part fileCoverPic,
                                       @Part MultipartBody.Part fileLogo,
                                       @Part("api_token") RequestBody api_token,
                                       @Part("no_of_people_allowd") RequestBody no_of_people_allowd,
                                       @Part("add_article") RequestBody add_article,
                                       @Part("add_magazine") RequestBody add_magazine,
                                       @Part("add_news") RequestBody add_news,
                                       @Part("business_id") RequestBody business_id);

    @Multipart
    @POST(IUrls.URL_ADD_POST)
    Call<ResponseBody> POSTAddPost(@Part("api_token") RequestBody api_token,
                                   @Part("user_id") RequestBody user_id,
                                   @Part("type") RequestBody type,
                                   @Part("ref_id") RequestBody ref_id,
                                   @Part("privacy_id") RequestBody privacy_id,
                                   @Part("text") RequestBody text,
                                   @Part("lat") RequestBody lat,
                                   @Part("lng") RequestBody lng,
                                   @Part("is_friend_tag") RequestBody is_friend_tag,
                                   @Part("tag_friends_ids") RequestBody tag_friends_ids,
                                   @Part("is_attachment") RequestBody is_attachment,
                                   @Part List<MultipartBody.Part> RoomPartList,
                                   @Part("is_check_in") RequestBody is_check_in,
                                   @Part("post_id") RequestBody post_id,
                                   @Part("is_ngo") RequestBody is_ngo,
                                   @Part("is_help") RequestBody is_help);


    @Multipart
    @POST(IUrls.URL_ADD_MAGZINE)
    Call<ResponseBody> POSTAddMagazine(@Part("user_id") RequestBody user_id,
                                       @Part("api_token") RequestBody api_token,
                                       @Part("business_id") RequestBody business_id,
                                       @Part("title") RequestBody title,
                                       @Part("magazine_id") RequestBody magazine_id,
                                       @Part MultipartBody.Part fileCoverPic);


    @Multipart
    @POST(IUrls.URL_ADD_N_UPDATE_SERVICE)
    Call<ResponseBody> POSTAddService(@Part("user_id") RequestBody user_id,
                                      @Part("service_name") RequestBody service_name,
                                      @Part("category") RequestBody category,
                                      @Part("sub_categories") RequestBody sub_categories,
                                      @Part("address") RequestBody address,
                                      @Part("city") RequestBody city,
                                      @Part("state") RequestBody state,
                                      @Part("country") RequestBody country,
                                      @Part("webssite") RequestBody webssite,
                                      @Part("phone") RequestBody phone,
                                      @Part("mobile") RequestBody mobile,
                                      @Part("email") RequestBody email,
                                      @Part("about_us") RequestBody about_us,
                                      @Part("lat") RequestBody lat,
                                      @Part("lng") RequestBody lng,
                                      @Part("date_of_establish") RequestBody date_of_establish,
                                      @Part("api_token") RequestBody api_token,
                                      @Part MultipartBody.Part fileCoverPic,
                                      @Part MultipartBody.Part fileLogo,
                                      @Part("service_id") RequestBody service_id);


    // @Part("banner_image") RequestBody banner_image,
    // @Part("logo") RequestBody logo,


    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_LIST)
    Call<ResponseBody> POSTAllBusinessList(
            @Field("user_id") String user_id,
            @Field("business_id") String business_id,
            @Field("page_no") String page_no,
            @Field("search") String search,
            @Field("api_token") String api_token,
            @Field("my_bussines_list") String myuser_id,
            @Field("is_skip_login") String is_skip_login,
            @Field("category_id") String category_id,
            @Field("subCategory_id") String subCategory_id);


    @FormUrlEncoded
    @POST(IUrls.URL_RECENT_VIEWED_BUSINESS_LIST)
    Call<ResponseBody> POSTRecentViewBusinessList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_BLOCK_BUSINESS_LIST)
    Call<ResponseBody> POSTBlockBusinessList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_SERVICE_LIST)
    Call<ResponseBody> POSTAllServiceList(
            @Field("user_id") String user_id,
            @Field("service_id") String business_id,
            @Field("page_no") String page_no,
            @Field("search") String search,
            @Field("api_token") String api_token,
            @Field("my_service_list") String myuser_id,
            @Field("is_skip_login") String is_skip_login);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_BUSINESS_HOURS)
    Call<ResponseBody> POSTADDBusinessHours(
            @Field("user_id") String user_id,
            @Field("business_hrs") JSONArray jsonArray,
            @Field("business_id") String business_id,
            @Field("action") String action,
            @Field("business_hrs_flag") String business_hrs_flag,
            @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_ADD_BUSINESS_HAPPY_HOURS)
    Call<ResponseBody> POSTBusinessHappyHours(
            @Field("user_id") String user_id,
            @Field("from_time") String from_time,
            @Field("to_time") String to_time,
            @Field("discount") String discount,
            @Field("discount_type") String discount_type,
            @Field("details") String details,
            @Field("is_active") String is_active,
            @Field("business_id") String business_id,
            @Field("happy_hr_id") String happy_hr_id,
            @Field("action") String action,
            @Field("api_token") String api_token,
            @Field("is_skip_login") String is_skip_login);

    @FormUrlEncoded
    @POST(IUrls.URL_MY_BUSINESS_SERVICES_LIST)
    Call<ResponseBody> POSTMyBusinessServices(
            @Field("user_id") String user_id,
            @Field("business_id") String business_id);

    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_SERVICE_LIST)
    Call<ResponseBody> POSTBusinessServices(
            @Field("business_category_id") String business_category_id,
            @Field("user_id") String user_id,
            @Field("business_id") String business_id);

    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_HIGHTLIGHTS)
    Call<ResponseBody> POSTMyBusinessHighlights(
            @Field("business_id") String business_id,
            @Field("page_no") String page_no,
            @Field("is_skip_login") String is_skip_login,
            @Field("api_token") String api_token);


    //USER PROFILE
    @FormUrlEncoded
    @POST(IUrls.URL_USER_EDUCATION_LIST)
    Call<ResponseBody> POSTUserEducation(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(IUrls.URL_USER_JOB_LIST)
    Call<ResponseBody> POSTJobList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(IUrls.URL_USER_PROFILE_INFO)
    Call<ResponseBody> POSTUserProfileInfo(
            @Field("user_id") String user_id,
            @Field("loginuser_id") String loginuser_id,
            @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_NEWS_LIST)
    Call<ResponseBody> getNewsList(
            @Field("is_skip_login") String is_skip_login,
            @Field("category_id") String category_id,
            @Field("search") String search,
            @Field("page_no") String page_no,
            @Field("api_token") String api_token);


    // Vacancy Webcalls/////////////////////////////////


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_VACANCY)
    Call<ResponseBody> POSTAddVacancy(@Field("user_id") String user_id,
                                      @Field("business_id") String business_id,
                                      @Field("title") String title,
                                      @Field("description") String description,
                                      @Field("job_type") String job_type,
                                      @Field("salary_range") String salary_range,
                                      @Field("status") String status,
                                      @Field("api_token") String api_token,
                                      @Field("vacancy_id") String vacancy_id);

    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_VACANCY_LIST)
    Call<ResponseBody> POSTBusinessVacancyList(@Field("user_id") String user_id,
                                               @Field("business_id") String business_id,
                                               @Field("api_token") String api_token,
                                               @Field("is_skip_login") String is_skip_login,
                                               @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_RESUME_LIST)
    Call<ResponseBody> POSTResumeList(@Field("user_id") String user_id,
                                      @Field("business_id") String business_id,
                                      @Field("vacancy_id") String vacancy_id,
                                      @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_MY_BUSINESS_VACANCY_STATUS)
    Call<ResponseBody> POSTMyBusinessVacancyStatus(@Field("user_id") String user_id,
                                                   @Field("business_id") String business_id,
                                                   @Field("vacancy_id") String vacancy_id,
                                                   @Field("status") String status,
                                                   @Field("api_token") String api_token);

*//*    @FormUrlEncoded
    @POST(IUrls.)
    Call<ResponseBody> POSTApplyOnVacancy(@Field("user_id") String user_id,
                                          @Field("vacancy_id") String vacancy_id,
                                          @Field("api_token") String api_token);*//*

    @Multipart
    @POST(IUrls.URL_APPLY_ON_VACANCY)
    Call<ResponseBody> POSTApplyOnVacancy(@Part("user_id") RequestBody user_id,
                                          @Part("vacancy_id") RequestBody vacancy_id,
                                          @Part MultipartBody.Part fileCoverPic,
                                          @Part("api_token") RequestBody api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_VACANCY)
    Call<ResponseBody> POSTDeleteVacancy(@Field("user_id") String user_id,
                                         @Field("business_id") String business_id,
                                         @Field("vacancy_id") String vacancy_id,
                                         @Field("api_token") String api_token);


//////////////////////////////////////////////////////////////////////////


    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_PAGE_ROLE)
    Call<ResponseBody> POSTBusinessPageRole(@Field("user_id") String user_id,
                                            @Field("business_id") String business_id,
                                            @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_PRODUCTS)
    Call<ResponseBody> POSTBusinessProducts(@Field("user_id") String user_id,
                                            @Field("business_id") String business_id,
                                            @Field("api_token") String api_token,
                                            @Field("is_skip_login") String is_skip_login,
                                            @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_EVENTS)
    Call<ResponseBody> POSTBusinessEvents(@Field("user_id") String user_id,
                                          @Field("business_id") String business_id,
                                          @Field("api_token") String api_token,
                                          @Field("is_skip_login") String is_skip_login,
                                          @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_EVENT_REPORT)
    Call<ResponseBody> POSTBusinessEventsReport(@Field("user_id") String user_id,
                                                @Field("business_id") String business_id,
                                                @Field("event_id") String event_id,
                                                @Field("description") String description,
                                                @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_OFFER_LIST)
    Call<ResponseBody> POSTOfferList(@Field("user_id") String user_id,
                                     @Field("business_id") String business_id,
                                     @Field("page_no") String page_no,
                                     @Field("api_token") String api_token,
                                     @Field("is_skip_login") String is_skip_login);


    @FormUrlEncoded
    @POST(IUrls.URL_EVENT_INTEREST_GOING)
    Call<ResponseBody> POSTBusinessEventsINTERESTandGOING(@Field("user_id") String user_id,
                                                          @Field("event_id") String event_id,
                                                          @Field("type") String type,
                                                          @Field("flag") String flag,
                                                          @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_INTERESTED_MEMBER_LIST)
    Call<ResponseBody> POSTBusinessEventsINTERESTandGOINGMemberList(@Field("user_id") String user_id,
                                                                    @Field("type") String type,
                                                                    @Field("event_id") String event_id,
                                                                    @Field("business_id") String business_id,
                                                                    @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_USER_JOB)
    Call<ResponseBody> POSTAddJob(@Field("user_id") String user_id,
                                  @Field("job_profile") String job_profile,
                                  @Field("place_of_work") String place_of_work,
                                  @Field("work_experience") String work_experience,
                                  @Field("city_name") String city_name,
                                  @Field("from_year") String from_year,
                                  @Field("to_year") String to_year,
                                  @Field("cureent_working") String cureent_working,
                                  @Field("job_id") String job_id,
                                  @Field("api_token") String api_token);

*/


/*
  Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("user_id",SharedPref.getPrefs(_act, IConstant.USER_ID));
        objectMap.put("job_profile",SharedPref.getPrefs(_act, IConstant.USER_ID));
        objectMap.put("place_of_work",SharedPref.getPrefs(_act, IConstant.USER_ID));
        objectMap.put("work_experience",SharedPref.getPrefs(_act, IConstant.USER_ID));
        objectMap.put("city_name",SharedPref.getPrefs(_act, IConstant.USER_ID));
        objectMap.put("from_year",SharedPref.getPrefs(_act, IConstant.USER_ID));
        objectMap.put("to_year",SharedPref.getPrefs(_act, IConstant.USER_ID));
        objectMap.put("cureent_working",SharedPref.getPrefs(_act, IConstant.USER_ID));
        objectMap.put("job_id",SharedPref.getPrefs(_act, IConstant.USER_ID));
        objectMap.put("api_token",SharedPref.getPrefs(_act, IConstant.USER_ID));

 @FormUrlEncoded
    @POST(IUrls.URL_ADD_USER_JOB)
    Call<ResponseBody> POSTAddJob(@FieldMap Map<String,Object> job_id);*/


   /* @FormUrlEncoded
    @POST(IUrls.URL_ADD_USER_EDUCATION)
    Call<ResponseBody> POSTAddEducation(@Field("user_id") String user_id,
                                        @Field("school_name") String school_name,
                                        @Field("from_year") String from_year,
                                        @Field("to_year") String to_year,
                                        @Field("education") String education,
                                        @Field("current_education") String current_education,
                                        @Field("education_id") String education_id,
                                        @Field("api_token") String api_token);*/


    /*@FormUrlEncoded
    @POST(IUrls.URL_DELETE_USER_JOB)
    Call<ResponseBody> POSTDeleteUserJob(@Field("user_id") String user_id,
                                         @Field("job_id") String order_id);

    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_USER_EDUCATION)
    Call<ResponseBody> POSTDeleteUserEducation(@Field("user_id") String user_id,
                                               @Field("education_id") String education_id);

    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_REVIEWS)
    Call<ResponseBody> POSTBusinessReviews(@Field("business_id") String business_id,
                                           @Field("api_token") String api_token,
                                           @Field("is_skip_login") String is_skip_login);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_BUSINESS_REVIEWS)
    public Call<ResponseBody> PostAddBusinessReviews(@Field("user_id") String user_id,
                                                     @Field("review") String review,
                                                     @Field("rate") String rate,
                                                     @Field("business_id") String business_id,
                                                     @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_SERVICE_REVIEWS)
    public Call<ResponseBody> PostAddServiceReviews(@Field("user_id") String user_id,
                                                    @Field("review") String review,
                                                    @Field("rate") String rate,
                                                    @Field("service_id") String service_id,
                                                    @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_SERVICE_REVIEWS_LIST)
    public Call<ResponseBody> PostServiceReviewsList(@Field("user_id") String user_id,
                                                     @Field("is_skip_login") String is_skip_login,
                                                     @Field("service_id") String service_id,
                                                     @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_PRODUCT_ENQUIRY)
    Call<ResponseBody> POSTSendEnquiry(
            @Field("user_id") String user_id,
            @Field("product_id") String product_id,
            @Field("api_token") String api_token);


    @Multipart
    @POST(IUrls.URL_ADD_PRODUCT)
    Call<ResponseBody> POSTAddAndUpdateProduct(@Part("user_id") RequestBody user_id,
                                               @Part("business_id") RequestBody business_id,
                                               @Part("product_name") RequestBody product_name,
                                               @Part("description") RequestBody description,
                                               @Part("terms_condition") RequestBody terms_condition,
                                               @Part("price") RequestBody price,
                                               @Part("product_condition") RequestBody product_condition,
                                               @Part("product_id") RequestBody product_id,
                                               @Part MultipartBody.Part file,
                                               @Part("api_token") RequestBody api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_PRODUCT)
    Call<ResponseBody> POSTDeleteProduct(
            @Field("user_id") String user_id,
            @Field("business_id") String business_id,
            @Field("product_id") String product_id,
            @Field("api_token") String api_token);


    @Multipart
    @POST(IUrls.URL_ADD_OFFER)
    Call<ResponseBody> POSTAddAndUpdateOffers(@Part("user_id") RequestBody user_id,
                                              @Part("business_id") RequestBody business_id,
                                              @Part("name") RequestBody name,
                                              @Part("offer_type") RequestBody offer_type,
                                              @Part("offer_amount") RequestBody offer_amount,
                                              @Part("from_date") RequestBody from_date,
                                              @Part("to_date") RequestBody to_date,
                                              @Part("offer_code") RequestBody offer_code,
                                              @Part("description") RequestBody description,
                                              @Part("terms") RequestBody terms,
                                              @Part MultipartBody.Part file,
                                              @Part("offer_id") RequestBody offer_id,
                                              @Part("api_token") RequestBody api_token);

    @Multipart
    @POST(IUrls.URL_ADD_BUSINESS_NEWS)
    Call<ResponseBody> POSTAddAndUpdateBusinessNews(@Part("user_id") RequestBody user_id,
                                                    @Part("api_token") RequestBody api_token,
                                                    @Part("business_id") RequestBody business_id,
                                                    @Part("category_id") RequestBody category_id,
                                                    @Part("title") RequestBody title,
                                                    @Part("description") RequestBody description,
                                                    @Part("news_id") RequestBody news_id,
                                                    @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_OFFER)
    Call<ResponseBody> POSTDeleteOffer(
            @Field("user_id") String user_id,
            @Field("business_id") String business_id,
            @Field("offer_id") String product_id,
            @Field("api_token") String api_token);


    @Multipart
    @POST(IUrls.URL_ADD_HIGHTLIGHTS)
    Call<ResponseBody> POSTAddHightLights(@Part("user_id") RequestBody user_id,
                                          @Part("title") RequestBody title,
                                          @Part("business_id") RequestBody business_id,
                                          @Part("api_token") RequestBody api_token,
                                          @Part("lat") RequestBody lat,
                                          @Part("lng") RequestBody lng,
                                          @Part MultipartBody.Part file);


    @Multipart
    @POST(IUrls.URL_ADD_SERVICE_HIGHTLIGHTS)
    Call<ResponseBody> POSTAddSevicesHightLights(@Part("user_id") RequestBody user_id,
                                                 @Part("title") RequestBody title,
                                                 @Part("service_id") RequestBody service_id,
                                                 @Part("api_token") RequestBody api_token,
                                                 @Part MultipartBody.Part file);


    @Multipart
    @POST(IUrls.URL_ADD_EVENT)
    Call<ResponseBody> POSTAddAndUpdateEvent(@Part("user_id") RequestBody user_id,
                                             @Part("business_id") RequestBody business_id,
                                             @Part("type") RequestBody type,
                                             @Part("title") RequestBody title,
                                             @Part("description") RequestBody description,
                                             @Part("event_start_date") RequestBody event_start_date,
                                             @Part("event_end_date") RequestBody event_end_date,
                                             @Part("no_of_people") RequestBody no_of_people,
                                             @Part("location") RequestBody location,
                                             @Part("lat") RequestBody lat,
                                             @Part("lng") RequestBody lng,
                                             @Part MultipartBody.Part file,
                                             @Part("event_id") RequestBody event_id,
                                             @Part("api_token") RequestBody api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_CHECK_USERNAME)
    Call<ResponseBody> GETCheckUsername(@Field("username") String username);


    @FormUrlEncoded
    @POST(IUrls.URL_PRODUCT_ENQUIRY_LIST)
    Call<ResponseBody> POSTProductEnqiryList(
            @Field("user_id") String user_id,
            @Field("business_id") String business_id,
            @Field("product_id") String product_id,
            @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_EVENT)
    Call<ResponseBody> POSTDeleteEvent(@Field("business_id") String business_id,
                                       @Field("user_id") String user_id,
                                       @Field("api_token") String api_token,
                                       @Field("event_id") String event_id);


    @FormUrlEncoded
    @POST(IUrls.URL_SUGGESTION_LIST)
    Call<ResponseBody> POSTSuggestionList(@Field("business_id") String business_id,
                                          @Field("api_token") String api_token,
                                          @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_BUSSINESS_SUGGESTION)
    Call<ResponseBody> POSTAddBussinessSuggestion(@Field("user_id") String user_id,
                                                  @Field("business_id") String business_id,
                                                  @Field("title") String title,
                                                  @Field("description") String description,
                                                  @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_SHOUTOUT_LIST)
    Call<ResponseBody> POSTShoutoutList(
            @Field("business_id") String business_id,
            @Field("api_token") String api_token,
            @Field("is_skip_login") String is_skip_login,
            @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_SHOUTOUT)
    Call<ResponseBody> POSTAddShoutOut(@Field("user_id") String user_id,
                                       @Field("business_id") String business_id,
                                       @Field("question") String question,
                                       @Field("shoutout_id") String shoutout_id,
                                       @Field("api_token") String api_token);


    @GET(IUrls.URL_DELETE_REASON)
    Call<ResponseBody> getBusinessDeleteReason();


    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_DELETE)
    Call<ResponseBody> POSTDeleteBusiness(@Field("user_id") String user_id,
                                          @Field("business_id") String business_id,
                                          @Field("api_token") String api_token,
                                          @Field("reason_id") String reason_id,
                                          @Field("reason") String reason);

    @FormUrlEncoded
    @POST(IUrls.URL_DEATIVATE_ACC)
    Call<ResponseBody> POSTDeactivateMyAcc(@Field("user_id") String user_id,
                                           @Field("api_token") String api_token,
                                           @Field("reason_id") String reason_id,
                                           @Field("reason") String reason);


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_SERVICE)
    Call<ResponseBody> POSTDeleteService(@Field("user_id") String user_id,
                                         @Field("service_id") String service_id,
                                         @Field("api_token") String api_token,
                                         @Field("reason_id") String reason_id,
                                         @Field("reason") String reason);


    @FormUrlEncoded
    @POST(IUrls.URL_GENRAL_PAGE_DETAILS)
    Call<ResponseBody> POSTGenralPageDetails(@Field("user_id") String user_id,
                                             @Field("business_id") String business_id,
                                             @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_GENRAL_PAGE_UPDATE)
    Call<ResponseBody> POSTGenralPageUpdate(@Field("user_id") String user_id,
                                            @Field("business_id") String business_id,
                                            @Field("api_token") String api_token,
                                            @Field("visibility") String visibility);

    @FormUrlEncoded
    @POST(IUrls.URL_SERVICE_GENRAL_PAGE_VISIBILITY)
    Call<ResponseBody> POSTServiceGenralPageDetails(@Field("user_id") String user_id,
                                                    @Field("service_id") String business_id,
                                                    @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_SERVICE_GENRAL_PAGE_UPDATE_VISIBILITY)
    Call<ResponseBody> POSTServiceGenralPageUpdate(@Field("user_id") String user_id,
                                                   @Field("service_id") String business_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("visibility") String visibility);


    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_LIKE_UNLIKE)
    Call<ResponseBody> POSTBusinessLikeUnlike(@Field("user_id") String user_id,
                                              @Field("business_id") String business_id,
                                              @Field("api_token") String api_token,
                                              @Field("like_flag") String like_flag);

    @FormUrlEncoded
    @POST(IUrls.URL_SERVICE_LIKE_UNLIKE)
    Call<ResponseBody> POSTServiceLikeUnlike(@Field("user_id") String user_id,
                                             @Field("services_id") String business_id,
                                             @Field("api_token") String api_token,
                                             @Field("like_flag") String like_flag);


    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_BLOCK)
    public Call<ResponseBody> POSTBlockBusiness(
            @Field("business_id") String business_id,
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("is_block") String is_block);


    @GET(IUrls.URL_GET_DOCUMENT_LIST)
    Call<ResponseBody> GETDocumentList();


    @Multipart
    @POST(IUrls.URL_BUSINESS_VERIFICATION_REQ)
    Call<ResponseBody> POSTSendReq(@Part("user_id") RequestBody user_id,
                                   @Part("business_id") RequestBody business_id,
                                   @Part("api_token") RequestBody api_token,
                                   @Part("username") RequestBody username,
                                   @Part("fullname") RequestBody fullname,
                                   @Part("document_id") RequestBody document_id,
                                   @Part("know_as") RequestBody know_as,
                                   @Part MultipartBody.Part fileLogo);

    @Multipart
    @POST(IUrls.URL_USER_ACCOUNT_VERIFICATION)
    Call<ResponseBody> POSTUSerAccVerification(@Part("user_id") RequestBody user_id,
                                               @Part("api_token") RequestBody api_token,
                                               @Part("username") RequestBody username,
                                               @Part("fullname") RequestBody fullname,
                                               @Part("document_id") RequestBody document_id,
                                               @Part("know_as") RequestBody know_as,
                                               @Part MultipartBody.Part fileLogo);


    @Multipart
    @POST(IUrls.URL_SERVICE_VERIFICATION)
    Call<ResponseBody> POSTServiceVerification(@Part("user_id") RequestBody user_id,
                                               @Part("service_id") RequestBody business_id,
                                               @Part("api_token") RequestBody api_token,
                                               @Part("username") RequestBody username,
                                               @Part("fullname") RequestBody fullname,
                                               @Part("document_id") RequestBody document_id,
                                               @Part("know_as") RequestBody know_as,
                                               @Part MultipartBody.Part fileLogo);


    @Multipart
    @POST(IUrls.URL_ADD_SERVICE_PACKAGE)
    Call<ResponseBody> POSTAddServicePackage(@Part("user_id") RequestBody user_id,
                                             @Part("api_token") RequestBody api_token,
                                             @Part("service_id") RequestBody service_id,
                                             @Part("service_name") RequestBody service_name,
                                             @Part("price") RequestBody price,
                                             @Part("amount") RequestBody amount,
                                             @Part("description") RequestBody description,
                                             @Part("appointment_hours") RequestBody appointment_hours,
                                             @Part("appointment_min") RequestBody appointment_min,
                                             @Part("service_extra_id") RequestBody service_extra_id,
                                             @Part MultipartBody.Part fileLogo);


    @Multipart
    @POST(IUrls.URL_BUSINESS_COUPONS_AND_OFFERS)
    Call<ResponseBody> POSTAddAndUpdateOffers_Coupons(@Part("user_id") RequestBody user_id,
                                                      @Part("business_id") RequestBody business_id,
                                                      @Part("api_token") RequestBody api_token,
                                                      @Part("name") RequestBody name,
                                                      @Part("code_type") RequestBody code_type,
                                                      @Part("from_date") RequestBody from_date,
                                                      @Part("to_date") RequestBody to_date,
                                                      @Part("coupons_code") RequestBody coupons_code,
                                                      @Part("description") RequestBody description,
                                                      @Part("coupons_id") RequestBody coupons_id,
                                                      @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_COUPONS_AND_OFFERS_LIST)
    public Call<ResponseBody> POSTCouponAndOfferList(@Field("user_id") String user_id,
                                                     @Field("business_id") String business_id,
                                                     @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_SPECIAL_OFFERS)
    public Call<ResponseBody> POSTAllOfferList(@Field("user_id") String user_id,
                                               @Field("api_token") String api_token,
                                               @Field("is_skip_login") String is_skip_login,
                                               @Field("page_no") String page_no,
                                               @Field("city_id") String city_id);


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_BUSINESS_COUPONS)
    public Call<ResponseBody> POSTDeleteCoupon(@Field("user_id") String user_id,
                                               @Field("business_id") String business_id,
                                               @Field("coupons_id") String coupons_id,
                                               @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_SERVICE_PACKAGE)
    public Call<ResponseBody> POSTServicePackage(@Field("user_id") String user_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("service_id") String service_id);

    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_PAGE_MANAGEMENT)
    public Call<ResponseBody> POSTPageManagement(@Field("user_id") String user_id,
                                                 @Field("business_id") String business_id,
                                                 @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_USER_LOGS)
    public Call<ResponseBody> POSTActivityLogs(@Field("user_id") String user_id,
                                               @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_SERVICE_PAGE_MANAGEMENT_HISTORY)
    public Call<ResponseBody> POSTServicePageManagement(@Field("user_id") String user_id,
                                                        @Field("service_id") String service_id,
                                                        @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_SERVICE_PACKAGE)
    public Call<ResponseBody> POSTServicePackageDelete(@Field("user_id") String user_id,
                                                       @Field("api_token") String api_token,
                                                       @Field("service_id") String service_id,
                                                       @Field("pacakge_id") String pacakge_id);


    @GET(IUrls.URL_INTERESTED_DOMAIN_LIST)
    Call<ResponseBody> GETInterestedDomain();


    @GET(IUrls.URL_PAGE_ROLE_TYPE)
    Call<ResponseBody> GETPageRoleType();


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_INTEREST)
    Call<ResponseBody> POSTAddInterest(@Field("user_id") String user_id,
                                       @Field("api_token") String api_token,
                                       @Field("interest_id") String interest_id);


    @FormUrlEncoded
    @POST(IUrls.URL_ALL_FRIENDS_LIST)
    public Call<ResponseBody> POSTAllFriendsList(@Field("user_id") String user_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("search") String search);


    @FormUrlEncoded
    @POST(IUrls.URL_CHAT_USER_LIST)
    public Call<ResponseBody> POSTChartFriendsList(@Field("user_id") String user_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("chat_type") String chat_type,
                                                   @Field("ref_id") String ref_id,
                                                   @Field("search") String search,
                                                   @Field("page_no") int page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_PAGE_ROLE_SUGGESTION_FRINDS)
    public Call<ResponseBody> POSTPageRoleSuggestionFriendsList(@Field("user_id") String user_id,
                                                                @Field("business_id") String business_id,
                                                                @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_BLOCK_FRIENDS_LIST)
    public Call<ResponseBody> POSTBlockFriendsList(@Field("user_id") String user_id,
                                                   @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_SUGGESTION_FRIENDS_LIST)
    public Call<ResponseBody> POSTSuggestionFriendsList(@Field("user_id") String user_id,
                                                        @Field("api_token") String api_token,
                                                        @Field("search") String search);


    @FormUrlEncoded
    @POST(IUrls.URL_ROLE_REQ_LIST)
    public Call<ResponseBody> POSTPageRoleReqList(@Field("user_id") String user_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("search") String search);


    @FormUrlEncoded
    @POST(IUrls.URL_SEND_FRIENDS_REQUEST)
    public Call<ResponseBody> POSTSendFreindsReq(@Field("user_id") String user_id,
                                                 @Field("friends_id") String friends_id,
                                                 @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_PAGE_ROLE_REQ_ACCEPT_AND_REJECT)
    public Call<ResponseBody> POSTPageRoleRecAcceptAndReject(@Field("user_id") String user_id,
                                                             @Field("business_id") String business_id,
                                                             @Field("is_approved") String is_approved,
                                                             @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_SEND_AND_RECEIVED_REQUEST_LIST)
    public Call<ResponseBody> POSTReqList(@Field("user_id") String user_id,
                                          @Field("action") String action,
                                          @Field("api_token") String api_token,
                                          @Field("search") String search);

    @FormUrlEncoded
    @POST(IUrls.URL_ACCEPT_REJECT_BLOCK)
    public Call<ResponseBody> POSTAcceptRejectBlock(
            @Field("user_id") String user_id,
            @Field("friends_id") String friends_id,
            @Field("action") String action,
            @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_UNFRIEND)
    public Call<ResponseBody> POSTUnFriend(
            @Field("user_id") String user_id,
            @Field("friends_id") String friends_id,
            @Field("api_token") String action);


    @FormUrlEncoded
    @POST(IUrls.URL_SERVICE_HIGHTLIGHTS)
    public Call<ResponseBody> POSTServiceHighlights(@Field("user_id") String user_id,
                                                    @Field("is_skip_login") String is_skip_login,
                                                    @Field("service_id") String service_id,
                                                    @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_BLOCK_SERVICE)
    public Call<ResponseBody> POSTBlockService(@Field("service_id") String service_id,
                                               @Field("user_id") String user_id,
                                               @Field("api_token") String api_token,
                                               @Field("is_block") String is_block);


    @FormUrlEncoded
    @POST(IUrls.URL_REMOVE_FROM_SUGGESTION_LIST)
    public Call<ResponseBody> POSTRemoveFromSuggestionList(@Field("user_id") String user_id,
                                                           @Field("other_user_id") String other_user_id,
                                                           @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_REMOVE_FRINDS_FROM_SUGGESTION_LIST)
    public Call<ResponseBody> POSTRemoveFromPageRoleSuggestionList(@Field("user_id") String user_id,
                                                                   @Field("other_user_id") String other_user_id,
                                                                   @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_SEND_REQUEST_FOR_PAGE_ROLE)
    public Call<ResponseBody> POSTSendAndCancelReqOfPageRole(@Field("user_id") String user_id,
                                                             @Field("business_id") String business_id,
                                                             @Field("user_type") String user_type,
                                                             @Field("request_user_id") String request_user_id,
                                                             @Field("request_type") String request_type,
                                                             @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_NEWS_LIST)
    Call<ResponseBody> POSTBusinessNewsList(@Field("user_id") String user_id,
                                            @Field("api_token") String api_token,
                                            @Field("business_id") String business_id);

    @FormUrlEncoded
    @POST(IUrls.URL_MAGZINE_LIST)
    Call<ResponseBody> POSTBusinessMagzineList(@Field("user_id") String user_id,
                                               @Field("api_token") String api_token,
                                               @Field("business_id") String business_id);


    ///Some Confucsion here//////////////////////////////////////////////////

    @FormUrlEncoded
    @POST(IUrls.URL_HOME_WEBCALL)
    Call<ResponseBody> POSTHome(@Field("user_id") String user_id,
                                @Field("api_token") String api_token,
                                @Field("is_skip_login") String is_skip_login,
                                @Field("city_id") String city_id);


    @FormUrlEncoded
    @POST(IUrls.URL_DASHBOARD_WEBCALL)
    Call<ResponseBody> POSTDashboard(@Field("user_id") String user_id,
                                     @Field("api_token") String api_token,
                                     @Field("is_skip_login") String is_skip_login,
                                     @Field("city_id") String city_id);


    @FormUrlEncoded
    @POST(IUrls.URL_SUBSCRIBE_NEW_AND_MAGZINE)
    Call<ResponseBody> POSTSubscribe(@Field("user_id") String user_id,
                                     @Field("api_token") String api_token,
                                     @Field("subscribe") String subscribe);

    ///Some Confucsion here//////////////////////////////////////////////////


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_BUSINESS_NEWS)
    Call<ResponseBody> POSTDeleteBusinessNews(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("business_id") String business_id,
            @Field("news_id") String news_id);

    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_BUSINESS_MAGAZINE)
    Call<ResponseBody> POSTDeleteBusinessMagazine(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("business_id") String business_id,
            @Field("magazine_id") String news_id);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_REQ_FOR_AMBASSADOR)
    Call<ResponseBody> POSTAddAmbassadorReqApplication(@Field("user_id") String user_id,
                                                       @Field("api_token") String api_token,
                                                       @Field("contact_no") String contact_no,
                                                       @Field("contact_email") String contact_email,
                                                       @Field("about_me") String about_me,
                                                       @Field("cities") String cities);


    @FormUrlEncoded
    @POST(IUrls.URL_CONCIERGE_CITY_LIST)
    Call<ResponseBody> POSTConciergeCityList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("is_skip_login") String is_skip_login,
            @Field("my_city") String my_city);


    @GET(IUrls.URL_BETTING_CITY)
    Call<ResponseBody> GETBettingCityList();

    @FormUrlEncoded
    @POST(IUrls.URL_POST_DID_YOU_KNOW_CITY_LIST)
    Call<ResponseBody> POSTDidYouKnowCityList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("is_skip_login") String is_skip_login,
            @Field("my_city") String my_city);


    @FormUrlEncoded
    @POST(IUrls.URL_BETTING_CITY)
    Call<ResponseBody> POSTBettingCityList(
            @Field("search") String search,
            @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_AMBASSDOR_LIST)
    Call<ResponseBody> POSTRegionList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("is_skip_login") String is_skip_login,
            @Field("my_conecrige_list") String my_conecrige_list,
            @Field("city_id") String city_id,
            @Field("search") String search,
            @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_AMBASSDOR_SUBSCRIBERS_LIST)
    Call<ResponseBody> POSTAmbassadorSubscribeList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("city_id") String city_id);


    @FormUrlEncoded
    @POST(IUrls.URL_AMBASSDOR_BUY_REQ)
    Call<ResponseBody> POSTAmbassadorBuyReq(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("ambassador_id") String ambassador_id,
            @Field("tranaction_id") String tranaction_id,
            @Field("amount") String amount,
            @Field("dulocal_concierge_id") String dulocal_concierge_id);

    //Chatting Api
    @FormUrlEncoded
    @POST(IUrls.URL_GET_CHATTING_DATA)
    public Call<ResponseBody> postGetChattingData(@Field("user_id") String user_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("sender_id") String sender_id,
                                                  @Field("receiver_id") String receiver_id,
                                                  @Field("page_no") int page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_SEND_CHATTING_DATA)
    public Call<ResponseBody> POSTSendChattingData(@Field("user_id") String user_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("sender_id") String sender_id,
                                                   @Field("receiver_id") String receiver_id,
                                                   @Field("message") String message);


    //GET Common Chatting Data
    @FormUrlEncoded
    @POST(IUrls.URL_GET_COMMON_CHATTING_DATA)
    public Call<ResponseBody> postGetCommonChattingData(@Field("user_id") String user_id,
                                                        @Field("api_token") String api_token,
                                                        @Field("sender_id") String sender_id,
                                                        @Field("receiver_id") String receiver_id,
                                                        @Field("page_no") int page_no,
                                                        @Field("chat_type") String chat_type,
                                                        @Field("sender_receiver_type") String sender_receiver_type);

    @FormUrlEncoded
    @POST(IUrls.URL_SEND_COMMON_CHATTING)
    public Call<ResponseBody> POSTSendCommonChattingData(@Field("user_id") String user_id,
                                                         @Field("api_token") String api_token,
                                                         @Field("sender_id") String sender_id,
                                                         @Field("receiver_id") String receiver_id,
                                                         @Field("message") String message,
                                                         @Field("chat_type") String chat_type,
                                                         @Field("sender_receiver_type") String sender_receiver_type);


    @FormUrlEncoded
    @POST(IUrls.URL_ARTICLE)
    Call<ResponseBody> POSTArticleList(@Field("is_skip_login") String is_skip_login,
                                       @Field("category_id") String category_id,
                                       @Field("search") String search,
                                       @Field("page_no") String page_no,
                                       @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_DASHBOARD_EVENT)
    Call<ResponseBody> POSTDashboardEvent(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("is_skip_login") String is_skip_login,
            @Field("category_id") String category_id,
            @Field("search") String search,
            @Field("page_no") String page_no,
            @Field("event_id") String event_id);

    @FormUrlEncoded
    @POST(IUrls.URL_NGO_LIST)
    Call<ResponseBody> POSTNGOList(
            @Field("user_id") String user_id,
            @Field("api_token") String api_token,
            @Field("is_skip_login") String is_skip_login,
            @Field("category_id") String category_id,
            @Field("search") String search,
            @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_COOL_THINGS_CITY_LIST)
    public Call<ResponseBody> POSTCoolThingsCity(@Field("user_id") String user_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("is_skip_login") String is_skip_login,
                                                 @Field("my_city") String my_city);


    @FormUrlEncoded
    @POST(IUrls.URL_COOL_THINGS_LIST)
    public Call<ResponseBody> POSTCoolThings(@Field("is_skip_login") String is_skip_login,
                                             @Field("city_id") String city_id,
                                             @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_POST_TIMELINE)
    public Call<ResponseBody> getPostView(@Field("api_token") String api_token,
                                          @Field("user_id") String user_id,
                                          @Field("is_skip_login") String is_skip_login,
                                          @Field("type") String type,
                                          @Field("ref_id") String ref_id,
                                          @Field("search") String search,
                                          @Field("page_no") String page_no,
                                          @Field("city_id") String city_id);

    @FormUrlEncoded
    @POST(IUrls.URL_POST_EXPLORE)
    public Call<ResponseBody> getPostExplore(@Field("api_token") String api_token,
                                             @Field("user_id") String user_id,
                                             @Field("is_skip_login") String is_skip_login,
                                             @Field("search") String search,
                                             @Field("page_no") String page_no,
                                             @Field("lat") String lat,
                                             @Field("lng") String lng);

    @FormUrlEncoded
    @POST(IUrls.URL_POST_DETAILS)
    public Call<ResponseBody> getPostDetailsView(@Field("api_token") String api_token,
                                                 @Field("user_id") String user_id,
                                                 @Field("is_skip_login") String is_skip_login,
                                                 @Field("type") String type,
                                                 @Field("ref_id") String ref_id,
                                                 @Field("post_id") String post_id);


    @FormUrlEncoded
    @POST(IUrls.URL_LIKE_UNLIKE)
    Call<ResponseBody> POSTLike(@Field("user_id") String user_id,
                                @Field("post_id") String post_id,
                                @Field("like_flag") String like_flag,
                                @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_POST_SHARE)
    Call<ResponseBody> POSTShare(@Field("api_token") String api_token,
                                 @Field("user_id") String user_id,
                                 @Field("post_id") String post_id,
                                 @Field("who_share_user_id") String who_share_user_id);


    @FormUrlEncoded
    @POST(IUrls.URL_POST_AGAINS_COMMENTS)
    Call<ResponseBody> POSTCommentListAgainstPost(@Field("user_id") String user_id,
                                                  @Field("post_id") String post_id,
                                                  @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_ADD_COMMENTS_AGAINS_POST)
    Call<ResponseBody> POSTAddCommentAgaintsPost(@Field("user_id") String user_id,
                                                 @Field("post_id") String post_id,
                                                 @Field("comment") String comment,
                                                 @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_ADD_ANSWER_OF_SHOUTOUT)
    public Call<ResponseBody> POSTAddShoutOutAnswer(@Field("user_id") String user_id,
                                                    @Field("shoutout_id") String shoutout_id,
                                                    @Field("comment") String comment,
                                                    @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_SHOUTOUT_ANSWER_LIST)
    public Call<ResponseBody> POSTShoutOutAnswerList(@Field("business_id") String business_id,
                                                     @Field("shoutout_id") String shoutout_id,
                                                     @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_SEND_CONTACT_US)
    public Call<ResponseBody> PostContactUs(@Field("user_id") String user_id,
                                            @Field("api_token") String api_token,
                                            @Field("is_skip_login") String is_skip_login,
                                            @Field("title") String title,
                                            @Field("describtion") String describtion,
                                            @Field("contact_no") String contact_no);


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_POST)
    public Call<ResponseBody> POSTDeletePost(@Field("api_token") String api_token,
                                             @Field("user_id") String user_id,
                                             @Field("post_id") String post_id);

    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_SHOUTOUT_QUESTION)
    public Call<ResponseBody> POSTDeleteShoutOutQue(@Field("user_id") String user_id,
                                                    @Field("business_id") String business_id,
                                                    @Field("shoutout_id") String shoutout_id,
                                                    @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_POSTED_IMAGE)
    Call<ResponseBody> POSTDeletePostImage(@Field("api_token") String api_token,
                                           @Field("user_id") String user_id,
                                           @Field("post_id") String post_id,
                                           @Field("post_image_id") String post_image_id);


    @FormUrlEncoded
    @POST(IUrls.URL_POST_IMAGES_AND_VIDEOS)
    public Call<ResponseBody> POSTImagesAndVideo(@Field("api_token") String api_token,
                                                 @Field("user_id") String user_id,
                                                 @Field("is_skip_login") String is_skip_login,
                                                 @Field("type") String type,
                                                 @Field("ref_id") String ref_id,
                                                 @Field("search") String search,
                                                 @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_GET_WALLET_TANSACTION)
    public Call<ResponseBody> getWalletTransaction(@Field("api_token") String api_token,
                                                   @Field("user_id") String user_id,
                                                   @Field("ref_id") String ref_id,
                                                   @Field("type") String type);

    @FormUrlEncoded
    @POST(IUrls.URL_RECHARGE_WALLET)
    public Call<ResponseBody> getRechargeWallet(@Field("user_id") String user_id,
                                                @Field("ref_id") String ref_id,
                                                @Field("type") String type,
                                                @Field("payment_status") String payment_status,
                                                @Field("amount") String amount,
                                                @Field("hash_key") String hash_key,
                                                @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(IUrls.URL_POST_BOOST)
    public Call<ResponseBody> POSTBoost(@Field("user_id") String user_id,
                                        @Field("post_id") String post_id,
                                        @Field("ref_id") String ref_id,
                                        @Field("type") String type,
                                        @Field("gender") String gender,
                                        @Field("to_age") String to_age,
                                        @Field("from_age") String from_age,
                                        @Field("start_date") String start_date,
                                        @Field("end_date") String end_date,
                                        @Field("total_amount") String total_amount,
                                        @Field("no_of_days") String no_of_days,
                                        @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_HANGOUT_LIST)
    Call<ResponseBody> POSTHangoutList(
            @Field("api_token") String api_token,
            @Field("user_id") String user_id,
            @Field("is_skip_login") String is_skip_login,
            @Field("search") String search,
            @Field("page_no") int page_no);

    @GET(IUrls.URL_PRIVACY_MASTER)
    Call<ResponseBody> GETPrivacyMaster();


    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_SETTINGS)
    public Call<ResponseBody> POSTSubmitSetting(@Field("user_id") String user_id,
                                                @Field("api_token") String api_token,
                                                @Field("basic_information_privacy_id") String basic_information_privacy_id,
                                                @Field("post_privacy_id") String post_privacy_id,
                                                @Field("friends_privacy_id") String friends_privacy_id,
                                                @Field("work_privacy_id") String work_privacy_id,
                                                @Field("education_privacy_id") String education_privacy_id,
                                                @Field("message_call_privacy_id") String message_call_privacy_id,
                                                @Field("notificationfriend_request") String notificationfriend_request,
                                                @Field("notification_message") String notification_message,
                                                @Field("notification_business_services_update") String notification_business_services_update,
                                                @Field("notification_comment_like") String notification_comment_like);


    @FormUrlEncoded
    @POST(IUrls.URL_BETTING_RULES)
    Call<ResponseBody> POSTBettingRules(
            @Field("api_token") String api_token,
            @Field("user_id") String user_id,
            @Field("type") String type,
            @Field("city_id") String city_id);

    @FormUrlEncoded
    @POST(IUrls.URL_BETTING_LIST)
    Call<ResponseBody> POSTBettingList(
            @Field("api_token") String api_token,
            @Field("user_id") String user_id,
            @Field("type") String type,
            @Field("city_id") String city_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("business_id") String business_id);


    @FormUrlEncoded
    @POST(IUrls.URL_APPLY_FOR_POSTION)
    Call<ResponseBody> POSTApplyForPostion(
            @Field("api_token") String api_token,
            @Field("user_id") String user_id,
            @Field("ref_id") String ref_id,
            @Field("type") String type,
            @Field("bitting_id") String bitting_id,
            @Field("rank") String rank,
            @Field("city_id") String city_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("hash_key") String hash_key,
            @Field("payment_id") String payment_id,
            @Field("payment_status") String payment_status,
            @Field("amount") String amount,
            @Field("business_id") String business_id);


    @FormUrlEncoded
    @POST(IUrls.URL_HEAT_MAP_CATEGORY_LIST)
    public Call<ResponseBody> POSTHeatMapCategory(@Field("user_id") String user_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("is_skip_login") String is_skip_login);

    @FormUrlEncoded
    @POST(IUrls.URL_HEAT_MAP_MARKER_LIST)
    public Call<ResponseBody> POSTHeatMapMarkers(@Field("user_id") String user_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("is_skip_login") String is_skip_login,
                                                 @Field("category") String category);


    @FormUrlEncoded
    @POST(IUrls.URL_POST_COMUNITY_FORUM_BANNERS)
    Call<ResponseBody> POSTComunityForumBanners(@Field("user_id") String user_id,
                                                @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_GET_USER_SETTINGS)
    public Call<ResponseBody> POSTGetUserSettings(@Field("user_id") String user_id,
                                                  @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST(IUrls.URL_UNIVERSAL_SEARCH)
    public Call<ResponseBody> POSTUniversalSearch(@Field("api_token") String api_token,
                                                  @Field("user_id") String user_id,
                                                  @Field("type") String type,
                                                  @Field("search") String search,
                                                  @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_PAGE_INSIGHTS)
    public Call<ResponseBody> POSTpageInsights(@Field("user_id") String user_id,
                                               @Field("api_token") String api_token,
                                               @Field("ref_id") String ref_id,
                                               @Field("type") String type,
                                               @Field("date_time_type") String date_time_type,
                                               @Field("from_date") String from_date,
                                               @Field("to_date") String to_date,
                                               @Field("search") String search,
                                               @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST(IUrls.URL_DULOCAL_HOTSPOTS)
    public Call<ResponseBody> POSTAllDulocalHotspots(@Field("user_id") String user_id,
                                                     @Field("api_token") String api_token,
                                                     @Field("is_skip_login") String is_skip_login,
                                                     @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_HIGHLIGHTS)
    public Call<ResponseBody> POSTAllBusinessHighlights(@Field("user_id") String user_id,
                                                        @Field("api_token") String api_token,
                                                        @Field("is_skip_login") String is_skip_login,
                                                        @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST(IUrls.URL_DELETE_COMMENT)
    public Call<ResponseBody> POSTDeleteComment(@Field("user_id") String user_id,
                                                @Field("api_token") String api_token,
                                                @Field("post_id") String post_id,
                                                @Field("post_comment_id") String post_comment_id);


*/
   /*

    @FormUrlEncoded
    @POST(IUrls.URL_PLACE_ORDER)
    Call<ResponseBody> POSTPlaceOrder(@Field("user_id") String user_id,
                                      @Field("vendor_id") String vendor_id,
                                      @Field("discount_price") String discount_price,
                                      @Field("gst_price") String gst_price,
                                      @Field("grand_total") String grand_total,
                                      @Field("total_price") String total_price,
                                      @Field("zone_id") String zone_id,
                                      @Field("region_id") String region_id,
                                      @Field("call_id") String call_id,
                                      @Field("order_type") String order_type,
                                      @Field("lat") String lat,
                                      @Field("lng") String lng,
                                      @Field("products") JSONArray jsonArray);

    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_LOCATION)
    public Call<ResponseBody> updateLocation(
            @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(IUrls.URL_CHECKOUT_REASONS_LIST)
    Call<ResponseBody> POSTCheckoutReason(@Field("user_id") String user_id);*/

   /* ,
    @Field("vendors") JSONArray jsonArray*/



  /*












    @GET(IUrls.URL_ESTORE_DASHBOARD_CATEGORY)
    Call<ResponseBody> getEstoreCategory();

    @GET(IUrls.URL_PRE_SCHOOL_EVENT_MANAGEMENT)
    Call<ResponseBody> getPreschoolEventBanner();

    @GET(IUrls.URL_PRE_SCHOOL_BRANDING_AND_MARKETING)
    Call<ResponseBody> getPreschoolEventBrandingAndMarketing();









    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_CIRRCULUM_CATEGORY)
    Call<ResponseBody> getPreschoolOwnerSmartCirriculumCategory(
            @Query("pre_owner_dash_id") String catId
    );

    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_CIRRCULUM_WEEK)
    Call<ResponseBody> getPreschoolOwnerSmartCirriculumCategoryWeeks(
            @Query("smart_curriculum_id") String catId
    );

    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_WORK_SHEET_PDF)
    Call<ResponseBody> getPreschoolOwnerSmartWorksheetPDF(
            @Query("smart_worksheet_id") String catId
    );

    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_TRAINING_OPTIONS)
    Call<ResponseBody> getPreschoolOwnerSmartTrainingOptions(
            @Query("pre_owner_dash_id") String catId
    );






    @GET(IUrls.URL_PRESCHOOL_SMART_CURRICULUM_NEW_WEBCALL)
    Call<ResponseBody> getPreschoolOwnerSmartCurriculumNewWebcall(
            @Query("user_id") String user_id
    );

    @GET(IUrls.URL_PRESCHOOL_ESTORE_PRODUCT)
    Call<ResponseBody> getEstoreProductList();

    @GET(IUrls.URL_PRESCHOOL_ESTORE_PRODUCT_BY_CATEGORY_ID)
    Call<ResponseBody> getProductByCategoryId(
            @Query("category_id") String category_id
    );

    @GET(IUrls.URL_WORKSHEET_STUDY_MATERIAL_LIST)
    Call<ResponseBody> getWorksheetList(
            @Query("smart_worksheet_id") String smart_worksheet_id

    );

    @GET(IUrls.URL_PRESCHOOL_SMART_CURRICULUM_ALl_COURCE_IMAGES)
    Call<ResponseBody> getSmartCurriculamAllCourceImage(
            @Query("smart_curriculum_id") String smart_curriculum_id

    );

    @GET(IUrls.URL_SMART_CURICULAM_WEEK_LIST)
    Call<ResponseBody> getCuriculamWeekList(
            @Query("smart_curriculum_id") String smart_curriculum_id,
            @Query("session_id") String session_id
    );

    @GET(IUrls.URL_SMART_CURICULAM_WEEK_WISE_IMAGE)
    Call<ResponseBody> getWeekWiseImage(
            @Query("smart_curriculum_week_id") String smart_curriculum_week_id
    );

    @GET(IUrls.URL_SMART_ASSESSMENT_MANUAL)
    Call<ResponseBody> get3yrAssessmentManual(
            @Query("smart_assessment_id") String smart_assessment_id

    );



    @GET(IUrls.URL_SMART_ASSESSMENT_GRADING)
    Call<ResponseBody> getAssessmentGradient(
            @Query("smart_assessment_id") String smart_assessment_id

    );

    @GET(IUrls.URL_SMART_TRANING_BANNER)
    Call<ResponseBody> getSmartTraining(
            @Query("join_training_type_id") String join_training_type_id

    );


    @GET(IUrls.URL_SMART_ASSESSMENT_ORDER_KIT_VALUES)
    Call<ResponseBody> getAssessmentOrderValues();

    @GET(IUrls.URL_STATIONARY_LIST)
    Call<ResponseBody> getStatinaryList();

    @GET(IUrls.URL_MY_ORDER_CURRICULUM)
    Call<ResponseBody> getMyOrdersCurriculum(
            @Query("curriculum_user_id") String user_id,
            @Query("order_status") String order_status

    );


    @GET(IUrls.URL_MY_PREVIOUS_ORDER)
    Call<ResponseBody> getMyPreviousOrder(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type,
            @Query("menu_id") String menu_id,
            @Query("order_status") String order_status

    );

    @GET(IUrls.URL_MY_PREVIOUS_ORDER_DETAILS)
    Call<ResponseBody> getMyPreviousOrderDetails(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type,
            @Query("common_order_id") String common_order_id,
            @Query("order_status") String order_status

    );



    @GET(IUrls.URL_CANCEL_MY_ORDER)
    Call<ResponseBody> getCancelStatus(
            @Query("curriculum_user_id") String curriculum_user_id,
            @Query("curriculum_order_id") String curriculum_order_id,
            @Query("status") String order_status

    );










    @GET(IUrls.URL_PROFILE)
    Call<ResponseBody> getProfile(
            @Query("user_id") String user_id,
            @Query("user_type") String user_type


    );







    @GET(IUrls.URL_ESTORE_NEW_DASHBOARD_PRODUCTS)
    Call<ResponseBody> getEstoreNewDashboardProductlist(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type,
            @Query("menu_id") String menu_id
    );

  *//*  @GET(IUrls.URL_CLASSES_INFO)
    Call<ResponseBody> getClassInfo();


    @GET(IUrls.URL_FACULTY)
    Call<ResponseBody> getFacultyInfo();

    @GET(IUrls.URL_SUBJECT)
    Call<ResponseBody> getSubjectInfo();

    @GET(IUrls.URL_PARENTS_DASHBOARD_GRID)
    Call<ResponseBody> getParentsDashboardgrid();*//*


     *//* @GET(IUrls.URL_CLASSES_INFO)
    Call<List<UserBannerObj>> getBanners();*//*






    @GET(IUrls.URL_STATIONARY_TYPE_WISE_DESIGN_LIST)
    Call<ResponseBody> getStatinaryTypeWiseDesign(
            @Query("stationary_id") String stationary_id,
            @Query("stationary_type_id") String stationary_type_id


    );

    @GET(IUrls.URL_STATIONARY_EXPANDABLE_LIST)
    Call<StationaryPojo> getStationaryList();

    @GET(IUrls.URL_STATIONARY_STUDENT_ID_AND_PROGRESS_LIST)
    Call<ResponseBody> getStudentAndProgressList(
            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_COMMON_ANNUAL_SPORTS_FINAL_LIST)
    Call<ResponseBody> getCommonSportsAnualFinalList(
            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_SUMMER_CAMP_LIST)
    Call<ResponseBody> getSummerCampList(
            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );

    @GET(IUrls.URL_STATIONARY_TRANSFER_CERTIFICATE_LIST)
    Call<ResponseBody> getTrasferCertificateList(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_ANNUL_SPORTS_FINAL)
    Call<ResponseBody> getCancelAnnualSportsFinalDayCertificate(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_ID_CARD_PROG_CARD)
    Call<ResponseBody> getCancelIDCardProgressCard(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_TRANSFER_CERTIFICATE)
    Call<ResponseBody> getCancelTransferCertificate(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_SUMMER_CAMP_CERTIFICATE)
    Call<ResponseBody> getCancelSummerCampCertificate(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );







    @FormUrlEncoded
    @POST(IUrls.URL_STATIONARY_UPDATE_TRANSFER_CERTIFICATE_FORM)


    public Call<ResponseBody> getUpdateTransferCertificateForm(
            @Field("user_id") String user_id,
            @Field("stationary_type_id") String stationary_type_id,
            @Field("transfer_certficate_id") String transfer_certficate_id,
            @Field("student_name") String student_name,
            @Field("father_name") String father_name,
            @Field("mother_name") String mother_name,
            @Field("birth_date") String birth_date,
            @Field("academic_year") String academic_year,
            @Field("present_group_name") String present_group_name,
            @Field("promoted_group") String promoted_group,
            @Field("reason") String reason,
            @Field("remark") String remark

    );


    @GET(IUrls.URL_STATIONARY_PRE_SCHOOL_LIST)
    Call<ResponseBody> getSchoolList(
            @Query("user_id") String user_id
    );



    //New Web Calls

    @GET(IUrls.URL_CURRICULUM_NEW_PRODUCT_WEBCALL)
    Call<ResponseBody> getCurriculumNewProductList(
            @Query("menu_id") String menu_id,
            @Query("category_id") String category_id,
            @Query("user_id") String user_id,
            @Query("login_type") String login_type
    );

    @FormUrlEncoded
    @POST(IUrls.URL_CURRICULUM_ADD_PRODUCT_TO_CART)
    public Call<ResponseBody> postAddProductInList(@Field("user_id") String user_id,
                                                   @Field("login_type") String login_type,
                                                   @Field("menu_id") String menu_id,
                                                   @Field("product_id") String product_id,
                                                   @Field("cart_status") String cart_status);



    @GET(IUrls.URL_CART_PRODUCT_LIST)
    public Call<ResponseBody> getUserCartList(
            *//*@Query("menu_id") String menu_id,*//*
            @Query("user_id") String user_id,
            @Query("login_type") String login_type
    );

    @FormUrlEncoded
    @POST(IUrls.URL_COMMON_ORDER)
    public Call<ResponseBody> postTotalCartProduct(
            *//*  @Field("menu_id") String menu_id,*//*
            @Field("user_id") String user_id,
            @Field("login_type") String login_type,
            @Field("order_person_name") String order_person_name,
            @Field("order_person_contact") String order_person_contact,
            @Field("order_person_email") String order_person_email,
            @Field("order_person_address") String order_person_address,
            @Field("order_person_delivery_address") String order_person_delivery_address,
            @Field("order_receiver_name") String order_receiver_name,
            @Field("delivery_charges") String delivery_charges,
            @Field("pincode") String pincode);

    @GET(IUrls.URL_CONNTACT_US_BY_MAIL_LIST)
    Call<ResponseBody> getAllMailContactList(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type

    );

    @GET(IUrls.URL_ESTORE_BANNERS)
    Call<ResponseBody> getEstoreBannersList();


    @FormUrlEncoded
    @POST(IUrls.URL_CONNTACT_US_BY_MAILFORM)
    public Call<ResponseBody> getCommonContactByMail(@Field("user_id") String user_id,
                                                     @Field("login_type") String login_type,
                                                     @Field("menu_name") String menu_name,
                                                     @Field("username") String username,
                                                     @Field("email") String email,
                                                     @Field("mobile") String mobile
    );*/


}
