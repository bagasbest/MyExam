package com.project.myexam.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel(
    val name: String? = null,
    val description: String? = null,
    val address: String? = null,
    val image: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val tempName: String? = null,
    val schedule: String? = null,
) : Parcelable