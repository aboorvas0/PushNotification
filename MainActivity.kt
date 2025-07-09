package com.example.pushnotification

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

lateinit var button:Button
 var airPlaneModeReceiver: AirPlaneModeReceiver?=null


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button=findViewById(R.id.click_btn)

        airPlaneModeReceiver= AirPlaneModeReceiver()
        //airoplane

        var filter=IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airPlaneModeReceiver,filter)


        //custom boardcaste
        val custom = IntentFilter(Constant.packageName)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(airPlaneModeReceiver, custom, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(airPlaneModeReceiver, custom)
        }




        lifecycleScope.launch {
            try {
                val token = withContext(Dispatchers.IO) {
                    FirebaseMessaging.getInstance().token.await()
                }
                Log.e(">>>>", token)
            } catch (e: Exception) {
                Log.e(">>>>", "Failed to get FCM token", e)
            }

        }

        button.setOnClickListener{

            //opening youtube

            Intent(Intent.ACTION_MAIN).also { //open the main screen
                it.`package`="com.google.android.youtube"
                try{
                    startActivity(it)
                }catch (e:ActivityNotFoundException){
                    e.stackTrace
                }
            }

            //Custom boardcaste

            val intent = Intent(Constant.packageName).apply {
                putExtra("custom_data", "6788999")
                setPackage(Constant.packageName)
            }
            sendBroadcast(intent)

        }



    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airPlaneModeReceiver)
    }
}