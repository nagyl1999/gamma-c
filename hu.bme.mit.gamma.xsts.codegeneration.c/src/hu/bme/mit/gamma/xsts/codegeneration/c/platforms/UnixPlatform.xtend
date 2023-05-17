package hu.bme.mit.gamma.xsts.codegeneration.c.platforms

class UnixPlatform implements IPlatform {
	
	override getHeaders() {
		return '''#include <sys/time.h>''';
	}
	
	override getStruct() {
		return '''struct timeval tval_before, tval_after, tval_result;''';
	}
	
	override getInitialization() {
		return '''gettimeofday(&statechart->tval_before, NULL);  // start measuring time during initialization''';
	}
	
	override getTimer() {
		return '''
			gettimeofday(&statechart->tval_after, NULL);
			timersub(&statechart->tval_after, &statechart->tval_before, &statechart->tval_result);
			int milliseconds = (int)statechart->tval_result.tv_sec * 1000 + (int)statechart->tval_result.tv_usec / 1000;
			gettimeofday(&statechart->tval_before, NULL);
		''';
	}
	
}