package kz.batana.intranet_v4

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kz.batana.intranet_v4.AppConstants.ANONYMOUS
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class App : Application()  {


    companion object {
        lateinit var databaseReference : DatabaseReference
        lateinit var firebaseAuth: FirebaseAuth
        var roleOfUser = ANONYMOUS

        fun log(message: String) {
            Log.d("accepted", message)
        }
    }

    override fun onCreate() {
        super.onCreate()
        log("App is loading")
        GlobalContext.startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(appModule)
        }
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
    }


}