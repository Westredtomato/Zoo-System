package org.example;

import org.example.dao.DatabaseConnection;
import org.example.dao.ZooManagerDAO;

import java.time.LocalDate;
import java.util.List;

// 在Main.java中测试数据库连接
public class Main {
    public static void main(String[] args) {
        CLIServiceMenu menu = new CLIServiceMenu();
        menu.run();
    }
}