package my.dahr.cardiocam.ui.screen.history

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import my.dahr.cardiocam.data.model.MeasurementRecord
import my.dahr.cardiocam.databinding.ItemHistoryBinding

class HistoryRvAdapter : RecyclerView.Adapter<HistoryRvAdapter.ViewHolder>() {

    private val items = mutableListOf<MeasurementRecord>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateList(newItems: List<MeasurementRecord>) {
        val diffResult = DiffUtil.calculateDiff(MeasurementRecordDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MeasurementRecord) {
            // TODO: Don't forget bind item's data to RecyclerView's items
        }
    }

}

class MeasurementRecordDiffCallback(
    private val oldList: List<MeasurementRecord>,
    private val newList: List<MeasurementRecord>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].timestamp == newList[newItemPosition].timestamp

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}