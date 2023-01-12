package ep.udemy.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ep.udemy.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbBMI)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"

        binding?.tbBMI?.setNavigationOnClickListener {
            // will finish() current activity
            onBackPressed()
        }

        binding?.btnCalculateUnit?.setOnClickListener {
            calculateUnits()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            when (checkedId) {
                R.id.btnMetric -> {
                    makeVisibleMetricUnitsView()
                }
                R.id.btnUsUnit -> {
                    makeVisibleUSUnitsView()
                }
                else -> {
                    makeVisibleMetricUnitsView()
                }
            }
        }
    }

    private fun calculateUnits() {
        when(currentVisibleView) {
            METRIC_UNITS_VIEW -> {
                if (validateMetricUnits()) {
                    val height: Float = binding?.edMetricUnitHeight?.text.toString().toFloat() / 100
                    val weight: Float = binding?.edMetricUnitWeight?.text.toString().toFloat()
                    val bmiResult = calculateBMI(height, weight)
                    displayBMIResult(bmiResult)
                } else {
                    Toast.makeText(this@BMIActivity, "Please Enter Value", Toast.LENGTH_LONG).show()
                }
            }
            US_UNITS_VIEW -> {
                if (validateUSUnits()) {
                    val weight: Float = binding?.edUSUnitWeight?.text.toString().toFloat()
                    val heightInch: Float = binding?.edUSUnitHeightFeet?.text.toString().toFloat() * 12 + binding?.edUSUnitHeightInch?.text.toString().toFloat()
                    val bmiResult = calculateBMI(heightInch, weight) * 703
                    displayBMIResult(bmiResult)
                } else {
                    Toast.makeText(this@BMIActivity, "Please Enter Value", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUSUnitWeight?.visibility = View.INVISIBLE
        binding?.tilUSUnitHeightFeet?.visibility = View.INVISIBLE
        binding?.tilUSUnitHeightInch?.visibility = View.INVISIBLE

        binding?.edMetricUnitHeight?.text!!.clear()
        binding?.edMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilUSUnitWeight?.visibility = View.VISIBLE
        binding?.tilUSUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilUSUnitHeightInch?.visibility = View.VISIBLE

        binding?.edUSUnitWeight?.text!!.clear()
        binding?.edUSUnitHeightFeet?.text!!.clear()
        binding?.edUSUnitHeightInch?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(BMIResult: Float) {

        val bmiLabel: String
        val bmiDesc: String

        when  {
            BMIResult.compareTo(15f) <= 0 -> {
                bmiLabel = "Very severely underweight"
                bmiDesc = "Oops! You really need to take better care of yourself! Eat more"
            }
            BMIResult.compareTo(16f) <= 0 -> {
                bmiLabel = "severely underweight"
                bmiDesc = "Oops! You really need to take better care of yourself! Eat more"
            }
            BMIResult.compareTo(18.5f) <= 0 -> {
                bmiLabel = "Normal"
                bmiDesc = "Congratulations! You are in a good shape"
            }
            BMIResult.compareTo(25f) <=0 -> {
                bmiLabel = "Overweight"
                bmiDesc = "Oops! You really need to take care of yourself! Workout more"
            }
            BMIResult.compareTo(30f) <= 0 -> {
                bmiLabel = "severely overweight"
                bmiDesc = "Oops! You really need to take care of yourself! Workout more"
            }
            BMIResult.compareTo(35f) <= 0 -> {
                bmiLabel = "Very severely Overweight"
                bmiDesc = "Oops! You really need to take care of yourself! Workout more"
            }
            else -> {
                bmiLabel = "Warning Level Overweight"
                bmiDesc = "Oops! You really need to take care of yourself! Workout more"
            }
        }

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = BigDecimal(BMIResult.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDesc
    }

    private fun calculateBMI(height: Float, weight: Float): Float {
        return weight / (height * height)
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.edMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.edMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true
        when {
            binding?.edUSUnitWeight?.text.toString().isEmpty() -> isValid = false
            binding?.edUSUnitHeightFeet?.text.toString().isEmpty() -> isValid = false
            binding?.edUSUnitHeightInch?.text.toString().isEmpty() -> isValid = false
        }
        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}