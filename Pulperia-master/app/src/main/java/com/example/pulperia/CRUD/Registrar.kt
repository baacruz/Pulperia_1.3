package com.example.pulperia.CRUD

import android.content.ContentValues
import android.content.Context
import com.example.pulperia.Configuracion.Transacciones
import com.example.pulperia.SQLiteconexiones.SQLiteconexion

class Registrar(private val context: Context) {
    fun movimiento(productId: Int, quantityChange: Int, type: String, notes: String?): Long {
        val conexion = SQLiteconexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase
        val values = ContentValues().apply {
            put(Transacciones.product_id, productId)
            put(Transacciones.qty, quantityChange)
            put(Transacciones.type, type)
            notes?.let { put(Transacciones.notes, it) }
        }
        val resultado = db.insert(Transacciones.tbMovements, null, values)
        db.close()
        return resultado
    }
}
