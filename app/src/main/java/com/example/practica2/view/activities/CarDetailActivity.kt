package com.example.practica2.view.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.practica2.R
import com.example.practica2.databinding.ActivityCarDetailBinding
import com.example.practica2.db.DbCar
import com.example.practica2.model.Car
import java.util.*


class CarDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarDetailBinding
    private lateinit var dbCars: DbCar

    var car: Car? = null
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if (bundle != null) {
            id = bundle.getInt(getString(R.string.app_car_id), 0)
        }

        dbCars = DbCar(this)

        car = dbCars.getCar(id)

        car?.let { car ->
            with(binding) {
                when (car.brand) {
                    getString(R.string.app_brands_audi) -> ivCar.setImageResource(R.drawable.audi)
                    getString(R.string.app_brands_bmw) -> ivCar.setImageResource(R.drawable.bmw)
                    getString(R.string.app_brands_chevrolet) -> ivCar.setImageResource(R.drawable.chevrolet)
                    getString(R.string.app_brands_toyota) -> ivCar.setImageResource(R.drawable.toyota)
                    getString(R.string.app_brands_volkswagen) -> ivCar.setImageResource(R.drawable.volkswagen)
                    else -> {
                        ivCar.setImageResource(R.drawable.generic)
                    }
                }
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

                tietModelo.inputType = InputType.TYPE_NULL
                tietVersion.inputType = InputType.TYPE_NULL
                tietAnio.inputType = InputType.TYPE_NULL
                tietKm.inputType = InputType.TYPE_NULL
            }
        }
    }


    fun click(view: View) {
        when (view.id) {
            R.id.btnEdit -> {
                val intent = Intent(this, EditCarActivity::class.java)
                intent.putExtra(getString(R.string.app_car_id), id)
                startActivity(intent)
                finish()
            }
            R.id.btnDelete -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_toast_warning_title))
                    .setMessage(getString(R.string.app_toast_warning_message))
                    .setPositiveButton(
                        getString(R.string.app_toast_accept)
                    ) { dialog, which ->
                        if (dbCars.deleteCar(id)) {
                            Toast.makeText(
                                this@CarDetailActivity,
                                getString(R.string.app_toast_delete_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(
                                Intent(
                                    this@CarDetailActivity,
                                    MainActivity::class.java
                                )
                            )
                            finish()
                        } else {
                            Toast.makeText(
                                this@CarDetailActivity,
                                getString(R.string.app_toast_delete_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .setNegativeButton(
                        getString(R.string.app_toast_cancel),
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                    .show()

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}