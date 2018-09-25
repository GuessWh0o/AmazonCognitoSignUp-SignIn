package cognito.amazon.signing.signinamazon.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cognito.amazon.signing.signinamazon.R;
import cognito.amazon.signing.signinamazon.iview.AuthView;
import cognito.amazon.signing.signinamazon.present.AuthPresenter;
import cognito.amazon.signing.signinamazon.ui.Tools.AuthAndRegUtils;

import static cognito.amazon.signing.signinamazon.present.Tools.AuthAndRegPresentTools.getTextLength;
import static cognito.amazon.signing.signinamazon.ui.Tools.AuthAndRegUtils.edGetText;

public class AuthActivity extends AppCompatActivity implements AuthView {
    private static final String RES_IDENTIFIER_NAME_STATUS_BAR_HEIGHT = "status_bar_height";
    private static final String DEF_TYPE_DIMEN = "dimen";
    private static final String DEF_PACKAGE_ANDROID = "android";

    private static final String PHONE_MASK = "+7 (___) ___ ____";

    private final AuthPresenter presenter = new AuthPresenter(this);

    private EditText loginEd;
    private EditText passEd;
    private Button signinBtn;

    public static Intent start(Context context) {
        return new Intent(context, AuthActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configViews();

        fillScroll();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            presenter.backBtnPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.activityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void close(int result) {
        setResult(result, new Intent());
        AuthAndRegUtils.finishActivity(this);
    }

    @Override
    public void showMessage(int messageRes) {
        AuthAndRegUtils.showMessage(this, messageRes);
    }

    @Override
    public void showMessage(String message) {
        AuthAndRegUtils.showMessage(this, message);
    }

    @Override
    public void setUpSignBtn(int resText, boolean enabled) {
        signinBtn.setText(resText);
        signinBtn.setEnabled(enabled);
    }

    @Override
    public void showConfirmDialog(String userId) {
        //todo resend confirm code
    }

    @Override
    public void getUseInfo() {
        //do nothing
    }

    @Override
    public void startRegisterActivity(int reguestCode, String login, String pass) {
        startActivityForResult(RegisterActivity.start(this, login, pass), reguestCode);
    }

    @Override
    public void setSigningInState() {
        setSignInBtnState(R.string.auth_signingIn, false);
    }

    @Override
    public void setSignInState() {
        setSignInBtnState(R.string.sign_in_button_text, true);
    }

    private void setSignInBtnState(int resSingInBtnText, boolean enabled) {
        signinBtn.setText(resSingInBtnText);
        signinBtn.setEnabled(enabled);
    }

    private void configViews() {
        loginEd = findViewById(R.id.loginEd);
        passEd = findViewById(R.id.passEd);
        signinBtn = findViewById(R.id.loginBtn);

        //TODO
        //addPhoneEdMask();

        addTextChagedListener(loginEd);
        addTextChagedListener(passEd);

        findViewById(R.id.regBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.regBtnPressed(edGetText(loginEd), edGetText(passEd));
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginBtnPressed(edGetText(loginEd), edGetText(passEd));
            }
        });
    }

    private void addTextChagedListener(EditText ed) {
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.textChanged(edGetTextLength(loginEd), edGetTextLength(passEd));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void fillScroll() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        int stateBarHeight = 0;
        int resourceId = getResources().getIdentifier(RES_IDENTIFIER_NAME_STATUS_BAR_HEIGHT, DEF_TYPE_DIMEN, DEF_PACKAGE_ANDROID);
        if (resourceId > 0) {
            stateBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        ImageView glassyLayout = findViewById(R.id.glassyLayout);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) glassyLayout.getLayoutParams();
        params.height = size.y - actionBarHeight - stateBarHeight;
        glassyLayout.requestLayout();
    }

    private int edGetTextLength(EditText ed) {
        return getTextLength(ed.getText().toString());
    }
}
