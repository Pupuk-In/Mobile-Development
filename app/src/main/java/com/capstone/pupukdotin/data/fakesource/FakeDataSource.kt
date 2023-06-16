package com.capstone.pupukdotin.data.fakesource

object FakeDataSource {
    fun getFakeDetailProduct(): FakeDetailProduct {
        return FakeDetailProduct(
            "KOKA NPK - Pupuk Khusus Kopi dan Kakao",
            "https://saraswantifertilizer.com/wp-content/uploads/2021/03/Front-Banner-Merk-koka.png",
            18000,
            "4.5",
            18,
            "Pupuk ini baik untuk kopi dan kakao. Berat bersih 50 Kg",
            listOf("Padi", "Kacang", "Jagung", "Kentang"),
            listOf("Bunga", "Akar"),
            FakeStore(
                "VigorinStore",
                "4.5",
                "Kec. Cilandak, Kota Jakarta Selatan",
                "https://saraswantifertilizer.com/wp-content/uploads/2021/03/Front-Banner-Merk-koka.png"
            )
        )
    }
}