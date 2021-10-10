package ALDM.daata

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface tDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(daata: Daata)

    @Query("SELECT * from daata ORDER BY gio,phut ASC") // tra ve 1 list bao thuc dc sap xep theo gio
    fun getDa(): LiveData<List<Daata>>

    @Update
    suspend fun update(daata: Daata)

    @Delete
    suspend fun delete(daata: Daata)




}