package maloney.jack.domain;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    private String firstName;

    private String secondName;

    private Integer accountNumber;

    public Account() {
    }

    public Account(String firstName, String secondName, Integer accountNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id)
                && Objects.equals(firstName, account.firstName)
                && Objects.equals(secondName, account.secondName)
                && Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, accountNumber);
    }

    @Override
    public String toString() {
        return "Account{"
                + " id='" + id + '\''
                + ", firstName='" + firstName + '\''
                + ", secondName='" + secondName + '\''
                + ", accountNumber=" + accountNumber
                + '}';
    }
}
