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
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.ac.jbnu.rice.havemeal.controller.BTConnector;
import kr.ac.jbnu.rice.havemeal.controller.MainFoodAdapter;
import kr.ac.jbnu.rice.havemeal.controller.MainNotiAdapter;
import kr.ac.jbnu.rice.havemeal.model.GlobalStorage;
import kr.ac.jbnu.rice.havemeal.view.LoginView;
import kr.ac.jbnu.rice.havemeal.view.OpenFreshBagView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static Context mainContext;

    private GlobalStorage globalStorage;
    private BTConnector btConnector;

    private MainNotiAdapter mainNotiAdapter;
    private MainFoodAdapter mainFoodAdapter;

    private ListView notiListView;
    private ListView foodListView;

    private Button openFreshBagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContext = getApplicationContext();

        setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.havemeal_linear_logo);

        globalStorage = GlobalStorage.getInstance();
        btConnector = BTConnector.getInstance();

        Intent loginIntent = new Intent(getApplicationContext(), LoginView.class);
        startActivityForResult(loginIntent, LoginView.LOGIN_VIEW_START);

        mainNotiAdapter = new MainNotiAdapter(this, new ArrayList<HashMap<String, String>>() {{
            add(new HashMap<String, String>() {{
                put("desc", "첫 이용에 대한 공지사항");
                put("user", "관리자");
            }});
            add(new HashMap<String, String>() {{
                put("desc", "문의 공지");
                put("user", "완주시 복지담당");
            }});
        }});

        mainFoodAdapter = new MainFoodAdapter(this, new ArrayList<HashMap<String, String>>() {{
            add(new HashMap<String, String>() {{
                put("date", "07.10");
                put("detail", "현미밥, 된장국, 아욱무침, 떡볶이");
                put("kcal", "576kcal");
            }});
            add(new HashMap<String, String>() {{
                put("date", "07.11");
                put("detail", "보리밥, 아욱국, 조랭이떡");
                put("kcal", "513kcal");
            }});
            add(new HashMap<String, String>() {{
                put("date", "07.11");
                put("detail", "육개장, 제육볶음");
                put("kcal", "712kcal");
            }});
        }});

        notiListView = (ListView) findViewById(R.id.main_noti_listview);
        foodListView = (ListView) findViewById(R.id.main_food_listview);

        notiListView.setAdapter(mainNotiAdapter);
        foodListView.setAdapter(mainFoodAdapter);

        openFreshBagButton = (Button) findViewById(R.id.main_open_fresh_bag);

        openFreshBagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openFreshBagIntent = new Intent(getApplicationContext(), OpenFreshBagView.class);
                startActivityForResult(openFreshBagIntent, OpenFreshBagView.OPEN_FRESH_BAG_VIEW_START);
            }
        });

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
                btConnector.onFinishConnect();

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