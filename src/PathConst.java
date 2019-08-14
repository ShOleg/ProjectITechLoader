package iTecLoader;


import java.io.File;

/**
 * <p>Title: ТехКорпорация</p>
 * <p/>
 * <p>Description: </p>
 * <p/>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p/>
 * <p>Company: Технополис</p>
 *
 * @author
 * @version 1.0
 */
public class PathConst {

    public static File userDir;

    static {
        PathConst p = new PathConst();
    }

    private PathConst() {
        super();

        userDir = new File(System.getProperty("user.dir"));

        System.setProperty("user.dir", userDir.getAbsolutePath());

    }
}
