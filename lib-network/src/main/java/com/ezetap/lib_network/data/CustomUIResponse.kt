package com.ezetap.lib_network.data

import android.os.Parcelable
import com.ezetap.lib_network.constants.UIType
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CustomUIResponse(
    @SerializedName("logo-url")
    val logoUrl: String? = null,
    @SerializedName("heading-text")
    val headingText: String? = null,
    @SerializedName("uidata")
    val views: List<CustomViewData>? = null
)

@Parcelize
data class CustomViewData(
    @SerializedName("uitype")
    @UIType val uiType: String? = null,
    val key: String? = null,
    val hint: String? = null,
    val value: String? = null
) : Parcelable

