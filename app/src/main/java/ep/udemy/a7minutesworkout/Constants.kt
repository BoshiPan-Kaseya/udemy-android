package ep.udemy.a7minutesworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        exerciseList.add(
            ExerciseModel(1, "Push Up", R.drawable.ic_push_up, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(2, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(3, "Jumping Jacks", R.drawable.ic_jumping_jacks, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(4, "High Knees Running in Place", R.drawable.ic_high_knees_running_in_place, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(5, "Lunge", R.drawable.ic_lunge, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(6, "Plank", R.drawable.ic_plank, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(7, "Side Plank", R.drawable.ic_side_plank, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(8, "Push Up and Rotation", R.drawable.ic_push_up_and_rotation, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(9, "Squat", R.drawable.ic_squat, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(10, "Step Up onto Chair", R.drawable.ic_step_up_onto_chair, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(11, "Triceps Dip on Chair", R.drawable.ic_triceps_dip_on_chair, false,
                isSelected = false
            )
        )

        exerciseList.add(
            ExerciseModel(12, "Wall Sit", R.drawable.ic_wall_sit, false,
                isSelected = false
            )
        )

        return exerciseList
    }
}