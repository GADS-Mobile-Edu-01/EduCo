package com.android.educo.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Catalogue(
    var title : String = "",
    var description : String = "",
    var downloadLink : String = "",
    var type : String = "",
    var id : String = "",
    @ServerTimestamp
    var timeCreated : Date? = null
)