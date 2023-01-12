package ep.udemy.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ep.udemy.a7minutesworkout.databinding.ActivityDialogCustomBackConfirmationBinding
import ep.udemy.a7minutesworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var media: MediaPlayer? = null

    private var exerciseStatusAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.tbExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts = TextToSpeech(this, this)
        exerciseList = Constants.defaultExerciseList()

        setupRestView()
        setUpExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        // the name of binding
        val dialogBinding = ActivityDialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }

        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    /**
     * func: set up recycler view in in the activity
     * desc: if we want to use recyclerview, we need to offer two things to it
     *       the first one would be layoutManager, which tells rv which where, how to present the data,
     *       the second one would be the adapter, which is used to tell rv which layout to use for presenting the data
     *       adapter can be treated as view object, we can binding the data (pass into adapter as parameter)
     * */
    private fun setUpExerciseStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseStatusAdapter = ExerciseStatusAdapter(exerciseList!!)

        binding?.rvExerciseStatus?.adapter = exerciseStatusAdapter
    }

    /**
     * fun: setupRestView
     * desc: this function is used to set up the rest views. In the layout, there are 2 set
     *       of the view for exercise and rest respectively. In this function, the code is to set
     *       view that used for rest section visible and update the currentPosition
     * */
    private fun setupRestView() {

        try {
            val soundURL = Uri.parse("android.resource://ep.udemy.a7minutesworkout/" + R.raw.press_start)
            media = MediaPlayer.create(applicationContext, soundURL)
            media?.isLooping = false
            media?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExercise?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivExerciseImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        currentExercisePosition++

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        setRestProgressBar()
    }

    /**
     * fun: setupExerciseView
     * desc: this function is used to set up the rest views. In the layout, there are 2 set
     *       of the view for exercise and rest respectively. In this function, the code is to set
     *       view that used for exercise section visible and update the currentPosition
     * */
    private fun setupExerciseView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExercise?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivExerciseImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())
        binding?.ivExerciseImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExercise?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    /**
     * fun: setRestProgressBar
     * desc: CountDownTimer used to track the rest time during the exercise
     *       onTick: the restProgress++ for every countDownInterval which is set 1000,
     *               and the text on the views will reduce as timer
     *       onFinish: when the CountDownTimer finished the given millisInFuture, the code
     *                 would be executed
     *       .start() will start the timer directly when the restTimer is called, when it finish
     *                the the image will be set to ivExerciseImage, and exercise view will be called
     * */
    private fun setRestProgressBar() {
        binding?.pbProgressBar?.progress = restProgress
        restTimer = object: CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.pbProgressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsSelected(true)
                // when change the data that binding to recycler view, need to call this function to update recycler view
                exerciseStatusAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }

        }.start()
    }

    /**
     * fun: setExerciseProgressBar
     * desc: CountDownTimer used to track the exercise time during the exercise
     *       same as the setRestProgressBar, but only longer timer and when the timer is finished
     *       it will call depends on the currentPosition
     *
     * */
    private fun setExerciseProgressBar() {
        binding?.pbProgressBarExercise?.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.pbProgressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseStatusAdapter!!.notifyDataSetChanged()

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    setupRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts?.stop()
        }

        if (media != null) {
            media!!.stop()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported")
            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }

}