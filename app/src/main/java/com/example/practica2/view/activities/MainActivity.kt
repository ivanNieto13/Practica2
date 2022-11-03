package com.example.practica2.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica2.R
import com.example.practica2.databinding.ActivityMainBinding
import com.example.practica2.db.DbCar
import com.example.practica2.model.Car
import com.example.practica2.view.activities.adapters.CarAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listCars: ArrayList<Car>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dbCars = DbCar(this)

        listCars = dbCars.getCars()

        val carsAdapter = CarAdapter(this, listCars)
        binding.rvCars.layoutManager = LinearLayoutManager(this)
        binding.rvCars.adapter = carsAdapter

        /*if(listGames.size == 0) binding.tvSinRegistros.visibility = View.VISIBLE
        else binding.tvSinRegistros.visibility = View.INVISIBLE*/
    }

    fun click(view: View) {
        startActivity(Intent(this, AddCarActivity::class.java))
        finish()
    }

    fun selectedCar(car: Car) {
        val intent = Intent(this, CarDetailActivity::class.java)
        intent.putExtra(getString(R.string.app_car_id), car.id)
        startActivity(intent)
        finish()
    }
}