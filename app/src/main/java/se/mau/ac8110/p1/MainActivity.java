package se.mau.ac8110.p1;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.util.StringUtil;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private ListView lvEconomy;
    private ArrayAdapter<String> adapter;
    private String categoryList[];
    private TextView tvSurplus;
    private EditText etDateFrom;
    private EditText etDateTo;
    private AppDatabase database;
    private List<Item> incomeList;
    private Button buttonChangeDate;
    private TextView tvErrorMessage;
    private EditText etAddFirstName;
    private EditText etAddLastName;
    private Button btnAddName;
    private String firstName;
    private String lastName;
    private boolean nameAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
