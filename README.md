# bearer-extractor

### What's this for

- Simple Bearer Token Extractor for JWT from HttpServletRequest
- Supports single static method to extract token

<br>

### How to Use

```java
@GetMapping
public String hello(final HttpServletRequest httpServletRequest) {

    // call resolve method of BearerExtractor
    String token = BearerExtractor.resolve(httpServletRequest);
    
    return "you've sent authorization header with valid jwt!";
}
```

<br>

### How to Import

- Step 1. Add the JitPack repository to your build file

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

- Step 2. Add the dependency

```gradle
dependencies {
        implementation 'com.github.HJ-Rich:bearer-extractor:0.1.1'
}
```

<br>
