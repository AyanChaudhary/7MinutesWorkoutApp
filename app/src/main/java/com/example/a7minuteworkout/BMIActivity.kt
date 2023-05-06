package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkout.databinding.ActivityBmiactivityBinding
import org.w3c.dom.Text
import java.math.BigDecimal
import java.math.RoundingMode


class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiactivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.bmiToolbar)
        if(supportActionBar!=null)supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.bmiToolbar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.rgUnits?.setOnCheckedChangeListener{ _, checkedId:Int->
            if(checkedId==R.id.rb_metricUnits){
                setUpMetricView()
            }
            else{
                setUpUsMetricView()
            }
        }

        binding?.btnCalculate?.setOnClickListener {
            var height:Float?=null
            var weight:Float?=null
            if(Validator()){
                if(binding?.rbMetricUnits?.isChecked==true) {
                    height = binding?.edHeightMetric?.text.toString().toFloat() / 100
                    weight=binding?.edWeightMetric?.text.toString().toFloat()
                }
                else {
                    val heightinFeet=binding?.edUsheightMetricFeet?.text.toString().toFloat()*12
                    height=(heightinFeet+binding?.edUsheightMetricInch?.text.toString().toFloat())
                    weight=(binding?.edWeightMetric?.text.toString().toFloat())
                }
                var bmi:Float=weight/(height*height)
                if(binding?.rbUsUnits?.isChecked==true)bmi*=703
                contentDisplay(bmi)
            }
            else{
                Toast.makeText(this, "Please enter data in all of the fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun setUpMetricView(){
        binding?.llUsViewHeight?.visibility=View.GONE
        binding?.tilHeighttMetric?.visibility=View.VISIBLE
        binding?.edHeightMetric?.text!!.clear()
        binding?.edWeightMetric?.text!!.clear()
        binding?.tilWeightMetric?.setHint("Weight (in kgs)")
        binding?.llBmiBody?.visibility=View.INVISIBLE
    }
    private fun setUpUsMetricView(){
        binding?.llUsViewHeight?.visibility=View.VISIBLE
        binding?.tilHeighttMetric?.visibility=View.GONE
        binding?.edWeightMetric?.text!!.clear()
        binding?.edUsheightMetricFeet?.text!!.clear()
        binding?.tilWeightMetric?.setHint("Weight (in pounds)")
        binding?.edUsheightMetricInch?.text!!.clear()
        binding?.llBmiBody?.visibility=View.INVISIBLE
    }
    private fun contentDisplay(bmi:Float){
        var bmiType:String?=null
        var bmiDesc:String?=null
        if(bmi.compareTo(15f) <=0){
            bmiType=" Very severely underweight "
            bmiDesc="You need to eat a lot"
        }
        else if(bmi.compareTo(15f) >0 && bmi.compareTo(16f) <=0){
            bmiType=" Severely underweight "
            bmiDesc="You need to eat a lot"
        }
        else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <=0){
            bmiType=" underweight "
            bmiDesc=" Eat more"
        }
        else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <=0){
            bmiType=" Normal "
            bmiDesc=" Congratulations!! You are perfect. Enjoy"
        }
        else if(bmi.compareTo(25f) > 0){
            bmiType=" overweight "
            bmiDesc=" You need to do workout"
        }
        binding?.llBmiBody?.visibility= View.VISIBLE
        binding?.bmiValue?.text=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.bmiType?.text=bmiType
        binding?.bmiDescription?.text=bmiDesc
    }

    private fun Validator():Boolean{
        var validate=true;
        if(binding?.edWeightMetric?.text.toString().isEmpty())validate=false
        if(binding?.rbMetricUnits?.isChecked==true){
            if(binding?.edHeightMetric?.text.toString().isEmpty())validate=false
        }
        else{
            if(binding?.edUsheightMetricFeet?.text.toString().isEmpty())validate=false
            if(binding?.edUsheightMetricInch?.text.toString().isEmpty())validate=false
        }
        return validate
    }
}