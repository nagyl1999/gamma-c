package hu.bme.mit.gamma.xsts.codegeneration.c.platforms

/**
 * Implementation of the IPlatform interface for Unix platforms.
 */
class UnixPlatform implements IPlatform {
	
	/**
     * Returns the headers specific to unix platforms.
     * 
     * @return the headers as a string
     */
	override getHeaders() {
		return '''#include <sys/time.h>''';
	}
	
	/**
     * Returns the part of struct specific to unix platforms.
     * 
     * @return the struct as a string
     */
	override getStruct() {
		return '''struct timeval tval_before, tval_after, tval_result;''';
	}
	
	/**
     * Returns the timer initialization specific to unix platforms.
     * 
     * @return the initialization as a string
     */
	override getInitialization() {
		return '''gettimeofday(&statechart->tval_before, NULL);  // start measuring time during initialization''';
	}
	
	/**
     * Returns the timer specific to unix platforms.
     * 
     * @return the timer as a string
     */
	override getTimer() {
		return '''
			gettimeofday(&statechart->tval_after, NULL);
			timersub(&statechart->tval_after, &statechart->tval_before, &statechart->tval_result);
			unsigned int milliseconds = (unsigned int)statechart->tval_result.tv_sec * 1000 + (unsigned int)statechart->tval_result.tv_usec / 1000;
			gettimeofday(&statechart->tval_before, NULL);
		''';
	}
	
}