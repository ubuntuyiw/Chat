package com.ubuntuyouiwe.chat.data.util

import com.google.firebase.firestore.Query

data class OrderBy(val field: String, val direction: Query.Direction)
data class WhereEqualTo(val field: String, val value: String?)

