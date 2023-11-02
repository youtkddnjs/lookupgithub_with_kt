package mhha.sample.lookupingithub.model

import com.google.gson.annotations.SerializedName

data class Repo(

    @SerializedName("id") //매핑용도 val id 대신 다른 단어를 써도됨.
    val id: Long,

    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("language")
    val language: String?,
    @SerializedName("stargazers_count")
    val starCount: Int,
    @SerializedName("forks_count")
    val forkCount: Int,
    @SerializedName("html_url")
    val htmlUrl: String,

)
