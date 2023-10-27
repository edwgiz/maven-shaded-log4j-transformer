[![We recommend IntelliJ IDEA](http://amihaiemil.github.io/images/intellij-idea-recommend.svg)](https://www.jetbrains.com/idea/)

# maven-shaded-log4j-transformer
:warning: The transformer has become an official Log4J module

:warning: All existing and future users are recommended to switch to the successor project, please note [new artifact documentation](https://logging.apache.org/log4j/transform/latest/#log4j-plugin-cache-transformer)

:warning: Its future development will continue in repo [log4j-transform-maven-shade-plugin-extensions](https://github.com/apache/logging-log4j-transform/tree/main/log4j-transform-maven-shade-plugin-extensions)


The solution intended to augment [Apache Maven Shade Plugin](https://maven.apache.org/plugins/maven-shade-plugin/),
handling the following cases:
1. Multiple `Log4j2Plugins.dat` files merging to deal with
   [LOG4J2-673](https://issues.apache.org/jira/browse/LOG4J2-673) and
   [LOG4J2-954](https://issues.apache.org/jira/browse/LOG4J2-954)
2. Log4J plugin's classes
   [relocating](https://maven.apache.org/plugins/maven-shade-plugin/examples/class-relocation.html) to the different 
   packages so `Log4j2Plugins.dat` file points to the new locations

Legacy versions are available:
- [2.20.0](https://github.com/edwgiz/maven-shaded-log4j-transformer/blob/cadff48627d814f052161d73b753cd29aa8af047/README.md)
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
