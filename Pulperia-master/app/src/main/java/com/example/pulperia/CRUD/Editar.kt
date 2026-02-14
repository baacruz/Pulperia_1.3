package com.example.pulperia.CRUD

import android.content.ContentValues
import android.content.Context
import com.example.pulperia.Configuracion.Transacciones
import com.example.pulperia.SQLiteconexiones.SQLiteconexion

class Editar(private val context: Context) {
    fun producto(id: Int, nombre: String, precio: Double, stock: Int, barcode: String?, minStock: Int?): Int {
        val conexion = SQLiteconexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase
        val values = ContentValues().apply {
            put(Transacciones.name, nombre)
            put(Transacciones.price, precio)
            put(Transacciones.stock, stock)
            barcode?.let { put(Transacciones.barcode, it) }
            minStock?.let { put(Transacciones.min_stock, it) }
        }
        val params = arrayOf(id.toString())
        val resultado = db.update(Transacciones.tbProducts, values, "${Transacciones.id} = ?", params)
        db.close()
        return resultado
    }
}
