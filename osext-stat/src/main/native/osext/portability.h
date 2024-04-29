#ifndef PORTABILITY_H
#define PORTABILITY_H

#include <unistd.h>

#if defined(__GLIBC__) && !defined(__LP64__)


// 32 bit GLIBC hardcodes a "long int" as the return type for
// TEMP_FAILURE_RETRY so the return value here gets truncated for
// functions that return 64 bit types.
#undef TEMP_FAILURE_RETRY
#define TEMP_FAILURE_RETRY(exp) ({         \
    __typeof__(exp) _rc;                   \
    do {                                   \
        _rc = (exp);                       \
    } while (_rc == -1 && errno == EINTR); \
    _rc; })

#endif  // __GLIBC__ && !__LP64__

#endif  // PORTABILITY_H
