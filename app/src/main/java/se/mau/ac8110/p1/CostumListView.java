package se.mau.ac8110.p1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CostumListView extends ArrayAdapter<String> {
    private ArrayList<String> itemNames;
    private ArrayList<String> info;
    private Integer[] imageId;
    private Activity context;
    private ArrayList<String> category;

    public CostumListView(Activity context, ArrayList<String> itemNames, ArrayList<String> info, Integer[] imageId, ArrayList<String> category) {
        super(context, R.layout.listview_layout, itemNames);
        this.context = context;
        this.itemNames = itemNames;
        this.info = info;
        this.imageId = imageId;
        this.category = category;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if(view==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.listview_layout,null, true);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(itemNames.get(position));
        viewHolder.tvInfo.setText(info.get(position));
        setImageInView(viewHolder, position);
        return view;
    }

    private void setImageInView(ViewHolder viewHolder, int position){
        if(category.get(position).equals("Lön")){
            viewHolder.imageView.setImageResource(imageId[0]);
        }else if(category.get(position).equals("Övrigt")){
            viewHolder.imageView.setImageResource(imageId[1]);
        }else if(category.get(position).equals("Livsmedel")){
            viewHolder.imageView.setImageResource(imageId[0]);
        }else if(category.get(position).equals("Fritid")){
            viewHolder.imageView.setImageResource(imageId[1]);
        }else if(category.get(position).equals("Resor")){
            viewHolder.imageView.setImageResource(imageId[2]);
        }else if(category.get(position).equals("Boende")){
            viewHolder.imageView.setImageResource(imageId[3]);
        }else if(category.get(position).equals("Övrigt")){
            viewHolder.imageView.setImageResource(imageId[4]);
        }
    }

    private class ViewHolder{
        TextView textView;
        TextView tvInfo;
        ImageView imageView;

        ViewHolder(View view){
            textView = (TextView) view.findViewById(R.id.tvInformation);
            tvInfo = (TextView) view.findViewById(R.id.tvInformation2);
            imageView =(ImageView) view.findViewById(R.id.imageViewTest);
        }
    }
}
