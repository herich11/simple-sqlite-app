package com.simplesqliteapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM person")
    List<Person> getAll();

    @Insert
    void insertAll(Person... people);

    @Delete
    void delete(Person person);

    @Update
        // Anotasi untuk update
    void update(Person person);
}
