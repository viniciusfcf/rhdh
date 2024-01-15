
## Keycloak (optional)
- Install RHBK operator
- Apply all yamls files 
- Create Keycloak
  - Config hostname
  - user
  - password
  - http
  - tls
  - new realm (backstage)
    - new client(backstage)
      - Client authentication, Service accounts roles
        - query-groups
        - query-users
        - view-users
- Configurar keycloak, extraContainers
  
  ```
    keycloak:
        baseUrl: 'https://keycloak-backstage.apps.cluster-kg2fs.dynamic.redhatworkshops.io'
        clientId: backstage
        clientSecret: qsOwWkR1yekklL3lUXIpw9EO5LAQG49f
        cookieSecret: ''
        realm: backstage
    
    service: (já existe)
        externalTrafficPolicy: Cluster
        ports:
        backend: 4180
        name: oauth2-proxy
        targetPort: oauth2-proxy
        sessionAffinity: None
        type: ClusterIP
  ```

  ```
    extraContainers: (está em upstream.backstage)
      - args:
          - '--provider=oidc'
          - '--email-domain=*'
          - '--upstream=http://localhost:7007'
          - '--http-address=0.0.0.0:4180'
          - '--skip-provider-button'
        env:
          - name: OAUTH2_PROXY_CLIENT_ID
            value: >-
              {{ required "Keycloak Client Secret is Required"
              .Values.keycloak.clientId }}
          - name: OAUTH2_PROXY_CLIENT_SECRET
            value: >-
              {{ required "Keycloak Client Secret is Required"
              .Values.keycloak.clientSecret }}
          - name: OAUTH2_PROXY_COOKIE_SECRET
            value: >-
              {{ default (randAlpha 32 | lower | b64enc)
              .Values.keycloak.cookieSecret }}
          - name: OAUTH2_PROXY_OIDC_ISSUER_URL
            value: >-
              {{ required "Keycloak Issuer URL is Required"
              .Values.keycloak.baseUrl }}/realms/{{ required "Keycloak Realm is
              Required" .Values.keycloak.realm }}
          - name: OAUTH2_PROXY_SSL_INSECURE_SKIP_VERIFY
            value: 'true'
          - name: NODE_TLS_REJECT_UNAUTHORIZED
            value: '0'
          - name: LOG_LEVEL
            value: debug
        image: 'quay.io/oauth2-proxy/oauth2-proxy:latest'
        imagePullPolicy: IfNotPresent
        name: oauth2-proxy
        ports:
          - containerPort: 4180
            name: oauth2-proxy
            protocol: TCP
  ```


- Criar usuário de teste no realm backstage, com e-mail verificado e password.
## Passos

- Install healm chart
- Create ConfigMap
  - https://access.redhat.com/documentation/en-us/red_hat_developer_hub/1.0/html/getting_started_with_red_hat_developer_hub/ref-rhdh-supported-configs_rhdh-getting-started#proc-add-custom-app-file-openshift_rhdh-getting-started

    Ex final:

    ```
        kind: ConfigMap
        apiVersion: v1
        metadata:
        name: app-config-rhdh
        data:
        app-config-rhdh.yaml: |
            app:
            title: Vinicius
            auth:
                environment: production
                    providers:
                        oauth2Proxy: {}
            signInPage: oauth2Proxy
    ```

## Fontes:

https://access.redhat.com/documentation/en-us/red_hat_developer_hub/1.0/html/getting_started_with_red_hat_developer_hub/proc-install-rhdh-helm_rhdh-getting-started#doc-wrapper



# Issues

## 1

Depois de habilitar o keycloak:

[2m2024-01-15T14:06:51.525Z[22m [34mbackstage[39m [32minfo[39m Adding plugin "catalog" to backend... 
/opt/app-root/src/node_modules/@backstage/config/dist/index.cjs.js:305
      throw new Error(errors.missing(this.fullKey(key)));
            ^

Error: Missing required config value at 'catalog.providers.keycloakOrg.default.baseUrl'
    at ConfigReader.getString (/opt/app-root/src/node_modules/@backstage/config/dist/index.cjs.js:305:13)
    at ObservableConfigProxy.getString (/opt/app-root/src/node_modules/@backstage/backend-app-api/dist/index.cjs.js:169:30)
    at /opt/app-root/src/dynamic-plugins-root/janus-idp-backstage-plugin-keycloak-backend-dynamic-1.7.6/dist/cjs/KeycloakOrgEntityProvider-65acf4cd.cjs.js:2955:44
    at Array.map (<anonymous>)
    at readProviderConfigs (/opt/app-root/src/dynamic-plugins-root/janus-idp-backstage-plugin-keycloak-backend-dynamic-1.7.6/dist/cjs/KeycloakOrgEntityProvider-65acf4cd.cjs.js:2952:33)
    at KeycloakOrgEntityProvider.fromConfig (/opt/app-root/src/dynamic-plugins-root/janus-idp-backstage-plugin-keycloak-backend-dynamic-1.7.6/dist/cjs/KeycloakOrgEntityProvider-65acf4cd.cjs.js:3193:12)
    at Object.catalog (/opt/app-root/src/dynamic-plugins-root/janus-idp-backstage-plugin-keycloak-backend-dynamic-1.7.6/dist/index.cjs.js:21:59)
    at /opt/app-root/src/packages/backend/dist/index.cjs.js:483:9
    at Array.forEach (<anonymous>)
    at createPlugin$5 (/opt/app-root/src/packages/backend/dist/index.cjs.js:481:98)
    at process.processTicksAndRejections (node:internal/process/task_queues:95:5)
    at async addPlugin (/opt/app-root/src/packages/backend/dist/index.cjs.js:692:31)
    at async main (/opt/app-root/src/packages/backend/dist/index.cjs.js:783:3)

Fix:
    no ConfigMap incluir

    ```
    catalog:
      providers:
        keycloakOrg:
          default:
            baseUrl: https://keycloak-backstage.apps.cluster-kg2fs.dynamic.redhatworkshops.io/
            loginRealm: backstage
            realm: backstage
            clientId: backstage
            clientSecret: qsOwWkR1yekklL3lUXIpw9EO5LAQG49f
    ```


## 2

2024-01-15T14:34:04.926Z catalog error Error while syncing Keycloak users and groups self-signed certificate type=plugin class=KeycloakOrgEntityProvider taskId=KeycloakOrgEntityProvider:default:refresh taskInstanceId=70a0e61d-b1e5-4ecf-93de-be4ca72b3daa name=Error stack=Error: self-signed certificate
at TLSSocket.onConnectSecure (node:_tls_wrap:1600:34)
at TLSSocket.emit (node:events:517:28)
at TLSSocket._finishInit (node:_tls_wrap:1017:8)
at ssl.onhandshakedone (node:_tls_wrap:803:12) status=undefined

Fix:
     
     ```
     extraEnvVars: (já existe esse, upstream.backstage.)
      - name: NODE_TLS_REJECT_UNAUTHORIZED
        value: '0'
     ```