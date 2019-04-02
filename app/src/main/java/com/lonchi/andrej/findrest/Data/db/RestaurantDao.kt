package com.lonchi.andrej.findrest.Data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(restaurant: Restaurant)

    @Delete
    fun delete(restaurant: Restaurant)

    @Query("DELETE FROM restaurant")
    fun deleteAll()

    @Query("SELECT * FROM restaurant")
    fun getAllRestaurants(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant WHERE id=:res_id")
    fun getRestaurantById(res_id: String): LiveData<Restaurant>

    @Query("SELECT * FROM restaurant LIMIT 1")
    fun getFirstRestaurant(): Restaurant
}