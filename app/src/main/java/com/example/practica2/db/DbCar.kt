package com.example.practica2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.practica2.R
import com.example.practica2.model.Car

class DbCar(private val context: Context) : DbHelper(context) {
    fun addCar(brand: String, model: String, version: String, year: Int, km: Int): Long {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var id: Long = 0

        try {
            val values = ContentValues()

            val table = context.getString(R.string.app_table_name)
            val brandString = context.getString(R.string.app_brand)
            val modelString = context.getString(R.string.app_model)
            val versionString = context.getString(R.string.app_version)
            val yearString = context.getString(R.string.app_year)
            val kmString = context.getString(R.string.app_km)

            values.put(brandString, brand)
            values.put(modelString, model)
            values.put(versionString, version)
            values.put(yearString, year)
            values.put(kmString, km)

            id = db.insert(table, null, values)

        } catch (e: Exception) {
            //Manejo de excepción
        } finally {
            db.close()
        }

        return id
    }

    fun getCars(): ArrayList<Car> {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var listCars = ArrayList<Car>()
        var carTmp: Car? = null
        var cursorCars: Cursor? = null

        cursorCars = db.rawQuery("SELECT * FROM CARS", null)

        if (cursorCars.moveToFirst()) {
            do {
                carTmp = Car(
                    cursorCars.getInt(0),
                    cursorCars.getString(1),
                    cursorCars.getString(2),
                    cursorCars.getString(3),
                    cursorCars.getInt(4),
                    cursorCars.getInt(5),
                )
                listCars.add(carTmp)
            } while (cursorCars.moveToNext())
        }

        cursorCars.close()

        return listCars
    }

    fun getCar(id: Int): Car? {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var car: Car? = null

        var cursorCars: Cursor? = null

        cursorCars = db.rawQuery("SELECT * FROM CARS WHERE id = $id LIMIT 1", null)

        if (cursorCars.moveToFirst()) {
            car = Car(
                cursorCars.getInt(0),
                cursorCars.getString(1),
                cursorCars.getString(2),
                cursorCars.getString(3),
                cursorCars.getInt(4),
                cursorCars.getInt(5),
            )
        }

        cursorCars.close()

        return car
    }

    fun updateCar(
        id: Int,
        brand: String,
        model: String,
        version: String,
        year: Int,
        km: Int
    ): Boolean {
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try {
            db.execSQL("UPDATE CARS SET brand = '$brand', model = '$model', version = '$version', year = $year, km = $km WHERE id = $id")
            banderaCorrecto = true
        } catch (e: Exception) {

        } finally {
            db.close()
        }

        return banderaCorrecto
    }

    fun deleteCar(id: Int): Boolean {
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try {
            db.execSQL("DELETE FROM CARS WHERE id = $id")
            banderaCorrecto = true
        } catch (e: Exception) {

        } finally {
            db.close()
        }

        return banderaCorrecto
    }

}
