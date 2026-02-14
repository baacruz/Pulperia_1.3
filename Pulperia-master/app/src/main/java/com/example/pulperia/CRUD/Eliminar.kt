package com.example.pulperia.CRUD

import android.content.Context
import com.example.pulperia.Configuracion.Transacciones
import com.example.pulperia.SQLiteconexiones.SQLiteconexion

class Eliminar(private val context: Context) {
    fun producto(id: Int): Int {
        val conexion = SQLiteconexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase
        val params = arrayOf(id.toString())
        val resultado = db.delete(Transacciones.tbProducts, "${Transacciones.id} = ?", params)
        db.close()
        return resultado
    }
}
