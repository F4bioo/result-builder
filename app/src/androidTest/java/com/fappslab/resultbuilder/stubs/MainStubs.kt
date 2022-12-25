package com.fappslab.resultbuilder.stubs

import com.fappslab.resultbuilder.domain.model.Address

fun stubAddress(): Address =
    Address(
        street = "Praça da Sé",
        city = "São Paulo",
        state = "SP",
        areaCode = "11",
    )
