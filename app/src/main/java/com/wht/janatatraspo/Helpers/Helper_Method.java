package com.wht.janatatraspo.Helpers;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;
import static com.itextpdf.text.factories.RomanNumberFactory.getString;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.textfield.TextInputLayout;
import com.wht.janatatraspo.InitialActivities.LoginActivity;
import com.wht.janatatraspo.R;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Helper_Method {

    private static final Random RANDOM = new Random();
    private static String time = "Just Now";

    private static ProgressDialog dialog;

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

/*    public static String toTitleCase(String input) {
        boolean nextTitleCase = true;
        StringBuilder titleCase = new StringBuilder();

        if (!input.isEmpty()) {
            for (char c : input.toCharArray()) {
                if (Character.isSpaceChar(c))
                    nextTitleCase = true;
                else if (nextTitleCase) {
                    c = Character.toTitleCase(c);
                    nextTitleCase = false;
                }
                titleCase.append(c);
            }
        }
        return titleCase.toString();
    }*/

    public static boolean isInvalidEmail(String email) {
        final String email_pattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return !Pattern.compile(email_pattern).matcher(email).matches();
    }

    public static void setSpinnerError(Spinner spinner, String prompt) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView errorText = (TextView) selectedView;
            errorText.setError("");
            errorText.setTextColor(Color.RED); // just to highlight that this is an error
            errorText.setText(prompt); // changes the selected item text to this
        }
    }

    public static void setErrorBelow(TextInputLayout textInputLayout, String prompt) {
        textInputLayout.requestFocus(View.FOCUS_DOWN);
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(prompt);
    }

    public static void setErrorBelow(EditText editText, String prompt) {
        editText.requestFocus(View.FOCUS_DOWN);
        editText.setError(prompt);
    }

    public static void removeError(EditText editText) {
        editText.setError(null);
    }

    public static void removeError(TextInputLayout[] inputLayouts) {
        for (TextInputLayout view : inputLayouts) {
            view.setErrorEnabled(false);
            view.setError(null);
        }
    }

    public static void removeError(TextInputLayout inputLayouts) {
        inputLayouts.setErrorEnabled(false);
        inputLayouts.setError(null);
    }

    public static void removeError(TextInputLayout[] inputLayouts, TextView textView) {
        for (TextInputLayout view : inputLayouts) {
            view.setErrorEnabled(false);
            view.setError(null);
        }
        textView.setVisibility(View.GONE);
    }

    public static void smoothScrollTo(View view, NestedScrollView scrollView) {
        scrollView.smoothScrollTo(0, view.getTop());
    }

    public static void toaster(Activity activity, String content) {
        Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();
    }

    public static void toaster_long(Activity activity, String content) {
        Toast.makeText(activity, content, Toast.LENGTH_LONG).show();
    }

    // where first 0 shows the starting and data.length()/end shows the ending span.
    // if you want to span only part of it than you can change these values like 5, 8 then it will underline part of it.
    public static SpannableString underlineText(String data, int start, int end) {
        SpannableString content = new SpannableString(data);
        content.setSpan(new UnderlineSpan(), start, end, 0);
        return content;
    }

    public static SpannableString strikeoutText(String data, int start, int end) {
        SpannableString content = new SpannableString(data);
        content.setSpan(new StrikethroughSpan(), start, end, 0);
        return content;
    }

    public static void setFontToolbard(Activity activity, View view) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Regular.ttf");
        if (view instanceof TextView) ((TextView) view).setTypeface(typeface);
        if (view instanceof EditText) ((EditText) view).setTypeface(typeface);
        if (view instanceof Button) ((Button) view).setTypeface(typeface);
    }

    public static void setFontHeader(Activity activity, View view) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Regular.ttf");
        if (view instanceof TextView) ((TextView) view).setTypeface(typeface);
        if (view instanceof EditText) ((EditText) view).setTypeface(typeface);
        if (view instanceof Button) ((Button) view).setTypeface(typeface);
    }

    public static void setFontSecondHeader(Activity activity, View view) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Regular.ttf");
        if (view instanceof TextView) ((TextView) view).setTypeface(typeface);
        if (view instanceof EditText) ((EditText) view).setTypeface(typeface);
        if (view instanceof Button) ((Button) view).setTypeface(typeface);
    }

    public static void setFontText(Activity activity, View view) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Regular.ttf");
        if (view instanceof TextView) ((TextView) view).setTypeface(typeface);
        if (view instanceof EditText) ((EditText) view).setTypeface(typeface);
        if (view instanceof Button) ((Button) view).setTypeface(typeface);
    }

    public static String leadZero(int month) {
        return month < 10 ? "0" + month : "" + month;
    }

    public static long getDate(int d) {
        return 1000 * 60 * 60 * 24 * (d - 1);
    }

    public static void warnUser(Activity activity, String title, String message, final boolean hideDialog) {
        Log.e("warnUser: ", "" + title + ", " + message);
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // if (hideDialog) Helper_Method.hideDialog();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void warnUser_finish(final Activity activity, String title, String message, final boolean hideDialog) {
        Log.e("warnUser: ", "" + title + ", " + message);

        new AlertDialog.Builder(activity)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (hideDialog) Helper_Method.hideDialog();

                        if (!activity.getClass().getName().endsWith("SplashActivity"))
                            activity.finish();
                        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            activity.finishAffinity();
                        }
                    }
                })
                .show();
    }

    private static void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.setMessage("processing...please wait !!");
        dialog.setCancelable(false);
    }

    private static void initDialog(Activity activity, String message) {
        dialog = new ProgressDialog(activity);
        dialog.setMessage(message);
        dialog.setCancelable(false);
    }

    public static void showDialog(Activity activity) {
        initDialog(activity);
        if (!dialog.isShowing()) dialog.show();
    }

    public static void showDialog(Activity activity, String message) {
        initDialog(activity, message);
        if (!dialog.isShowing())
            dialog.show();
    }

    public static void hideDialog() {
        if (dialog != null)
            if (dialog.isShowing())
                dialog.dismiss();
    }

    public static AlertDialog noConnectivityDialog(final Activity activity, final boolean closeApp) {
        return new AlertDialog.Builder(activity)
                .setTitle(R.string.unable_to_connect)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.internet_connection_required)
                .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                            }
                        }
                )
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Helper_Method.toaster(activity, activity.getString(R.string.internet_connection_required));
                                if (closeApp) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    activity.finishAffinity();
                                }
                            }
                        }
                )
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Helper_Method.toaster(activity, activity.getString(R.string.internet_connection_required));
                        if (closeApp) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            activity.finishAffinity();
                        }
                    }
                })
                .create();
    }

    public static AlertDialog noConnectivityDialog_exit(final Activity activity) {
        return new AlertDialog.Builder(activity)
                .setTitle(R.string.unable_to_connect)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.internet_connection_required)
                .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    activity.finishAffinity();
                                }
                                // ExitActivity.exitApplicationAndRemoveFromRecent(activity);
                                System.exit(0);
                            }
                        }
                )
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Helper_Method.toaster(activity, activity.getString(R.string.internet_connection_required));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    activity.finishAffinity();
                                }
                                // ExitActivity.exitApplicationAndRemoveFromRecent(activity);
                                System.exit(0);
                            }
                        }
                )
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Helper_Method.toaster(activity, activity.getString(R.string.internet_connection_required));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            activity.finishAffinity();
                        }
                        // ExitActivity.exitApplicationAndRemoveFromRecent(activity);
                        System.exit(0);
                    }
                })
                .create();
    }

    public static void intentActivity(Activity activity, Class activityClass, Boolean finishActivity) {
        activity.startActivity(new Intent(activity, activityClass).putExtra("className", activity.getClass().getName()));
        if (finishActivity) activity.finish();
    }

    public static void intentActivity_animate_fade(Activity activity, Class activityClass, Boolean finishActivity) {
        activity.startActivity(new Intent(activity, activityClass).putExtra("className", activity.getClass().getName()));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (finishActivity) activity.finish();
    }

    public static void intentActivity_animate(Activity activity, Class activityClass, Boolean finishActivity) {
        activity.startActivity(new Intent(activity, activityClass).putExtra("className", activity.getClass().getName()));
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (finishActivity) activity.finish();
    }

    public static void finish_anim_fade(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void finish_anim(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    // compile 'com.github.pwittchen:reactivenetwork-rx2:0.12.1'
   /* public static void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed())
                subscription.dispose();
        }
    }*/

    public static String getNextArrayList(ArrayList<String> arrayList, String curr_element) {
        int idx = arrayList.indexOf(curr_element);
        if (idx < 0 || idx + 1 == arrayList.size()) return "";
        return arrayList.get(idx + 1);
    }

    public static String getPreviousArrayList(ArrayList<String> arrayList, String curr_element) {
        int idx = arrayList.indexOf(curr_element);
        if (idx <= 0) return "";
        return arrayList.get(idx - 1);
    }

    /*public static String unescapeHtml(String escapedHtml) {
        String html = StringEscapeUtils.unescapeHtml4(escapedHtml);
        if (html.contains("&amp;")) html = html.replace("&amp;", "&");
        if (html.contains("&lt;")) html = html.replace("&lt;", "<");
        if (html.contains("&gt;")) html = html.replace("&gt;", ">");
        if (html.contains("&quot;")) html = html.replace("&quot;", "\"");
        if (html.contains("&amp;nbsp;")) html = html.replace("&amp;nbsp;", "&nbsp;");
        if (html.contains("&nbsp;")) html = html.replace("&nbsp;", " ");
        // if (html.contains("#")) html = html.replace("#", "/-/");
        // this is used for parsing # in queryString
        return html;
    }*/


    public static String formatDateString(String inputFormat, String outputFormat, String inputDate) {

        Date parsed;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
            Log.e("ParseException ", "ParseException - dateFormat");
        }

        return outputDate;
    }

    public static Point getDisplaySize(Display display) {
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static void sout(Object object) {
        System.out.println("--->> " + object);
    }

    public static void sout(String label, Object object) {
        System.out.println("--->> " + label + "__ " + object);
    }

    /**
     * Making notification bar transparent
     */
    public static void changeStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS |
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setStatusBarColor(activity.getResources().getColor(R.color.white));
        }
    }

    public static void setupUI(View view, final Activity activity) {
        // Set up touch listener for non-text box views to hide keyboard
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    return false;
                }
            });
        }

        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }

    public static boolean checkGps(Activity activity) {
        LocationManager service = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        return service != null && service.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean checkPermissions(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static long randomLong(long lower, long upper) {
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    public static String ddtoYY(String oldDate) {
        String finalString = null;
        if (oldDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
            Date date;
            try {
                date = formatter.parse(oldDate);
                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                finalString = newFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return finalString;
    }

    public static String getDeviceInfo() {
        String model = Build.MODEL;
        String manufacturer = Build.MANUFACTURER;

        return model.toLowerCase().startsWith(manufacturer.toLowerCase()) ?
                capitalize(model) : capitalize(manufacturer) + " " + model;
    }

    public static String getOsInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("Android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            int fieldValue = -1;
            String fieldName = field.getName();

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException | IllegalAccessException | NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ");
                builder.append("SDK=").append(fieldValue);
            }
        }

        return builder.toString();
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static int getIndexInSpinner(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static String reverseGeoCoder(Activity activity, Double latitude, Double longitude, boolean onlyCityName) {
        Geocoder geoCoder = new Geocoder(activity, Locale.getDefault());
        String resAddress = "";

        try {
            List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                // System.out.println(addresses.get(0));
                if (!onlyCityName) {
                    resAddress = addresses.get(0).getFeatureName() + "," + addresses.get(0).getSubLocality() +
                            "," + addresses.get(0).getSubAdminArea() + "," + addresses.get(0).getPostalCode() +
                            "," + addresses.get(0).getCountryName();
                } else {
                    resAddress = addresses.get(0).getLocality();
                    // resAddress = addresses.get(0).getSubAdminArea();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resAddress.replaceAll(",null", "");
    }

    public static void hideSoftInput(Context context) {
        /*View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);*/
        View view = ((AppCompatActivity) context).getCurrentFocus();
        if (view == null) view = new View(context);
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static ProgressDialog progress;

    public static void showProgressBar(final Context context, String text) {
        progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.setMessage(text);

        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        progress.setOnKeyListener(new ProgressDialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    finish((Activity) context);
                }
                return true;
            }
        });
    }

    public static String getIndianRupee(String value) {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return format.format(new BigDecimal(value));
    }

    private static void finish(Activity context) {
        context.finish();
    }

    public static void showProgressBarTemp(Context context, String text) {
        progress = new ProgressDialog(context);
        progress.setCancelable(true);
        progress.setMessage(text);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    public static void dismissProgessBar() {
        if (progress != null) {
            progress.dismiss();
        }

    }

    public static void logD(String tag, String msg) {
        Log.d(tag, msg);
    }
    /*public double checkNull(String value, double defult) {
        if (value == null || value.equalsIgnoreCase("null") || value.equalsIgnoreCase(""))
            return defult;

        return defult;
    }*/

    public static String dateTimeConvert(String strDate) {
        //Input date in String format
        // String input = "15/02/2014 22:22:12";
        //Date/time pattern of input date
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
        Date date = null;
        String output = null;
        try {
            //Conversion of input String to date
            date = df.parse(strDate);
            //old date format to new date format
            output = outputformat.format(date);
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    public static String getHardwareAndSoftwareInfo() {

        return /*getString(R.string.serial) + " " + Build.SERIAL + "\n" +*/
                getString(R.string.model) + " " + Build.MODEL + "\n" +
                        getString(R.string.id) + " " + Build.ID + "\n" +
                        getString(R.string.manufacturer) + " " + Build.MANUFACTURER + "\n" +
                        getString(R.string.brand) + " " + Build.BRAND + "\n" +
                        getString(R.string.type) + " " + Build.TYPE + "\n" +
                        getString(R.string.user) + " " + Build.USER + "\n" +
                        getString(R.string.base) + " " + Build.VERSION_CODES.BASE + "\n" +
                        getString(R.string.incremental) + " " + Build.VERSION.INCREMENTAL + "\n" +
                        getString(R.string.sdk) + " " + Build.VERSION.SDK + "\n" +
                        getString(R.string.board) + " " + Build.BOARD + "\n" +
                        getString(R.string.host) + " " + Build.HOST + "\n" +
                        getString(R.string.fingerprint) + " " + Build.FINGERPRINT + "\n" +
                        getString(R.string.versioncode) + " " + Build.VERSION.RELEASE;
    }

    public static void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        editText.setClickable(false);
     /*   //editText.setBackground(Drawable.bo);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackgroundDrawable(ContextCompat.getDrawable(_act, R.drawable.edittext_lable_with_border));
        } else {
            editText.setBackground(ContextCompat.getDrawable(_act, R.drawable.edittext_lable_with_border));
        }*/
        // editText.setTextColor(Color.BLACK);
    }


    public static void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setClickable(true);
        editText.setTextColor(Color.BLACK);
       /* final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackgroundDrawable(ContextCompat.getDrawable(_act, R.drawable.enable_edittext_lable_border));
        } else {
            editText.setBackground(ContextCompat.getDrawable(_act, R.drawable.enable_edittext_lable_border));
        }*/
        //editText.setBackgroundColor(getResources().getColor(R.color.primary_light));
    }

    private void openFile(Context context, File url) {

        try {

            Uri uri = Uri.fromFile(url);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".zip")) {
                // ZIP file
                intent.setDataAndType(uri, "application/zip");
            } else if (url.toString().contains(".rar")) {
                // RAR file
                intent.setDataAndType(uri, "application/x-rar-compressed");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

    /*public static void autoLogout(Context context, boolean isSessionExpired, String stringMsg) {
        if (isSessionExpired) {

            new AlertDialog.Builder(context)
                    .setIcon(context.getResources().getDrawable(R.drawable.ic_alert))
                    .setTitle(R.string.session_expired)
                    .setCancelable(false)
                    .setMessage(R.string.session_expired_msg)
                    .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Helper_Method.toaster_long((Activity) context, stringMsg);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                SharedPref.clearPref(context);
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                ActivityCompat.finishAffinity((Activity) context);

                            }
                        }
                    })
                    *//*   .setNegativeButton(activity.getString(R.string._continue), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       })*//*
                    .show();

        } else {
            Helper_Method.toaster((Activity) context, stringMsg);
        }
    }

    public static void closeCheckLogin(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setIcon(activity.getResources().getDrawable(R.drawable.ic_alert))
                .setTitle("Alert")
                .setMessage(R.string.login_msg)
                .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPref.clearPref(activity);
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                        ActivityCompat.finishAffinity((Activity) activity);
                    }
                })
                .setNegativeButton(activity.getString(R.string.exit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }*/

    public static String getTimestampDifference(String dateTime) {
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
        // sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));//google 'android list of timezones'

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("UK/Pacific"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = dateTime;
        try {
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24)));
        } catch (ParseException e) {
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage());
            difference = "0";
        }
        return difference;
    }

/*    public static String how_long_ago(String created_at) {
        DateTime sinceGraduation = new DateTime(created_at, GregorianChronology.getInstance());
        DateTime currentDate = new DateTime(); //current date

        Months diffInMonths = Months.monthsBetween(sinceGraduation, currentDate);
        Days diffInDays = Days.daysBetween(sinceGraduation, currentDate);
        Hours diffInHours = Hours.hoursBetween(sinceGraduation, currentDate);
        Minutes diffInMinutes = Minutes.minutesBetween(sinceGraduation, currentDate);
        Seconds seconds = Seconds.secondsBetween(sinceGraduation, currentDate);

        Log.d("since grad", "before if " + sinceGraduation);
        if (diffInDays.isGreaterThan(Days.days(31))) {
            time = diffInMonths.getMonths() + " months ago";
            if (diffInMonths.getMonths() == 1) {
                time = diffInMonths.getMonths() + " month ago";
            } else {
                time = diffInMonths.getMonths() + " months ago";
            }
            return time;
        } else if (diffInHours.isGreaterThan(Hours.hours(24))) {
            if (diffInDays.getDays() == 1) {
                time = diffInDays.getDays() + " day ago";
            } else {
                time = diffInDays.getDays() + " days ago";
            }
            return time;
        } else if (diffInMinutes.isGreaterThan(Minutes.minutes(60))) {
            if (diffInHours.getHours() == 1) {
                time = diffInHours.getHours() + " hour ago";
            } else {
                time = diffInHours.getHours() + " hours ago";
            }
            return time;
        } else if (seconds.isGreaterThan(Seconds.seconds(60))) {
            if (diffInMinutes.getMinutes() == 1) {
                time = diffInMinutes.getMinutes() + " minute ago";
            } else {
                time = diffInMinutes.getMinutes() + " minutes ago";
            }
            return time;
        } else if (seconds.isLessThan(Seconds.seconds(60))) {
            return time;
        }
        Log.d("since grad", "" + sinceGraduation);
        return time;
    }*/

    public static void autoLogout(final Context context, boolean isSessionExpired, final String stringMsg) {
        if (isSessionExpired) {

            new AlertDialog.Builder(context)
                    .setIcon(context.getResources().getDrawable(R.drawable.ic_alert))
                    .setTitle(R.string.session_expired)
                    .setCancelable(false)
                    .setMessage(R.string.session_expired_msg)
                    .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Helper_Method.toaster_long((Activity) context, stringMsg);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                SharedPref.clearPref(context);
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                ActivityCompat.finishAffinity((Activity) context);

                            }
                        }
                    })
                    /*   .setNegativeButton(activity.getString(R.string._continue), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       })*/
                    .show();

        } else {
            Helper_Method.toaster((Activity) context, stringMsg);
        }
    }



    public static void removePhoneKeypad(Activity context) {
        if (context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null) {
            System.out.println("getCurrentFocus() in frag");
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            IBinder binder = context.getCurrentFocus().getWindowToken();
            inputManager.hideSoftInputFromWindow(binder,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        context.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }

    public static void ManualLogout(final Context context) {

        new AlertDialog.Builder(context)
                .setIcon(context.getResources().getDrawable(R.drawable.logo))
                .setTitle(R.string.logout_msg)
                .setCancelable(false)
                .setMessage(R.string.logout_full_message)
                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Helper_Method.toaster_long((Activity) context, stringMsg);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            SharedPref.clearPref(context);
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            ActivityCompat.finishAffinity((Activity) context);
                        }
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();


    }
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }
}
