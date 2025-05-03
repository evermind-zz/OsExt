# JNI libraries
For some projects I required some special features that were not available
on some older Android versions or not available at all. They are all
using JNI to call native libc methods.

### osext-stat
[osext-stat](osext-stat/README.md)

### Releases
Releases are distributed via jitpack. Make sure you include jitpack in
your main `build.gradle`
```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
        // other sources

    }
}
```
