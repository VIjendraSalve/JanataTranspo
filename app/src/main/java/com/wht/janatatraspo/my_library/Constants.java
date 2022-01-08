package com.wht.janatatraspo.my_library;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wht.janatatraspo.R;


public class Constants {

    public static final String rs = "\u20B9 ";
    public static final String REG_ID = "reg_id";
    public static final String UNIQUE_ID = "UNIQUE_ID";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String REG_MOBILE = "reg_mobile";
    public static final String IS_VERIFIED = "IS_VERIFIED";
    public static final String Created_At = "Created_At";
    public static final String BarcodeImage = "BarcodeImage";
    public static final String IsLogin = "IsLogin";
    public static final String OFFICE_CODE = "OFFICE_CODE";
    public static final int App_Course = 1;
    public static final int SUB_MASTER = 2;
    public static final int DELIVERY_BOY = 3;
    public static final String LIST_DATA = "list_data";
    public static final String DELIVERY_BOY_DATA = "delivery_boy_data";
    public static final String SUBMASTER_DATA = "submaster_data";
    public static final Object ADD_COLLECTION = "add_collection";
    public static final Object UPDATE_COLLECTION = "update_collection";
    public static final String CITY_NAME = "city_name";
    public static final String PINCODE_NUMBER = "pincode_number";
    public static final String AWBNumber = "AWBNumber";
    public static final String AWBNumber1 = "AWBNumber1";
    public static final String RefNumber = "RefNumber";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String EMAIL_ID = "EMAIL_ID";
    public static final String APP_KEY = "APP_KEY";
    public static final String Flag = "Flag";
    public static final String Data = "Data";
    public static final String PODUrl = "PODUrl";
    public static final String UserID = "UserID";
    public static final String ROLE_ID = "ROLE_ID";
    public static final String userType = "userType";
    public static final String AccessType = "AccessType";
    public static final String officeCode = "officeCode";
    public static final String FirstName = "FirstName";
    public static final String LastName = "LastName";
    public static final String MobileNo = "MobileNo";
    public static final String EmailId = "EmailId";
    public static final String Country = "Country";
    public static final String Country123 = "Country123";
    public static final String Gender = "Gender";
    public static final String PROFILE_IMAGE = "PROFILE_IMAGE";
    public static final String Address = "Address";
    public static final String is_document_verified = "is_document_verified";
    public static final String CategoryForEvent = "CategoryForEvent";
    public static final String DESIGNATION = "DESIGNATION";

    public static final String NOTIFICATION_TOKEN = "NOTIFICATION_TOKEN";

    public static final String BlogList = "BlogList";
    public static final String BlogPosition = "BlogPosition";
    public static final String SponsorsList = "SponsorsList";
    public static final String SponsorsPosition = "SponsorsPosition";
    public static final String NotificationFlag = "NotificationFlag";
    public static final String List = "List";
    public static final String pos = "pos";
    public static final String Path = "Path";
    public static final String NewsList = "NewsList";
    public static final String NewsPosition = "NewsPosition";
    public static final String CategoryIdForQuestion = "CategoryIdForQuestion";
    public static final String CategoryNameForQuestion = "CategoryNameForQuestion";
    public static final String QuestionList = "QuestionList";
    public static final String AnswerIdForQuestion = "AnswerIdForQuestion";
    public static final String AnswerNameForQuestion = "AnswerNameForQuestion";
    public static final String AddUpdateFlag = "AddUpdateFlag";
    public static final String OfferList = "OfferList";
    public static final String OfferPosition = "OfferPosition";
    public static final String OfferID = "OfferID";
    public static final String Nationality = "nationality";
    public static final String place_of_residence = "place_of_residence";
    public static final String age = "age";

