package com.example.practica2.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2.R
import com.example.practica2.databinding.ActivityAddCarBinding
import com.example.practica2.db.DbCar
import java.util.*

class AddCarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun click(view: View) {
        val dbCars = DbCar(this)
        with(binding) {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            when {
                spMarca.selectedItemId == 0.toLong() -> {
                    Toast.makeText(
                        this@AddCarActivity,
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
                    val id = dbCars.addCar(
                        marcas[spMarca.selectedItemId.toInt()].lowercase(),
                        tietModelo.text.toString(),
                        tietVersion.text.toString(),
                        tietAnio.text.toString().toInt(),
                        tietKm.text.toString().toInt()
                    )

                    if (id > 0) {
                        Toast.makeText(
                            this@AddCarActivity,
                            R.string.app_toast_add_success,
                            Toast.LENGTH_SHORT
                        ).show()
                        tietModelo.setText("")
                        tietVersion.setText("")
                        tietAnio.setText("")
                        tietKm.setText("")
                        spMarca.requestFocus()
                    } else {
                        Toast.makeText(
                            this@AddCarActivity,
                            R.string.app_toast_add_error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))

    }

}