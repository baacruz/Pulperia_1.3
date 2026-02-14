package com.example.pulperia

import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pulperia.CRUD.Editar
import com.example.pulperia.CRUD.Leer
import com.example.pulperia.CRUD.Registrar
import com.example.pulperia.Configuracion.Transacciones
import com.example.pulperia.Models.productos
import com.example.pulperia.SQLiteconexiones.SQLiteconexion

class Movimientos : AppCompatActivity() {

    private lateinit var conexion: SQLiteconexion
    private lateinit var lista: ListView
    private lateinit var adp: ArrayAdapter<String>
    private var lproducts = ArrayList<productos>()

    // Vistas para la actualización
    private lateinit var txtIdMovimiento: EditText
    private lateinit var txtQuantity: EditText
    private lateinit var btnActualizar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimientos)

        conexion = SQLiteconexion(this, Transacciones.dbname, null, Transacciones.dbversion)
        lista = findViewById(R.id.tblmovimientos)
        txtIdMovimiento = findViewById(R.id.txtid_movimiento)
        txtQuantity = findViewById(R.id.txtquantity)
        btnActualizar = findViewById(R.id.btnactualizar)

        obtenerProductos()

        btnActualizar.setOnClickListener {
            actualizarStockProducto()
        }

        val mainLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun actualizarStockProducto() {
        val idStr = txtIdMovimiento.text.toString()
        val newQuantityStr = txtQuantity.text.toString()

        if (idStr.isEmpty() || newQuantityStr.isEmpty()) {
            Toast.makeText(this, "Debe ingresar el ID del producto y la nueva cantidad", Toast.LENGTH_SHORT).show()
            return
        }

        val productoId = idStr.toInt()
        val newQuantity = newQuantityStr.toInt()

        val leer = Leer(this)
        val producto = leer.producto(productoId)

        if (producto == null) {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        val oldQuantity = producto.stock
        val quantityChange = newQuantity - oldQuantity
        val editar = Editar(this)
        val resultadoUpdate = editar.producto(
            id = producto.id,
            nombre = producto.name,
            precio = producto.price,
            stock = newQuantity, // La nueva cantidad de stock
            barcode = producto.barcode,
            minStock = producto.min_stock
        )

        if (resultadoUpdate > 0) {
            Toast.makeText(this, "Stock actualizado correctamente", Toast.LENGTH_SHORT).show()

            val registrar = Registrar(this)
            registrar.movimiento(
                productId = productoId,
                quantityChange = quantityChange,
                type = "Ajuste Manual",
                notes = "Ajuste desde pantalla de Movimientos"
            )

            txtIdMovimiento.text.clear()
            txtQuantity.text.clear()
            obtenerProductos()
        } else {
            Toast.makeText(this, "Error al actualizar el stock", Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtenerProductos() {
        val db = conexion.readableDatabase
        lproducts.clear()
        val cursor = db.rawQuery("SELECT * FROM ${Transacciones.tbProducts}", null)

        while (cursor.moveToNext()) {
            lproducts.add(cursorToProducto(cursor))
        }
        cursor.close()
        db.close()

        llenarLista()
    }

    private fun llenarLista() {
        val arrProducts = lproducts.map { "${it.id} - ${it.name} | Stock: ${it.stock} | Unit: L. ${String.format("%.2f", it.price)}" }
        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrProducts)
        lista.adapter = adp
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
