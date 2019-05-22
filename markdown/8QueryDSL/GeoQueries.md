# 地理位置查询

ES支持两种类型的地理位置数据：支持经纬度的 **geo_point** ，以及支持点、线、圆、多边形等的 **geo_shape** 。

[geo_shape查询](#GeoShape查询)

 - 可以查询坐标与指定区域相交、不相交或包含的地理位置。
 
[geo_bounding_box查询](#GeoBoundingBox查询)

 - 可以查询坐标在矩形区域的地理位置。
 
[geo_distance查询](#GeoDistance查询)

 - 可以查询指定坐标距离内的地理位置。
 
[geo_polygon查询](#GeoPolygon查询)

 - 可以查询坐标在多边形范围内的地理位置。
 


## GeoShape查询

查看[Geo Shape Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-geo-shape-query.html)

注意： **geo_shape** 类型使用 **Spatial4J** 和 **JTS** 两种依赖：

```xml
<dependency>
    <groupId>org.locationtech.spatial4j</groupId>
    <artifactId>spatial4j</artifactId>
    <version>0.6</version>                        
</dependency>

<dependency>
    <groupId>com.vividsolutions</groupId>
    <artifactId>jts</artifactId>
    <version>1.13</version>                         
    <exclusions>
        <exclusion>
            <groupId>xerces</groupId>
            <artifactId>xercesImpl</artifactId>
        </exclusion>
    </exclusions>
</dependency>
``` 

```java
// Import ShapeRelation and ShapeBuilder
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
```

```java
GeoShapeQueryBuilder qb = geoShapeQuery(
        //字段
        "pin.location",                                      
        //区域
        ShapeBuilders.newMultiPoint(                         
                new CoordinatesBuilder()
            .coordinate(0, 0)
            .coordinate(0, 10)
            .coordinate(10, 10)
            .coordinate(10, 0)
            .coordinate(0, 0)
            .build()));
//关系可以是ShapeRelation.CONTAINS、ShapeRelation.WITHIN、ShapeRelation.INTERSECTS和ShapeRelation.DISJOINT
qb.relation(ShapeRelation.WITHIN);       
```

```java
// Using pre-indexed shapes
GeoShapeQueryBuilder qb = geoShapeQuery(
            //字段
            "pin.location",                                  
            //包含预索引形状的文档ID
            "DEU",                                           
            //预索引形状的索引类型
            "countries");    
//关系
qb.relation(ShapeRelation.WITHIN)  
    //预索引形状所在的索引名，默认是shapes                          
    .indexedShapeIndex("shapes")  
    //包含预索引形状的指定路径，默认是shape                           
    .indexedShapePath("location"); 
```

## GeoBoundingBox查询

查看[Geo Bounding Box Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-geo-bounding-box-query.html)

```java
//字段名
geoBoundingBoxQuery("pin.location")  
    //矩形左上角的坐标                        
    .setCorners(40.73, -74.1,   
                //矩形右上角的坐标
                40.717, -73.99); 
```

## GeoDistance查询

查看[Geo Distance Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-geo-bounding-box-query.html)

```java
//字段名
geoDistanceQuery("pin.location")                             
    //中心点
    .point(40, -70)      
    //距离中心点的距离                                    
    .distance(200, DistanceUnit.KILOMETERS);  
```

## GeoPolygon查询

查看[Geo Polygon Query](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-geo-polygon-query.html)

```java
List<GeoPoint> points = new ArrayList<GeoPoint>();           
points.add(new GeoPoint(40, -70));
points.add(new GeoPoint(30, -80));
points.add(new GeoPoint(20, -90));
geoPolygonQuery("pin.location", points); 
```

