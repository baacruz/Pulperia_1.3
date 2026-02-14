package com.example.pulperia.SQLiteconexiones

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pulperia.Configuracion.Transacciones

class SQLiteconexion(context: Context, dbName: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, dbName, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        val createProductsTable = "CREATE TABLE ${Transacciones.tbProducts} (" +
                "${Transacciones.id} INTEGER PRIMARY KEY," +
                "${Transacciones.name} TEXT NOT NULL," +
                "${Transacciones.price} REAL NOT NULL," +
                "${Transacciones.stock} INTEGER NOT NULL," +
                "${Transacciones.barcode} TEXT," +
                "${Transacciones.min_stock} INTEGER," +
                "${Transacciones.created_at} DATETIME DEFAULT CURRENT_TIMESTAMP)"

        val createMovementsTable = "CREATE TABLE ${Transacciones.tbMovements} (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Transacciones.product_id} INTEGER NOT NULL," +
                "${Transacciones.qty} INTEGER NOT NULL," +
                "${Transacciones.type} TEXT NOT NULL," +
                "${Transacciones.date} DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "${Transacciones.notes} TEXT," +
                "FOREIGN KEY(${Transacciones.product_id}) REFERENCES ${Transacciones.tbProducts}(${Transacciones.id}))"

        val createAlertsTable = "CREATE TABLE ${Transacciones.tbAlerts} (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Transacciones.product_id} INTEGER NOT NULL," +
                "${Transacciones.alert_type} TEXT NOT NULL," +
                "${Transacciones.message} TEXT NOT NULL," +
                "${Transacciones.status} TEXT NOT NULL," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(${Transacciones.product_id}) REFERENCES ${Transacciones.tbProducts}(${Transacciones.id}))"

        db.execSQL(createProductsTable)
        db.execSQL(createMovementsTable)
        db.execSQL(createAlertsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS products")
        db.execSQL("DROP TABLE IF EXISTS ${Transacciones.tbAlerts}")
        db.execSQL("DROP TABLE IF EXISTS ${Transacciones.tbMovements}")
        db.execSQL("DROP TABLE IF EXISTS ${Transacciones.tbProducts}")
        onCreate(db)
    }
}
