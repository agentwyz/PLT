#include <stdio.h>

static NODE*
raise(SCANNER* s, const char* p) {
	s->err = strdup(p);
	return NULL;
} 

void fatal(const char* msg) {
	
}

NODE* invalid_token(SCANNER* S) {
	
}
