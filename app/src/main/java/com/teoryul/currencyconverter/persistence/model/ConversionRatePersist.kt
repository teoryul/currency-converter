package com.teoryul.currencyconverter.persistence.model

import android.arch.persistence.room.*

@Entity(
    tableName = "conversion_rate_table",
    foreignKeys = [ForeignKey(
        entity = CurrencyPairPersist::class,
        parentColumns = arrayOf("pair"),
        childColumns = arrayOf("currency_pair"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index(
        value = arrayOf("currency_pair")
    )]
)
data class ConversionRatePersist(

    @ColumnInfo(name = "currency_pair")
    val currencyPair: String,

    @ColumnInfo(name = "rate")
    val rate: Float,

    @ColumnInfo(name = "date")
    val date: Long
) : Comparable<ConversionRatePersist> {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConversionRatePersist) return false

        return this.currencyPair == other.currencyPair
                && this.date == other.date
    }

    override fun hashCode(): Int {
        var result = 1
        result = result xor currencyPair.hashCode()
        result = result xor date.hashCode()

        return result
    }

    override fun compareTo(other: ConversionRatePersist): Int = date.compareTo(other.date)
}