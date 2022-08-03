import java.io.Serializable;

public class Courier {

    //переменные
    private String login;
    private String password;
    private String firstName;
    private String id;

    //Геттеры
    public String getLogin() {
    return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    //Сеттеры
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Конструкторы
    public Courier (String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier (String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Courier (String id) {
        this.id = id;
    }

    public Courier () {
    }
}
