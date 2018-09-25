package cognito.amazon.signing.signinamazon;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.config.AWSConfiguration;

import cognito.amazon.signing.signinamazon.utils.AWSProvider;

//import cognito.amazon.signing.signinamazon.utils.AppUtils;

public class SignInApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    private int depth = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        AWSProvider.initialize(getApplicationContext());
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (depth == 0) {
            Log.d("ActivityLifeCycle", "Application entered foreground");
            AWSProvider.getInstance().getPinpointManager().getSessionClient().startSession();
            AWSProvider.getInstance().getPinpointManager().getAnalyticsClient().submitEvents();
        }
        depth++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        depth--;
        if (depth == 0) {
            Log.d("ActivityLifeCycle", "Application entered background");
            AWSProvider.getInstance().getPinpointManager().getSessionClient().stopSession();
            AWSProvider.getInstance().getPinpointManager().getAnalyticsClient().submitEvents();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


//    private void createCredentialsProvider() {
//        // Initialize application
//
//        AppUtils.init(getApplicationContext());
//        awsConfiguration = new AWSConfiguration(this);
//
//
//        if (IdentityManager.getDefaultIdentityManager() == null) {
//            CognitoCachingCredentialsProvider credentials = new CognitoCachingCredentialsProvider(
//                    getApplicationContext(),
//                    AppUtils.IDENTITY_POOL_ID,
//                    AppUtils.REGION);
//            final IdentityManager identityManager = new IdentityManager(getApplicationContext(), awsConfiguration);
//            IdentityManager.setDefaultIdentityManager(identityManager);
//        }
//
//
//        // Add UserPools as an SignIn Provider.
//        IdentityManager.getDefaultIdentityManager().addSignInProvider(CustomCognitoUserPoolsSignInProvider.class);
//
//    }
}
