package model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.OrderDetail;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-29T22:03:49")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile ListAttribute<Product, OrderDetail> orderDetailList;
    public static volatile SingularAttribute<Product, String> productcode;
    public static volatile SingularAttribute<Product, String> productdescription;
    public static volatile SingularAttribute<Product, String> size;
    public static volatile SingularAttribute<Product, BigDecimal> buyprice;
    public static volatile SingularAttribute<Product, Short> quantityinstock;
    public static volatile SingularAttribute<Product, String> sex;
    public static volatile SingularAttribute<Product, String> weight;
    public static volatile SingularAttribute<Product, String> productname;
    public static volatile SingularAttribute<Product, String> productcolor;
    public static volatile SingularAttribute<Product, String> warrenty;
    public static volatile SingularAttribute<Product, String> producttype;

}