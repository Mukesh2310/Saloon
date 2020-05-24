package saloon.com.saloon;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class MyApplication extends Application {
    private static Context context;
    private static Context ACTIVITYCONTEXT = null;
    static SimpleDateFormat sdfForDynamicDateTime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);
    static String tag = "MyApplication";
    static Activity activity;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static Context getACTIVITYCONTEXT() {
        return ACTIVITYCONTEXT;
    }

    public static void setACTIVITYCONTEXT(Context aCTIVITYCONTEXT) {
        ACTIVITYCONTEXT = aCTIVITYCONTEXT;
    }

    public static void hideSoftKeyboard(Context ctx, View view) {
        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
