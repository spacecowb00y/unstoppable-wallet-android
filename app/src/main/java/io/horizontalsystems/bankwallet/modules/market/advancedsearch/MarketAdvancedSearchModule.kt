package io.horizontalsystems.bankwallet.modules.market.advancedsearch

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.horizontalsystems.bankwallet.R
import io.horizontalsystems.bankwallet.core.App
import io.horizontalsystems.bankwallet.modules.market.MarketModule

object MarketAdvancedSearchModule {
    class Factory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val service = MarketAdvancedSearchService(MarketModule.currencyUSD, App.xRateManager)
            return MarketAdvancedSearchViewModel(service, listOf(service)) as T
        }

    }
}

enum class CoinList(val itemsCount: Int, @StringRes val titleResId: Int) {
    Top100(100, R.string.Market_Filter_Top_100),
    Top250(250, R.string.Market_Filter_Top_250),
    Top500(500, R.string.Market_Filter_Top_500),
    Top1000(1000, R.string.Market_Filter_Top_1000),
    Top2500(2500, R.string.Market_Filter_Top_2500),;
}

enum class Range(@StringRes val titleResId: Int, val values: Pair<Long?, Long?>) {
    Range_0_1M(R.string.Market_Filter_Range_0_1M, Pair(null, 1_000_000)),
    Range_1M_10M(R.string.Market_Filter_Range_1M_10M, Pair(1_000_000, 10_000_000)),
    Range_10M_100M(R.string.Market_Filter_Range_10M_100M, Pair(10_000_000, 100_000_000)),
    Range_100M_1B(R.string.Market_Filter_Range_100M_1B, Pair(100_000_000, 1_000_000_000)),
    Range_1B_10B(R.string.Market_Filter_Range_1B_10B, Pair(1_000_000_000, 10_000_000_000)),
    Range_10B_More(R.string.Market_Filter_Range_10B_More, Pair(10_000_000_000, null)),
}

enum class TimePeriod(@StringRes val titleResId: Int) {
    TimePeriod_1D(R.string.Market_Filter_TimePeriod_1D),
    TimePeriod_1W(R.string.Market_Filter_TimePeriod_1W),
    TimePeriod_1M(R.string.Market_Filter_TimePeriod_1M),
    TimePeriod_3M(R.string.Market_Filter_TimePeriod_3M),
    TimePeriod_6M(R.string.Market_Filter_TimePeriod_6M),
    TimePeriod_1Y(R.string.Market_Filter_TimePeriod_1Y),
}

enum class PriceChange(@StringRes val titleResId: Int, @ColorRes val colorResId: Int, val values: Pair<Long?, Long?>) {
    Positive_10_plus(R.string.Market_Filter_PriceChange_Positive_10_plus, R.color.remus, Pair(10, null)),
    Positive_25_plus(R.string.Market_Filter_PriceChange_Positive_25_plus, R.color.remus, Pair(25, null)),
    Positive_50_plus(R.string.Market_Filter_PriceChange_Positive_50_plus, R.color.remus, Pair(50, null)),
    Positive_100_plus(R.string.Market_Filter_PriceChange_Positive_100_plus, R.color.remus, Pair(100, null)),
    Negative_10_minus(R.string.Market_Filter_PriceChange_Negative_10_minus, R.color.lucian, Pair(null, -10)),
    Negative_25_minus(R.string.Market_Filter_PriceChange_Negative_25_minus, R.color.lucian, Pair(null, -25)),
    Negative_50_minus(R.string.Market_Filter_PriceChange_Negative_50_minus, R.color.lucian, Pair(null, -50)),
    Negative_100_minus(R.string.Market_Filter_PriceChange_Negative_100_minus, R.color.lucian, Pair(null, -100)),
}
