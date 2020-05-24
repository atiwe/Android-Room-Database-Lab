package se.mau.ac8110.p1;


import android.arch.persistence.room.Room;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment {
    private String selectedType;
    private String category;
    private ArrayAdapter<CharSequence> staticAdapter;
    private Spinner staticSpinner;
    private EditText etTitle;
    private EditText etAmount;
    private Button btnAdd;
    private AppDatabase database;
    private TextView tvError;
    private TextView tvConfirmAddItem;

    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        initSpinner(view);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new ButtonListener());
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etAmount = (EditText) view.findViewById(R.id.etAmount);
        database = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "databasenames").allowMainThreadQueries().build();
        tvError = (TextView) view.findViewById(R.id.tvAddItemError);
        tvConfirmAddItem = (TextView) view.findViewById(R.id.tvConfirmAdd);
    }

    public void setCategory(String category){
        this.category = category;
        changeSpinnerArray();
    }

    private void changeSpinnerArray(){
        Context context = getContext();
        if(category==null){
            staticAdapter = ArrayAdapter.createFromResource(context, R.array.spending,
                    android.R.layout.simple_spinner_item);
        }
        else if(category.equals("Utgifter")){
            staticAdapter = ArrayAdapter.createFromResource(context, R.array.spending,
                    android.R.layout.simple_spinner_item);
        }else {
            staticAdapter = ArrayAdapter.createFromResource(context, R.array.incomeArray,
                    android.R.layout.simple_spinner_item);
        }
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);
    }


    private void initSpinner(View view) {

        staticSpinner = (Spinner) view.findViewById(R.id.static_spinner);
        changeSpinnerArray();
        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                selectedType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String title = etTitle.getText().toString();
            String amount = etAmount.getText().toString();
            if(!title.isEmpty() && amount.matches("\\d+(?:\\.\\d+)?")){
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
                int date = Integer.parseInt(df.format(c));
                Item newItem = new Item(title, category, date, Integer.parseInt(amount), selectedType);
                database.itemDao().insertItem(newItem);
                tvConfirmAddItem.setText(R.string.add_item_confirm);
            }
        }
    }

}
