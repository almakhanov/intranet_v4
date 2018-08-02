package kz.batana.intranet_v4.data.Entities

data class Admin(var name: String, var age: Int){
    constructor(): this("",0)
}