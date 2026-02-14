package com.example.pulperia.Configuracion

object Transacciones {

    const val dbname = "pulperia.db"
    const val dbversion = 3



    const val tbProducts = "Products"

    const val id = "id"
    const val name = "name"
    const val price = "price"
    const val stock = "stock"
    const val barcode = "barcode"
    const val min_stock = "min_stock"
    const val created_at = "created_at"



    const val tbMovements = "Movements"

    const val product_id = "product_id"
    const val qty = "qty"
    const val type = "type"
    const val date = "date"
    const val notes = "notes"


    const val tbAlerts = "Alerts"

    const val alert_type = "alert_type"
    const val message = "message"
    const val status = "status"
}
