package com.example.a7minuteworkout

import android.util.Log.d

object Constants {
    fun getExercises():ArrayList<ExerciseModel>{
        val exerciseList=ArrayList<ExerciseModel>()

        val exe1=ExerciseModel(1,"PUSHUPS", R.drawable.img,false,false)
        exerciseList.add(exe1)
        val exe2=ExerciseModel(2,"PULLUPS",R.drawable.img,false,false)
        exerciseList.add(exe2)
        val exe3=ExerciseModel(3,"SITUPS",R.drawable.img,false,false)
        exerciseList.add(exe3)
        val exe4=ExerciseModel(4,"SKIPPING",R.drawable.img,false,false)
        exerciseList.add(exe4)
        val exe5=ExerciseModel(5,"STRETCHING",R.drawable.img,false,false)
        exerciseList.add(exe5)
        val exe6=ExerciseModel(6,"SUPERMAN",R.drawable.img,false,false)
        exerciseList.add(exe6)

        return exerciseList
    }
}