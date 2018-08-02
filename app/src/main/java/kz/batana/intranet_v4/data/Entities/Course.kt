package kz.batana.intranet_v4.data.Entities

data class Course(var name: String, var year: Int, var credit: Int) {
    constructor(): this("",0, 0)
}