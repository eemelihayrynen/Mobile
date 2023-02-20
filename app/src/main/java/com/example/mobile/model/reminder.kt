package com.example.mobile.model

import androidx.room.*


@Entity(tableName = "reminder_table",
indices = [
    Index("id", unique = true),
Index("creator_id")
],
foreignKeys = [
    ForeignKey(
    entity = Category::class,
    parentColumns = ["id"],
    childColumns = ["creator_id"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
    )


])
data class reminder (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val creation_time: Long = 0,
    @ColumnInfo(name = "creator_id") val creator_id: Long,
    @ColumnInfo(name = "message") val Message: String,
    @ColumnInfo(name = "location-x") val location_x: String,
    @ColumnInfo(name = "location-y") val location_y: String,
    @ColumnInfo(name = "reminder-time") val reminder_time : String,
    @ColumnInfo(name = "reminder-seen")val reminder_seen: String
)