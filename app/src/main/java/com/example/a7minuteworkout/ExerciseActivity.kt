package com.example.a7minuteworkout

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding
import com.example.a7minuteworkout.databinding.CustomdialogforbackbuttonBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding?=null
    private var timerDuration:Long=10000
    private var timeProgress:Long=0
    private var restTimer:CountDownTimer?=null
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress:Long=0
    private var myAdapter:ExerciseAdapter?=null
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExerciseNum=-1
    private var tts:TextToSpeech?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar!=null)supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        tts= TextToSpeech(this,this)
        exerciseList=Constants.getExercises()
        restView()
        setUpAdapter()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }
    private fun customDialogForBackButton(){
        val customDialog=Dialog(this)
        val dialogBinding=CustomdialogforbackbuttonBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setUpAdapter(){
        myAdapter=ExerciseAdapter(exerciseList!!)
        binding?.rv?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding?.rv?.adapter=myAdapter
    }

    private fun restView(){
        binding?.tvExerciseName?.visibility=View.INVISIBLE
        binding?.flProgressBar?.visibility= View.VISIBLE
        binding?.flProgressBarExercise?.visibility=View.INVISIBLE
        binding?.tvGetReady?.visibility=View.VISIBLE
        binding?.ivExercise?.visibility=View.INVISIBLE
        binding?.tvUpcomingExName?.visibility=View.VISIBLE
        binding?.tvUpcomingExName?.visibility=View.VISIBLE
        binding?.tvUpcomingExName?.text=exerciseList!![currentExerciseNum+1].name
        if(restTimer!=null){
            restTimer?.cancel()
            timeProgress=0
        }
        speakOut("Resting time")
        setUpRestTimer()
    }
    private fun exerciseView(){
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        binding?.tvExerciseName?.visibility=View.VISIBLE
        binding?.flProgressBar?.visibility= View.INVISIBLE
        binding?.flProgressBarExercise?.visibility=View.VISIBLE
        binding?.tvGetReady?.visibility=View.INVISIBLE
        binding?.ivExercise?.visibility=View.VISIBLE
        binding?.tvUpcomingExName?.visibility=View.INVISIBLE
        binding?.tvUpcomingExName?.visibility=View.INVISIBLE
        binding?.tvExerciseName?.text=exerciseList!![currentExerciseNum].name
        binding?.ivExercise?.setImageResource(exerciseList!![currentExerciseNum].image)
        speakOut(exerciseList!![currentExerciseNum].name)
        setUpExerciseTimer()
    }

    private fun setUpRestTimer(){
        restTimer=object:CountDownTimer(3000-timeProgress,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeProgress=3000-millisUntilFinished
                binding?.tvTimer?.text=(millisUntilFinished/1000).toString()
                binding?.progressBar?.progress=(millisUntilFinished/1000).toInt()
            }

            override fun onFinish() {
                currentExerciseNum++
                exerciseList!![currentExerciseNum].isSelected=true
                myAdapter!!.notifyDataSetChanged()
                exerciseView()
            }

        }.start()
    }
    private fun setUpExerciseTimer(){
        exerciseTimer=object:CountDownTimer(3000-exerciseProgress,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress=3000-millisUntilFinished
                binding?.tvTimerExercise?.text=(millisUntilFinished/1000).toString()
                binding?.progressBarExercise?.progress=(millisUntilFinished/1000).toInt()
            }

            override fun onFinish() {
                exerciseList!![currentExerciseNum].isCompleted=true;
                exerciseList!![currentExerciseNum].isSelected=false;
                myAdapter!!.notifyDataSetChanged()
                if(currentExerciseNum!=exerciseList!!.size-1)restView()
                else Toast.makeText(this@ExerciseActivity, "congrats", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }
    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }
    override fun onInit(status: Int) {
        if(status!=TextToSpeech.ERROR){
            tts?.setLanguage(Locale.ENGLISH)
        }
        else{
            Toast.makeText(this@ExerciseActivity, "Error with text to speach", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()

        if(restTimer!=null){
            restTimer?.cancel()
            timeProgress=0
        }
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
            tts=null
        }
        binding=null
    }


}