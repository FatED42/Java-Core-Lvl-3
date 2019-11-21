import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Method1and4Test {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {true, new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}},
                {true, new int[]{1, 2, 1, 4, 2, 3, 1, 1, 7}},
                {true, new int[]{1, 2, 4, 2, 2, 3, 2, 1, 7}},
                {true, new int[]{1, 4, 3, 2, 2, 3, 2, 1, 7}}
        });
    }

    Method1and4 method;
    private boolean a;
    private int[] b;

    public Method1and4Test(boolean a, int[] b) {
        this.a = a;
        this.b = b;
    }

    @Before
    public void init() {
        method = new Method1and4();
    }

    @Test
    public void test1() {
        Assert.assertEquals(a, method.Method1And4(b));
    }
}
