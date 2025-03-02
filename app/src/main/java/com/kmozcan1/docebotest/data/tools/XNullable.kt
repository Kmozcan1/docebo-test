package com.kmozcan1.docebotest.data.tools

import com.squareup.moshi.JsonQualifier

/**
 * Marks an optional variable that will be forcibly serialized as "null" when being transformed
 * to json. (By default, an optional variable is omitted from the output json string,
 * for optimisation purposes.)
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class XNullable
