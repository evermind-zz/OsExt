## About this library
Kitkat aka Android 4.4 lacks fstatvfs() and statvfs() and more.
This projects makes fstatvfs() and statvfs() accessible from Java.

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

and in your `whatever/build.gradle` in dependencies include:
```
implementation "com.github.evermind-zz.osext:osext-stat:TAG"
```
replace `TAG` with the actual version you want to use.

### Usage
See [OsExtStatVfsTest.java](osext-stat/src/androidTest/java/com/github/evermindzz/osext/system/OsExtStatVfsTest.java)
