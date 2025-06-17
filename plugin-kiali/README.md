Execute: 02-create-plugin-registry.sh

atualize yaml:

```


```

backend:
sha512-mfR58VK9lHWxpUlUBrClTDklEZumkcNdPPyx7z0SRGTtxQQvHHepuXmoYfbdIpbAsZ5w6nVh0PBPtZ78KkkvEg==


front:

sha512-oBkaf3/0v8x2V4Mo4EXP5C7e6lYjMiPPjc4lx3ddqLtJ4iW41FA4CxmHu0g1IXQLu+LJPVVaijmkCnptrB9vsg==


```
plugins:
    - package: 'http://plugin-registry:8080/backstage-community-plugin-kiali-backend-dynamic-1.22.0.tgz'
    disabled: false
    integrity: 'sha512-mfR58VK9lHWxpUlUBrClTDklEZumkcNdPPyx7z0SRGTtxQQvHHepuXmoYfbdIpbAsZ5w6nVh0PBPtZ78KkkvEg=='
    - package: 'http://plugin-registry:8080/backstage-community-plugin-kiali-dynamic-1.38.0.tgz'
    disabled: false
    integrity: 'sha512-oBkaf3/0v8x2V4Mo4EXP5C7e6lYjMiPPjc4lx3ddqLtJ4iW41FA4CxmHu0g1IXQLu+LJPVVaijmkCnptrB9vsg=='
```

ou 

```
plugins:
    - package: 'https://raw.githubusercontent.com/viniciusfcf/rhdh/refs/heads/master/plugin-kiali/deploy/backstage-community-plugin-kiali-backend-dynamic-1.22.0.tgz'
    disabled: false
    integrity: 'sha512-mfR58VK9lHWxpUlUBrClTDklEZumkcNdPPyx7z0SRGTtxQQvHHepuXmoYfbdIpbAsZ5w6nVh0PBPtZ78KkkvEg=='
    - package: 'https://raw.githubusercontent.com/viniciusfcf/rhdh/refs/heads/master/plugin-kiali/deploy/backstage-community-plugin-kiali-dynamic-1.38.0.tgz'
    disabled: false
    integrity: 'sha512-oBkaf3/0v8x2V4Mo4EXP5C7e6lYjMiPPjc4lx3ddqLtJ4iW41FA4CxmHu0g1IXQLu+LJPVVaijmkCnptrB9vsg=='
    pluginConfig:
        dynamicPlugins:
        frontend:
            backstage-community.plugin-kiali:
            appIcons:
                - importName: KialiIcon
                name: kialiIcon
            dynamicRoutes:
                - importName: KialiPage
                menuItem:
                    icon: kialiIcon
                    text: Kiali
                path: /kiali
            mountPoints:
                - config:
                    if:
                    anyOf:
                        - hasAnnotation: backstage.io/kubernetes-namespace
                importName: EntityKialiContent
                mountPoint: entity.page.kiali/cards

```