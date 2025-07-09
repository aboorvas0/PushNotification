package com.example.pushnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApi.Settings


class AirPlaneModeReceiver :BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      if(intent?.action==Intent.ACTION_AIRPLANE_MODE_CHANGED){
          val isAirPlaneOn=android.provider.Settings.Global.getInt(
              context?.contentResolver,
              android.provider.Settings.Global.AIRPLANE_MODE_ON
          )!=0
          Log.e(">>>>airplane","$isAirPlaneOn")
          var message = if(isAirPlaneOn){
              "ON"
          } else{
              "OFF"
          }
          Toast.makeText(context,"Airplane is $message",Toast.LENGTH_SHORT).show()

      }
        else if(intent?.action==Constant.packageName){
          val data = intent.getStringExtra("custom_data")
          Toast.makeText(context, "Received: $data", Toast.LENGTH_SHORT).show()
        }
    }
}