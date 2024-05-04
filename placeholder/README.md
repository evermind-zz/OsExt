This empty placeholder project is just used to make jitpack create sub modules.

Otherwise we get the ATM lonely modul osext-stat served as main project with
a path like that:
https://jitpack.io/com/github/evermind-zz/osext/TAG/osext-TAG.aar
-> implementation "com.github.evermind-zz:osext:TAG"


This empty placeholder project changes this:
https://jitpack.io/com/github/evermind-zz/osext/osext-stat/TAG/osext-stat-TAG.aar
-> implementation "com.github.evermind-zz.osext:osext-stat:TAG"

I want to keep it like that in case there will be more modules.
