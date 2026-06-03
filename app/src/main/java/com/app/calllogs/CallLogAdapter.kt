package com.app.calllogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.calllogs.database.CallLogEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CallLogAdapter(
    private val onEditClick: (CallLogEntity) -> Unit
) : ListAdapter<CallLogEntity, CallLogAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<CallLogEntity>() {
        override fun areItemsTheSame(oldItem: CallLogEntity, newItem: CallLogEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CallLogEntity, newItem: CallLogEntity) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_call_log, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), onEditClick)
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        private val tvType: TextView = itemView.findViewById(R.id.tvType)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
//        private val tvNote: TextView = itemView.findViewById(R.id.tvNote)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)

        private val fmt = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())

        fun bind(item: CallLogEntity, onEdit: (CallLogEntity) -> Unit) {
            tvNumber.text = item.name+" "+ item.number
            tvType.text = item.type
            tvTime.text = fmt.format(Date(item.timestamp))

//            val note = item.note?.trim().orEmpty()
//            if (note.isNotEmpty()) {
//                tvNote.visibility = View.VISIBLE
//                tvNote.text = "Note: $note"
//            } else {
//                tvNote.visibility = View.GONE
//            }

            btnEdit.setOnClickListener { onEdit(item) }
            itemView.setOnClickListener { onEdit(item) } // tap row to edit too
        }
    }
}
