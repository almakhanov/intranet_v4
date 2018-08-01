package kz.batana.intranet_v4

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kz.batana.intranet_v4.AppConstants.ANONYMOUS

class App : Application() {

    companion object {
        lateinit var databaseReference : DatabaseReference
        lateinit var firebaseAuth: FirebaseAuth
        lateinit var roleOfUser: String

        fun log(message: String) {
            Log.d("accepted", message)
        }
    }

    override fun onCreate() {
        super.onCreate()
        log("App is loading")
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        roleOfUser = ANONYMOUS
    }
}