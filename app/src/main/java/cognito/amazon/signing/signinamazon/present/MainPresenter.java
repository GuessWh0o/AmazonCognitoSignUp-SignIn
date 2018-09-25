package cognito.amazon.signing.signinamazon.present;


import android.content.Context;
import android.content.Intent;

import cognito.amazon.signing.signinamazon.R;
import cognito.amazon.signing.signinamazon.iview.MainView;
import cognito.amazon.signing.signinamazon.model.DataParams;
import cognito.amazon.signing.signinamazon.model.DataSaver;
import cognito.amazon.signing.signinamazon.model.network.AuthenticationProvider;

import static cognito.amazon.signing.signinamazon.present.Tools.AuthAndRegPresentTools.REGISTER_REQUEST;
import static cognito.amazon.signing.signinamazon.present.Tools.AuthAndRegPresentTools.getUserMinLength;
import static cognito.amazon.signing.signinamazon.ui.Tools.UiTools.getResultOk;

public class MainPresenter implements AuthenticationProvider.AuthListener {
    private static final int SIGN_IN_REQUEST = 1;
    private static final Object TAG = "MainPresenter";

    private MainView view;

    private Context context;

    private AuthenticationProvider authProvider;

    public MainPresenter(MainView view) {
        this.view = view;
        this.context = (Context) view;
        authProvider = new AuthenticationProvider(this, (Context) view);
    }

    public void onCreate() {
        checkAuth();
    }

    public void onResume() {
        checkAuth();
    }

    public void detachView() {
        view = null;
    }

    public void signInBtnPressed() {
        view.startAuthActivity(SIGN_IN_REQUEST);
    }

    public void RegisterBtnPressed() {
        view.startRegisterActivity(REGISTER_REQUEST);
    }

    public void signOutBtnPressed() {
        signOut();
    }

    public void exitBtnPressed() {
        view.close();
    }

    public void menuHeaderClick() {
        view.showMessage(context.getString(R.string.no_editable));
    }

    public void activityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getResultOk()) {
            return;
        }

        switch (requestCode) {
            case SIGN_IN_REQUEST:
                setUserInfo(DataSaver.getParam(DataParams.PHONE, context.getString(R.string.header_emptyProfile), context));

                return;
            case REGISTER_REQUEST:
                signIn();

                return;
        }
    }

    @Override
    public void onFailure(Exception exception) {
        view.showMessage(exception.getMessage());
    }

    @Override
    public void signInSuccessful(String userToken, String userId) {
        DataSaver.saveParam(DataParams.TOKEN, userToken, context);
        //saveData(userId, null, null, null, userToken, context);
        setUserInfo(userId);
    }

    @Override
    public void signOutSuccessful() {
        removeGreeting();
        view.fillProfileInfo(getEmptyProfileString(), getEmptyProfileString(), getEmptyProfileString());
    }

    @Override
    public void onGetUserAttributes(String name, String email) {
        setUserAttributes(name, email);
    }

    private void setUserAttributes(String name, String email) {
        view.setUserAttributes(name, email);
    }

    private String getEmptyProfileString() {
        return context.getString(R.string.header_emptyProfile);
    }

    private void checkAuth() {
        /*if (authProvider.haveCurrentUser()) {
            Logger.log("main presenter  2 begin" + authProvider.getUserId());
            String userId = authProvider.getUserId();
            setUserInfo(userId);
            Logger.log("main presenter  2 end");
            return;
        }*/

        signIn();
    }

    private void signIn() {
        String login = DataSaver.getParam(DataParams.PHONE, DataParams.DEF_VALUE, context);
        String password = DataSaver.getParam(DataParams.PASSWORD, DataParams.DEF_VALUE, context);
        if (login != null && password != null) {
            authProvider.signIn(login, password);
            return;
        }

        setUserInfo(getEmptyProfileString());
        removeGreeting();
    }

    private void setUserInfo(String userId) {
        view.setUser(userId);
        if (userId.length() >= getUserMinLength()) {
            view.setGreeting(userId);
            authProvider.getUserAttributes();
        } else {
            removeGreeting();
            view.setUserAttributes(getEmptyProfileString(), getEmptyProfileString());
        }
    }

    private void removeGreeting() {
        view.setGreeting(R.string.hello_world);
    }

    private void signOut() {
        authProvider.signOut();
        setUserInfo(getEmptyProfileString());
    }
}
