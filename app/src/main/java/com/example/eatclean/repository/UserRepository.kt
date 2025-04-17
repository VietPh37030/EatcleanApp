package com.example.eatclean.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository {

    fun isVipUser(): Flow<Boolean> = flow {
        // Giả lập kiểm tra tài khoản VIP
        emit(true) // hoặc false để test chưa mua VIP
    }
}
