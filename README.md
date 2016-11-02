# Request Scoped Bean Test

The purpose of this repo is to use the minimal amount of code to implement a request-scoped bean.

## Files
1. **RequestScopedBean** - Contains a single boolean property that defaults to false for each new request
* **Foo** - The source model class 
* **Bar** - The destination model class 
* **FooToBarViewConverter** - A Spring converter that automatically gets registered by Spring Boot and has RequestScopedBean autowired
* **BarController** - Has a single @RequestMapping that returns a BarView

## How it works

*RequestScopedBean*

```java
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class RequestScopedBean {
    private Boolean loadSaleStats = false;
}
```

We set the `RequestScopeBean` to the request scope which means that instead of being a singleton, it is instantiated every single time there is a new request. The proxyMode defaults to NO, which is really not useful since it means that it can only be autowired when there is an active request. When the application context is starting up there is no active request so any autowiring of this bean would fail on startup.

*BarController*
```java
    @RequestMapping("/bar/{id}")
    public BarView getGallery(@PathVariable("id") Integer id,
                              @RequestParam(value="loadSaleStats", required = false) Boolean loadSaleStats) {
        requestScopedBean.setLoadSaleStats(loadSaleStats);
        return conversionService.convert(Foo.builder()
                .id(id)
                .build(), BarView.class);
    }
```

Notice that we are setting the bean, but **NOT** setting any value in the Foo.builder().

Take a look at three requests

```bash
# Request 1
curl http://localhost:8080/bar/1
{"barId":1,"loadSaleStats":null}%
# Request 2
curl http://localhost:8080/bar/1\?loadSaleStats\=false
{"barId":1,"loadSaleStats":false}%
# Request 3
curl http://localhost:8080/bar/1\?loadSaleStats\=true
{"barId":1,"loadSaleStats":true}%
# Request 4
curl http://localhost:8080/bar/1
{"barId":1,"loadSaleStats":null}%
```

1. RequestScopeBean defaults to false in the object but it sets it to false since loadSaleStats is null from not being set by the request param.
* It's set to false and is picked up in the converter
* It's set to true and is picked up in the converter
* Just to show that it really is request-scoped it reverts back to null when we don't set the request param
