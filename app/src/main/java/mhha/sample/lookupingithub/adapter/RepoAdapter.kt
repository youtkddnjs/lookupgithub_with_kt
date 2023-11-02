package mhha.sample.lookupingithub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mhha.sample.lookupingithub.databinding.ItemRepoBinding
import mhha.sample.lookupingithub.model.Repo

class RepoAdapter(private val onClick: (Repo) -> Unit ) : ListAdapter<Repo, RepoAdapter.ViewHoler>(diffUtil) {

    inner class ViewHoler(private val viewBinding: ItemRepoBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: Repo) {
            viewBinding.repoNameTextView.text = item.name
            viewBinding.descriptionTextView.text = item.description
            viewBinding.starCountTextView.text = item.starCount.toString()
            viewBinding.forkCountTextView.text = item.forkCount.toString()

            viewBinding.root.setOnClickListener {
                onClick(item)
            }

        } //fun bind(item: Repo)
        //inner class ViewHoler(private  val viewBinding: ItemRepoBinding) : RecyclerView.ViewHolder(viewBinding.root)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.ViewHoler {
        return ViewHoler(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,

            ) //ItemRepoBinding.inflate
        ) //return ViewHoler
    } //override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.ViewHoler

    override fun onBindViewHolder(holder: RepoAdapter.ViewHoler, position: Int) {
        holder.bind(currentList[position])
    } //override fun onBindViewHolder(holder: RepoAdapter.ViewHoler, position: Int)

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }

        }//val diffUtil = object : DiffUtil.ItemCallback<Repo>()
    }//companion object

}