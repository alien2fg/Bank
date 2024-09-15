package com.laba.solvd.enums;

public enum TransactionType {
    DEPOSIT("Deposit to account"),
    WITHDRAWAL("Withdrawal from account"),
    TRANSFER("Transfer between accounts");

    private String description;

    TransactionType(String description){
        this.description =description;
    }

    public String getDescription() {
        return description;
    }

   public  static TransactionType fromString(String type){
        for (TransactionType t : TransactionType.values()){
            if (t.name().equalsIgnoreCase(type)){
                return t;
            }
        }
        return null;
   }
}
