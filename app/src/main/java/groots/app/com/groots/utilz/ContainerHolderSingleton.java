package groots.app.com.groots.utilz;

import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Created by aakash on 19/12/16.
 */

public class ContainerHolderSingleton {
    private static ContainerHolder containerHolder;


    private ContainerHolderSingleton() {
    }

    public static ContainerHolder getContainerHolder() {
        return containerHolder;
    }

    public static void setContainerHolder(ContainerHolder c) {
        containerHolder = c;
    }
}
