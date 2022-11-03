package com.example.practica2.view.activities.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practica2.R
import com.example.practica2.databinding.CarsElementBinding
import com.example.practica2.model.Car
import com.example.practica2.view.activities.MainActivity

class CarAdapter(private val context: Context, val cars: ArrayList<Car>): RecyclerView.Adapter<CarAdapter.ViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: CarsElementBinding): RecyclerView.ViewHolder(view.root) {
        val ivBrand = view.ivLogoMarca
        val tvModel = view.tvModelo
        val tvVersion = view.tvVersion
        val tvYear = view.tvAnio
        val tvKm = view.tvKm
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarsElementBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when(cars[position].brand) {
            getString(R.string.app_brands_audi) -> holder.ivBrand.setImageResource(R.drawable.audi)
            getString(R.string.app_brands_bmw) -> holder.ivBrand.setImageResource(R.drawable.bmw)
            getString(R.string.app_brands_chevrolet) -> holder.ivBrand.setImageResource(R.drawable.chevrolet)
            getString(R.string.app_brands_toyota) -> holder.ivBrand.setImageResource(R.drawable.toyota)
            getString(R.string.app_brands_volkswagen) -> holder.ivBrand.setImageResource(R.drawable.volkswagen)
            else -> {
                holder.ivBrand.setImageResource(R.drawable.generic)
            }
        }

        holder.tvModel.text = cars[position].model
        holder.tvVersion.text = cars[position].version
        holder.tvYear.text = cars[position].year.toString()
        holder.tvKm.text = cars[position].km.toString() + getString(R.string.app_km)

        holder.itemView.setOnClickListener {
            if(context is MainActivity) context.selectedCar(cars[position])
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    fun getString(id: Int): String {
        return context.getString(id)
    }

}