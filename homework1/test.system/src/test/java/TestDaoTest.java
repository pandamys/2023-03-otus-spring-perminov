import ru.otus.test.system.dao.TestDao;
import ru.otus.test.system.domain.Test;
import ru.otus.test.system.domain.TestImpl;

public class TestDaoTest implements TestDao {

    @Override
    public Test get() {
        System.out.println("test");
        return new TestImpl();
    }
}
