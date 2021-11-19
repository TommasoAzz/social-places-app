package it.unibo.socialplaces.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity.*
import it.unibo.socialplaces.R

class RecognizedActivity : IntentService(TAG) {
    companion object {
        private val TAG = RecognizedActivity::class.qualifiedName
    }

    /** Maps Android activities of HAR to SocialPlaces activities. */
    private var acceptedActivities = mapOf(
        WALKING to "walk",
        STILL to "still",
        IN_VEHICLE to "car"
    )

    override fun onHandleIntent(receivedIntent: Intent?) {
        Log.v(TAG, "onHandleIntent")

        if(receivedIntent == null) {
            return
        }

        val recognizedActivities = ActivityRecognitionResult.extractResult(receivedIntent) ?: return

        val detectedActivity = recognizedActivities.mostProbableActivity
        Log.d(TAG,"Detected human activity: ${detectedActivity.type} (confidence = ${detectedActivity.confidence}).")

        val sendHaIntent = Intent(getString(R.string.recognized_ha)).apply {
            putExtra("type", acceptedActivities[detectedActivity.type])
            putExtra("confidence", detectedActivity.confidence)
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(sendHaIntent)
    }
}
