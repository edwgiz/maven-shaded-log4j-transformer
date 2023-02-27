[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
&nbsp; [![CodeFactor](https://www.codefactor.io/repository/github/edwgiz/maven-shaded-log4j-transformer/badge/master)](https://www.codefactor.io/repository/github/edwgiz/maven-shaded-log4j-transformer/overview/master)
&nbsp; [![GitHub build (v2.20.0)](https://img.shields.io/github/workflow/status/edwgiz/maven-shaded-log4j-transformer/Java%20CI/master?&label=Build%20v2.20.0&logo=github)](https://github.com/edwgiz/maven-shaded-log4j-transformer/actions/workflows/maven.yml?query=workflow%3AJava+branch%3Amaster)
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
                <version>3.4.0</version>
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
                        <version>2.20.0</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>

    </build>

</project>
```

A number of the transformer version corresponds to the number of the artifacts in `org.apache.logging.log4j` group

Below versions are available:
- 2.20.0, :warning: the prior transformer declaration in your `pom.xml` should be rewritten accordingly above example
- [2.19.0](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/d733d45930ba97cd57d0fa95c57cb8e90cb3bd03/README.md)
- [2.18.0](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/e9ce2805e307cadfa0785113ce25372155979395/README.md)
- [2.17.2](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/fb5f108e5ea95714ba521a8442138ae86cce2bcf/README.md)
- [2.17.1](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/66aaa997f22ee63b242e660fe75f731601c1dd34/README.md)
- [2.8.1](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/8d1d3f00d533e367fdb784f2ef529b8e7487b830/README.md)
- [2.8](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/acb049022d5a7771d322c689b15db0dedc96f565/README.md)
- [2.7](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/2492aed3c6952eedf05d229c01c0ebb45cb10fae/README.md)
- [2.6.2](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/8d921f72f0bfca646a2304e92a9aefaab925e33d/README.md)
- [2.6.1](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/0b2b117d441793d59fbe89bb60f643902d414f0b/README.md)
- [2.1](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/fc2184df2d899971fd71fcfeec87480d6d24a2fb/README.md)
