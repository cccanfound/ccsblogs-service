package com.example.demo;

import com.cc.word.WordHelperServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author liyc
 * @date 2021/9/30 14:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WordHelperServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SyncFile {
/*    @Resource
    private FileDfsUtil fileDfsUtil ;
    @Autowired
    private ImgDao imgDao;*/
    @Test
    public void syncUploadFiles() throws InterruptedException {
        int n = 0;
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        /*String filepath = "D:\\work\\files";//D盘下的file文件夹的目录*/

        for (int j = 0; j < 100; j++) {


            final int i = j;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(i);
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());

                    }
                }
            });


        }
        fixedThreadPool.shutdown();
        while (!fixedThreadPool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("线程池中还有任务在处理");
        }
        /*while(true){
            if(fixedThreadPool.isTerminated()){
                fixedThreadPool.shutdown();
                break;
            }
            Thread.sleep(5000);
        }*/
    }
}
