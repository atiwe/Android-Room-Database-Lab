package se.mau.ac8110.p1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "item_table")
public class Item {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    private int itemId;

    @ColumnInfo(name = "item_name")
    private String name;

    @ColumnInfo(name = "item_category")
    private String category;

    @ColumnInfo(name = "item_date")
    private int date;

    @ColumnInfo(name = "item_amount")
    private int amount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ColumnInfo(name = "item_type")
    private String type = "sad";

    public Item(String name, String category, int date, int amount, String type) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    public int getItemId(){
        return itemId;
    }

    public void setItemId(int itemId){
        this.itemId = itemId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public int getDate(){
        return date;
    }

    public void setDate(int date){
        this.date = date;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
}

