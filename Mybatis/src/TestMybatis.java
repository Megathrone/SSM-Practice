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

        HashMap<String,Object> params = new HashMap<>();
        params.put("id",1);
        params.put("name","cat");

        System.out.println("查询所有");
        List<Product> ps = session.selectList("listProductPro");
        for(Product p : ps){
            System.out.println(p);
        }

        System.out.println("模糊查询");
        Map<String,Object> map = new HashMap<>();
        map.put("name","a");
        List<Product> ps2 = session.selectList("listProductPro",map);
        for(Product p:ps2){
            System.out.println(p);
        }
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