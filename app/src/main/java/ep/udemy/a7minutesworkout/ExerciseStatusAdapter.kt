package ep.udemy.a7minutesworkout


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ep.udemy.a7minutesworkout.databinding.ItemExerciseStatusBinding

/**
 * class ExerciseStatusAdapter is used to create adapter for specific recycler view
 * @param: data list
 * @desc: inherit RecyclerView.Adapter<ExerciseStatusAdapter.InnerClassViewHolder>
 * */

class ExerciseStatusAdapter(private val exerciseList: ArrayList<ExerciseModel>)
    : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){

    /**
     * inner class is used to bind recycler view item to recycler view
     * */
    inner class ViewHolder (binding: ItemExerciseStatusBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItem
    }

    /**
     * inflate the view holder using the binding
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    /**
     * when the view holder has bound to recycler view, then what you want to do
     * including the text of the view
     * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = exerciseList[position]
        holder.tvItem.text = model.getId().toString()

        when {
            model.getIsSelected() -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_boarder)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }

            model.getIsCompleted() -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_background)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_gray_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    /**
     * need to return how many items to render
     * */
    override fun getItemCount(): Int {
        return exerciseList.size
    }
}