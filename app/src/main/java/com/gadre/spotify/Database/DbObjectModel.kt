package com.gadre.spotify.Database

data class TCity (val cityname:String){
    var cityID:Int?=null
    override fun toString(): String {
        return cityname
    }
}
data class TSalesPerson(val personname:String){
    var personID:Int?= null
    override fun toString(): String {
        return personname
    }
}

data class  TSales(val month :String,val year:String,val salesPersonID:Int,val cityID:Int,val sales:Double)

data class SalesInfo(val month: String,val year: String,val salesPersonName: String,val cityName: String,val sales: Double)

object ColumnHelper{
    //tcity
    val TABLE_NAME_CITY:String="tCity"
    val TCITY_ID:String="id"
    val TCITY_NAME:String = "cityName"



    //tSalesPerson
    val TABLE_NAME_SELLS_PERSPN :String="tSalesPerson"
    val TSALES_PERSON_ID:String="id"
    val TSALES_PERSON_NAME:String = "salesPerson"


    //tSales
    val TABLE_SALES:String="tSales"
    val TSALES_MONTH:String="month"
    val TSALES_YEAR:String="year"
    val TSALES_PERSON_FK:String="salesPerson"
    val TSALES_CITY_FK:String="city"
    val TSALES_SELLS:String="sells"



}

