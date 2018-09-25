package cognito.amazon.signing.signinamazon.present.Tools;

import android.content.Context;
import android.util.Log;

import cognito.amazon.signing.signinamazon.model.DataParams;
import cognito.amazon.signing.signinamazon.model.DataSaver;

public class AuthAndRegPresentTools {
    public static final int REGISTER_REQUEST = 3;

    private static final int MIN_LENGTH = 1;
    private static final int PASS_MIN_LENGTH = 6;
    private static final String TAG = "AuthAndRegUtils";

    public static boolean isPassCorrect(int passLen) {
        return (passLen >= PASS_MIN_LENGTH);
    }

    public static boolean isParamCorrect(int len) {
        return (len >= MIN_LENGTH);
    }

    public static int getTextLength(String s) {
        return s.length();
    }

    public static void saveData(String phone, String name, String email, String password, String userToken, Context context) {
        Log.d(TAG, "saveData: pass = " + password);
        DataSaver.saveParam(DataParams.PHONE, phone, context);
        DataSaver.saveParam(DataParams.NAME, name, context);
        DataSaver.saveParam(DataParams.EMAIL, email, context);
        DataSaver.saveParam(DataParams.PASSWORD, password, context);
        DataSaver.saveParam(DataParams.TOKEN, userToken, context);
    }

    public static int getUserMinLength() {
        return MIN_LENGTH;
    }
}
