package se.mau.ac8110.p1;


import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
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
    private int surplus;
    View view;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeCompnoents(view);
        if(savedInstanceState!=null){
            categoryList = new String[] {savedInstanceState.getString("income"), savedInstanceState.getString("spending")};
            setListView();
            tvSurplus.setText(savedInstanceState.getString("surplus"));
        }
        return view;
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("income", categoryList[0]);
        outState.putString("spending", categoryList[1]);
        outState.putString("surplus", tvSurplus.getText().toString());
    }

    public void onResume() {
        initializeCompnoents(view);
        super.onResume();
    }

    private void initializeCompnoents(View view) {
        tvWelcome = view.findViewById(R.id.tvWelcome);
        tvSurplus = (TextView) view.findViewById(R.id.tvSurplus);
        etDateFrom = (EditText) view.findViewById(R.id.etDateFrom);
        etDateTo = (EditText) view.findViewById(R.id.etDateTo);
        lvEconomy = (ListView) view.findViewById(R.id.lvItems);
        database = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "databasenames").allowMainThreadQueries().build();
        incomeList = database.itemDao().findListByCategory("Income");
        int sumIncome = database.itemDao().findSumCategory("Inkomster");
        int sumSpending = database.itemDao().findSumCategory("Utgifter");
        categoryList = new String[] {"Inkomster: " + sumIncome, "Utgifter: " + sumSpending};
        surplus = (sumIncome - sumSpending);
        tvSurplus.setText("Överskott: " + surplus);

        setListView();
        lvEconomy.setOnItemClickListener(new ListViewListener());
        buttonChangeDate = (Button) view.findViewById(R.id.btnChangeDate);
        buttonChangeDate.setOnClickListener(new ButtonListener());
        btnAddName = (Button) view.findViewById(R.id.btnAddName);
        btnAddName.setOnClickListener(new AddNameListener());
        tvErrorMessage = (TextView) view.findViewById(R.id.tvErrorMessage);
        etAddFirstName = view.findViewById(R.id.etAddFirstName);
        etAddLastName = view.findViewById(R.id.etAddLastName);
        setTvWelcome();
    }
    private void setTvWelcome(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MainActivity", Activity.MODE_PRIVATE);
        firstName = sharedPreferences.getString("firstName", null);
        lastName = sharedPreferences.getString("lastName", null);
        if(firstName!=null && lastName!=null){
            if(surplus>0){
                tvWelcome.setText(getString(R.string.welcome_text1) + firstName + " " + lastName + getString(R.string.welcome_text2good) + surplus + getString(R.string.welcome_text3));
            }else{
                tvWelcome.setText(getString(R.string.welcome_text1) + firstName + " " + lastName + getString(R.string.welcome_text2bad) + surplus + getString(R.string.welcome_text3));
            }

        }else {
            etAddFirstName.setVisibility(View.VISIBLE);
            etAddLastName.setVisibility(View.VISIBLE);
            btnAddName.setVisibility(View.VISIBLE);
        }
    }

    private void changeActivity(String category){
        Intent intent = new Intent(getContext(), DetailedActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    public void setListView(){
        adapter = new ArrayAdapter<String>(getContext(), R.layout.activity_list_view, R.id.textView, categoryList);
        lvEconomy.setAdapter(adapter);
    }

    public class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String textFrom = etDateFrom.getText().toString();
            String textTo = etDateTo.getText().toString();
            if(textFrom.matches("\\d+(?:\\.\\d+)?") && textTo.matches("\\d+(?:\\.\\d+)?"))
            {
                Log.d("match", "Matches");
                tvErrorMessage.setText("");
                int sumIncome = database.itemDao().getSumBetweenDates(Integer.parseInt(textFrom), Integer.parseInt(textTo), "Inkomster");
                int sumSpending = database.itemDao().getSumBetweenDates(Integer.parseInt(textFrom), Integer.parseInt(textTo), "Utgifter");
                categoryList = new String[] {"Inkomster: " + sumIncome, "Utgifter: " + sumSpending};
                setListView();
                tvSurplus.setText("Överskott: " + (sumIncome-sumSpending));

            }else{
                tvErrorMessage.setText("Felaktigt datumintervall");
                tvErrorMessage.setTextColor(Color.RED);
            }
        }
    }

    private class ListViewListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String message = "";
            if(position==0){
                message = "Inkomster";
            }else if(position==1){
                message = "Utgifter";
            }
            changeActivity(message);
        }
    }

    private class AddNameListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(etAddFirstName.getText().toString().isEmpty() || etAddLastName.getText().toString().isEmpty()){
                tvErrorMessage.setText("Kan inte lägga till tomt namn!");
            }else {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MainActivity", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("firstName", etAddFirstName.getText().toString());
                editor.putString("lastName", etAddLastName.getText().toString());
                editor.apply();
            }
        }
    }
}
