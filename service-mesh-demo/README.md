Compile:

`mvn clean package`

JBOSS:
```
wget https://github.com/wildfly/wildfly/releases/download/30.0.0.Final/wildfly-30.0.0.Final.zip
unzip wildfly-30.0.0.Final.zip
cd wildfly-30.0.0.Final
```

Deploy:
`cp target/service-mesh-demo.war $JBOSS/standalone/deployments`

Init JBOSS:
`./bin/standalone.sh`

Test:
[http](http://localhost:8080/service-mesh-demo/)