    public static final String AadharImage = "AadharImage";
    public static final String PanImage = "PanImage";
    public static final String EPassImage = "EPassImage";
    public static final String MedicalImage = "MedicalImage";
    public static final String HotelID = "HotelID";
    public static final String SafetyArray = "SafetyArray";
    public static final String PROFILE_IMAGE_PATH = "PROFILE_IMAGE_PATH";
    public static final String DOC_IMAGE_PATH = "DOC_IMAGE_PATH";
    public static final String CheckinCheckOut = "CheckinCheckOut";
    public static final String HotelName = "HotelName";
    public static final String HotelNumber = "HotelNumber";
    public static final String HotelEmail = "HotelEmail";
    public static final String Latitude = "Latitude";
    public static final String Longitude = "Longitude";
    public static final String Description = "Description";
    public static final String AmenetiesImagesPath = "AmenetiesImagesPath";
    public static final String HotelDescription = "HotelDescription";
    public static final String HotelHygine = "HotelHygine";
    public static final String HotelPolicies = "HotelPolicies";
    public static final String HotelImportantInfo = "HotelImportantInfo";
    public static final String Hotelchildren_extra_bed = "Hotelchildren_extra_bed";
    public static final String Signature = "Signature";
    public static final String Like = "Like";
    public static final String RoomID = "RoomID";
    public static final String BookingID = "BookingID";
    public static final String CheckInDate = "CheckInDate";
    public static final String CheckinTime = "CheckinTime";
    public static final String RoomPrice = "RoomPrice";
    public static final String RoomNumber = "RoomNumber";
    public static final String RoomType = "RoomType";
    public static final String FoodList = "FoodList";
    public static final String LastBookingID = "LastBookingID";
    public static final String LastHotelID = "LastHotelID";
    public static final String LastRoomID = "LastRoomID";
    public static final String GuestList = "GuestList";
    public static final String HotelPoliciesImagePath = "HotelPoliciesImagePath";
    public static final String HotelHygineImagePath = "HotelHygineImagePath";


    public static void showDocument(Context context, String string_url) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_document_detail_view);
        dialog.setCancelable(false);
        final ImageView iv_doc = (ImageView) dialog.findViewById(R.id.iv_doc);
        final ProgressBar dialog_progress_bar = (ProgressBar) dialog.findViewById(R.id.dialog_progress_bar);
        Glide.with(context).load(string_url)
                .thumbnail(0.5f)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_doc);
        dialog.findViewById(R.id.iv_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public static void loadImageDoc(Context context, String string_url, final ImageView imageView) {
        Glide.with(context).load(string_url)
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void AlertDailogue(final String message, Context context){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Message");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      dialog.dismiss();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        //alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        alert.show();
    }


    public static void AlertDailoguetoAddProduct(final String message, Context context){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Message");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.dismiss();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        //alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        alert.show();
    }

    public static void AlertDailoguetoDeleteProduct(final String message, Context context){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Message");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        //alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        alert.show();
    }

    public static void EditTextAnim(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //float finalRadius = (float) Math.max((int) event.getX(), (int) event.getY()) * 1.2f;
                if (event.getAction() == MotionEvent.ACTION_DOWN && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !editText.hasFocus()) {

                    ViewAnimationUtils.createCircularReveal(editText,
                            editText.getWidth() / 2,
                            1,
                            0,
                            editText.getWidth() / 2).start();

                    //Log.d("my_tag", "onTouch: CenterX "+(int) event.getX()+"\nCenterY" +(int) event.getY()+"\nHeight "+editText.getHeight()+"\nWidth "+editText.getWidth());
                }
                return false;
            }
        });
    }

    public static String Reg_id(Context context) {
        String role_id = "0";
        if (Shared_Preferences.getPrefs(context, REG_ID) != null)
            role_id = Shared_Preferences.getPrefs(context, REG_ID);
        return role_id;
    }

    public static String Reg_mobile(Context context) {
        String reg_mobile = "0";
        if (Shared_Preferences.getPrefs(context, REG_MOBILE) != null)
            reg_mobile = Shared_Preferences.getPrefs(context, REG_MOBILE);
        return reg_mobile;
    }

   /* public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            String imei = telephonyManager.getDeviceId();
         *//*   Log.e("imei", "=" + imei);*//*
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }*/
}
