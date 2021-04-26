[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
&nbsp; [![CodeFactor](https://www.codefactor.io/repository/github/edwgiz/maven-shaded-log4j-transformer/badge/master)](https://www.codefactor.io/repository/github/edwgiz/maven-shaded-log4j-transformer/overview/master)
&nbsp; [![GitHub build (v2.16.0)](https://img.shields.io/github/workflow/status/edwgiz/maven-shaded-log4j-transformer/Java%20CI/master?&label=Build%20v2.16.0&logo=github)](https://github.com/edwgiz/maven-shaded-log4j-transformer/actions/workflows/maven.yml?query=workflow%3AJava+branch%3Amaster)
&nbsp; ![Test Coverage](.readme/jacoco.svg)
&nbsp; [![Latest Maven Central release](https://img.shields.io/maven-central/v/io.github.edwgiz/log4j-maven-shade-plugin-extensions.svg?logo=java)](http://mvnrepository.com/artifact/io.github.edwgiz/log4j-maven-shade-plugin-extensions)
&nbsp; [![We recommend IntelliJ IDEA](http://amihaiemil.github.io/images/intellij-idea-recommend.svg)](https://www.jetbrains.com/idea/)

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
                                        implementation="io.github.edwgiz.log4j.maven.plugins.shade.transformer.Log4j2PluginCacheFileTransformer">
                                </transformer>
                            </transformers>
...
                        </configuration>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>io.github.edwgiz</groupId>
                        <artifactId>log4j-maven-shade-plugin-extensions</artifactId>
                        <version>2.16.0</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>

    </build>

</project>
```

A number of the transformer version corresponds to the number of the artifacts in `org.apache.logging.log4j` group

Below versions are available:
- 2.16.0, :warning: the prior transformer declaration in your `pom.xml` should be rewritten accordingly above example
- 2.15.0
- 2.14.1
- 2.14.0
- 2.13.3
- 2.13.2
- 2.13.1
- 2.13.0, :warning: note that transformer implementation in the `maven-shade-plugin` configuration should be changed if it was already referenced before.
- 2.8.1
- 2.8
- 2.7
- 2.6.2
