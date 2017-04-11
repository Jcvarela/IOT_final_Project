import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by jcvar on 4/8/2017.
 */
public class Problem_1_OversizedPancakeFlipper {

    public static void main(String[] args) throws Exception{
        Path p = Paths.get("./file/input1.txt");
        Scanner in = new Scanner(p);

        System.out.println(in.next());

    }

}
