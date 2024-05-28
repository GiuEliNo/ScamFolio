package com.dosti.scamfolio.db.repositories

import androidx.lifecycle.LiveData
import com.dosti.scamfolio.db.dao.PurchaseDao
import com.dosti.scamfolio.db.entities.Purchase

class PurchasingsRepository(private val purchaseDao: PurchaseDao) {
    suspend fun insertPurchasing(purchase: Purchase) = purchaseDao.insert(purchase)

    suspend fun updatePurchasing(purchase: Purchase) = purchaseDao.update(purchase)

    suspend fun deletePurchasing(purchase: Purchase) = purchaseDao.delete(purchase)

    // fun getAllPurchasings(??) il dao non prende buona come entity il purchasing zio pera
}