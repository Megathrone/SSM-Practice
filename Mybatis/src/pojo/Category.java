package pojo;

import java.util.List;

public class Category {

    private int id;
    private String name;
    List<Product> products;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Category [id =" +id+ ", name=" + name +"]";
    }
}