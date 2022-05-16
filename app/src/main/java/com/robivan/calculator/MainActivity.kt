package com.robivan.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.robivan.calculator.databinding.ActivityMainBinding
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var calculator: CalculatorModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()
    }

    override fun onStart() {
        super.onStart()
        setTheme()
    }

    override fun onSaveInstanceState(instanceState: Bundle) {
        super.onSaveInstanceState(instanceState)
        instanceState.putSerializable(CALC_KEY, calculator)
    }

    override fun onRestoreInstanceState(instanceState: Bundle) {
        super.onRestoreInstanceState(instanceState)
        if (instanceState.containsKey(CALC_KEY)) {
            calculator = instanceState.getSerializable(CALC_KEY) as CalculatorModel

            with(binding) {
                expression.text = calculator.expressionValue
                result.text = calculator.resultValue
            }
        }
    }

    private fun initialization() {
        calculator = CalculatorModel()
        binding.result.text = Util.DEFAULT_MEANING
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }
        val numberButtonClickListener = View.OnClickListener { v: View ->
            calculator.onNumPressed(v.id)
            with(binding) {
                expression.text = calculator.expressionValue
                result.text = calculator.resultValue
            }
        }
        val actionButtonClickListener = View.OnClickListener { v: View ->
            try {
                calculator.onActionPressed(v.id)
                binding.result.text = calculator.resultValue
            } catch (e: NullPointerException) {
                binding.result.text = this.getString(R.string.division_by_zero)
            }
            binding.expression.text = calculator.expressionValue
        }

        for (numberId in Util.NUMBER_IDS) {
            findViewById<View>(numberId).setOnClickListener(numberButtonClickListener)
        }
        for (actionId in Util.ACTION_IDS) {
            findViewById<View>(actionId).setOnClickListener(actionButtonClickListener)
        }
    }

    private fun setTheme() {
        getSharedPreferences(Settings.pREF_NAME, MODE_PRIVATE)
            .getString(Settings.kEY_THEME, null)?.let { themeValue ->
                when (themeValue) {
                    Settings.kEY_DARK_THEME -> AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                    Settings.kEY_LIGHT_THEME -> AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                }
            }
    }

    companion object {
        private val CALC_KEY = MainActivity::class.java.canonicalName?.plus("calc_key")
    }
}