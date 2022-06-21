package draft_orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class TaxLines(

    @SerializedName("rate") var rate: Double? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("price") var price: String? = null

) : Serializable