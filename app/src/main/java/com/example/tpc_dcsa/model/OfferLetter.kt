package com.example.tpc_dcsa.model

data class OfferLetter(
    val id: String,
    val studentId: String,
    val companyId: String,
    val offerDate: String,
    val packageAmount: Double
)
