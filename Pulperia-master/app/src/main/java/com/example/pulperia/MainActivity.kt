package com.example.pulperia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pulperia.CRUD.Crear
import com.example.pulperia.CRUD.Editar
import com.example.pulperia.CRUD.Eliminar
import com.example.pulperia.CRUD.Leer

class MainActivity : AppCompatActivity() {

    private lateinit var id: EditText
    private lateinit var nombre: EditText
    private lateinit var precio: EditText
    private lateinit var stock: EditText
    private lateinit var btncrear: Button
    private lateinit var btnleer: Button
    private lateinit var btnupdate: Button
    private lateinit var btndelete: Button
    private lateinit var btnhistorial: Button
    private lateinit var btnresumen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = findViewById(R.id.txtid)
        nombre = findViewById(R.id.txtnombre)
        precio = findViewById(R.id.txtprecio)
        stock = findViewById(R.id.txtstock)
        btncrear = findViewById(R.id.btncrear)
        btnleer = findViewById(R.id.btnleer)
        btnupdate = findViewById(R.id.btnupdate)
        btndelete = findViewById(R.id.btndelete)
        btnhistorial = findViewById(R.id.btnlist)
        btnresumen = findViewById(R.id.btnresumen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btncrear.setOnClickListener { crearProducto() }
        
        // Modificado: El botón Leer ahora abre la pantalla de Resumen
        btnleer.setOnClickListener { 
            val intent = Intent(this, Resumen::class.java)
            startActivity(intent)
        }

        btnupdate.setOnClickListener { actualizarProducto() }
        btndelete.setOnClickListener { eliminarProducto() }
        
        btnresumen.setOnClickListener {
            val intent = Intent(this, Resumen::class.java)
            startActivity(intent)
        }

        btnhistorial.setOnClickListener {
            val intent = Intent(this, Movimientos::class.java)
            startActivity(intent)
        }
    }
    
    private fun crearProducto() {
        val idStr = id.text.toString()
        val nombreStr = nombre.text.toString()
        val precioStr = precio.text.toString()
        val stockStr = stock.text.toString()

        if (idStr.isNotEmpty() && nombreStr.isNotEmpty() && precioStr.isNotEmpty() && stockStr.isNotEmpty()) {
            val crear = Crear(this)
            val resultado = crear.producto(idStr.toInt(), nombreStr, precioStr.toDouble(), stockStr.toInt(), null, null)
            
            if (resultado != -1L) {
                Toast.makeText(this, "Producto guardado con ID: $idStr", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al guardar. ¿El ID $idStr ya existe?", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Debe llenar TODOS los campos, incluyendo el ID", Toast.LENGTH_SHORT).show()
        }
    }

    private fun leerProducto() {
        val idStr = id.text.toString()
        if (idStr.isNotEmpty()) {
            val leer = Leer(this)
            val producto = leer.producto(idStr.toInt())

            if (producto != null) {
                nombre.setText(producto.name)
                precio.setText(producto.price.toString())
                stock.setText(producto.stock.toString())
                // Aquí podrías mostrar barcode y min_stock si tuvieras los EditTexts
            } else {
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            }
        } else {
            Toast.makeText(this, "Ingrese un ID para buscar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarProducto() {
        val idStr = id.text.toString()
        val nombreStr = nombre.text.toString()
        val precioStr = precio.text.toString()
        val stockStr = stock.text.toString()

        if (idStr.isNotEmpty() && nombreStr.isNotEmpty() && precioStr.isNotEmpty() && stockStr.isNotEmpty()) {
            val editar = Editar(this)
            val resultado = editar.producto(idStr.toInt(), nombreStr, precioStr.toDouble(), stockStr.toInt(), null, null)
            if (resultado > 0) {
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarProducto() {
        val idStr = id.text.toString()
        if (idStr.isNotEmpty()) {
            val eliminar = Eliminar(this)
            val resultado = eliminar.producto(idStr.toInt())
            if (resultado > 0) {
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese un ID para eliminar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        id.text.clear()
        nombre.text.clear()
        precio.text.clear()
        stock.text.clear()
    }
}
