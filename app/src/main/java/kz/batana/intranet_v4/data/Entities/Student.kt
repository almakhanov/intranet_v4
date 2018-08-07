package kz.batana.intranet_v4.data.Entities

data class Student(var name: String, var age: Int, var yearOfStudy: Int){
    constructor(): this("",0,  0)
}