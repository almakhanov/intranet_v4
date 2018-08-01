package kz.batana.intranet_v4.data.Entities

data class User(var username: String, var password: String) {
    constructor() : this("", "") {}
}