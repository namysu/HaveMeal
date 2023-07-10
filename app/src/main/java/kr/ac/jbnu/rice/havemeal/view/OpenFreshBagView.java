package kr.ac.jbnu.rice.havemeal.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import io.reactivex.functions.Consumer;
import kr.ac.jbnu.rice.havemeal.MainActivity;
import kr.ac.jbnu.rice.havemeal.R;
import kr.ac.jbnu.rice.havemeal.controller.BTConnector;
import kr.ac.jbnu.rice.havemeal.model.GlobalStorage;

public class OpenFreshBagView extends AppCompatActivity {
    private static final String TAG = "OpenFreshBagView";

    public static final int OPEN_FRESH_BAG_VIEW_START = 30;
    public static final int OPEN_FRESH_BAG_OPEN_SUCCESS = 31;
    public static final int OPEN_FRESH_BAG_OPEN_FAIL = 32;

    private TextView freshbagMACTextView;

    private Button freshbagRecognizeQRButton;
    private Button freshbagOpenButton;

    private GlobalStorage globalStorage;
    private BTConnector btConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_fresh_bag_view);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.menu_center);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        globalStorage = GlobalStorage.getInstance();
        btConnector = BTConnector.getInstance();

        TextView mTitle = (TextView)findViewById(R.id.custom_title);
        mTitle.setText("프레시백 개방");

        globalStorage = GlobalStorage.getInstance();

        freshbagMACTextView = (TextView) findViewById(R.id.fresh_bag_mac_textview);

        freshbagRecognizeQRButton = (Button) findViewById(R.id.fresh_bag_recognize_qr_button);
        freshbagOpenButton = (Button) findViewById(R.id.open_fresh_bag_button);

        freshbagMACTextView.setText("배달이 2023.07.10.11.02.03에 완료되었습니다.");

        freshbagRecognizeQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.getMainContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    new IntentIntegrator(OpenFreshBagView.this).initiateScan();
                }
            }
        });

        freshbagOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect("98:D3:41:FD:92:C0");
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
            if (result != null) {
                // 스캔된 QRCode --> result.getContents()
                String resData = result.getContents();
                Log.d(TAG, "----- resdata : " + resData);

                connect(resData);
            }
        }
    }

    public void connect(String mac) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OpenFreshBagView.this);
        btConnector.connectDevice(mac, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                BluetoothSerialDevice device = (BluetoothSerialDevice) o;
                SimpleBluetoothDeviceInterface deviceInterface = device.toSimpleDeviceInterface();

                deviceInterface.sendMessage("1");
            }
        }, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });

        builder.setTitle("프래시백 개방");
        builder.setMessage("프래시백 " + mac + " 을 개방합니다.");
        builder.setCancelable(false);

        builder.setPositiveButton("확인", null);
        builder.show();
    }
}
