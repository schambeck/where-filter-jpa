# where-filter-jpa

Maven:
```xml
<dependency>
    <groupId>com.schambeck.wherefilter</groupId>
    <artifactId>where-filter-jpa</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Example:
```java
public class Main {
    public static void main(String[] args) {
        String where = "name LIKE 'mouse%'";
        WhereBuilder<T> whereBuilder = WhereBuilder.create(builder, from, getClazz(), where);
        Runner<T> runner = new Runner<>(whereBuilder);
        Predicate[] predicates = runner.run();
        query.where(predicates);
    }
}
```
