import java.util.*;
import java.security.*;

public class LenientSecurityManager extends SecurityManager {
    private Hashtable grantedPermissions;

    public LenientSecurityManager(){
	permissions = new HashSet();
    }

    public void checkPermission(Permission perm) {
	try{
	    super.checkPermission(perm);
	} catch(AccessControlException e){
	    if(grantedPermissions.get(perm) == null){
		grantedPermissions.put(perm,perm);
	    }
	}
    }
}
