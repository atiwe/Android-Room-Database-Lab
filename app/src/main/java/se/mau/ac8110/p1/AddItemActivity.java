package se.mau.ac8110.p1;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    AddItemFragment addItemFragment;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Bundle bundle = getIntent().getExtras();
        category = bundle.getString("cat");
        initAddItemFragment();
    }

    private void initAddItemFragment() {
        FragmentManager fm = getSupportFragmentManager();
        addItemFragment = (AddItemFragment) fm.findFragmentById(R.id.fragment3);
        addItemFragment.setCategory(category);
    }
}
