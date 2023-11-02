package mhha.sample.lookupingithub

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mhha.sample.lookupingithub.adapter.RepoAdapter
import mhha.sample.lookupingithub.databinding.ActivityRepoBinding
import mhha.sample.lookupingithub.model.Repo
import mhha.sample.lookupingithub.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRepoBinding
    private lateinit var repoAdapter: RepoAdapter
    private var page = 0
    private var hasMore = true

//    //레트로핏 설정
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.github.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username") ?: return
        binding.usernameTextView.text = username

        repoAdapter= RepoAdapter{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl))
            startActivity(intent)
        }

        val layoutLayoutManager = LinearLayoutManager(this@RepoActivity)

        binding.repoRecyclerView.apply {
            layoutManager = layoutLayoutManager
            adapter = repoAdapter
        }

        //스크롤될때 리스너
        binding.repoRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val totalCount = layoutLayoutManager.itemCount
                val lastVisiblePosition = layoutLayoutManager.findLastCompletelyVisibleItemPosition()

                if(lastVisiblePosition >= (totalCount-1) && hasMore){
                    page += 1
                    listRepo(username, page)
                }
            } //override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int)

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        }) //binding.repoRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener()

        listRepo(username, 0)

    }//override fun onCreate(savedInstanceState: Bundle?)

    private fun listRepo(username : String, page: Int){
        val githubService = ApiClient.retrofit.create(GithubService::class.java)
        githubService.listRepos(username, page).enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e("LGH_info", "List Repo :${ response.body().toString() }")
                hasMore = response.body()?.count() == 30
                repoAdapter.submitList(repoAdapter.currentList + response.body().orEmpty())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })//githubService.listRepos("youtkddnjs").enqueue(object : Callback<List<Repo>>
    } //private fun listRepo(username : String, page: Int)

}//class RepoActivity: AppCompatActivity()