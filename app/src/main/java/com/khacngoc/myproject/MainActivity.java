package com.khacngoc.myproject;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DemoHMSAcountKit";
    AccountAuthParams authParams;
    AccountAuthService authService;
    Button signin;
    Button reward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin = (Button) findViewById(R.id.signin);
        reward = (Button) findViewById(R.id.reward);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                singInId();
            }
        });

        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, RewardActivity.class);
                startActivity(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> signInIDResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    Intent data = result.getData();
                    Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
                    if (authAccountTask.isSuccessful()){
                        AuthAccount authAccount = authAccountTask.getResult();
                        Log.i (TAG, authAccount.getDisplayName()+ "sigin success ");
                        Log.i (TAG, "idToken+ {" + authAccount.getIdToken()+"}");

                    }
                    else {
                        Log.i (TAG, "signin failed: "+ ((ApiException)authAccountTask.getException()).getStatusCode());

                    }
                }
            });
    private void singInId (){
        authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).
                setIdToken().setAccessToken().createParams();
        authService = AccountAuthManager.getService(MainActivity.this, authParams);
        signInIDResult.launch(authService.getSignInIntent());
    }

}