package kr.ac.jbnu.rice.havemeal.controller;

import com.harrysoft.androidbluetoothserial.BluetoothManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BTConnector {
    private static final String TAG = "BTConnector";

    private static BTConnector btConnector = null;

    private BluetoothManager bluetoothManager = null;

    private String deviceMAC;

    private BTConnector() {
        this.bluetoothManager = BluetoothManager.getInstance();
        this.deviceMAC = "";
    }

    public static BTConnector getInstance() {
        if (btConnector == null) {
            btConnector = new BTConnector();
        }

        return btConnector;
    }

    public void connectDevice(String mac, Consumer onConnected, Consumer onError) {
        bluetoothManager.openSerialDevice(mac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onConnected, onError);

        deviceMAC = mac;
    }

    public void onCloseConnection() {
        if (bluetoothManager != null) {
            bluetoothManager.closeDevice(deviceMAC);
        }
    }

    public void onFinishConnect() {
        if (bluetoothManager != null) {
            bluetoothManager.close();
        }
    }


}
