package com.example.proyectofinalandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitaAdapter(
    private var citas: List<Cita>,
    private val onEditar: (Cita) -> Unit,
    private val onEliminar: (Cita) -> Unit
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fechaTextView: TextView = itemView.findViewById(R.id.fechaTextView)
        val servicioTextView: TextView = itemView.findViewById(R.id.servicioTextView)
        val precioTextView: TextView = itemView.findViewById(R.id.precioTextView)
        val tiempoTextView: TextView = itemView.findViewById(R.id.tiempoTextView)
        val btnEditar: View = itemView.findViewById(R.id.btnEditar)
        val btnEliminar: View = itemView.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]

        val fechaFormateada = cita.fecha.replace("T", " ").substringBeforeLast(":")
        holder.fechaTextView.text = "Fecha: $fechaFormateada"
        holder.servicioTextView.text = "Servicio: ${cita.servicio.nombre}"
        holder.precioTextView.text = "Precio: ${cita.servicio.precio}€"
        holder.tiempoTextView.text = "Tiempo: ${cita.servicio.tiempo} minutos"

        holder.btnEditar.setOnClickListener { onEditar(cita) }
        holder.btnEliminar.setOnClickListener { onEliminar(cita) }
    }

    override fun getItemCount(): Int = citas.size

    fun actualizarCitas(nuevasCitas: List<Cita>) {
        citas = nuevasCitas
        notifyDataSetChanged()
    }
}
