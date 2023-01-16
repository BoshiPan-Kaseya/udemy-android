package ep.udemy.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import ep.udemy.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbFinish)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.tbFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener{
            val intent = Intent(this@FinishActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val historyDao = (application as WorkoutApp).db.historyDao()
        addDataToDatabase(historyDao)
    }

    private fun addDataToDatabase(historyDao: IHistoryDao) {

        val c = Calendar.getInstance()
        val dataTime = c.time
        Log.e("Date: ", "" + dataTime)

        val sdf = SimpleDateFormat("dd MM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dataTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date.toString()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}