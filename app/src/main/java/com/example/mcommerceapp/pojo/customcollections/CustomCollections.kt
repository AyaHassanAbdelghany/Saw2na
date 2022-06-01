package com.example.mcommerceapp.pojo.customcollections

import com.google.gson.annotations.SerializedName

data class CustomCollections(

    @SerializedName("id")
    var id: String? = null,
    @SerializedName("handle")
    var handle: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    @SerializedName("body_html")
    var bodyHtml: String? = null,
    @SerializedName("published_at")
    var publishedAt: String? = null,
    @SerializedName("sort_order")
    var sortOrder: String? = null,
    @SerializedName("template_suffix")
    var templateSuffix: String? = null,
    @SerializedName("published_scope")
    var publishedScope: String? = null,
    @SerializedName("admin_graphql_api_id")
    var adminGraphqlApiId: String? = null

)