package com.example.pulperia

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pulperia.Configuracion.Transacciones
import com.example.pulperia.SQLiteconexiones.SQLiteconexion

class Resumen : AppCompatActivity() {

    private lateinit var conexion: SQLiteconexion
    private lateinit var txtTotal: TextView
    private lateinit var listAllProducts: ListView
    private lateinit var listaProductos: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)

        conexion = SQLiteconexion(this, Transacciones.dbname, null, Transacciones.dbversion)
        txtTotal = findViewById(R.id.textView)
        listAllProducts = findViewById(R.id.tblresumen)

        mostrarResumen()
    }

    private fun mostrarResumen() {
        val db = conexion.readableDatabase

        val totalQuery = "SELECT SUM(${Transacciones.price} * ${Transacciones.stock}) FROM ${Transacciones.tbProducts}"
        db.rawQuery(totalQuery, null)?.use { cursorTotal ->
            var total = 0.0
            if (cursorTotal.moveToFirst()) {
                total = cursorTotal.getDouble(0)
            }
            txtTotal.text = "Valor Total del Inventario: L. ${String.format("%.2f", total)}"
        }

        val queryAllProducts = """
            SELECT ${Transacciones.name}, ${Transacciones.price}, ${Transacciones.stock}, (${Transacciones.price} * ${Transacciones.stock}) as valor_total
            FROM ${Transacciones.tbProducts}
            ORDER BY valor_total DESC
        """        .trimIndent()

        db.rawQuery(queryAllProducts, null)?.use { cursor ->
            listaProductos = ArrayList()
            while (cursor.moveToNext()) {
                val nombre = cursor.getString(0)
                val precioUnitario = cursor.getDouble(1)
                val stock = cursor.getInt(2)
                val valorTotal = cursor.getDouble(3)
                val item = "$nombre | Stock: $stock | Unit: L. ${String.format("%.2f", precioUnitario)} | Total: L. ${String.format("%.2f", valorTotal)}"
                listaProductos.add(item)
            }
        }

        if (::listaProductos.isInitialized) {
            val adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaProductos)
            listAllProducts.adapter = adp
        }
    }
}
