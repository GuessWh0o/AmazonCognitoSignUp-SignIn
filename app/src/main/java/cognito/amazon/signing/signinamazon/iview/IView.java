package cognito.amazon.signing.signinamazon.iview;

public interface IView {
    void close(int resultCode);

    void showMessage(int messageRes);

    void showMessage(String message);
}
