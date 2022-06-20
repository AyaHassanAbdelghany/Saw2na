package orders

import com.google.gson.annotations.SerializedName


data class LineItems (

  @SerializedName("id"                           ) var id                         : Long?              = null,
  @SerializedName("admin_graphql_api_id"         ) var adminGraphqlApiId          : String?           = null,
  @SerializedName("fulfillable_quantity"         ) var fulfillableQuantity        : Int?              = null,
  @SerializedName("fulfillment_service"          ) var fulfillmentService         : String?           = null,
  @SerializedName("fulfillment_status"           ) var fulfillmentStatus          : String?           = null,
  @SerializedName("gift_card"                    ) var giftCard                   : Boolean?          = null,
  @SerializedName("grams"                        ) var grams                      : Int?              = null,
  @SerializedName("name"                         ) var name                       : String?           = null,
  @SerializedName("price"                        ) var price                      : String?           = null,
  @SerializedName("price_set"                    ) var priceSet                   : PriceSet?         = PriceSet(),
  @SerializedName("product_exists"               ) var productExists              : Boolean?          = null,
  @SerializedName("product_id"                   ) var productId                  : Long?              = null,
  @SerializedName("properties"                   ) var properties                 : ArrayList<String> = arrayListOf(),
  @SerializedName("quantity"                     ) var quantity                   : Int?              = null,
  @SerializedName("requires_shipping"            ) var requiresShipping           : Boolean?          = null,
  @SerializedName("sku"                          ) var sku                        : String?           = null,
  @SerializedName("taxable"                      ) var taxable                    : Boolean?          = null,
  @SerializedName("title"                        ) var title                      : String?           = null,
  @SerializedName("total_discount"               ) var totalDiscount              : String?           = null,
  @SerializedName("total_discount_set"           ) var totalDiscountSet           : TotalDiscountSet? = TotalDiscountSet(),
  @SerializedName("variant_id"                   ) var variantId                  : Long?              = null,
  @SerializedName("variant_inventory_management" ) var variantInventoryManagement : String?           = null,
  @SerializedName("variant_title"                ) var variantTitle               : String?           = null,
  @SerializedName("vendor"                       ) var vendor                     : String?           = null,
  @SerializedName("tax_lines"                    ) var taxLines                   : ArrayList<String> = arrayListOf(),
  @SerializedName("duties"                       ) var duties                     : ArrayList<String> = arrayListOf(),
  @SerializedName("discount_allocations"         ) var discountAllocations        : ArrayList<String> = arrayListOf()

)