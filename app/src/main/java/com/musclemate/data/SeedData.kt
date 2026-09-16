package com.musclemate.data

import com.musclemate.data.local.ExerciseEntity

object SeedData {
    private fun e(id:String,name:String,muscle:String,target:String,secondary:String,equipment:String,difficulty:String)=
        ExerciseEntity(
            id=id,name=name,muscleGroup=muscle,targetMuscle=target,secondaryMuscles=secondary,
            equipment=equipment,difficulty=difficulty,
            animationUrl="https://cdn.example.com/musclemate/animations/"+id+".json",
            videoUrl="https://cdn.example.com/musclemate/videos/"+id+".mp4",
            instructions="Starting Position|Controlled Movement|Breathing|Return position",
            commonMistakes="Using momentum|Poor range of motion|Losing control",
            sets=3,reps="8-12",restTime=60
        )

    val exercises = listOf(
        e("1","Bench Press","Chest","Middle Chest","Triceps, Front Delts","Barbell","Intermediate"),
        e("2","Incline Bench Press","Chest","Upper Chest","Triceps, Front Delts","Barbell","Intermediate"),
        e("3","Dumbbell Bench Press","Chest","Middle Chest","Triceps, Front Delts","Dumbbell","Beginner"),
        e("4","Incline Dumbbell Press","Chest","Upper Chest","Triceps, Front Delts","Dumbbell","Beginner"),
        e("5","Cable Crossover","Chest","Middle Chest","Front Delts","Cable","Beginner"),
        e("6","Push Up","Chest","Middle Chest","Triceps, Front Delts","Bodyweight","Beginner"),
        e("7","Pull Up","Back","Lats","Biceps, Rear Delts","Bodyweight","Advanced"),
        e("8","Lat Pulldown","Back","Lats","Biceps","Machine","Beginner"),
        e("9","Barbell Row","Back","Mid Back","Lats, Biceps","Barbell","Intermediate"),
        e("10","Seated Cable Row","Back","Mid Back","Biceps, Rear Delts","Cable","Beginner"),
        e("11","Single Arm Dumbbell Row","Back","Lats","Biceps","Dumbbell","Beginner"),
        e("12","Back Extension","Back","Lower Back","Glutes, Hamstrings","Bodyweight","Beginner"),
        e("13","Overhead Press","Shoulders","Front Delts","Triceps, Side Delts","Barbell","Intermediate"),
        e("14","Dumbbell Shoulder Press","Shoulders","Front Delts","Triceps, Side Delts","Dumbbell","Beginner"),
        e("15","Lateral Raise","Shoulders","Side Delts","Upper Traps","Dumbbell","Beginner"),
        e("16","Face Pull","Shoulders","Rear Delts","Traps, Rotator Cuff","Cable","Beginner"),
        e("17","Barbell Curl","Biceps","Biceps","Forearms","Barbell","Beginner"),
        e("18","Dumbbell Curl","Biceps","Biceps","Forearms","Dumbbell","Beginner"),
        e("19","Hammer Curl","Biceps","Brachialis","Biceps, Forearms","Dumbbell","Beginner"),
        e("20","Preacher Curl","Biceps","Biceps","Forearms","Machine","Beginner"),
        e("21","Cable Curl","Biceps","Biceps","Forearms","Cable","Beginner"),
        e("22","Cable Pushdown","Triceps","Triceps","Forearms","Cable","Beginner"),
        e("23","Skull Crusher","Triceps","Triceps","Forearms","Barbell","Intermediate"),
        e("24","Close Grip Bench Press","Triceps","Triceps","Chest, Front Delts","Barbell","Intermediate"),
        e("25","Dumbbell Kickback","Triceps","Triceps","Rear Delts","Dumbbell","Beginner"),
        e("26","Back Squat","Legs","Quadriceps","Glutes, Hamstrings","Barbell","Intermediate"),
        e("27","Leg Press","Legs","Quadriceps","Glutes, Hamstrings","Machine","Beginner"),
        e("28","Leg Extension","Legs","Quadriceps","Rectus Femoris","Machine","Beginner"),
        e("29","Romanian Deadlift","Legs","Hamstrings","Glutes, Lower Back","Barbell","Intermediate"),
        e("30","Goblet Squat","Legs","Quadriceps","Glutes, Core","Dumbbell","Beginner")
    )
}