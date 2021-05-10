package com.cowin.vaccinateme.data.models

data class Centers(
    val center_id: String,
    val name: String,
    val name_l: String,
    val address: String,
    val address_l: String,
    val state_name: String,
    val state_name_l: String,
    val district_name: String,
    val district_name_l: String,
    val block_name: String,
    val block_name_l: String,
    val pincode: String,
    val lat: String,
    val long: String,
    val from: String,
    val to: String,
    val fee_type: String,
    val vaccince_fees: ArrayList<VaccineFee>,
    val sessions: ArrayList<Session>,
)

/*
Sample response from calanderByPin

"centers": [
    {
      "center_id": 1234,
      "name": "District General Hostpital",
      "name_l": "",
      "address": "45 M G Road",
      "address_l": "",
      "state_name": "Maharashtra",
      "state_name_l": "",
      "district_name": "Satara",
      "district_name_l": "",
      "block_name": "Jaoli",
      "block_name_l": "",
      "pincode": "413608",
      "lat": 28.7,
      "long": 77.1,
      "from": "09:00:00",
      "to": "18:00:00",
      "fee_type": "Free",
      "vaccine_fees": [
        {
          "vaccine": "COVISHIELD",
          "fee": "250"
        }
      ],
      "sessions": [
        {
          "session_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "date": "31-05-2021",
          "available_capacity": 50,
          "min_age_limit": 18,
          "vaccine": "COVISHIELD",
          "slots": [
            "FORENOON",
            "AFTERNOON"
          ]
        }
      ]
    }
  ]

*/