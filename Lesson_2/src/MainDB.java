import java.sql.SQLException;

public class MainDB {
    public static void main(String[] args) {
        CRUD checkCRUD = new CRUD();
        try {
            checkCRUD.connect();
            checkCRUD.creationTable();

            System.out.println("Поля таблицы");
            checkCRUD.selectData(true);

            checkCRUD.addData("Иван", 4);
            checkCRUD.addData("Павел", 10);
            checkCRUD.addData("Дарья", 5);

            System.out.println("Добавленные записи");
            checkCRUD.selectData(false);

            checkCRUD.updateData("Павел","Максим");
            System.out.println("Изменения");
            checkCRUD.selectData(false);

            checkCRUD.deleteData(null, "Иван");
            System.out.println("Удаление строки");
            checkCRUD.selectData(false);

            checkCRUD.deleteTable("students");

            checkCRUD.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
