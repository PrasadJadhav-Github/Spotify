package com.gadre.spotify.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Locale

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "DbSalesRecords", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        try {
            val createTCityTable =
                "CREATE TABLE ${ColumnHelper.TABLE_NAME_CITY} (${ColumnHelper.TCITY_ID} INTEGER PRIMARY KEY AUTOINCREMENT,${ColumnHelper.TCITY_NAME} TEXT)"
            db?.execSQL(createTCityTable)

            val createTPersonTable =
                "CREATE TABLE ${ColumnHelper.TABLE_NAME_SELLS_PERSPN}(${ColumnHelper.TSALES_PERSON_ID} INTEGER PRIMARY KEY AUTOINCREMENT,${ColumnHelper.TSALES_PERSON_NAME} TEXT)"
            db?.execSQL(createTPersonTable)

            val createTSalesTable =
                "CREATE TABLE ${ColumnHelper.TABLE_SALES}(${ColumnHelper.TSALES_MONTH} TEXT,${ColumnHelper.TSALES_YEAR} TEXT,${ColumnHelper.TSALES_PERSON_FK} INTEGER,${ColumnHelper.TSALES_CITY_FK} INTEGER,${ColumnHelper.TSALES_SELLS} TEXT)"
            db?.execSQL(createTSalesTable)
        } catch (e: Exception) {

            e.printStackTrace()
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}


    fun insertCity(city: TCity): Boolean {
        val db = this.writableDatabase // method is used for operations like insert data
        val values = ContentValues().apply {
            put(ColumnHelper.TCITY_NAME, city.cityname)
        }
        val id = db.insert(ColumnHelper.TABLE_NAME_CITY, null, values)
        db.close()
        return id != -1L
    }

    fun insertSales(sales: TSales): Boolean {
        val db = this.writableDatabase
        val sales = ContentValues().apply {
            put(ColumnHelper.TSALES_MONTH, sales.month.lowercase(Locale.ROOT))
            put(ColumnHelper.TSALES_YEAR, sales.year)
            put(ColumnHelper.TSALES_PERSON_FK, sales.salesPersonID)
            put(ColumnHelper.TSALES_CITY_FK, sales.cityID)
            put(ColumnHelper.TSALES_SELLS, sales.sales)
        }
        val id = db.insert(ColumnHelper.TABLE_SALES, null, sales)
        return id != -1L
    }


    fun insertPersonName(tSalesPerson: TSalesPerson): Boolean {
        val db = this.writableDatabase
        val names = ContentValues().apply {
            put(ColumnHelper.TSALES_PERSON_NAME, tSalesPerson.personname)
        }

        val id = db.insert(ColumnHelper.TABLE_NAME_SELLS_PERSPN, null, names)
        db.close()
        return id != -1L
    }


    //function to select all city from sqlite database
    fun selectCity(): List<TCity> {  //ArrayList is used to collect and return the list of TCity objects
        val db = this.readableDatabase //method is used for operations that only fetch or read data

        //execute query to select all city from sqlite database
        //rowqery =used to execute a custom SQL query
        val cursor: Cursor? =
            db.rawQuery("SELECT * FROM ${ColumnHelper.TABLE_NAME_CITY}", arrayOf())

        // ArrayList named globalarr is initialized to hold the TCity objects that will be created from the query results.
        var globalarr: ArrayList<TCity> = ArrayList()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    val cityname =
                        cursor.getString(cursor.getColumnIndexOrThrow(ColumnHelper.TCITY_NAME))
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(ColumnHelper.TCITY_ID))
                    val city = TCity(cityname)
                    city.cityID = id
                    globalarr.add(city)
                }
            }
            cursor.close()
        }
        db.close()
        return globalarr
    }


    fun selectpersonname(): List<TSalesPerson> {
        val db = this.readableDatabase
        val curser: Cursor? =
            db.rawQuery("SELECT * FROM ${ColumnHelper.TABLE_NAME_SELLS_PERSPN}", arrayOf())
        var globalarrperson: ArrayList<TSalesPerson> = ArrayList()

        if (curser != null) {
            if (curser.moveToFirst()) {
                while (curser.moveToNext()) {
                    val name =
                        curser.getString(curser.getColumnIndexOrThrow(ColumnHelper.TSALES_PERSON_NAME))
                    val id =
                        curser.getInt(curser.getColumnIndexOrThrow(ColumnHelper.TSALES_PERSON_ID))
                    val personname = TSalesPerson(name)
                    personname.personID = id
                    globalarrperson.add(personname)

                }
            }
            curser.close()
        }

        db.close()
        return globalarrperson
    }

    fun selectSalesDetails(month: String, year: String): ArrayList<SalesInfo> {
        val db = this.readableDatabase
        val curser = db.rawQuery(
            "SELECT * FROM ${ColumnHelper.TABLE_SALES} WHERE ${ColumnHelper.TSALES_MONTH}='" + month.lowercase() + "' AND ${ColumnHelper.TSALES_YEAR}='" + year + "'",
            arrayOf()
        )
        val globalArraySales: ArrayList<SalesInfo> = ArrayList()
        if (curser != null) {
            if (curser.moveToFirst()) {
                while (curser.moveToNext()) {
                    val month =
                        curser.getString(curser.getColumnIndexOrThrow(ColumnHelper.TSALES_MONTH))
                    val year =
                        curser.getString(curser.getColumnIndexOrThrow(ColumnHelper.TSALES_YEAR))
                    val personFk =
                        curser.getInt(curser.getColumnIndexOrThrow(ColumnHelper.TSALES_PERSON_FK))
                    val cityFk =
                        curser.getInt(curser.getColumnIndexOrThrow(ColumnHelper.TSALES_CITY_FK))
                    val sales =
                        curser.getDouble(curser.getColumnIndexOrThrow(ColumnHelper.TSALES_SELLS))

                    val city = selectCityFromId(cityFk)
                    val person = selectPersonFromId(personFk)
                    val salesInfo = SalesInfo(
                        month = month,
                        year = year,
                        sales = sales,
                        cityName = city.cityname,
                        salesPersonName = person.personname
                    )
                    globalArraySales.add(salesInfo)
                }
            }
            curser.close()

        }
        db.close()
        return globalArraySales
    }

    fun selectCityFromId(id: Int): TCity {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${ColumnHelper.TABLE_NAME_CITY} WHERE  ${ColumnHelper.TCITY_ID}=$id ",
            arrayOf()
        )
        var tCity: TCity = TCity("")
        if (cursor.moveToFirst()) {
            val cityname = cursor.getString(cursor.getColumnIndexOrThrow(ColumnHelper.TCITY_NAME))
            tCity = TCity(cityname)
            tCity.cityID = id
        }
        cursor.close()
        db.close()
        return tCity
    }

    fun selectPersonFromId(id: Int): TSalesPerson {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${ColumnHelper.TABLE_NAME_SELLS_PERSPN} WHERE ${ColumnHelper.TSALES_PERSON_ID}=$id",
            arrayOf()
        )
        var tPersonName: TSalesPerson = TSalesPerson(" ")
        if (cursor.moveToFirst()) {
            val personname =
                cursor.getString(cursor.getColumnIndexOrThrow(ColumnHelper.TSALES_PERSON_NAME))
            tPersonName = TSalesPerson(personname)
            tPersonName.personID = id
        }
        cursor.close()
        db.close()
        return tPersonName
    }

}


