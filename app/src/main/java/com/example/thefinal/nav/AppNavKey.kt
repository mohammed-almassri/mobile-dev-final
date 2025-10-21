package com.example.thefinal.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

interface AppNavKey: NavKey

@Serializable
object Login: AppNavKey
@Serializable
object Category: AppNavKey

@Serializable
data class Items(val category: String): AppNavKey
@Serializable
object ItemDetails: AppNavKey
@Serializable
object Settings: AppNavKey
