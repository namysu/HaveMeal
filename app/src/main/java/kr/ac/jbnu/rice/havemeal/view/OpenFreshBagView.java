package kr.ac.jbnu.rice.havemeal.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import kr.ac.jbnu.rice.havemeal.MainActivity;
import kr.ac.jbnu.rice.havemeal.R;
import kr.ac.jbnu.rice.havemeal.model.GlobalStorage;

public class OpenFreshBagView extends AppCompatActivity {
    private static final String TAG = "OpenFreshBagView";

    public static final int OPEN_FRESH_BAG_VIEW_START = 30;
    public static final int OPEN_FRESH_BAG_OPEN_SUCCESS = 31;
    public static final int OPEN_FRESH_BAG_OPEN_FAIL = 32;

    private TextView freshbagMACTextView;

    private Button freshbagRecognizeQRButton;
    private Button freshbagOpenButton;

    private String recognizeFreshbagMAC;

    private GlobalStorage globalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_fresh_bag_view);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.menu_center);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        globalStorage = GlobalStorage.getInstance();

        TextView mTitle = (TextView)findViewById(R.id.custom_title);
        mTitle.setText("프레시백 개방");

        globalStorage = GlobalStorage.getInstance();

        freshbagMACTextView = (TextView) findViewById(R.id.fresh_bag_mac_textview);

        freshbagRecognizeQRButton = (Button) findViewById(R.id.fresh_bag_recognize_qr_button);
        freshbagOpenButton = (Button) findViewById(R.id.open_fresh_bag_button);

        freshbagMACTextView.setText("QR을 인식시켜 주세요.");

        freshbagRecognizeQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.getMainContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    new IntentIntegrator(OpenFreshBagView.this).initiateScan();

                } else {

                }
            }
        });

        freshbagOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result == null) {
                // 취소됨

            } else {
                // 스캔된 QRCode --> result.getContents()
                String resData = result.getContents();

                Log.d(TAG, "----- resdata : " + resData);

            }
        }
    }
}
