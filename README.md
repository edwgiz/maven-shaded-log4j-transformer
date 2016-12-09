# maven-shaded-log4j-transformer
Transformer implementation to concatenate Log4j2Plugins.dat files due build with Maven Shaded plugin.

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
                <version>2.4.3</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <finalName>${project.artifactId}${appSuffix}</finalName>
                            <transformers>
...
                                <transformer
                                        implementation="com.github.edwgiz.mavenShadePlugin.log4j2CacheTransformer.PluginsCacheFileTransformer">
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
                        <version>2.6.2</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>

    </build>

    <pluginRepositories>
        <pluginRepository>
            <id>central</id>
            <name>Central Repository</name>
            <url>http://central.maven.org/maven2</url>
        </pluginRepository>
    </pluginRepositories>

</project>
```
