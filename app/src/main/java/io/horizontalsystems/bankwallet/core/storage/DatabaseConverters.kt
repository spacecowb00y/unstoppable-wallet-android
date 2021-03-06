package io.horizontalsystems.bankwallet.core.storage

import androidx.room.TypeConverter
import io.horizontalsystems.bankwallet.core.App
import io.horizontalsystems.bankwallet.entities.CoinType
import io.horizontalsystems.bankwallet.entities.PriceAlert
import io.horizontalsystems.bankwallet.entities.SubscriptionJob
import java.math.BigDecimal

class DatabaseConverters {

    // BigDecimal

    @TypeConverter
    fun fromString(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    @TypeConverter
    fun toString(bigDecimal: BigDecimal?): String? {
        return bigDecimal?.toPlainString()
    }

    // SecretString

    @TypeConverter
    fun decryptSecretString(value: String?): SecretString? {
        if (value == null) return null

        return try {
            SecretString(App.encryptionManager.decrypt(value))
        } catch (e: Exception) {
            null
        }
    }

    @TypeConverter
    fun encryptSecretString(secretString: SecretString?): String? {
        return secretString?.value?.let { App.encryptionManager.encrypt(it) }
    }

    // SecretList

    @TypeConverter
    fun decryptSecretList(value: String?): SecretList? {
        if (value == null) return null

        return try {
            SecretList(App.encryptionManager.decrypt(value).split(","))
        } catch (e: Exception) {
            null
        }
    }

    @TypeConverter
    fun encryptSecretList(secretList: SecretList?): String? {
        return secretList?.list?.joinToString(separator = ",")?.let {
            App.encryptionManager.encrypt(it)
        }
    }

    @TypeConverter
    fun fromCoinType(coinType: CoinType?): String {
        return coinType?.serialize() ?: ""
    }

    @TypeConverter
    fun toCoinType(value: String): CoinType? {
        return CoinType.deserialize(value)
    }

    @TypeConverter
    fun fromChangeState(state: PriceAlert.ChangeState): String{
        return state.value
    }

    @TypeConverter
    fun toChangeState(value: String?): PriceAlert.ChangeState?{
        return PriceAlert.ChangeState.valueOf(value)
    }

    @TypeConverter
    fun fromTrendState(state: PriceAlert.TrendState): String{
        return state.value
    }

    @TypeConverter
    fun toTrendState(value: String?): PriceAlert.TrendState?{
        return PriceAlert.TrendState.valueOf(value)
    }

    @TypeConverter
    fun fromStateType(state: SubscriptionJob.StateType): String{
        return state.value
    }

    @TypeConverter
    fun toStateType(value: String?): SubscriptionJob.StateType?{
        return SubscriptionJob.StateType.valueOf(value)
    }

    @TypeConverter
    fun fromJobType(state: SubscriptionJob.JobType): String{
        return state.value
    }

    @TypeConverter
    fun toJobType(value: String?): SubscriptionJob.JobType?{
        return SubscriptionJob.JobType.valueOf(value)
    }

}
