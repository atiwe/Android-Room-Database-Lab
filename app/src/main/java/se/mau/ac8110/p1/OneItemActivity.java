package se.mau.ac8110.p1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OneItemActivity extends AppCompatActivity {
    private String name;
    private int itemId;
    private String category;
    private int amount;
    private int date;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_item);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        itemId = bundle.getInt("itemId");
        category = bundle.getString("category");
        amount = bundle.getInt("amount");
        date = bundle.getInt("date");
        type = bundle.getString("type");
        initAddItemFragment();
    }
    private void initAddItemFragment() {
        FragmentManager fm = getSupportFragmentManager();
        OneItemFragment oneItemFragment = (OneItemFragment) fm.findFragmentById(R.id.oneItemFragment);
        oneItemFragment.setTextViews(name, itemId, category, amount, date, type);
    }
}
