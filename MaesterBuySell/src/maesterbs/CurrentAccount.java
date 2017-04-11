/**
 * Needed a global variable-like class to store current account name. For logging
 */

package maesterbs;

public class CurrentAccount {

	private static String accountName = "none";
	
	public void setCurrentName(String userName)
	{
		accountName = userName;
	}
	
	public String getCurrentName()
	{
		return accountName;
	}
	
}
