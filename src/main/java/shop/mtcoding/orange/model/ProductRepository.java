package shop.mtcoding.orange.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper // Mybatis 에서 제공, ProductRepositoyIMPL이름으로 IOC 컨테이너에 product.xml 생성됨
public interface ProductRepository {
    public List<Product> findAll(); // findAll = select id값

    public Product findOne(int id);

    // 변경된 행이 리턴된다. -1 DB오류(Syntax error), 2 변경된행이2건, 1 변경된행이1건, 0 변경된행이없다 (조건문을
    // 만족하는 행이 없는 것과 오류 구분할 것)
    public int insert(@Param("name") String name, @Param("price") int price, @Param("qty") String qty);

    public int delete(@Param("id") int id);

    public int update(@Param("id") int id, @Param("name") String name, @Param("price") int price,
            @Param("qty") int qty);

}

/*
 * class -> interface
 * 
 * @Mapper - product.xml 로 연결
 * 
 * 
 */