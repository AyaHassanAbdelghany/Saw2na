package draft_orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class NoteAttributes (

  @SerializedName("name"  ) var name  : String? = null,
  @SerializedName("value" ) var value : String? = null

): Serializable