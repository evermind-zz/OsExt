# osext-stat Android library
Kitkat aka Android 4.4 lacks fstatvfs() and statvfs() and more.
This projects makes fstatvfs() and statvfs() accessible from Java.

### Dependencies
Add the library to your `app/build.gradle`:
```gradle
dependencies {
    implementation 'com.github.evermind-zz.osext:osext-stat:TAG'
}
```
Replace `TAG` with the desired version (e.g., `1.0.0`).

### Usage
See [OsExtStatVfsTest.java](osext-stat/src/androidTest/java/com/github/evermindzz/osext/system/OsExtStatVfsTest.java)
