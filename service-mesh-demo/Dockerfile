# Base oficial do WildFly
FROM quay.io/wildfly/wildfly:30.0.0.Final-jdk17

# Copia o seu WAR para a pasta de deployments
COPY target/service-mesh-demo.war /opt/jboss/wildfly/standalone/deployments/

# Expor a porta padrão HTTP do WildFly
EXPOSE 8080

# Comando padrão de inicialização
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
