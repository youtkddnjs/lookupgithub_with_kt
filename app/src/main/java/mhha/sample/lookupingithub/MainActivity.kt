package mhha.sample.lookupingithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import mhha.sample.lookupingithub.adapter.UserAdapter
import mhha.sample.lookupingithub.databinding.ActivityMainBinding
import mhha.sample.lookupingithub.model.Repo
import mhha.sample.lookupingithub.model.UserDto
import mhha.sample.lookupingithub.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter:UserAdapter
    private var searchFor: String = ""

//    //레트로핏 설정
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.github.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 리사이클러뷰에 들어갈 어댑터
        userAdapter = UserAdapter{
            val intent = Intent(this@MainActivity, RepoActivity::class.java)
            intent.putExtra("username", it.userame)
            startActivity(intent)
        }




        // 리사이클러 뷰 생성
        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        val runnable = Runnable {
            searchUser(searchFor)
        }

        //editText에 글자가 바뀌는 것을 확인하는 리스너
        binding.searchEditText.addTextChangedListener {
            searchFor = it.toString()
            //핸들러을 이용한 debounce 유사하게 구현한 코드
            handler.removeCallbacks(runnable)
            handler.postDelayed(
                runnable,
                300,
            )
        }



    } //override fun onCreate(savedInstanceState: Bundle?)

    private fun searchUser(query:String){
        val githubService = ApiClient.retrofit.create(GithubService::class.java)
        githubService.searchUsers(query).enqueue(object : Callback<UserDto>{
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                Log.e("LGH_info", "Search User :${ response.body().toString() }")
                //데이터를 어댑터에 넣는 코드
                userAdapter.submitList(response.body()?.items)

            } //override fun onResponse(call: Call<UserDto>, response: Response<UserDto>)

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Toast.makeText(this@MainActivity, "에러",Toast.LENGTH_SHORT).show()
                t.printStackTrace() // 에러 표시하는 코드
            } //override fun onFailure(call: Call<UserDto>, t: Throwable)

        }) //githubService.searchUsers("youtkddnjs").enqueue(object : Callback<UserDto>
    }//private fun searchUser(query:String)

} //class MainActivity : AppCompatActivity()