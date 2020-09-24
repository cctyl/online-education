import com.atguigu.statistics.StatisApplication;
import com.atguigu.statistics.task.DailyCountTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={StatisApplication.class})
public class DailyTaskTest {

    @Autowired
    private DailyCountTask dailyCountTask;

    @Test
    public void test(){
        dailyCountTask.dailyCount();
    }

}
