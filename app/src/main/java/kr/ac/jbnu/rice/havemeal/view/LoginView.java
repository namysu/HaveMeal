package kr.ac.jbnu.rice.havemeal.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import kr.ac.jbnu.rice.havemeal.R;
import kr.ac.jbnu.rice.havemeal.model.GlobalStorage;

public class LoginView extends AppCompatActivity {
    private static final String TAG = "LoginView";

    public static final int LOGIN_VIEW_START = 10;
    public static final int LOGIN_VIEW_SUCCESS = 11;
    public static final int LOGIN_VIEW_FAIL_ID = 12;
    public static final int LOGIN_VIEW_FAIL_PW = 13;
    public static final int LOGIN_VIEW_FAIL_SERVER = 14;
    public static final int LOGIN_VIEW_CANCEL_USER = 15;

    private EditText userInputIDEditText;
    private EditText userInputPasswordEditText;
    private Button userLoginButton;
    private TextView userSignUpTextView;

    private GlobalStorage globalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.havemeal_linear_logo);

        globalStorage = GlobalStorage.getInstance();

        hasGetPermissions(LoginView.this);

        userInputIDEditText = (EditText) findViewById(R.id.login_input_id);
        userInputPasswordEditText = (EditText) findViewById(R.id.login_input_password);

        userLoginButton = (Button) findViewById(R.id.login_button);

        userSignUpTextView = (TextView) findViewById(R.id.login_signup);

        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalStorage.setUserID(userInputIDEditText.getText().toString());

                setResult(LOGIN_VIEW_SUCCESS);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginView.this);
        builder.setTitle("종료");
        builder.setMessage("정말로 종료하시겠습니까?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean hasGetPermissions(Context context) {
        final int PERMISSION_REQUEST_CODE1 = 9990;
        final String[] permissions = {
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.USE_FULL_SCREEN_INTENT,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.FOREGROUND_SERVICE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (context != null) {
            for (String permission : permissions) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && permission.equals(android.Manifest.permission.USE_FULL_SCREEN_INTENT)) {
                    continue;

                } else {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(LoginView.this, permissions, PERMISSION_REQUEST_CODE1);
                        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
