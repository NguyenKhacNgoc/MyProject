package com.khacngoc.myproject;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "HMSAccountKit";
    AccountAuthParams authParams;
    AccountAuthService authService;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();
            }
        });
    }
    ActivityResultLauncher<Intent> signinIdResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
                    if (authAccountTask.isSuccessful())
                    {
                        AuthAccount authAccount = authAccountTask.getResult();
                        Log.i(TAG, authAccount.getDisplayName()+"Đăng nhập thành công");
                        Log.i(TAG, "ID Token + {"+authAccount.getIdToken() + "}");

                    }
                    else
                    {
                        Log.i(TAG, "Đăng nhập không thành công" + ((ApiException) authAccountTask.getException()).getStatusCode());
                    }

                }
            });
    public void Signin ()
    {
        authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).
                setIdToken().setAccessToken().createParams();
        authService = AccountAuthManager.getService(SettingsActivity.this, authParams);
        signinIdResult.launch(authService.getSignInIntent());

    }




}