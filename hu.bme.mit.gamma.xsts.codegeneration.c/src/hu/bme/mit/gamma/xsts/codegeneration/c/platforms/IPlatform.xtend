package hu.bme.mit.gamma.xsts.codegeneration.c.platforms

/**
 * Interface representing a platform.
 */
interface IPlatform {
	/**
     * Returns the headers specific to the platform.
     * 
     * @return the headers as a string
     */
	public def String getHeaders();
	
	/**
     * Returns the part of struct specific to the platform.
     * 
     * @return the struct as a string
     */
	public def String getStruct();
	
	/**
     * Returns the timer initialization specific to the platform.
     * 
     * @return the initialization as a string
     */
	public def String getInitialization();
	
	/**
     * Returns the timer specific to the platform. All platforms
     * should use 'milliseconds' indicating the elapsed time.
     * 
     * @return the timer as a string
     */
	public def String getTimer();
	
}