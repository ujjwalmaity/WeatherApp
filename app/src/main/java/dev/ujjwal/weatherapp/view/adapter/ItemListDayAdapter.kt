package dev.ujjwal.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.ujjwal.weatherapp.R
import dev.ujjwal.weatherapp.model.day.ListData
import dev.ujjwal.weatherapp.model.unitSymbol
import kotlinx.android.synthetic.main.item_layout_day.view.*

class ItemListDayAdapter : RecyclerView.Adapter<ItemListDayAdapter.ItemListDayVH>() {

    private var items: ArrayList<ListData> = arrayListOf()

    fun updateItemListDay(items: List<ListData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemListDayVH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_layout_day, parent, false)
    )

    override fun onBindViewHolder(holder: ItemListDayVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ItemListDayVH(view: View) : RecyclerView.ViewHolder(view) {
        private var textView: TextView = view.day_tv_details

        fun bind(item: ListData) {
            val weather = item.weather[0].description
            val temp = item.main.temp
            textView.text = "Timestamp: ${item.dt_txt}\nWeather: $weather\nTemp: $temp $unitSymbol"
        }
    }
}
