package kr.ac.jbnu.rice.havemeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import kr.ac.jbnu.rice.havemeal.view.LoginView;
import kr.ac.jbnu.rice.havemeal.view.OpenFreshBagView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContext = getApplicationContext();

        setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.havemeal_linear_logo);

        Intent loginIntent = new Intent(getApplicationContext(), LoginView.class);
        startActivityForResult(loginIntent, LoginView.LOGIN_VIEW_START);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LoginView.LOGIN_VIEW_START) {
            if (resultCode == LoginView.LOGIN_VIEW_SUCCESS) {
                Intent openFreshbagIntent = new Intent(getApplicationContext(), OpenFreshBagView.class);
                startActivityForResult(openFreshbagIntent, OpenFreshBagView.OPEN_FRESH_BAG_VIEW_START);

            } else if (resultCode == LoginView.LOGIN_VIEW_CANCEL_USER) {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }

    }

    public static Context getMainContext() {
        return mainContext;
    }
}