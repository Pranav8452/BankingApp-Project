package com.example.bankingapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insertTransaction(Transaction transaction);

    // ✅ Get all transactions where user is sender or receiver
    @Query("SELECT * FROM transactions WHERE fromEmail = :email OR toEmail = :email ORDER BY id DESC")
    List<Transaction> getTransactionsForUser(String email);
}
