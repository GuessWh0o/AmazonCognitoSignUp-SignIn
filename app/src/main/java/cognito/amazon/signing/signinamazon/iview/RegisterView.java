package cognito.amazon.signing.signinamazon.iview;

public interface RegisterView extends IView {
    void setUpSignBtn(int btn_text, boolean enable);

    void showConfirmDialog(String userId);

    void getUseInfo();
}
