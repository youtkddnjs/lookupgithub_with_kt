package mhha.sample.lookupingithub.network

import mhha.sample.lookupingithub.model.Repo
import mhha.sample.lookupingithub.model.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @Headers("Authorization: Bearer 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")
    @GET("users/{username}/repos")
    fun listRepos(@Path("username") username: String, @Query("page") page: Int): Call<List<Repo>>

    @Headers("Authorization: Bearer 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")
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<UserDto>

}//interface GithubService