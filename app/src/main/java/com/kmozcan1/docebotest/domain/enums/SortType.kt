package com.kmozcan1.docebotest.domain.enums

/**
 * Created by Kadir Mert Özcan on 05-Jan-21.
 */
enum class SortType (val stringValue: String) {
    ALPHABETIC("full_name"),
    CREATE_DATE("created"),
    PUSH_DATE("pushed"),
    LAST_UPDATED("updated")
}