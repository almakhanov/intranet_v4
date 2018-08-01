package kz.batana.intranet_v4.data.Entities

data class Teacher(var name: String, var age: Int, var degree: String){
    constructor(): this("",0, ""){}
}