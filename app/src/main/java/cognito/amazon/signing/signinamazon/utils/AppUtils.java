//package cognito.amazon.signing.signinamazon.utils;
//
//import android.content.Context;
//
//import com.amazonaws.regions.Regions;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class AppUtils {
//
//    public static final Regions REGION = Regions.EU_CENTRAL_1;
//
//
//
//
//    public static final String IDENTITY_POOL_ID = "us-east-1:ba3d2ee7-97ec-432e-b778-be6816264677";
//    // App settings
//
//    private static List<String> attributeDisplaySeq;
//    private static Map<String, String> signUpFieldsC2O;
//    private static Map<String, String> signUpFieldsO2C;
//
//    private static AppHelper appHelper;
//    private static CognitoUserPool userPool;
//    private static String user;
//    private static CognitoDevice newDevice;
//
//    private static List<ItemToDisplay> currDisplayedItems;
//    private static int itemCount;
//
//    private static List<ItemToDisplay> trustedDevices;
//    private static int trustedDevicesCount;
//    private static List<CognitoDevice> deviceDetails;
//    private static CognitoDevice thisDevice;
//    private static boolean thisDeviceTrustState;
//
//    private static List<ItemToDisplay> firstTimeLogInDetails;
//    private static Map<String, String> firstTimeLogInUserAttributes;
//    private static List<String> firstTimeLogInRequiredAttributes;
//    private static int firstTimeLogInItemsCount;
//    private static Map<String, String> firstTimeLogInUpDatedAttributes;
//    private static String firstTimeLoginNewPassword;
//
//    // Change the next three lines of code to run this demo on your user pool
//
//    /**
//     * Add your pool id here
//     */
//    public static final String userPoolId = "us-east-1_H9jNLncP8";
//
//    /**
//     * Add you app id
//     */
//    private static final String clientId = "7ksggetonggpvu4rcj3thea606";
//
//    /**
//     * App secret associated with your app id - if the App id does not have an associated App secret,
//     * set the App secret to null.
//     * e.g. clientSecret = null;
//     */
//    private static final String clientSecret = "ln76nu7gohokmbbrsk7lu96v4bmmi1gvc5hlmt1en4ooq4u2cub";
//
//    /**
//     * Set Your User Pools region.
//     * e.g. if your user pools are in US East (N Virginia) then set cognitoRegion = Regions.US_EAST_1.
//     */
//    public static final Regions cognitoRegion = Regions.US_EAST_1;
//
//    // User details from the service
//    private static CognitoUserSession currSession;
//    private static CognitoUserDetails userDetails;
//
//    // User details to display - they are the current values, including any local modification
//    private static boolean phoneVerified;
//    private static boolean emailVerified;
//
//    private static boolean phoneAvailable;
//    private static boolean emailAvailable;
//
//    private static Set<String> currUserAttributes;
//
//
//
//    public static void init(Context context) {
//        setData();
//
//        if (appHelper != null && userPool != null) {
//            return;
//        }
//
//        if (appHelper == null) {
//            appHelper = new AppHelper();
//        }
//
//        if (userPool == null) {
//
//            // Create a user pool with default ClientConfiguration
//            userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);
//
//            // This will also work
//            /*
//            ClientConfiguration clientConfiguration = new ClientConfiguration();
//            AmazonCognitoIdentityProvider cipClient = new AmazonCognitoIdentityProviderClient(new AnonymousAWSCredentials(), clientConfiguration);
//            cipClient.setRegion(Region.getRegion(cognitoRegion));
//            userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, cipClient);
//            */
//
//        }
//
//        phoneVerified = false;
//        phoneAvailable = false;
//        emailVerified = false;
//        emailAvailable = false;
//
//        currUserAttributes = new HashSet<String>();
//        currDisplayedItems = new ArrayList<ItemToDisplay>();
//        trustedDevices = new ArrayList<ItemToDisplay>();
//        firstTimeLogInDetails = new ArrayList<ItemToDisplay>();
//        firstTimeLogInUpDatedAttributes = new HashMap<String, String>();
//
//        newDevice = null;
//        thisDevice = null;
//        thisDeviceTrustState = false;
//    }
//
//    private static void setData() {
//        // Set attribute display sequence
//        attributeDisplaySeq = new ArrayList<String>();
//        attributeDisplaySeq.add("given_name");
//        attributeDisplaySeq.add("middle_name");
//        attributeDisplaySeq.add("family_name");
//        attributeDisplaySeq.add("nickname");
//        attributeDisplaySeq.add("phone_number");
//        attributeDisplaySeq.add("email");
//
//        signUpFieldsC2O = new HashMap<String, String>();
//        signUpFieldsC2O.put("Given name", "given_name");
//        signUpFieldsC2O.put("Family name", "family_name");
//        signUpFieldsC2O.put("Nick name", "nickname");
//        signUpFieldsC2O.put("Phone number", "phone_number");
//        signUpFieldsC2O.put("Phone number verified", "phone_number_verified");
//        signUpFieldsC2O.put("Email verified", "email_verified");
//        signUpFieldsC2O.put("Email", "email");
//        signUpFieldsC2O.put("Middle name", "middle_name");
//
//        signUpFieldsO2C = new HashMap<String, String>();
//        signUpFieldsO2C.put("given_name", "Given name");
//        signUpFieldsO2C.put("family_name", "Family name");
//        signUpFieldsO2C.put("nickname", "Nick name");
//        signUpFieldsO2C.put("phone_number", "Phone number");
//        signUpFieldsO2C.put("phone_number_verified", "Phone number verified");
//        signUpFieldsO2C.put("email_verified", "Email verified");
//        signUpFieldsO2C.put("email", "Email");
//        signUpFieldsO2C.put("middle_name", "Middle name");
//
//    }
//
//
//}
