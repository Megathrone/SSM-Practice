import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import pojo.Category;
import pojo.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class TestMybatis {
    public static void main(String[] args) throws IOException{
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        HashMap<String,Object> params = new HashMap<>();
        params.put("id",1);
        params.put("name","cat");

        List<Category> cs = session.selectList("listCategory");
        for(Category c: cs){
            System.out.println(c);
            List<Product> ps = c.getProducts();
            for (Product p:ps){
                System.out.println("\t"+p);
            }
        }
        //listAll(session);
        session.commit();
        session.close();

    }

    private static void listAll(SqlSession session){
        List<Category> cs = session.selectList("listCategory");
        for(Category c : cs){
            System.out.println(c.getName());
        }
    }
}
