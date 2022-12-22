package com.fappslab.resultbuilder.data.model

import com.fappslab.resultbuilder.domain.model.Address
import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("logradouro")
    val street: String?,
    @SerializedName("localidade")
    val city: String?,
    @SerializedName("uf")
    val state: String?,
    @SerializedName("ddd")
    val areaCode: String?,
) {

    fun toAddress(): Address =
        Address(
            street = street.orEmpty(),
            city = city.orEmpty(),
            state = state.orEmpty(),
            areaCode = areaCode.orEmpty()
        )
}
