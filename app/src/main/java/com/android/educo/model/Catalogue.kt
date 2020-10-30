package com.android.educo.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Catalogue(
    var id: String = "",
    var type: String = "",
    var name: String = "",
    var size: String = "",
    var duration: String = "",
    var title: String = "",
    var description: String = "",
    var downloadLink: String = "",
    @ServerTimestamp
    var timeCreated: Date? = null
)