[![We recommend IntelliJ IDEA](http://amihaiemil.github.io/images/intellij-idea-recommend.svg)](https://www.jetbrains.com/idea/)

# maven-shaded-log4j-transformer
Transformer for `maven-shaded-plugin`, that concatenates Log4j2Plugins.dat files 
in order to provide a workaround for [LOG4J2-673](https://issues.apache.org/jira/browse/LOG4J2-673) and 
[LOG4J2-954](https://issues.apache.org/jira/browse/LOG4J2-954) bugs. 

How to use:
```xml
<project>
...
    <build>
...
        <plugins>
...
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.2.4</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
...
                                <transformer
                                        implementation="com.github.edwgiz.maven_shade_plugin.log4j2_cache_transformer.PluginsCacheFileTransformer">
                                </transformer>
                            </transformers>
...
                        </configuration>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>com.github.edwgiz</groupId>
                        <artifactId>maven-shade-plugin.log4j2-cachefile-transformer</artifactId>
                        <version>2.14.0</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>

    </build>

</project>
```

A number of the transformer version corresponds to the number of the artifacts in `org.apache.logging.log4j` group

Below versions are available:
- 2.14.0
- 2.13.3
- 2.13.2
- 2.13.1
- 2.13.0, please note that transformer implementation in the `maven-shade-plugin` configuration should be changed if it was already referenced before.
- 2.8.1
- 2.8
- 2.7
- 2.6.2