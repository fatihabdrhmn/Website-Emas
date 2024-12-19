/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.persistence.*;

@Entity
@Table(name = "bank_accounts") // Assuming the table name is 'users'
public class BankAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "gold_amount")
    private float goldAmount;
    @Column(name = "rupiah_amount")
    private float rupiahAmount;

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountNumber) {
        this.accountName = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public float getRupiahAmount() {
        return rupiahAmount;
    }

    public void setRupiahAmount(float rupiahAmount) {
        this.rupiahAmount = rupiahAmount;
    }
    public float getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(float goldAmount) {
        this.goldAmount = goldAmount;
    }
}
