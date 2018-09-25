package cognito.amazon.signing.signinamazon.ui.Tools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class AuthAndRegUtils {
    private static final String TAG = "AuthAndRegUtils";

    public static String edGetText(EditText ed) {
        return ed.getText().toString();
    }

    public static void finishActivity(Activity activity) {
        activity.finish();
    };

    public static void showMessage(Context context, int messageRes) {
        showMessage(context, context.getString(messageRes));
    }

    public static void showMessage(Context context, String message) {
        Log.d(TAG, "showMessage: " + message);
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
