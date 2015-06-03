# speedment-maven-plugin
This small plugin makes it possible to run Speedment as a Maven goal in your IDE. All you have to do is add the following code to your projects ```pom.xml```-file:
```xml
<plugin>
    <artifactId>speedment-maven-plugin</artifactId>
    <groupId>com.speedment</groupId>
    <version>${speedment.version}</version>
</plugin>
```

This will add two new goals, one that launches the Speedment GUI and one that generates code from a ```.groovy```-file.

![Screenshot from the IDE](http://frslnd.se/github/illustrations/speedment_maven_goals.png)

