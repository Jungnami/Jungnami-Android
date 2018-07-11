package sopt_jungnami_android.jungnami.data

data class CoinInfoData(
        val coin: Int,
        val exchange: ArrayList<Exchange>
)

data class Exchange (
        val coin: Int,
        val won: Int
)