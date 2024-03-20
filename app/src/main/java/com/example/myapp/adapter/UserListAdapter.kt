package com.example.myapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapp.databinding.RowItemBinding
import com.example.myapp.interfaces.OnItemClickListener
import com.example.myapp.model.UserList
import com.example.myapp.room.UserData
import com.example.myapp.utils.setImageCircle
import com.example.myapp.utils.setText

class UserListAdapter(private val context: Context, private val arrayList: ArrayList<UserData>, private val listener: OnItemClickListener) : RecyclerView.Adapter<UserListAdapter.Holder>() {
    
    inner class Holder(val binding: RowItemBinding) : ViewHolder(binding.root)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(RowItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }
    
    override fun getItemCount(): Int {
        return arrayList.size
    }
    
    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = arrayList[holder.adapterPosition]
        
        with(holder.binding) {
            setText(tvName, "${model.first_name} ${model.last_name} ")
                    setText(tvEmail, model.email.toString())

            setImageCircle(ivProfile, model.avatar)

        }
    }
    
}