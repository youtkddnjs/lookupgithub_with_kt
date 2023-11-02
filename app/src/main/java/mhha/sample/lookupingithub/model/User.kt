package mhha.sample.lookupingithub.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val userame: String
)