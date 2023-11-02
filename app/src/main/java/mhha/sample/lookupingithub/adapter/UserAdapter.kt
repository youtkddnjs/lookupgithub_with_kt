package mhha.sample.lookupingithub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mhha.sample.lookupingithub.databinding.ItemUserBinding
import mhha.sample.lookupingithub.model.User

class UserAdapter(val onclick: (User) -> Unit ): ListAdapter<User , UserAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val viewBinding: ItemUserBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(item:User){
            viewBinding.usernameTextView.text = item.userame
            viewBinding.root.setOnClickListener{
                onclick(item)
            }
        }//fun bind()
    }

    //스크롤 할 때 또는 처음 만들어 졌을 때 새로 만드는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)//ItemUserBinding.inflate
        )//return ViewHolder
    }

    // currentList를 통해 받아 올 수 있음.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        } //val diffUtil = object : DiffUtil.ItemCallback<User>()
    } //companion object
}