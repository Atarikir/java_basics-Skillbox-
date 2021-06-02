import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BsonField;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.eq;

public class Shops {

    private final MongoClient mongoClient = new MongoClient("localhost", 27017);
    private final MongoDatabase mongoDatabase = mongoClient.getDatabase("shopsDb");

    private final MongoCollection<Document> shops = mongoDatabase.getCollection("shops");

    public MongoCollection<Document> getProducts() {
        return products;
    }

    private final MongoCollection<Document> products = mongoDatabase.getCollection("products");

    public void addShop(String nameShop) {

        Document shop = new Document("name", nameShop);
        shop.append("products", new ArrayList<String>());

        if (getShop(nameShop) == null) {
            shops.insertOne(shop);
            System.out.println("Вы добавили магазин " + nameShop);
        } else
            System.out.println("Магазин с названием " + nameShop + " уже существует.");
    }

    public void addProduct(String namePriceProduct) {

        String[] stringCommand = namePriceProduct.split(" ");
        String nameProduct = stringCommand[0];
        int priceProduct = Integer.parseInt(stringCommand[1]);

        Document product = new Document("name", nameProduct);
        product.append("price", priceProduct);

        if (getProduct(nameProduct) == null) {
            products.insertOne(product);
            System.out.println("Вы добавили товар " + nameProduct + " стоимостью " + priceProduct + " руб");
        } else
            System.out.println("Товар уже существует.");
    }

    public void exhibitGoods(String nameProductNameShop) {

        String[] stringCommand = nameProductNameShop.split(" ");
        String nameProduct = stringCommand[0];
        String nameShop = stringCommand[1];

        if (getProduct(nameProduct) != null && getShop(nameShop) != null) {
            shops.updateOne(eq(getShop(nameShop)), new Document("$addToSet", new Document("products",
                    getProduct(nameProduct).get("name"))));
            System.out.println("Товар " + nameProduct + " добавлен в магазин " + nameShop);
        } else
            System.out.println("Товара или магазина не существует");
    }

    public void productStatistics(MongoCollection<Document> collection) {

        try {
            System.out.println("Общее количество наименований товаров: " + products.countDocuments());

            Object averagePriceOfGoods = Objects.requireNonNull(collection.aggregate(Collections.singletonList(
                    group("_id", new BsonField("AverageAge", new BsonDocument("$avg",
                            new BsonString("$price")))))).first()).values();
            System.out.println("Средняя цена товаров " + averagePriceOfGoods);

            System.out.println("Самый дорогой товар " + Objects.requireNonNull(collection.find()
                    .sort(BsonDocument.parse("{price:-1}")).first()).values());

            System.out.println("Самый дешевый товар " + Objects.requireNonNull(collection.find()
                    .sort(BsonDocument.parse("{price: 1}")).first()).values());

            Bson query = new BasicDBObject("price", new BasicDBObject("$lt", 100));
            System.out.println("Количество товаров дешевле 100 рублей ");
            collection.find(query).forEach((Consumer<Document>) System.out::println);
        } catch (NullPointerException e) {
            System.out.println("Не добавлено ни одного товара");
        }
    }

    public void exit() {
        //mongoDatabase.drop();
        mongoClient.close();
        System.out.println("Вы завершили работу.");
    }

    private Document getShop(String name) {
        return shops.find(new Document("name", name)).first();
    }

    private Document getProduct(String name) {
        return products.find(new Document("name", name)).first();
    }
}