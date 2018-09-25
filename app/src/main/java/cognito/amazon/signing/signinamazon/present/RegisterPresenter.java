package cognito.amazon.signing.signinamazon.present;

import android.content.Context;

import cognito.amazon.signing.signinamazon.R;
import cognito.amazon.signing.signinamazon.iview.RegisterView;
import cognito.amazon.signing.signinamazon.model.network.AuthenticationProvider;

import static cognito.amazon.signing.signinamazon.present.Tools.AuthAndRegPresentTools.isParamCorrect;
import static cognito.amazon.signing.signinamazon.present.Tools.AuthAndRegPresentTools.isPassCorrect;
import static cognito.amazon.signing.signinamazon.present.Tools.AuthAndRegPresentTools.saveData;


public class RegisterPresenter implements AuthenticationProvider.SignUpListener {
    private RegisterView view;

    private Context context;

    private AuthenticationProvider authProvider;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
        this.context = (Context) view;
        authProvider = new AuthenticationProvider(this, (Context) view);
    }

    public void textChanged(int phoneLength, int nameLength, int emailLength,
                            int passLength, int confirmPassLength) {
        if (isRegParamsCorrect(phoneLength, nameLength, emailLength, passLength, confirmPassLength)) {
            view.setUpSignBtn(R.string.reg_btn_text_signin, true);
        } else {
            view.setUpSignBtn(R.string.auth_btn_text_not_fill, false);
        }
    }

    public void regBtnPressed(String phone, String name, String email, String pass, String confirmPass) {
        if (pass.equals(confirmPass)) {
            authProvider.register(phone, name, email, pass);
        } else {
            view.showMessage(R.string.pass_not_equals);
        }
    }

    public void detachView() {
        view = null;
    }

    @Override
    public void onFailure(Exception exception) {
        view.showMessage(exception.getMessage());
    }

    @Override
    public void onRegSuccess(String userId) {
        view.showMessage(R.string.regSuccess);
        view.getUseInfo();
        view.showConfirmDialog(userId);
    }

    @Override
    public void onFailure(int resError) {
        view.showMessage(resError);
    }

    @Override
    public void onConfirmRegSuccess() {
        //do nothing
    }

    private boolean isRegParamsCorrect(int nameLen, int loginLen, int emailLen, int passLen, int confirmPassLen) {
        return isParamCorrect(nameLen) && isParamCorrect(loginLen) && isParamCorrect(emailLen)
                && isPassCorrect(passLen) && isPassCorrect(confirmPassLen);
    }

    public void getUserInfo(String phone, String name, String email, String password) {
        saveData(phone, name, email, password, null, context);
    }
}
