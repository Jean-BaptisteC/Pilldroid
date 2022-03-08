package net.foucry.pilldroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import net.foucry.pilldroid.models.Medic;

import java.util.List;

@Dao
public interface MedicDAO {
  @Insert
  void insertAll(Medic... medics);

  @Update
  void update(Medic... medics);

  @Delete
  void delete(Medic medic);


  @Query("SELECT * FROM medics")
  List<Medic> getMedics();

/*  @Query("SELECT * FROM medics WHERE id = :id")
  Medic getMedicBy(Long id);*/

  @Query("SELECT * FROM medics WHERE cip13 = :cip13")
  Medic getMedicByCIP13(String cip13);

  @Query("SELECT count(*) FROM medics")
  int getMedicCount();
}
