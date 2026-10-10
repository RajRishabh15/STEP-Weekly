// import java.util.Scanner;
class Account{
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;

    public Account(int accountNumber,String name,int age,double balance,String accountType,String status){
        this.accountNumber=accountNumber;
        this.name=name;
        this.age=age;
        this.balance=balance;
        this.accountType=accountType;
        this.status=status;
    }

    boolean deposit(double amount){
        if(amount>0){
            return true;
        }else{
            return false;
        }
    }

    boolean withdraw(double amount){
        if(amount>0 && amount<=balance){
            return true;
        }else{
            return false;
        }
    }

    int getAccountNumber(){
        return accountNumber;
    }

    String getNanme(){
        return name;
    }

    int getAge(){
        return age;
    }

    double getBalance(){
        return balance;
    }

    String getAccountType(){
        return accountType;
    }

    String getStatus(){
        return status;
    }

    void setName(String name){
        this.name=name;
    }

    void setAge(int age){
        this.age=age;
    }

    
}