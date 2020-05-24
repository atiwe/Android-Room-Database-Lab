package se.mau.ac8110.p1;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insertItem(Item item);

    @Update
    void updateItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Query("DELETE FROM item_table")
    void deleteAllItems();

    @Query("DELETE FROM item_table WHERE item_id = :id")
    void deleteItemById(int id);

    @Query("SELECT * FROM item_table WHERE item_id LIKE :id LIMIT 1")
    Item findItemById(int id);

    @Query("Select * FROM item_table WHERE item_category LIKE :category")
    public List<Item> findByCategory(String category);

    @Query("SELECT item_name FROM item_table")
    List<String> allNames();

    @Query("SELECT * FROM item_table")
    List<Item> getAllItems();

    @Query("SELECT * FROM item_table WHERE item_category LIKE :wantedCategory")
    List<Item> findListByCategory(String wantedCategory);

    @Query("SELECT sum(item_amount) FROM item_table WHERE item_category LIKE :sumCategory")
    int findSumCategory(String sumCategory);

    @Query("SELECT sum(item_amount) FROM item_table WHERE item_date >= :dateFrom and item_date <= :dateTo and item_category LIKE :sumCategory")
    int getSumBetweenDates(int dateFrom, int dateTo, String sumCategory);

    @Query("SELECT * FROM item_table WHERE item_date >= :dateFrom and item_date <= :dateTo and item_category LIKE :category")
    List<Item> getItemsBetweenDates(int dateFrom, int dateTo, String category);


}
