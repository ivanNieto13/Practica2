package com.example.practica2.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.practica2.R
import com.example.practica2.databinding.ActivityEditCarBinding
import com.example.practica2.db.DbCar
import com.example.practica2.model.Car
import java.util.*

class EditCarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCarBinding
    private lateinit var dbCars: DbCar

    var car: Car? = null
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if (bundle != null) {
            id = bundle.getInt(getString(R.string.app_car_id), 0)
        }

        dbCars = DbCar(this)

        car = dbCars.getCar(id)

        car?.let { car ->
            with(binding) {
                val brands = resources.getStringArray(R.array.marcasTipos)
                val brand = brands.indexOf(car.brand.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                })
                spMarca.setSelection(brand)
                tietModelo.setText(car.model)
                tietVersion.setText(car.version)
                tietAnio.setText(car.year.toString())
                tietKm.setText(car.km.toString())
            }
        }
    }

    fun click(view: View) {
        val dbCars = DbCar(this)
        with(binding) {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            when {
                spMarca.selectedItemId == 0.toLong() -> {
                    Toast.makeText(
                        this@EditCarActivity,
                        R.string.app_toast_brand,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                tietModelo.text.toString().isEmpty() -> {
                    tietModelo.error = getString(R.string.app_toast_empty)
                }
                tietVersion.text.toString().isEmpty() -> {
                    tietVersion.error = getString(R.string.app_toast_empty)
                }
                tietAnio.text.toString().isEmpty() -> {
                    tietAnio.error = getString(R.string.app_toast_empty)
                }
                tietAnio.text.toString().toInt() > currentYear + 1 || tietAnio.text.toString()
                    .toInt() < currentYear - 50 -> {
                    tietAnio.error = getString(R.string.app_toast_year_error)
                }
                tietKm.text.toString().isEmpty() -> {
                    tietKm.error = getString(R.string.app_toast_empty)
                }
                else -> {
                    val marcas = resources.getStringArray(R.array.marcasTipos)
                    if (dbCars.updateCar(
                            id, marcas[spMarca.selectedItemId.toInt()].lowercase(),
                            tietModelo.text.toString(),
                            tietVersion.text.toString(),
                            tietAnio.text.toString().toInt(),
                            tietKm.text.toString().toInt()
                        )
                    ) {
                        Toast.makeText(
                            this@EditCarActivity,
                            getString(R.string.app_toast_update_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@EditCarActivity, CarDetailActivity::class.java)
                        intent.putExtra(getString(R.string.app_car_id), id)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@EditCarActivity,
                            getString(R.string.app_toast_update_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, CarDetailActivity::class.java)
        intent.putExtra(getString(R.string.app_car_id), id)
        startActivity(intent)
    }
}