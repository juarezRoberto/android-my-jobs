package com.juarezcode.myjobs.ui.home

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.juarezcode.myjobs.data.local.PreferenciasLocales
import com.juarezcode.myjobs.data.models.Postulacion
import com.juarezcode.myjobs.databinding.ActivityHomeAdminBinding
import com.juarezcode.myjobs.ui.vacante.VacantesActivity
import java.text.SimpleDateFormat
import java.util.*

class HomeAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeAdminBinding
    private val preferenciasLocales = PreferenciasLocales.getInstance(this)
    private val usuarioEnSesion by lazy { preferenciasLocales.obtenerUsuarioEnSesion() }
    private val viewModel: HomeViewModel by viewModels()
    private val postulacionesAdapter = PostulacionesAdapter(::asignarFecha)
    var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setearDatos()

        binding.homeAdminRecyclerPostulaciones.apply {
            layoutManager =
                LinearLayoutManager(this@HomeAdminActivity, LinearLayoutManager.VERTICAL, false)
            adapter = postulacionesAdapter
        }

        binding.homeAdminBotonIrAPantallaVacantes.setOnClickListener {
            val intent = Intent(this, VacantesActivity::class.java)
            startActivity(intent)
        }

        viewModel.postulaciones.observe(this) { postulaciones ->
            postulacionesAdapter.submitList(postulaciones)
        }
    }

    private fun asignarFecha(postulacion: Postulacion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog(
                this,
                { view, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val fecha = sdf.format(calendar.time)
                    asignarFechaDeCita(postulacion.id, fecha)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

    private fun asignarFechaDeCita(postulacionId: Int, fecha: String) {
        viewModel.asignarFechaDeCita(postulacionId, fecha)
    }

    private fun setearDatos() {
        binding.homeAdmintTxtUsuarioNombre.text = usuarioEnSesion.nombreCompleto
    }

    override fun onResume() {
        super.onResume()
        viewModel.obtenerPostulaciones()
    }
}