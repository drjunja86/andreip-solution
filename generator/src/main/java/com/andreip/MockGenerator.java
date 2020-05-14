package com.andreip;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MockGenerator {

    public static void main(String[] args) {
        Connection connection;
        Statement stmt;
        try {
           Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://postgres:5432/testdb",
                            "dbuser", "ajfo3489fj033");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            HashMap<String, Double> productPrices = new HashMap<>();
            ArrayList<String> productIds = new ArrayList<>();

            stmt = connection.createStatement();

            // Delete all rows from tables
            cleanTables(stmt);

            // Generate products
            generateProducts(stmt, productPrices, productIds);

            // generate orders
            generateOrders(stmt, productPrices, productIds);

            stmt.close();
            connection.commit();
            connection.close();

            System.out.println("Generation successfully complete");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Remove all rows from all tables
     * @param stmt          reference to the Statement object
     * @throws SQLException exception
     */
    private static void cleanTables(Statement stmt) throws SQLException {
        String sql = "DELETE FROM lines;";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM orders;";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM product;";
        stmt.executeUpdate(sql);
    }

    /**
     * Generate all products and insert into DB
     * @param stmt          reference to the Statement object
     * @param productPrices object containing product prices
     * @param productIds    object containing all product ids
     * @throws SQLException exception
     */
    private static void generateProducts(Statement stmt, HashMap<String, Double> productPrices,
                                         ArrayList<String> productIds) throws SQLException {
        String sql;
        Random rnd = new Random();
        PrimitiveIterator.OfDouble doubleIterator = rnd.doubles().iterator();
        String[] flowers = {"Gazania", "Cornflower", "Iberis", "Narcissus", "Coneflower", "Linseed",
                "Jasminoides", "Sneezeweed", "Candytuft", "Polemonium", "Mallow", "Lavatera",
                "Argyranthemum", "Tropaeolum", "Nemophila", "Papaver", "Clintonia", "Rondeletia",
                "Roses", "Thunbergia", "Ursinia"};
        PrimitiveIterator.OfInt flowersIterator = rnd.ints(0, flowers.length).iterator();
        String productId;
        Double price;
        for (int i = 0; i < 20; i++) {
            sql = "INSERT INTO product(productId, productName, stock, price) VALUES('%s','%s',%d,%.2f);";
            productId = UUID.randomUUID().toString();
            productIds.add(productId);
            price = doubleIterator.nextDouble();
            productPrices.put(productId, price);
            sql = String.format(
                    sql,
                    productId,
                    flowers[flowersIterator.nextInt()],
                    1 + rnd.nextInt(100),
                    price);
            System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
        }
    }

    /**
     * Generates orders and insert into DB
     *
     * @param stmt          reference to the Statement object
     * @param productPrices object containing product prices
     * @param productIds    object containing all product ids
     * @throws SQLException  exception
     */
    private static void generateOrders(Statement stmt, HashMap<String, Double> productPrices,
                                       ArrayList<String> productIds) throws SQLException {
        String sql;
        String productId;
        Double price;
        Random rnd = new Random();
        PrimitiveIterator.OfInt productIterator =
                rnd.ints(0, productIds.size()).iterator();
        double total, linePrice;
        int productsCount, orderLines, orderId;
        for (int i = 0; i < 50; i++) {
            total = 0.00;
            sql = "INSERT INTO orders(purchaseDate, total) VALUES('%s', %.2f) RETURNING id;";
            sql = String.format(
                    sql,
                    getRandomTimeStamp().toString(),
                    total);
            System.out.println("SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            orderId = rs.getInt("id");
            orderLines = 1 + rnd.nextInt(5);
            for (int line = 0; line < orderLines; line++) {
                productId = productIds.get(productIterator.nextInt());
                price = productPrices.get(productId);
                productsCount = 1 + rnd.nextInt(10);
                linePrice = price * productsCount;
                total += linePrice;
                sql = "insert into lines(productId, orderId, amount, subtotal) values('%s',%d,%d,%.2f);";
                sql = String.format(
                        sql,
                        productId,
                        orderId,
                        productsCount,
                        linePrice);
                System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
            }

            sql = "UPDATE orders set total = %.2f where id=%d;";
            sql = String.format(sql, total, orderId);
            System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
        }
    }

    private static Timestamp getRandomTimeStamp() {
        long rangeBegin = Timestamp.valueOf("2010-01-01 00:00:00").getTime();
        long rangeEnd = Timestamp.valueOf(LocalDateTime.now()).getTime();
        long diff = rangeEnd - rangeBegin + 1;
        return new Timestamp(rangeBegin + (long) (Math.random() * diff));
    }
}
