package com.example.pulperia.CRUD

import android.content.Context
import android.database.Cursor
import com.example.pulperia.Configuracion.Transacciones
import com.example.pulperia.Models.productos
import com.example.pulperia.SQLiteconexiones.SQLiteconexion

class Leer(private val context: Context) {
    fun producto(id: Int): productos? {
        val conexion = SQLiteconexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.readableDatabase
        val params = arrayOf(id.toString())
        var producto: productos? = null

        try {
            val cursor = db.query(Transacciones.tbProducts, null, "${Transacciones.id} = ?", params, null, null, null)

            if (cursor.moveToFirst()) {
                producto = cursorToProducto(cursor)
            }
            cursor.close()
        } catch (e: Exception) {
            // Manejar la excepción, por ejemplo, loguearla
        }
        db.close()
        return producto
    }

    private fun cursorToProducto(cursor: Cursor): productos {
        return productos().apply {
            setId(cursor.getInt(cursor.getColumnIndexOrThrow(Transacciones.id)))
            setName(cursor.getString(cursor.getColumnIndexOrThrow(Transacciones.name)))
            setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(Transacciones.price)))
            setStock(cursor.getInt(cursor.getColumnIndexOrThrow(Transacciones.stock)))
            setBarcode(cursor.getString(cursor.getColumnIndexOrThrow(Transacciones.barcode)))
            setMin_stock(cursor.getInt(cursor.getColumnIndexOrThrow(Transacciones.min_stock)))
            setCreated_at(cursor.getString(cursor.getColumnIndexOrThrow(Transacciones.created_at)))
        }
    }
}
