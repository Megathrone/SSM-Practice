import mapper.CategoryMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import pojo.Category;
import pojo.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMybatis {
    public static void main(String[] args) throws IOException{
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        CategoryMapper mapper = session.getMapper(CategoryMapper.class);
        update(mapper,1);
        listAll(mapper);

        session.commit();
        session.close();

    }
    private static void update(CategoryMapper mapper,int i){
        Category c = mapper.get(i);
        c.setName("category1");
        mapper.update(c);
        listAll(mapper);
    }
    private static void get(CategoryMapper mapper) {
        Category c= mapper.get(8);
        System.out.println(c.getName());
    }

    private static void delete(CategoryMapper mapper) {
        mapper.delete(3);
        listAll(mapper);
    }

    private static void add(CategoryMapper mapper) {
        Category c = new Category();
        c.setName("新增加的Category");
        mapper.add(c);
        listAll(mapper);
    }
    private static void listAll(CategoryMapper mapper) {
        List<Category> cs = mapper.list();
        for (Category c : cs) {
            System.out.println(c.getName());
            List<Product> ps = c.getProducts();
            for(Product p:ps){
                System.out.println("\t"+p.getName());
            }
        }
    }
    private static void listAll(SqlSession session){
        List<Category> cs = session.selectList("listCategory");
        for(Category c : cs){
            System.out.println(c.getName());
        }
    }
}