package com.ubuntuyouiwe.chat.data.util

import com.google.firebase.firestore.Query

data class OrderBy(val field: DatabaseFieldNames, val direction: Query.Direction)
data class WhereEqualTo(val field: DatabaseFieldNames, val value: String?)

