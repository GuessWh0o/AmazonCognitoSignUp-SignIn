package cognito.amazon.signing.signinamazon.present;

import android.content.Context;
import android.util.Log;

import cognito.amazon.signing.signinamazon.R;
import cognito.amazon.signing.signinamazon.iview.ConfirmRegistrationView;
import cognito.amazon.signing.signinamazon.model.network.AuthenticationProvider;

import static cognito.amazon.signing.signinamazon.ui.Tools.UiTools.getResultOk;


public class ConfirmRegistrationPresenter implements AuthenticationProvider.SignUpListener {
    private static final String TAG = "ConfirmRegistrationPres";
    private ConfirmRegistrationView view;

    AuthenticationProvider authProvider;

    public ConfirmRegistrationPresenter(ConfirmRegistrationView view, Context context) {
        this.view = view;

        authProvider = new AuthenticationProvider(this, context);
    }

    public void detachView() {
        view = null;
    }

    public void onBtnClick(String code, String userId) {
        authProvider.confirmReg(code, userId);
    }

    @Override
    public void onFailure(Exception exception) {
        view.showMessage(exception.getMessage());
    }

    @Override
    public void onRegSuccess(String userId) {
        //do nothing
    }

    @Override
    public void onFailure(int resError) {
        view.showMessage(resError);
    }

    @Override
    public void onConfirmRegSuccess() {
        Log.d(TAG,"   <<<onConfirmRegSuccess>>>    ");
        view.showMessage(R.string.confirm_success);
        view.close(getResultOk());
    }
}
