package cognito.amazon.signing.signinamazon.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cognito.amazon.signing.signinamazon.R;
import cognito.amazon.signing.signinamazon.iview.RegisterView;
import cognito.amazon.signing.signinamazon.present.RegisterPresenter;
import cognito.amazon.signing.signinamazon.ui.Tools.AuthAndRegUtils;

import static cognito.amazon.signing.signinamazon.present.Tools.AuthAndRegPresentTools.getTextLength;
import static cognito.amazon.signing.signinamazon.ui.Tools.AuthAndRegUtils.edGetText;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    public static final String TAG = "RegisterActivity";

    public static final String ARG_LOGIN = "login";
    public static final String ARG_PASSWORD = "password";

    private final RegisterPresenter presenter = new RegisterPresenter(this);

    private EditText phoneEd;
    private EditText nameEd;
    private EditText emailEd;
    private EditText passEd;
    private EditText confirmPassEd;
    private Button signupBtn;

    public static Intent start(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    public static Intent start(Context context, String login, String pass) {
        Intent intent = new Intent(context, RegisterActivity.class);

        Bundle arguments = new Bundle();
        arguments.putString(ARG_LOGIN, login);
        arguments.putString(ARG_PASSWORD, pass);
        intent.putExtras(arguments);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        configViews();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void setUpSignBtn(int resText, boolean enabled) {
        signupBtn.setText(resText);
        signupBtn.setEnabled(enabled);
    }

    @Override
    public void showConfirmDialog(String userId) {
        signupBtn.setEnabled(false);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        ConfirmRegistrationFragment confirmFragment = ConfirmRegistrationFragment.newInstance(userId);
        trans.add(R.id.reg_contanier, confirmFragment);
        trans.commit();
    }

    @Override
    public void getUseInfo() {
        Log.d(TAG, "getUseInfo: ");
        presenter.getUserInfo(phoneEd.getText().toString(), nameEd.getText().toString(),
                emailEd.getText().toString(), passEd.getText().toString());
    }

    @Override
    public void close(int result) {
        Log.d(TAG, "close: result " + result);
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

    private void configViews() {
        phoneEd = findViewById(R.id.phoneEd);
        nameEd = findViewById(R.id.nameEd);
        emailEd = findViewById(R.id.emailEd);
        passEd = findViewById(R.id.passEd);
        confirmPassEd = findViewById(R.id.confirmPassEd);
        signupBtn = findViewById(R.id.registerBtn);

        phoneEd.setText(getIntent().getStringExtra(ARG_LOGIN));
        passEd.setText(getIntent().getStringExtra(ARG_PASSWORD));
        confirmPassEd.setText(passEd.getText().toString());

        addTextChagedListener(phoneEd);
        addTextChagedListener(nameEd);
        addTextChagedListener(emailEd);
        addTextChagedListener(passEd);
        addTextChagedListener(confirmPassEd);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.regBtnPressed(edGetText(phoneEd), edGetText(nameEd), edGetText(emailEd),
                        edGetText(passEd), edGetText(confirmPassEd));
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
                presenter.textChanged(edGetTextLength(phoneEd), edGetTextLength(nameEd),
                        edGetTextLength(emailEd), edGetTextLength(passEd), edGetTextLength(confirmPassEd));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private int edGetTextLength(EditText ed) {
        return getTextLength(ed.getText().toString());
    }
}
