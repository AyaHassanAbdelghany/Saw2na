package com.example.mcommerceadminapp.pojo.coupon.price_rule

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PriceRules(

    @SerializedName("id")
    var id: String? = null ,
    @SerializedName("value_type")
var valueType: String? = null,
    @SerializedName("value")
var value: String? = null,
    @SerializedName("customer_selection")
var customerSelection: String? = null,
    @SerializedName("target_type")
var targetType: String? = null,
    @SerializedName("target_selection")
var targetSelection: String? = null,
    @SerializedName("allocation_method")
var allocationMethod: String? = null,
    @SerializedName("allocation_limit")
var allocationLimit: String? = null,
    @SerializedName("once_per_customer")
var oncePerCustomer: Boolean? = null,
    @SerializedName("usage_limit")
var usageLimit: String? = null,
    @SerializedName("starts_at")
var startsAt: String? = null,
    @SerializedName("ends_at")
var endsAt: String? = null,
    @SerializedName("created_at")
var createdAt: String? = null,
    @SerializedName("updated_at")
var updatedAt: String? = null,
    @SerializedName("entitled_product_ids")
var entitledProductIds: ArrayList<String> = arrayListOf(),
    @SerializedName("entitled_variant_ids")
var entitledVariantIds: ArrayList<String>  = arrayListOf(),
    @SerializedName("entitled_collection_ids")
var entitledCollectionIds: ArrayList<String>  = arrayListOf(),
    @SerializedName("entitled_country_ids")
var entitledCountryIds: ArrayList<String>  = arrayListOf(),
    @SerializedName("prerequisite_product_ids")
var prerequisiteProductIds: ArrayList<String>  = arrayListOf(),
    @SerializedName("prerequisite_variant_ids")
var prerequisiteVariantIds: ArrayList<String>  = arrayListOf(),
    @SerializedName("prerequisite_collection_ids")
var prerequisiteCollectionIds: ArrayList<String>  = arrayListOf(),
    @SerializedName("prerequisite_saved_search_ids")
var prerequisiteSavedSearchIds: ArrayList<String>  = arrayListOf(),
    @SerializedName("prerequisite_customer_ids")
var prerequisiteCustomerIds: ArrayList<String>  = arrayListOf(),
    @SerializedName("prerequisite_subtotal_range")
var prerequisiteSubtotalRange: String? = null,
    @SerializedName("prerequisite_quantity_range")
var prerequisiteQuantityRange: String? = null,
    @SerializedName("prerequisite_shipping_price_range")
var prerequisiteShippingPriceRange: String? = null,
    @SerializedName("prerequisite_to_entitlement_quantity_ratio")
var prerequisiteToEntitlementQuantityRatio: PrerequisiteToEntitlementQuantityRatio? = null,
    @SerializedName("prerequisite_to_entitlement_purchase")
var prerequisiteToEntitlementPurchase: PrerequisiteToEntitlementPurchase? = null,
    @SerializedName("title")
var title: String? = null,
    @SerializedName("admin_graphql_api_id")
var adminGraphqlApiId: String? = null

)
