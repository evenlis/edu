import java.util.*;
import java.security.*;

public class LenientSecurityManager extends SecurityManager {
    private HashSet permissions;

    public LenientSecurityManager(){
	permissions = new HashSet();
    }

    public void checkPermission(Permission perm) {
	try{
	    super.checkPermission(perm);
	} catch(AccessControlException e){
	    if(!permissions.contains(perm)){
		permissions.add(perm);
	    }
	}
    }
}
