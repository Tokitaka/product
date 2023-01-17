package shop.mtcoding.orange.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product { // table 모델링 한 클래스 : Entity
    private Integer id;
    private String name;
    private String price;
    private String qty;
    private Timestamp createdAt;
}
