package se.mau.ac8110.p1;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {
    private TextView tvTitle;
    private String category;
    private AppDatabase database;
    private ListView lvItems;
    private CostumListView adapter;
    private Button btnAddItem;
    private Button btnChangeDate;
    private EditText etDateFrom;
    private EditText etDateTo;
    private TextView tvErrorMessage;
    private List<Item> itemList;
    private Integer[] imgidIncome;
    private Integer[] imgidSpending;
    private int dateFrom;
    private int dateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        initializeComponents();
        if(savedInstanceState!=null){
            dateFrom = savedInstanceState.getInt("datefrom");
            dateTo = savedInstanceState.getInt("dateto");
        }else{
            dateFrom = 0;
            dateTo = 999999;
        }
        setListViewBetweenDates();
        lvItems.setOnItemClickListener(new ListViewListener());
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("datefrom", dateFrom);
        outState.putInt("dateto", dateTo);
    }

    private void initializeComponents() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        Bundle message = getIntent().getExtras();
        category = message.getString("category");
        tvTitle.setText(category);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "databasenames").allowMainThreadQueries().build();
        lvItems = (ListView) findViewById(R.id.lvItems);
        btnAddItem = (Button) findViewById(R.id.btnNewItem);
        btnAddItem.setOnClickListener(new ButtonListener());
        btnChangeDate = (Button) findViewById(R.id.btnChangeDetailedDate);
        btnChangeDate.setOnClickListener(new ButtonListener());
        etDateFrom = (EditText) findViewById(R.id.etDetailedDateFrom);
        etDateTo = (EditText) findViewById(R.id.etDetailedDateTo);
        tvErrorMessage = (TextView) findViewById(R.id.tvDetailedErrorMessage);
        imgidIncome =new Integer[]{R.drawable.salary, R.drawable.extraincome};
        imgidSpending =new Integer[]{R.drawable.gros, R.drawable.sparetime,R.drawable.travel, R.drawable.rent,R.drawable.extraspending};
    }

    private void setListViewBetweenDates(){
        ArrayList<String> listItems = new ArrayList<String>();
        ArrayList<String> listItemsInfo = new ArrayList<String>();
        ArrayList<String> categorys = new ArrayList<String>();
        itemList = database.itemDao().getItemsBetweenDates(dateFrom, dateTo, category);
        for(int i = 0; i<itemList.size(); i++){
            Item item = itemList.get(i);
            listItems.add(item.getName() + " - " + item.getAmount() + "kr");
            listItemsInfo.add("Datum: " + item.getDate() + ", Kategori: " + item.getType());
            categorys.add(item.getType());
        }
        if(category.equals("Inkomster")){
            adapter = new CostumListView(this, listItems, listItemsInfo, imgidIncome, categorys);
        }else{
            adapter = new CostumListView(this, listItems, listItemsInfo, imgidSpending, categorys);
        }
        lvItems.setAdapter(adapter);
    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnNewItem){
                changeActivity();
            }else if(v.getId() == R.id.btnChangeDetailedDate){
                changeListDates();
            }
        }
    }

    private void changeListDates(){
        String textFrom = etDateFrom.getText().toString();
        String textTo = etDateTo.getText().toString();
        if(textFrom.matches("\\d+(?:\\.\\d+)?") && textTo.matches("\\d+(?:\\.\\d+)?"))
        {
            tvErrorMessage.setText("");
            dateFrom = Integer.parseInt(textFrom);
            dateTo = Integer.parseInt(textTo);
            setListViewBetweenDates();
        }else{
            tvErrorMessage.setText("Felaktigt datumintervall");
            tvErrorMessage.setTextColor(Color.RED);
        }
    }

    private void changeActivity(){
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("cat", category);
        startActivity(intent);
    }

    private class ListViewListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String name = itemList.get(position).getName();
            int itemId = itemList.get(position).getItemId();
            int amount = itemList.get(position).getAmount();
            int date = itemList.get(position).getDate();
            String type = itemList.get(position).getType();
            //tvDetailedItem.setText("" + amount);
            goOneItemActivity(name, itemId, category, amount, date, type);
        }
    }

    private void goOneItemActivity(String name, int itemId, String category, int amount, int date, String type) {
        Intent intent = new Intent(this, OneItemActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("itemId", itemId);
        intent.putExtra("category", category);
        intent.putExtra("amount", amount);
        intent.putExtra("date", date);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
