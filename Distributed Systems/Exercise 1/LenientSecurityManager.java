import java.util.*;
import java.security.*;

public class LenientSecurityManager extends SecurityManager {
    private Hashtable grantedPermissions;

    public LenientSecurityManager(){
	grantedPermissions = new Hashtable();
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
