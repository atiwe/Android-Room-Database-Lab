package se.mau.ac8110.p1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneItemFragment extends Fragment {
    private TextView tvName;
    private TextView tvId;
    private TextView tvCategory;
    private TextView tvAmount;
    private TextView tvDate;
    private TextView tvType;


    public OneItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one_item, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {
        tvName = view.findViewById(R.id.tvItemNameTitle);
        tvId= view.findViewById(R.id.tvIdOne2);
        tvCategory = view.findViewById(R.id.tvCategoryOne2);
        tvAmount = view.findViewById(R.id.tvAmountOne2);
        tvDate = view.findViewById(R.id.tvDateOne2);
        tvType = view.findViewById(R.id.tvTypeOne2);
    }

    public void setTextViews(String name, int itemId, String category, int amount, int date, String type){
        tvName.setText(name);
        tvId.setText("" + itemId);
        tvCategory.setText(category);
        tvAmount.setText("" + amount);
        tvDate.setText("" + date);
        tvType.setText(type);
    }
}
