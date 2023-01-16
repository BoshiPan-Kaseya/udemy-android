package ep.udemy.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ep.udemy.a7minutesworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Set upe tool bar on history page
        setSupportActionBar(binding?.tbHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "History"
        binding?.tbHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}