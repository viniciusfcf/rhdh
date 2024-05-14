Ex de execução: 

```ansible-playbook -e server=https://api.cluster-m998k.sandbox1967.opentlc.com:6443 playbook.yaml```

# Pontos que devem ser atualizados:
- certificado do keycloak
- usuário e senha dos postgres
- Valid redirect URIs, Valid post logout redirect URIs e Web origins do realm "rhdh". Atualmente estão com '*'
# FAQ
- Senha de admin do keycloak: keycloak-initial-admin