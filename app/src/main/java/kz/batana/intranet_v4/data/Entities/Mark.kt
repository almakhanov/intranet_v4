package kz.batana.intranet_v4.data.Entities

data class Mark(var value: Int, var course_id: String) {
    constructor(): this(0,"")
}