package draft_orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class DraftOrders(

    @SerializedName("draft_orders") var draftOrders: ArrayList<DraftOrder> = arrayListOf()

): Serializable