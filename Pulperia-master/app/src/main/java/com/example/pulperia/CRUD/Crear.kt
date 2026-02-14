package com.example.pulperia.CRUD

import android.content.ContentValues
import android.content.Context
import com.example.pulperia.Configuracion.Transacciones
import com.example.pulperia.SQLiteconexiones.SQLiteconexion

class Crear(private val context: Context) {
    fun producto(id: Int, nombre: String, precio: Double, stock: Int, barcode: String?, minStock: Int?): Long {
        val conexion = SQLiteconexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase
        val values = ContentValues().apply {
            put(Transacciones.id, id)
            put(Transacciones.name, nombre)
            put(Transacciones.price, precio)
            put(Transacciones.stock, stock)
            barcode?.let { put(Transacciones.barcode, it) }
            minStock?.let { put(Transacciones.min_stock, it) }
        }
        val resultado = db.insert(Transacciones.tbProducts, null, values)
        db.close()
        return resultado
    }
}
