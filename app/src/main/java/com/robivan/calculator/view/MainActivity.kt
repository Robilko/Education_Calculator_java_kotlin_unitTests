package com.robivan.calculator.view

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.robivan.calculator.R
import com.robivan.calculator.databinding.ActivityMainBinding
import com.robivan.calculator.model.CalculatorModel
import com.robivan.calculator.presenter.MainContract
import com.robivan.calculator.presenter.MainPresenter
import com.robivan.calculator.util.ButtonsIds
import com.robivan.calculator.util.setApplicationTheme

class MainActivity : AppCompatActivity(), MainContract.MainView {
    private lateinit var binding: ActivityMainBinding

    private lateinit var mPresenter: MainContract.MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()
    }

    override fun onStart() {
        super.onStart()
        setApplicationTheme(this@MainActivity)
    }

    override fun onSaveInstanceState(instanceState: Bundle) {
        super.onSaveInstanceState(instanceState)
        instanceState.putSerializable(CALC_KEY, mPresenter.onDetach())
    }

    override fun onRestoreInstanceState(instanceState: Bundle) {
        super.onRestoreInstanceState(instanceState)
        if (instanceState.containsKey(CALC_KEY)) {
            val calculator = instanceState.getSerializable(CALC_KEY) as CalculatorModel
            mPresenter.onAttach(calculator, this)
        }
    }

    private fun initialization() {
        mPresenter = MainPresenter()
        mPresenter.onAttach(CalculatorModel(), this)

        onSettingsButtonClickListener()
        onNumberButtonClickListener()
        onActionButtonClickListener()
    }

    private fun onSettingsButtonClickListener() {
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }
    }

    private fun onNumberButtonClickListener() {
        for (numberId in ButtonsIds.NUMBER_IDS) {
            findViewById<View>(numberId).setOnClickListener { v: View ->
                mPresenter.onNumPressed(v.id)
            }
        }
    }

    private fun onActionButtonClickListener() {
        for (actionId in ButtonsIds.ACTION_IDS) {
            findViewById<View>(actionId).setOnClickListener { v: View ->
                mPresenter.onActionPressed(v.id)
            }
        }
    }
    override fun showResult(resultValue: String) {
        binding.result.text = resultValue
    }

    override fun showExpression(expressionValue: String) {
        binding.expression.text = expressionValue
    }

    override fun showError() {
        val toast =
            Toast.makeText(this, this.getString(R.string.division_by_zero), Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    companion object {
        private val CALC_KEY = MainActivity::class.java.canonicalName?.plus("calc_key")
    }
}